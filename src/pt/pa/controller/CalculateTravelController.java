package pt.pa.controller;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.dijkstra.DijkstraResult;
import pt.pa.graph.Vertex;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.strategy.CalculateDistanceStrategy;
import pt.pa.strategy.Strategy;
import pt.pa.view.travelmanage.NetworkCalculateTravelView;
import pt.pa.view.routemanage.RouteInvalidOperation;
import pt.pa.view.routemanage.StopInvalidOperation;
import pt.pa.view.travelmanage.SaveToPDFPanel;

import java.util.List;

/**
 * This class allows the user to interact with the 'Calculate Travel' window.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class CalculateTravelController {
    private ImportToGraph model;
    private NetworkCalculateTravelView view;
    private DijkstraResult<Stop, Route> dijkstraResult;
    private Strategy calculateStrategy;

    /**
     * Creates a CalculateTravelController instance.
     *
     * @param model ImportToGraph model
     * @param view  NetworkCalculateTravelView view
     */
    public CalculateTravelController(ImportToGraph model, NetworkCalculateTravelView view) {
        this.model = model;
        this.view = view;
        this.calculateStrategy = new CalculateDistanceStrategy();

        /* binds actions to user interface*/
        this.view.setTriggers(this);

        /* bind observer */
        this.model.addObservers(this.view);
    }

    /**
     * Executes the shortest path between two stops.
     */
    public void doShortestPath() {
        try {
            view.clearGraphStyle();

            String initStopName = view.getShortestInitialStopNameAdd();
            String endStopName = view.getShortestEndStopNameAdd();

            if (initStopName.trim().isEmpty()) {
                view.displayError("You must choose a name for the initial Stop.");
                return;
            }

            if (endStopName.trim().isEmpty()) {
                view.displayError("You must choose a name for the destination Stop.");
                return;
            }

            dijkstraResult = model.shortestPathBetweenTwoStops(initStopName, endStopName);


            if (dijkstraResult.getPath() == null) {
                view.displayError("The selected vertices have different components.");
                return;
            }

            Vertex<Stop> oldVertex = null;
            for (Vertex<Stop> v : dijkstraResult.getPath()) {
                view.getGraphPanel().getStylableVertex(v).setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
                if (oldVertex != null) {
                    view.getGraphPanel().getStylableEdge(model.findRoute(oldVertex.element().getStopName(), v.element().getStopName())).setStyle("-fx-stroke: red; -fx-stroke-width: 3;");
                }
                oldVertex = v;
            }


            view.clearError();
            view.clearControls();
            view.getLblShortestPathCost().setText(calculateStrategy.compute(dijkstraResult));
        } catch (RouteInvalidOperation | StopInvalidOperation e) {
            this.view.displayError(e.getMessage());
        }
    }


    /**
     * Executes the furthest path between two stops.
     */
    public void doMostDistantStopsPath() {
        try {
            view.clearGraphStyle();
            dijkstraResult = DijkstraResult.mostDistantVerticesInGraph(model.getNetwork());

            if (dijkstraResult.getPath() == null) {
                view.displayError("There is no graph loaded.");
                return;
            }

            Vertex<Stop> vOld = null;
            for (Vertex<Stop> v : dijkstraResult.getPath()) {
                view.getGraphPanel().getStylableVertex(v).setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
                if (vOld != null) {
                    view.getGraphPanel().getStylableEdge(model.findRoute(vOld.element().getStopName(), v.element().getStopName())).setStyle("-fx-stroke: red; -fx-stroke-width: 3;");
                }
                vOld = v;
            }

            view.clearError();
            view.clearControls();

        } catch (RouteInvalidOperation e) {
            this.view.displayError(e.getMessage());
        }
    }


    /**
     * Searches for stops within N routes.
     */
    public void doStopsWithinNRoutes() {
        view.clearGraphStyle();

        String initStopName = view.getNDistanceInitialStopName();
        String maxRoutes = view.getNDistanceStopsMaxNumber();

        if (initStopName.trim().isEmpty()) {
            view.displayError("You must choose a name to the initial stop.");
            return;
        }
        if (maxRoutes == null) {
            view.displayError("You must have a maximum distance for it.");
            return;
        }

        List<Vertex<Stop>> list = model.stopsWithinNRoutes(model.findStop(initStopName), Integer.parseInt(maxRoutes));

        for (Vertex<Stop> v : list) {
            view.getGraphPanel().getStylableVertex(v).setStyle("-fx-stroke: black; -fx-stroke-width: 3;");
        }

        view.clearError();
        view.clearControls();
        view.getLblNDistanceStopsCount().setText(Integer.toString(list.size()));

    }

    /**
     * Saves the current path to a PDF.
     */
    public void doSaveToPDF() {
        if (dijkstraResult == null) {
            view.displayError("No route found!");
            return;
        }
        Stage stage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(new SaveToPDFPanel(dijkstraResult), 350, 200);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Save to PDF");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(view.getScene().getWindow());
        stage.showAndWait();
    }

    /**
     * Changes the strategy for the dijkstra result.
     *
     * @param strategy Strategy to change to
     */
    public void changeStrategy(Strategy strategy) {
        this.calculateStrategy = strategy;
        if (dijkstraResult != null)
            view.getLblShortestPathCost().setText(calculateStrategy.compute(dijkstraResult));
    }
}
