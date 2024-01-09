package pt.pa.view.mainmenu;

import com.brunomnsilva.smartgraph.containers.ContentZoomPane;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.controller.CalculateTravelController;
import pt.pa.controller.RouteManagerController;
import pt.pa.graph.Graph;
import pt.pa.graphcomponents.GraphComponents;
import pt.pa.view.travelmanage.NetworkCalculateTravelView;
import pt.pa.view.routemanage.NetworkRouteManagerView;
import pt.pa.commands.graphviewhandler.BackgroundType;
import pt.pa.commands.graphviewhandler.GraphViewHandler;
import pt.pa.io.imports.InvalidDatasetException;
import pt.pa.model.Route;
import pt.pa.model.Stop;

/**
 * This class is used to define the display of the graph.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphDisplay extends BorderPane {
    private SmartGraphPanel<Stop, Route> graphView;
    private ContentZoomPane centerContent;
    private final GraphComponents graphComponents;
    private BackgroundType currentBackground;
    private Background background;

    /**
     * Creates a GraphDisplay instance.
     *
     * @param dataset String the dataset to be used
     */
    public GraphDisplay(String dataset) {
        graphComponents = new GraphComponents(dataset);
    }

    /**
     * Auxiliary method to initiate the graph view.
     *
     * @throws InvalidDatasetException if the dataset is invalid
     */
    private void initGraphView() throws InvalidDatasetException {
        graphComponents.initComponents();
        Graph<Stop, Route> graph = graphComponents.getGraph();
        graphView = new SmartGraphPanel<>(graph);
    }

    /**
     * Initiates the display of the content.
     */
    public void initContentDisplay() {
        try {
            initGraphView();
            centerContent = new ContentZoomPane(graphView);
            setCenter(centerContent);
            setTop(new TopMenuBar(this));
            setRight(new GraphDisplayRightPanel(this));
            changeBackground(BackgroundType.DEFAULT);
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used to change the background.
     *
     * @param backgroundType BackgroundType the type of the background
     */
    public void changeBackground(BackgroundType backgroundType) {
        currentBackground = backgroundType;
        background = GraphViewHandler.changeBackground(graphComponents, currentBackground);
        updateBackground();
    }

    /**
     * Used to update the background.
     */
    public void updateBackground() {
        graphView.setBackground(new Background(new BackgroundFill(Paint.valueOf("WHITE"), null, null)));
        GraphViewHandler.changeVertexLabel(this);
        graphView.setBackground(background);
    }

    /**
     * Used to set the positions of vertex.
     */
    public void setPositionsOfVertex() {
        try {
            GraphViewHandler.loadDefaultStopsPositionToGraphView(graphView, graphComponents);
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the graph view.
     *
     * @return SmartGraphPanel the graph view.
     */
    public SmartGraphPanel<Stop, Route> getGraphView() {
        return graphView;
    }

    /**
     * Used to set the graph view.
     *
     * @param graphView SmartGraphPanel the graph view
     */
    public void setGraphView(SmartGraphPanel<Stop, Route> graphView) {
        this.graphView = graphView;
        updateContent();
    }

    /**
     * Auxiliary method to update the content.
     */
    private void updateContent() {
        centerContent = new ContentZoomPane(graphView);
        setCenter(centerContent);
        setTop(new TopMenuBar(this));
        setRight(new GraphDisplayRightPanel(this));
    }

    /**
     * Returns the components of the graph.
     *
     * @return GraphComponents the components of the graph
     */
    public GraphComponents getGraphComponents() {
        return graphComponents;
    }

    /**
     * Used to initiate the graph view.
     */
    public void init() {
        graphView.init();
    }

    /**
     * Used to change to the travel screen display.
     */
    public void changeToTravelScreen() {
        NetworkCalculateTravelView networkCalculateTravelView = new NetworkCalculateTravelView(this, graphComponents.getModel(), graphView);
        CalculateTravelController controller = new CalculateTravelController(graphComponents.getModel(), networkCalculateTravelView);
        Scene scene = new Scene(networkCalculateTravelView, 1350, 825);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Bus Network Travel Management");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        getScene().getWindow().hide();
        updateBackground();
    }

    /**
     * Used to change to the manager screen display.
     */
    public void changeToManagerScreen() {
        NetworkRouteManagerView routeManagerView = new NetworkRouteManagerView(this, graphComponents.getModel(), graphView);
        RouteManagerController controller = new RouteManagerController(routeManagerView, graphComponents.getModel());
        Scene scene = new Scene(routeManagerView, 1350, 825);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Bus Network Management");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        getScene().getWindow().hide();
        updateBackground();
    }

    /**
     * Returns the current background.
     *
     * @return BackgroundType the current background
     */
    public BackgroundType getCurrentBackground() {
        return currentBackground;
    }

    /**
     * Used to update the graph view.
     */
    public void update() {
        graphView.update();
    }
}
