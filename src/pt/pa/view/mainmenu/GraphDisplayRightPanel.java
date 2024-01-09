package pt.pa.view.mainmenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.view.travelmanage.GraphNotLoadedException;
import pt.pa.view.metrics.MetricsPane;

/**
 * This class is used to define the display of the right panel.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphDisplayRightPanel extends BorderPane {
    private final GraphDisplay graphDisplay;

    /**
     * Creates a GraphDisplayRightPanel instance.
     *
     * @param graphDisplay GraphDisplay the graph display
     */
    public GraphDisplayRightPanel(GraphDisplay graphDisplay) {
        this.graphDisplay = graphDisplay;
        drawRight();
    }

    /**
     * Auxiliary method to draw the right panel container.
     */
    private void drawRight() {
        VBox rightButtons = drawRightButtons();
        setRight(rightButtons);
    }

    /**
     * Auxiliary method to draw the right button on their container.
     *
     * @return VBox the container with the buttons
     */
    private VBox drawRightButtons() {
        VBox rightButtons = new VBox();
        Button manageButton = drawButton("Manage Network");
        manageButton.setOnAction(e -> setManageOnClickAction());
        Button infoButton = drawButton("Network Metrics");
        infoButton.setOnAction(e -> setMetricsOnClickAction());
        Button travelButton = drawButton("Travel Management");
        travelButton.setOnAction(e -> setTravelOnClickAction());
        rightButtons.getChildren().addAll(manageButton, infoButton, travelButton);
        rightButtons.setAlignment(Pos.TOP_CENTER);
        rightButtons.setSpacing(40);
        rightButtons.setPadding(new Insets(50));
        rightButtons.setBackground(new Background(new BackgroundFill(Paint.valueOf("GAINSBORO"), new CornerRadii(0), new Insets(0))));
        return rightButtons;
    }

    /**
     * Auxiliary method set the click action of the button.
     */
    private void setTravelOnClickAction() {
        graphDisplay.changeToTravelScreen();
    }

    /**
     * Auxiliary method to draw the button.
     *
     * @param text String the text to put on the button
     * @return Button the button
     */
    private Button drawButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        button.setPrefSize(150, 80);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setWrapText(true);
        button.setBackground(new Background(new BackgroundFill(Paint.valueOf("ROYALBLUE"), new CornerRadii(5), new Insets(0))));
        button.setTextFill(Paint.valueOf("White"));
        button.setOnMouseEntered(e -> button.setBackground(new Background(new BackgroundFill(Paint.valueOf("DODGERBLUE"), new CornerRadii(5), new Insets(0)))));
        button.setOnMouseExited(e -> button.setBackground(new Background(new BackgroundFill(Paint.valueOf("ROYALBLUE"), new CornerRadii(5), new Insets(0)))));
        return button;
    }

    /**
     * Auxiliary method to set the click action of the button.
     */
    private void setManageOnClickAction() {
        graphDisplay.changeToManagerScreen();
    }

    /**
     * Used to draw the button to return to the menu.
     *
     * @return Button the button to draw
     */
    public static Button drawReturnMenuButton() {
        Button returnButton = new Button("Return to Menu");
        returnButton.setFont(Font.font("Calibri", 15));
        returnButton.setPrefSize(150, 60);
        returnButton.setTextAlignment(TextAlignment.CENTER);
        returnButton.setWrapText(true);
        returnButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("SLATEGREY"), new CornerRadii(5), null)));
        returnButton.setTextFill(Paint.valueOf("BLACK"));
        returnButton.setOnMouseEntered(e -> returnButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("DIMGREY"), new CornerRadii(5), null))));
        returnButton.setOnMouseExited(e -> returnButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("SLATEGREY"), new CornerRadii(5), null))));
        return returnButton;
    }

    /**
     * Auxiliary method to set the metric button click action.
     */
    private void setMetricsOnClickAction() {
        try {
            if (graphDisplay.getGraphView() == null) throw new GraphNotLoadedException();
            Scene scene = new Scene(new MetricsPane(graphDisplay), graphDisplay.getWidth(), graphDisplay.getHeight());
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.setTitle("Bus Network Metrics");
            stage.setResizable(false);
            getScene().getWindow().hide();
            stage.show();
        } catch (GraphNotLoadedException e) {
            sendAlert();
        }
    }

    /**
     * Auxiliary method to send an alert if the dataset wasn't defined or the graph is not loaded.
     */
    private void sendAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Dataset wasn't defined");
        alert.setHeaderText("Graph not loaded");
        alert.showAndWait();
    }
}
