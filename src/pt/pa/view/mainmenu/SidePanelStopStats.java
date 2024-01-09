package pt.pa.view.mainmenu;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pt.pa.io.imports.ImportToGraph;

/**
 * This class is used to define the display of the side panel stop statistics.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class SidePanelStopStats extends VBox {
    private ImportToGraph model;
    private GridPane stopStatsPane;
    private Label lblStops;
    private GridPane routeStatsPane;
    private Label lblRoutes;
    private Circle routesCircle;
    private Label lblNumberRoutes;
    private Label lblNumberStops;
    private Circle stopCircle;

    /**
     * Creates a SidePanelStopStats instance.
     *
     * @param model ImportToGraph the model
     */
    public SidePanelStopStats(ImportToGraph model) {
        super();
        this.model = model;
        stopStatsPane = new GridPane();
        routeStatsPane = new GridPane();
        createStopPanel();
        createRoutePanel();
        this.getChildren().addAll(stopStatsPane, routeStatsPane);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(5);
        this.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * Returns a label with the existing number of stops.
     *
     * @return Label with the number of stops
     */
    public Label getLabelNumberStops() {
        return lblNumberStops;
    }

    /**
     * Returns a label with the existing number of routes.
     *
     * @return Label with the number of routes
     */
    public Label getLabelNumRoutes() {
        return lblNumberRoutes;
    }



    /**
     * Auxiliary method to create a panel used for the stops' statistics.
     */
    private void createStopPanel() {
        initializePane(stopStatsPane);

        lblStops = new Label("Stops");
        lblStops.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        lblNumberStops = new Label(Integer.toString(model.size()));
        lblNumberStops.setStyle("-fx-text-fill: gray; -fx-font-size: 14;");
        stopCircle = new Circle(20);
        stopCircle.setFill(Paint.valueOf("#ADD8E6"));

        stopStatsPane.add(stopCircle, 0, 0, 1, 1);
        stopStatsPane.add(lblStops, 1, 0);
        stopStatsPane.add(lblNumberStops, 1, 1);
    }

    /**
     * Auxiliary method to create a panel used for the routes' statistics.
     */
    private void createRoutePanel() {
        initializePane(routeStatsPane);

        lblRoutes = new Label("Routes");
        lblRoutes.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        lblNumberRoutes = new Label(Integer.toString(model.getNetwork().numEdges()));
        lblNumberRoutes.setStyle("-fx-text-fill: gray; -fx-font-size: 14;");
        routesCircle = new Circle(20);
        routesCircle.setFill(Paint.valueOf("#CBC3E3"));

        routeStatsPane.add(routesCircle, 0, 0, 1, 1);
        routeStatsPane.add(lblRoutes, 1, 0);
        routeStatsPane.add(lblNumberRoutes, 1, 1);
    }

    /**
     * Auxiliary method to initialize a grid pane.
     *
     * @param pane GridPane to be initialized
     */
    private void initializePane(GridPane pane) {
        pane.setHgap(10);
        pane.setVgap(0);
        pane.setPadding(new Insets(10, 10, 0, 5)); // set top, right, bottom, left
    }

}
