package pt.pa.view.routemanage;

import com.brunomnsilva.smartgraph.containers.ContentZoomPane;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import pt.pa.model.Stop;
import pt.pa.model.Route;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

/**
 * This class is used to define the view creator.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class NetworkViewCreator {
    private static NetworkViewCreator instance = null;

    /**
     * Private constructor to create a singleton.
     */
    private NetworkViewCreator() {

    }

    /**
     * Used to instance a NetworkViewCreator or return it in case it has already been instanced.
     *
     * @return NetworkViewCreator the creator
     */
    public static NetworkViewCreator getInstance() {
        if (instance == null) {
            instance = new NetworkViewCreator();
        }
        return instance;
    }

    /**
     * Creates the layout of the view with the style patterns of the program.
     *
     * @param networkView BorderPane the pane
     * @param graphPanel  SmartGraphPanel the panel
     * @param sidePanel   VBox the side panel
     * @param error       Label the label to show an error
     */
    public void createLayout(BorderPane networkView, SmartGraphPanel<Stop, Route> graphPanel, VBox sidePanel, Label error) {
        //graphPanel.setAutomaticLayout(true);
        //graphPanel.setStyle("-fx-background-color: white;");
        networkView.setCenter(new ContentZoomPane(graphPanel));
        /* RIGHT PANEL */
        networkView.setRight(sidePanel);
        sidePanel.setStyle("-fx-background-color: gainsboro;");
        /* BOTTOM */
        error.setStyle("-fx-text-fill: red");
        HBox bottom = new HBox(error);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setStyle("-fx-background-color: white;");
        networkView.setBottom(bottom);
    }
}
