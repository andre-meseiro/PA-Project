package pt.pa.view.travelmanage;

import com.brunomnsilva.smartgraph.graphview.SmartGraphEdge;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pt.pa.controller.CalculateTravelController;
import pt.pa.graph.Edge;
import pt.pa.graph.Vertex;
import pt.pa.strategy.CalculateDistanceStrategy;
import pt.pa.strategy.CalculateDurationStrategy;
import pt.pa.view.mainmenu.GraphDisplay;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.AutoCompleteText;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.observer.Observable;
import pt.pa.observer.Observer;
import pt.pa.view.routemanage.NetworkViewCreator;

/**
 * This class is used to define the view for the network calculate travel.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class NetworkCalculateTravelView extends BorderPane implements CalculateTravelUI, Observer {
    private GraphDisplay mainScreen;
    private ImportToGraph model;
    private SmartGraphPanel<Stop, Route> graphPanel;
    private Label lblShortestPath;
    private Label lblStopsNDistance;
    private Label lblError;
    private Label lblShortestPathCost;
    private Label lblNDistanceStopsCount;
    private Button btBackMenu;
    private Button btShortestCalculateCost;
    private Button btNDistanceCalculate;
    private Button btFurthestStops;
    private AutoCompleteText txtShortestInitialStopName;
    private AutoCompleteText txtShortestEndStopName;
    private AutoCompleteText txtNDistanceInitialStopName;
    private TextField txtNDistanceStopsMaxNumber;
    private CheckBox checkBoxDistance;
    private CheckBox checkBoxDuration;
    private Button btSaveToPDF;

    /**
     * Creates a NetworkCalculateTravelView instance.
     *
     * @param mainScreen GraphDisplay the display
     * @param model      ImportToGraph the model
     * @param graphPanel SmartGraphPanel the panel
     */
    public NetworkCalculateTravelView(GraphDisplay mainScreen, ImportToGraph model, SmartGraphPanel<Stop, Route> graphPanel) {
        this.graphPanel = graphPanel;
        this.mainScreen = mainScreen;
        this.model = model;

        createLayout();
    }

    @Override
    public String getShortestInitialStopNameAdd() {
        return txtShortestInitialStopName.getText();
    }

    @Override
    public String getShortestEndStopNameAdd() {
        return txtShortestEndStopName.getText();
    }

    @Override
    public String getNDistanceInitialStopName() {
        return txtNDistanceInitialStopName.getText();
    }

    @Override
    public String getNDistanceStopsMaxNumber() {
        return txtNDistanceStopsMaxNumber.getText();
    }

    /**
     * Returns the SmarthGraphPanel.
     *
     * @return SmartGraphPanel the panel
     */
    public SmartGraphPanel<Stop, Route> getGraphPanel() {
        return graphPanel;
    }

    /**
     * Returns the label with the cost of the shortest path.
     *
     * @return Label the label
     */
    public Label getLblShortestPathCost() {
        return lblShortestPathCost;
    }

    /**
     * Returns the label with N distance stops count.
     *
     * @return Label the label
     */
    public Label getLblNDistanceStopsCount() {
        return lblNDistanceStopsCount;
    }

    /**
     * Auxiliary method to create the layout of the menu for the path calculations.
     */
    private void createLayout() {
        lblError = new Label("");
        NetworkViewCreator.getInstance().createLayout(this, graphPanel, createSidePanel(), lblError);

        /* bind double click on vertex */
        graphPanel.setVertexDoubleClickAction((SmartGraphVertex<Stop> graphVertex) -> {

            if (txtShortestInitialStopName.getText().isEmpty()) {
                txtShortestInitialStopName.setText(graphVertex.getUnderlyingVertex().element().getStopName());
            } else {
                txtShortestEndStopName.setText(graphVertex.getUnderlyingVertex().element().getStopName());
            }
        });

    }

    /**
     * Auxiliary method to create the side panel of the layout.
     *
     * @return VBox the side panel
     */
    private VBox createSidePanel() {
        btFurthestStops = new Button("Show Furthest Stops");
        btFurthestStops.setPrefHeight(35);
        HBox furthesStops = new HBox(btFurthestStops);
        furthesStops.setAlignment(Pos.CENTER);
        furthesStops.setPadding(new Insets(0, 0, 20, 0));


        setFields();

        HBox checkBoxes = createStrategySelect();

        Rectangle rectShortestPathCost = new Rectangle(15, 15);
        Label lblShortPath = new Label("Cost:");
        lblShortestPathCost = new Label("0");
        HBox shortestPathCost = createUserInterfaceBox(rectShortestPathCost, lblShortPath, lblShortestPathCost);

        Rectangle rectNDistanceStopsMax = new Rectangle(15, 15);
        Label lblNDistanceStops = new Label("Number Of Stops:");
        lblNDistanceStopsCount = new Label("0");
        HBox NDistanceStopsMaxNumberCost = createUserInterfaceBox(rectNDistanceStopsMax, lblNDistanceStops, lblNDistanceStopsCount);

        btBackMenu = new Button("Back to Main Menu");
        btSaveToPDF = new Button("Save Route To PDF");
        VBox backAndSaveButtons = new VBox(btSaveToPDF, btBackMenu);
        backAndSaveButtons.setAlignment(Pos.CENTER);
        backAndSaveButtons.setSpacing(20);

        VBox sidePanel = new VBox(furthesStops, new Separator(), lblShortestPath, txtShortestInitialStopName,
                txtShortestEndStopName, btShortestCalculateCost, checkBoxes, shortestPathCost, new Separator(),
                lblStopsNDistance, txtNDistanceInitialStopName, txtNDistanceStopsMaxNumber, btNDistanceCalculate,
                NDistanceStopsMaxNumberCost, backAndSaveButtons);

        sidePanel.setPadding(new Insets(10, 10, 10, 10));
        sidePanel.setSpacing(5);
        sidePanel.setAlignment(Pos.CENTER);

        return sidePanel;
    }

    /**
     * Method that sets all fields from createSidePanel()
     *
     */
    private void setFields(){
        lblShortestPath = new Label("Shortest Path");
        lblShortestPath.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        lblShortestPath.setAlignment(Pos.CENTER_LEFT);
        lblShortestPath.setPadding(new Insets(30, 0, 0, 0));

        txtShortestInitialStopName = new AutoCompleteText();
        txtShortestInitialStopName.getEntries().addAll(model.getStopNames());
        txtShortestInitialStopName.setPromptText("Initial Stop");

        txtShortestEndStopName = new AutoCompleteText();
        txtShortestEndStopName.getEntries().addAll(model.getStopNames());
        txtShortestEndStopName.setPromptText("End Stop name");

        btShortestCalculateCost = new Button("Calculate");
        btShortestCalculateCost.setPrefSize(100, 25);

        lblStopsNDistance = new Label("Stops within N distance");
        lblStopsNDistance.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        lblStopsNDistance.setAlignment(Pos.CENTER_LEFT);

        txtNDistanceInitialStopName = new AutoCompleteText();
        txtNDistanceInitialStopName.getEntries().addAll(model.getStopNames());
        txtNDistanceInitialStopName.setPromptText("Initial Stop");

        txtNDistanceStopsMaxNumber = new TextField("");
        txtNDistanceStopsMaxNumber.setPromptText("Maximum Number Of Stops");

        btNDistanceCalculate = new Button("Calculate");
        btNDistanceCalculate.setPrefSize(100, 25);
    }



    /**
     * Auxiliary method to create the strategy select.
     *
     * @return HBox with the selection
     */
    private HBox createStrategySelect() {
        checkBoxDistance = new CheckBox("Distance");
        checkBoxDistance.setAlignment(Pos.CENTER_LEFT);
        checkBoxDistance.setSelected(true);
        checkBoxDuration = new CheckBox("Duration");
        checkBoxDuration.setAlignment(Pos.CENTER_RIGHT);
        HBox checkBoxes = new HBox(checkBoxDistance, checkBoxDuration);
        checkBoxes.setAlignment(Pos.CENTER);
        checkBoxes.setSpacing(20);
        return checkBoxes;
    }

    /**
     * Auxiliary method to create the user interface HBox.
     *
     * @param rectangle   Rectangle the rectangle
     * @param firstLabel  Label the first label
     * @param secondLabel Label the second label
     * @return HBox the box
     */
    private HBox createUserInterfaceBox(Rectangle rectangle, Label firstLabel, Label secondLabel) {
        rectangle.setFill(Paint.valueOf("#F9C802"));
        setStyles(firstLabel);
        setStyles(secondLabel);
        HBox hbox = new HBox(rectangle, firstLabel, secondLabel);
        hbox.setPadding(new Insets(5, 0, 20, 0));
        return hbox;
    }

    /**
     * Auxiliary method to set the styles of a label.
     *
     * @param label Label the label
     */
    private void setStyles(Label label) {
        label.setPadding(new Insets(-5, 0, 0, 5));
        label.setStyle("-fx-font-size: 16;");
    }

    /**
     * Used to put each vertex of the graph and update the interface.
     */
    public void initGraphDisplay() {
        //Meter cada vertice nas suas coordenadas respectivas na janela
        for (Vertex<Stop> v : model.getNetwork().vertices()) {
            graphPanel.setVertexPosition(v, v.element().getLatitude(), v.element().getLongitude());
        }

        update(model, null);
    }

    @Override
    public void update(Observable subject, Object arg) {
        if (subject == model) {
            /* update graph panel */
            graphPanel.update();
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
        txtShortestInitialStopName.clear();
        txtShortestEndStopName.clear();
        txtNDistanceInitialStopName.clear();
        txtNDistanceStopsMaxNumber.clear();
        lblNDistanceStopsCount.setText("0");
        lblShortestPathCost.setText("0");
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

    @Override
    public void setTriggers(CalculateTravelController controller) {
        btFurthestStops.setOnAction(event -> {
            controller.doMostDistantStopsPath();
        });

        btShortestCalculateCost.setOnAction(event -> {
            controller.doShortestPath();
        });

        btNDistanceCalculate.setOnAction(event -> {
            controller.doStopsWithinNRoutes();
        });

        btBackMenu.setOnAction(event -> {
            clearGraphStyle();
            mainScreen.setGraphView(graphPanel);
            ((Stage) getScene().getWindow()).close();
            ((Stage) mainScreen.getScene().getWindow()).show();
            mainScreen.updateBackground();
        });

        checkBoxDistance.setOnAction(e -> {
            checkBoxDuration.setSelected(false);
            controller.changeStrategy(new CalculateDistanceStrategy());
            if (!checkBoxDuration.isSelected()) {
                checkBoxDistance.setSelected(true);
            }
        });

        checkBoxDuration.setOnAction(e -> {
            checkBoxDistance.setSelected(false);
            controller.changeStrategy(new CalculateDurationStrategy());
            if (!checkBoxDistance.isSelected()) {
                checkBoxDuration.setSelected(true);
            }
        });

        btSaveToPDF.setOnAction(e -> controller.doSaveToPDF());
    }

}
