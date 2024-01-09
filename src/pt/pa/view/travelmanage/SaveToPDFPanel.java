package pt.pa.view.travelmanage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import pt.pa.dijkstra.DijkstraResult;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.pdf.TicketFactory;

/**
 * This class is used to define the save to PDF panel.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class SaveToPDFPanel extends BorderPane {
    private DijkstraResult<Stop, Route> dijkstraResult;
    private ComboBox<String> comboBox;
    private Button confirmButton;
    private TextField txtField;

    /**
     * Creates a SaveToPDFPanel instance.
     *
     * @param dijkstraResult the result
     */
    public SaveToPDFPanel(DijkstraResult<Stop, Route> dijkstraResult) {
        this.dijkstraResult = dijkstraResult;
        drawCenter();
        setTriggers();
    }

    /**
     * Auxiliary method to draw the center.
     */
    private void drawCenter() {
        VBox vBox = new VBox();
        Label label = new Label("Save as...");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        HBox center = drawTextBox();
        HBox comboBox = drawComboBox();
        vBox.getChildren().addAll(label, center, comboBox);
        setCenter(vBox);
    }

    /**
     * Auxiliary method to draw the combo box.
     *
     * @return HBox with the combo box in it
     */
    private HBox drawComboBox() {
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Simple Ticket", "Intermediate Ticket", "Complete Ticket");
        comboBox.getSelectionModel().select(0);
        comboBox.setPrefSize(240, 30);
        HBox hBox = new HBox(comboBox);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        return hBox;
    }

    /**
     * Auxiliary method to set the trigger for the buttons.
     */
    private void setTriggers() {
        confirmButton.setOnAction(e -> {
            if (checkText()) {
                int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
                switch (selectedIndex) {
                    case 0:
                        TicketFactory.create("simple", dijkstraResult, txtField.getText());
                        break;
                    case 1:
                        TicketFactory.create("intermediate", dijkstraResult, txtField.getText());
                    case 2:
                        TicketFactory.create("complete", dijkstraResult, txtField.getText());
                }
                sendAlert("PDF created", "PDF was successfully created!", Alert.AlertType.INFORMATION);
                ((Stage) getScene().getWindow()).close();
            }
        });
    }

    /**
     * Auxiliary method to check the inserted text associated with the name of the PDF to create.
     *
     * @return TRUE if the name is ok, FALSE otherwise
     */
    private boolean checkText() {
        if (txtField.getText().isEmpty()) {
            sendAlert("Error creating PDF", "Please insert a valid name for the file", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    /**
     * Auxiliary method to draw the text box.
     *
     * @return HBox with the text
     */
    private HBox drawTextBox() {
        HBox hBox = new HBox();
        txtField = new TextField();
        txtField.setPromptText("Insert PDF name");
        txtField.setPrefSize(150, 20);
        confirmButton = new Button("Confirm");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(txtField, confirmButton);
        hBox.setSpacing(30);
        return hBox;
    }

    /**
     * Auxiliary method to send an alert to the user.
     *
     * @param title  String the title
     * @param header String the header
     * @param type   AlertType the type
     */
    private void sendAlert(String title, String header, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
