package pt.pa.view.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import pt.pa.io.exports.DatasetAlreadyExistException;
import pt.pa.io.exports.ExportGraphToDataset;

/**
 * This class is used to export the dataset panel.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class ExportDatasetPanel extends BorderPane {
    private GraphDisplay graphDisplay;

    /**
     * Creates an ExportDatasetPanel instance.
     *
     * @param graphDisplay GraphDisplay the display of the graph
     */
    public ExportDatasetPanel(GraphDisplay graphDisplay) {
        this.graphDisplay = graphDisplay;
        drawCenter();
    }

    /**
     * Auxiliary method to draw the center.
     */
    private void drawCenter() {
        VBox vBox = new VBox();
        Label label = new Label("Save as...");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        HBox center = drawTextBox();
        vBox.getChildren().addAll(label, center);
        setCenter(vBox);
    }

    /**
     * Auxiliary method to draw the text box.
     *
     * @return HBox the text box
     */
    private HBox drawTextBox() {
        HBox hBox = new HBox();
        TextField txtField = new TextField();
        txtField.setPromptText("Insert dataset name...");
        txtField.setPrefSize(100, 20);
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            if (!txtField.getText().isEmpty()) {
                confirmAction(txtField.getText());
                txtField.clear();
            } else {
                sendAlert("Dataset already exists", "The dataset already exists!");
            }
        });
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(txtField, confirmButton);
        hBox.setSpacing(30);
        return hBox;
    }

    /**
     * Auxiliary method to confirm the export action, in case the dataset already exists.
     *
     * @param datasetName String the name of the dataset
     */
    private void confirmAction(String datasetName) {
        ExportGraphToDataset exportGraphToDataset = new ExportGraphToDataset(graphDisplay, datasetName);
        try {
            exportGraphToDataset.exportGraphToDataset();
            ((Stage) getScene().getWindow()).close();
        } catch (DatasetAlreadyExistException e) {
            sendAlert("Dataset already exists", "The dataset already exists!");
        }
    }

    /**
     * Auxiliary method to send an alert.
     *
     * @param title String the title of the alert
     * @param header String the header of the alert
     */
    private void sendAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}