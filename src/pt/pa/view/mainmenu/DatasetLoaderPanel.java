package pt.pa.view.mainmenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.io.imports.ImportData;

import java.util.ArrayList;

/**
 * This class is used to load the dataset panel.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class DatasetLoaderPanel extends BorderPane {

    /**
     * Creates a DatasetLoaderPanel instance.
     */
    public DatasetLoaderPanel() {
        drawCenter();
    }

    /**
     * Auxiliary method to draw the center.
     */
    private void drawCenter() {
        VBox vBox = drawContent();
        setCenter(vBox);
    }

    /**
     * Auxiliary method to draw the content of the panel.
     *
     * @return VBox with the content
     */
    private VBox drawContent() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        VBox labels = drawLabels();
        ComboBox<String> comboBox = drawComboBox();
        Button loadButton = drawLoadButton(comboBox);
        vBox.getChildren().addAll(labels, comboBox, loadButton);
        return vBox;
    }

    /**
     * Auxiliary method to draw the labels.
     *
     * @return VBox with the labels
     */
    private VBox drawLabels() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Bus Network");
        titleLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 35));
        Label subTitlelabel = new Label("Select one of the datasets bellow:");
        subTitlelabel.setFont(Font.font("Arial", 20));
        vBox.getChildren().addAll(titleLabel, subTitlelabel);
        return vBox;
    }

    /**
     * Auxiliary method to draw the load button.
     *
     * @param comboBox ComboBox the combo box
     * @return Button the load button
     */
    private Button drawLoadButton(ComboBox<String> comboBox) {
        Button loadButton = new Button("Confirm");
        loadButton.setAlignment(Pos.CENTER);
        loadButton.setPrefSize(200, 40);
        loadButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        loadButton.setTextFill(Paint.valueOf("WHITE"));
        setButtonBackground(loadButton, "ROYALBLUE");
        loadButton.setOnMouseEntered(e -> setButtonBackground(loadButton, "DODGERBLUE"));
        loadButton.setOnMouseExited(e -> setButtonBackground(loadButton, "ROYALBLUE"));
        loadButton.setOnAction(e -> setLoadButtonAction(comboBox));
        return loadButton;
    }

    /**
     * Auxiliary method to set the load button action.
     *
     * @param comboBox ComboBox the combo box
     */
    private void setLoadButtonAction(ComboBox<String> comboBox) {
        if (comboBox.getSelectionModel().getSelectedItem() != null) {
            initGraphDisplay(comboBox.getSelectionModel().getSelectedItem());
        } else {
            sendAlert();
        }
    }

    /**
     * Auxiliary method to set the button background.
     *
     * @param button Button the button
     * @param color String the color
     */
    private void setButtonBackground(Button button, String color) {
        button.setBackground(new Background(new BackgroundFill(Paint.valueOf(color), new CornerRadii(5), null)));
    }

    /**
     * Auxiliary method to draw the combo box.
     *
     * @return ComboBox the drawn combo box
     */
    private ComboBox<String> drawComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefSize(300, 30);
        ArrayList<String> list = ImportData.listAvailableDatasets();
        list.forEach(e -> comboBox.getItems().add(e));
        return comboBox;
    }

    /**
     * Auxiliary method to initiate the display of the graph.
     *
     * @param dataset String the dataset to use
     */
    private void initGraphDisplay(String dataset) {
        GraphDisplay graphDisplay = new GraphDisplay(dataset);
        graphDisplay.initContentDisplay();
        Scene scene = new Scene(graphDisplay, 1350, 825);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Bus Network Visualization");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Stage) getScene().getWindow()).close();
        graphDisplay.updateBackground();
        graphDisplay.init();
        graphDisplay.setPositionsOfVertex();
    }

    /**
     * Auxiliary method to send an alert in case a dataset isn't selected.
     */
    private void sendAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dataset wasn't selected");
        alert.setHeaderText("Please select a dataset!");
        alert.showAndWait();
    }
}