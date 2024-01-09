package pt.pa.view.routemanage;

import com.brunomnsilva.smartgraph.graphview.SmartGraphEdge;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.pa.commands.Command;
import pt.pa.commands.CommandAdd;
import pt.pa.commands.CommandRemove;
import pt.pa.commands.CommandsUsed;
import pt.pa.controller.RouteManagerController;
import pt.pa.graph.Edge;
import pt.pa.graph.Vertex;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.AutoCompleteText;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.observer.Observable;
import pt.pa.view.mainmenu.GraphDisplay;
import pt.pa.view.mainmenu.SidePanelStopStats;

/**
 * This class is used to define the view of the network route manager.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class NetworkRouteManagerView extends BorderPane implements NetworkRouteManagerUI {
    private GraphDisplay graphDisplay;
    private ImportToGraph model;
    private SmartGraphPanel<Stop, Route> graphPanel;
    private CommandsUsed commandsUsed;
    private SidePanelStopStats sidePanelStopStats;
    private Button btAddRoute;
    private Button btRemoveRoute;
    private Button btUndo;
    private Button btBackMenu;
    private TextField txtRouteDuration;
    private TextField txtRouteDistance;
    private AutoCompleteText txtInitialStopName;
    private AutoCompleteText txtEndStopName;
    private Label lblError;

    /**
     * Creates a NetworkRouteManagerView instance.
     *
     * @param graphDisplay GraphDisplay the graph display
     * @param model ImportToGraph the model
     * @param graphPanel SmartGraphPanel the panel
     */
    public NetworkRouteManagerView(GraphDisplay graphDisplay, ImportToGraph model, SmartGraphPanel<Stop, Route> graphPanel) {
        this.graphPanel = graphPanel;
        this.graphDisplay = graphDisplay;
        this.model = model;
        commandsUsed = new CommandsUsed();
        createLayout();
    }

    @Override
    public String getRouteDistanceAdd() {
        return txtRouteDistance.getText();
    }

    @Override
    public String getInitialStopNameAdd() {
        return txtInitialStopName.getText();
    }

    @Override
    public String getEndStopNameAdd() {
        return txtEndStopName.getText();
    }

    /**
     * Returns the route duration on the text field.
     *
     * @return String the route duration
     */
    public String getRouteDurationAdd() {
        return txtRouteDuration.getText();
    }

    /**
     * Sets the actions
     *
     * @param controller RouteManagerController the controller
     */
    @Override
    public void setTriggers(RouteManagerController controller) {

        btAddRoute.setOnAction(event -> {
            executeCommand(new CommandAdd(controller, model));
        });

        btRemoveRoute.setOnAction(event -> {
            executeCommand(new CommandRemove(controller, model, getInitialStopNameAdd(), getEndStopNameAdd()));
        });

        btUndo.setOnAction(event -> {
            undo();
        });

        btBackMenu.setOnAction(event -> {
            clearGraphStyle();
            graphDisplay.setGraphView(graphPanel);
            ((Stage) getScene().getWindow()).close();
            ((Stage) graphDisplay.getScene().getWindow()).show();
            graphDisplay.updateBackground();
        });
    }

    /**
     * Auxiliary method to execute a command and save it on a local variable.
     *
     * @param command Command the command
     */
    private void executeCommand(Command command) {
        command.execute();
        commandsUsed.push(command);
    }

    /**
     * Auxiliary method to undo a command, getting it from a saved stack.
     */
    private void undo() {
        if (commandsUsed.isEmpty()) return;

        Command command = commandsUsed.pop();
        if (command != null) {
            command.undo();
        }
    }

    @Override
    public void displayError(String msg) {
        lblError.setText(msg);
    }

    @Override
    public void clearError() {
        lblError.setText("");
    }

    @Override
    public void clearControls() {
        txtEndStopName.clear();
        txtRouteDistance.clear();
        txtInitialStopName.clear();
        txtRouteDuration.clear();
    }

    @Override
    public void clearGraphStyle() {
        for (Vertex<Stop> v : model.getNetwork().vertices()) {
            graphPanel.getStylableVertex(v).setStyle("");
        }
        for (Edge<Route, Stop> e : model.getNetwork().edges()) {
            graphPanel.getStylableEdge(e).setStyle("");
        }
    }

    /**
     * Auxiliary method to create the layout of the manu on the route manager.
     */
    private void createLayout() {
        lblError = new Label("");
        NetworkViewCreator.getInstance().createLayout(this, graphPanel, createSidePanel(), lblError);
        /* bind double click on edge */
        graphPanel.setEdgeDoubleClickAction((SmartGraphEdge<Route, Stop> graphEdge) -> {

            txtInitialStopName.setText(graphEdge.getUnderlyingEdge().vertices()[0].element().toString());
            txtEndStopName.setText(graphEdge.getUnderlyingEdge().vertices()[1].element().toString());
        });

        /* bind double click on vertex */
        graphPanel.setVertexDoubleClickAction((SmartGraphVertex<Stop> graphVertex) -> {

            if (txtInitialStopName.getText().isEmpty()) {
                txtInitialStopName.setText(graphVertex.getUnderlyingVertex().element().getStopName());
            } else {
                txtEndStopName.setText(graphVertex.getUnderlyingVertex().element().getStopName());
            }
        });
    }

    /**
     * Auxiliary method to create the side panel.
     *
     * @return VBox the side panel
     */
    private VBox createSidePanel() {
        sidePanelStopStats = new SidePanelStopStats(model);

        VBox routePane = new VBox();

        routePane.setAlignment(Pos.CENTER);
        routePane.setPadding(new Insets(20)); // set top, right, bottom, left

        Label lblRouteManager = new Label("Routes Management");
        lblRouteManager.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        lblRouteManager.setAlignment(Pos.CENTER);

        HBox undoRedoBtns = new HBox();
        btUndo = new Button("Undo");
        btUndo.setPrefSize(120, 24);
        undoRedoBtns.setAlignment(Pos.CENTER);

        setFields();

        routePane.setSpacing(10);
        routePane.getChildren().addAll(undoRedoBtns,
                txtInitialStopName,
                txtEndStopName,
                txtRouteDistance,
                txtRouteDuration,
                btAddRoute,
                btRemoveRoute,
                btUndo,
                btBackMenu);

        VBox panel = new VBox(sidePanelStopStats,
                new Separator(),
                lblRouteManager,
                routePane);
        panel.setPadding(new Insets(20, 10, 10, 10));
        panel.setSpacing(10);
        panel.setAlignment(Pos.TOP_CENTER);
        return panel;
    }

    /**
     * Method that sets all fields from createSidePanel()
     *
     */
    public void setFields(){
        txtRouteDuration = new TextField("");
        txtRouteDuration.setPromptText("Route duration");
        txtRouteDuration.setStyle("-fx-prompt-text-fill: green");

        txtRouteDistance = new TextField("");
        txtRouteDistance.setPromptText("Route distance");
        txtRouteDistance.setStyle("-fx-prompt-text-fill: green");

        txtInitialStopName = new AutoCompleteText();
        txtInitialStopName.getEntries().addAll(model.getStopNames());
        txtInitialStopName.setPromptText("Initial Stop name");

        txtEndStopName = new AutoCompleteText();
        txtEndStopName.getEntries().addAll(model.getStopNames());
        txtEndStopName.setPromptText("End Stop name");

        btAddRoute = new Button("Add Route");
        btAddRoute.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        btAddRoute.setPrefSize(120, 30);
        btRemoveRoute = new Button("Remove Route");
        btRemoveRoute.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        btRemoveRoute.setPrefSize(120, 30);
        btBackMenu = new Button("Back to Main Menu");
    }


    @Override
    public void update(Observable subject, Object arg) {
        if (subject == model) {
            /* update graph panel */
            graphPanel.update();
            sidePanelStopStats.getLabelNumberStops().setText(Integer.toString(model.size()));
            sidePanelStopStats.getLabelNumRoutes().setText(Integer.toString(model.getNetwork().numEdges()));
        }
    }
}
