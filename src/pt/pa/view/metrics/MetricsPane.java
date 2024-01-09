package pt.pa.view.metrics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import pt.pa.graph.Graph;
import pt.pa.view.mainmenu.GraphDisplay;
import pt.pa.metrics.GraphMetrics;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.view.mainmenu.GraphDisplayRightPanel;

import java.util.List;
import java.util.Map;

/**
 * This class is used to define the metrics pane.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class MetricsPane extends BorderPane {
    private final GraphDisplay graphDisplay;
    private List<Map.Entry<Stop, Integer>> listOfAdjacencySorted;

    /**
     * Creates a MetricsPane instance.
     *
     * @param graphDisplay GraphDisplay the graph display
     */
    public MetricsPane(GraphDisplay graphDisplay) {
        this.graphDisplay = graphDisplay;
        drawTop();
        drawLeft();
        drawCenter();
        drawRight();
        drawBottom();
        setPadding(new Insets(60));
    }

    /**
     * Auxiliary method to draw the top part of the instance.
     */
    private void drawTop() {
        HBox hBox = new HBox();
        Label title = new Label("Network Metrics");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
        title.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20));
        hBox.getChildren().add(title);
        setTop(hBox);
    }

    /**
     * Auxiliary method to draw the bottom part of the instance.
     */
    private void drawBottom() {
        HBox hBox = new HBox();
        Button backButton = GraphDisplayRightPanel.drawReturnMenuButton();
        backButton.setOnAction(e -> setBackOnClickAction());
        hBox.getChildren().add(backButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 0, 20, 0));
        setBottom(hBox);
    }

    /**
     * Auxiliary method to set the back on click action.
     */
    private void setBackOnClickAction() {
        ((Stage) graphDisplay.getScene().getWindow()).show();
        graphDisplay.updateBackground();
        ((Stage) getScene().getWindow()).close();
    }

    /**
     * Auxiliary method to draw the left part of the instance.
     */
    private void drawLeft() {
        VBox metrics = drawMetrics();
        setLeft(metrics);
    }

    /**
     * Auxiliary method to draw the metrics.
     *
     * @return VBox with the metrics
     */
    private VBox drawMetrics() {
        Graph<Stop, Route> graph = graphDisplay.getGraphComponents().getGraph();
        VBox metrics = new VBox();
        metrics.setBorder(new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
        HBox numberOfStops = drawLine("Number Of Stops:", String.valueOf(graph.vertices().size()));
        HBox numberOfRoutes = drawLine("Number Of Routes:", String.valueOf(graph.edges().size()));
        HBox numberOfComponents = drawLine("Number Of Components:", String.valueOf(GraphMetrics.numberGraphComponents(graph)));
        metrics.getChildren().addAll(numberOfStops, numberOfRoutes, numberOfComponents);
        metrics.setAlignment(Pos.CENTER);
        metrics.setPadding(new Insets(30));
        metrics.setSpacing(30);
        return metrics;
    }

    /**
     * Auxiliary method to draw the center part of the instance.
     */
    private void drawCenter() {
        VBox center = drawStopsAdjacency();
        setCenter(center);
    }

    /**
     * Auxiliary method to draw the right part of the instance.
     */
    private void drawRight() {
        setRight(drawBarChart(listOfAdjacencySorted));
    }

    /**
     * Auxiliary method to draw the bar chart.
     *
     * @param list the list with the info to be drawn
     * @return BarChart the chart with the info
     */
    private BarChart<String, Number> drawBarChart(List<Map.Entry<Stop, Integer>> list) {
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setTitle("Top 7 - Central Stops");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Nº of Adjacent Stops");
        for (int i = 0; i < 7; i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(list.get(i).getKey().getStopCode(), list.get(i).getValue());
            series1.getData().add(data);
        }
        barChart.getData().add(series1);
        return barChart;
    }

    /**
     * Auxiliary method to draw the stops' adjacency.
     *
     * @return VBox with the info
     */
    private VBox drawStopsAdjacency() {
        VBox stopsAdjacency = new VBox();
        Label label = new Label("Stop's Centrality");
        label.setFont(new Font(20));
        stopsAdjacency.setPadding(new Insets(20));
        stopsAdjacency.setAlignment(Pos.CENTER);
        listOfAdjacencySorted = GraphMetrics.adjacencyOrdered(graphDisplay.getGraphComponents().getGraph());
        StopsListView stopsListView = new StopsListView(listOfAdjacencySorted);
        stopsAdjacency.getChildren().addAll(label, stopsListView);
        return stopsAdjacency;
    }

    /**
     * Auxiliary method to draw the line.
     *
     * @param key String the key
     * @param value String the value
     * @return HBox with the info
     */
    private HBox drawLine(String key, String value) {
        HBox hBox = new HBox();
        Label labelKey = new Label(key);
        labelKey.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
        Label labelValue = new Label(value);
        labelValue.setFont(Font.font("Arial", 20));
        hBox.getChildren().addAll(labelKey, labelValue);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
}