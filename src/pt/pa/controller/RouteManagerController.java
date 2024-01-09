package pt.pa.controller;

import pt.pa.graph.Edge;
import pt.pa.view.routemanage.RouteInvalidOperation;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.Stop;
import pt.pa.model.Route;
import pt.pa.view.routemanage.NetworkRouteManagerView;

/**
 * This class allows the user to interact with the 'Route Manager' window.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class RouteManagerController {
    private NetworkRouteManagerView view;
    private ImportToGraph model;

    /**
     * Creates a RouteManagerController instance.
     *
     * @param view  NetworkCalculateTravelView view
     * @param model ImportToGraph model
     */
    public RouteManagerController(NetworkRouteManagerView view, ImportToGraph model) {
        this.view = view;
        this.model = model;

        this.view.setTriggers(this);

        this.model.addObservers(this.view);
    }

    /**
     * Executes the addition of a route.
     *
     * @return Edge the added edge
     */
    public Edge<Route, Stop> doAddRoute() {
        try {
            view.clearGraphStyle();

            String routeDistance = view.getRouteDistanceAdd();
            String initialStopName = view.getInitialStopNameAdd();
            String endStopName = view.getEndStopNameAdd();
            String routeDuration = view.getRouteDurationAdd();

            if(routeDistance == null) {
                view.displayError("You must provide a distance.");
                return null;
            }

            if(initialStopName.trim().isEmpty()) {
                view.displayError("You must provide a name for the initial stop.");
                return null;
            }

            if(endStopName.trim().isEmpty()) {
                view.displayError("You must provide a name for the end stop.");
                return null;
            }

            Edge<Route, Stop> e = model.addRoute(initialStopName, endStopName, Integer.parseInt(routeDistance), Integer.parseInt(routeDuration));

            view.clearError();
            view.clearControls();

            return e;

        } catch (RouteInvalidOperation e) {
            this.view.displayError( e.getMessage() );
        } catch (NumberFormatException e2) {
            view.displayError("The distance must be an integer number.");
        }
        return null;
    }

    /**
     * Executes the removal of an edge
     *
     * @return Edge the removed edge
     */
    public Edge<Route, Stop> doRemoveRoute() {
        try {
            view.clearGraphStyle();

            String initialStop = view.getInitialStopNameAdd();
            String endStop = view.getEndStopNameAdd();

            if(initialStop.trim().isEmpty()) {
                view.displayError("You must provide a name for the initial stop.");
                return null;
            }

            if(endStop.trim().isEmpty()) {
                view.displayError("You must provide a name for the end stop");
                return null;
            }

            Edge<Route, Stop> e = model.removeRoute(initialStop, endStop);

            view.clearError();
            view.clearControls();

            return e;

        } catch (RouteInvalidOperation e) {
            this.view.displayError( e.getMessage() );
        }
        return null;
    }
}
