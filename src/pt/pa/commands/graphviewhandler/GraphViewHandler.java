package pt.pa.commands.graphviewhandler;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartStylableNode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Pair;
import pt.pa.graph.Vertex;
import pt.pa.graphcomponents.GraphComponents;
import pt.pa.io.imports.ImportData;
import pt.pa.io.imports.InvalidDatasetException;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.view.mainmenu.GraphDisplay;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Class that handles the changes made to the display of the graph
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class GraphViewHandler {
    /**
     * Loads the default positions of every stop into the graph view
     *
     * @param graphView       SmartGraphPanel<Stop, Route> panel that handles the display of the graph
     * @throws InvalidDatasetException if the dataset is invalid
     * @param graphComponents GraphComponents the components of the graph
     */
    public static void loadDefaultStopsPositionToGraphView(SmartGraphPanel<Stop, Route> graphView, GraphComponents graphComponents) throws InvalidDatasetException {
        Map<String, Vertex<Stop>> vertexMap = graphComponents.getVertexMap();
        Map<String, Pair<Integer, Integer>> stopsPosition = ImportData.loadStopsPosition(graphComponents.getDataset());
        for (Map.Entry<String, Pair<Integer, Integer>> stopPosition : stopsPosition.entrySet()) {
            Vertex<Stop> vStop = vertexMap.get(stopPosition.getKey());
            int stopX = stopPosition.getValue().getKey();
            int stopY = stopPosition.getValue().getValue();
            graphView.setVertexPosition(vStop, stopX, stopY);
        }
    }

    /**
     * Used to change the label of the vertex.
     *
     * @param graphDisplay GraphDisplay the graph display
     */
    public static void changeVertexLabel(GraphDisplay graphDisplay) {
        Collection<Vertex<Stop>> vertices = graphDisplay.getGraphComponents().getGraph().vertices();
        for (Vertex<Stop> vertex : vertices) {
            SmartStylableNode label = graphDisplay.getGraphView().getStylableLabel(vertex);
            label.setStyle(graphDisplay.getCurrentBackground().getVertexLabelStyle());
        }
    }

    /**
     * Changes the background.
     *
     * @param graphComponents GraphComponents the components of the graph
     * @param backgroundType  BackgroundType the background type
     * @return Background a new background
     */
    public static Background changeBackground(GraphComponents graphComponents, BackgroundType backgroundType) {
        Image image = loadImageFile("datasets/" + graphComponents.getDataset() + "/img/" + backgroundType.toString());
        return createBackground(image);
    }

    /**
     * Loads an image from a file.
     *
     * @param filePath String with the filePath
     * @return Image the image that was loaded
     */
    private static Image loadImageFile(String filePath) {
        try {
            return ImportData.loadImage(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new background.
     *
     * @param image Image the image of the background
     * @return Background a new instance of a background
     */
    private static Background createBackground(Image image) {
        BackgroundRepeat noRepeat = BackgroundRepeat.NO_REPEAT;
        BackgroundPosition position = BackgroundPosition.DEFAULT;
        BackgroundSize size = BackgroundSize.DEFAULT;
        BackgroundImage backgroundImage = new BackgroundImage(image, noRepeat, noRepeat, position, size);
        return new Background(backgroundImage);
    }
}
