package pt.pa.commands.graphviewhandler;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * This class stores an enum with the backgrounds that can be used.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public enum BackgroundType {
    DEFAULT, DARK, TERRAIN, SATELLITE;

    @Override
    public String toString() {
        switch (this) {
            case DARK:
                return "dark.png";
            case TERRAIN:
                return "terrain.png";
            case SATELLITE:
                return "satellite.png";
            default:
                return "map.png";
        }
    }

    /**
     * Returns the existing label.
     *
     * @return String the label
     */
    public String getVertexLabelStyle() {
        switch (this) {
            case DARK:
            case SATELLITE:
                return "-fx-font: 8pt sans-serif; -fx-fill: white;";
            default:
                return "-fx-font: 8pt sans-serif; -fx-fill: black;";
        }
    }
}
