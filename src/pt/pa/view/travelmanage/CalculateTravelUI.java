package pt.pa.view.travelmanage;

import pt.pa.controller.CalculateTravelController;

/**
 * This class is used to define the interface UI for travel calculations.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public interface CalculateTravelUI {
    /**
     * Returns the name of the input initial stop of the shortest path.
     *
     * @return String the initial stop name
     */
    String getShortestInitialStopNameAdd();

    /**
     * Returns the name of the input destination stop of the shortest path.
     *
     * @return String the destination stop name
     */
    String getShortestEndStopNameAdd();

    /**
     * Returns the input initial stop name within N routes.
     *
     * @return String the initial stop name
     */
    String getNDistanceInitialStopName();

    /**
     * Returns the input max number of stops to go through.
     *
     * @return String the number
     */
    String getNDistanceStopsMaxNumber();

    /**
     * Displays an error to the user.
     *
     * @param msg String the message
     */
    void displayError(String msg);

    /**
     * Clears the errors display.
     */
    void clearError();

    /**
     * Clears the window controls.
     */
    void clearControls();

    /**
     * Clears the graphs' vertices and edges style.
     */
    void clearGraphStyle();

    /**
     * Sets the triggers to be executed by the controller.
     *
     * @param controller CalculateTravelController the controller
     */
    void setTriggers(CalculateTravelController controller);

}
