package pt.pa.view.metrics;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.pa.model.Stop;

import java.util.List;
import java.util.Map;

/**
 * This class is used to define the stops list view.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class StopsListView extends ListView<Map.Entry<Stop, Integer>> {
    private final int DEFAULT_CELL_SIZE = 50;

    /**
     * Creates a StopsListView instance.
     *
     * @param entries List with the entries
     */
    public StopsListView(List<Map.Entry<Stop, Integer>> entries) {
        ObservableList<Map.Entry<Stop, Integer>> entriesList = FXCollections.observableList(entries);
        setItems(entriesList);
        setCellFactory((ListView<Map.Entry<Stop, Integer>> listview) -> new ListCell<Map.Entry<Stop, Integer>>() {
            @Override
            public void updateItem(Map.Entry<Stop, Integer> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                if (item != null) {
                    setGraphic(drawCell(item));
                }
            }
        });
        getItems().forEach(this::drawCell);
        prefHeightProperty().bind(Bindings.size(entriesList).multiply(DEFAULT_CELL_SIZE));
    }

    /**
     * Auxiliary method to draw a cell.
     *
     * @param entry Map with the entry to be drawn
     * @return Node the drawn cell
     */
    private Node drawCell(Map.Entry<Stop, Integer> entry) {
        HBox cell = new HBox();
        Label stopLabel = new Label("Stop: " + entry.getKey().getStopCode() + ", " + entry.getKey().getStopName());
        stopLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        Label nLabel = new Label("Number of Adjacent Stops: " + entry.getValue());
        VBox vBox = new VBox(stopLabel, nLabel);
        vBox.setAlignment(Pos.CENTER);
        cell.getChildren().addAll(vBox);
        cell.setSpacing(10);
        cell.setAlignment(Pos.CENTER);
        cell.setPrefSize(100, DEFAULT_CELL_SIZE);
        return cell;
    }
}


