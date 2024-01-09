package pt.pa.view.routemanage;

import pt.pa.controller.RouteManagerController;
import pt.pa.observer.*;

/**
 * This class is used to define the interface UI for the network route manager.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public interface NetworkRouteManagerUI extends Observer {
    /**
     * Returns the duration of the input route.
     *
     * @return String the duration
     */
    String getRouteDurationAdd();

    /**
     * Returns the distance of the input route.
     * @return String the distance
     */
    String getRouteDistanceAdd();


    /**
     * Returns the input initial stop name.
     * @return String the name
     */
    String getInitialStopNameAdd();

    /**
     * Returns the input destination stop name.
     *
     * @return String the name
     */
    String getEndStopNameAdd();

    /**
     * Displays an error to the user.
     *
     * @param msg String the error message
     */
    void displayError(String msg);

    /**
     * Clears the errors display.
     */
    void clearError();

    /**
     * Clears the controls.
     */
    void clearControls();

    /**
     * Clears the style of the graphs' vertices and edges.
     */
    void clearGraphStyle();

    /**
     * Set the triggers to be executed by the controller.
     *
     * @param controller RouteManagerController the controller
     */
    void setTriggers(RouteManagerController controller);

    /**
     * Used to update the observer.
     *
     * @param subject Observable the subject
     * @param arg Object an object
     */
    void update(Observable subject, Object arg);
}
