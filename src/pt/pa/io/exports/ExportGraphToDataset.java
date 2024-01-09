package pt.pa.io.exports;

import javafx.util.Pair;
import pt.pa.graph.Edge;
import pt.pa.graph.Vertex;
import pt.pa.view.mainmenu.GraphDisplay;
import pt.pa.io.imports.ImportData;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * This class is used to export a graph to a dataset.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class ExportGraphToDataset {
    private final GraphDisplay graphDisplay;
    private final String outputPath;
    private final String outputName;
    private final String DATASETS_DEFAULT_PATH = "./datasets/";

    /**
     * Creates an instance of ExportGraphToDataset.
     *
     * @param graphDisplay GraphDisplay the display to save the data from
     * @param outputName   String the name of the new dataset
     */
    public ExportGraphToDataset(GraphDisplay graphDisplay, String outputName) {
        this.outputName = outputName;
        this.outputPath = DATASETS_DEFAULT_PATH + outputName + "/";
        this.graphDisplay = graphDisplay;
    }

    /**
     * Auxiliary method that returns a Map with the stop as the key and a pair representing its position in the graph (x,y).
     *
     * @return Map with stops and its positions
     */
    private Map<Stop, Pair<Integer, Integer>> vertexPositionToMap() {
        Map<Stop, Pair<Integer, Integer>> vertexPositions = new HashMap<>();
        Collection<Vertex<Stop>> vertices = graphDisplay.getGraphComponents().getGraph().vertices();
        for (Vertex<Stop> stop : vertices) {
            int stopX = (int) Math.round(graphDisplay.getGraphView().getVertexPositionX(stop));
            int stopY = (int) Math.round(graphDisplay.getGraphView().getVertexPositionY(stop));
            vertexPositions.put(stop.element(), new Pair<>(stopX, stopY));
        }
        return vertexPositions;
    }

    /**
     * Auxiliary method that returns a list with the stops and its positions in String format.
     *
     * @return List with position of the stops in String format
     */
    private ArrayList<String> vertexPositionToData() {
        Map<Stop, Pair<Integer, Integer>> vertexPositions = vertexPositionToMap();
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<Stop, Pair<Integer, Integer>> entry : vertexPositions.entrySet()) {
            Stop stop = entry.getKey();
            Pair<Integer, Integer> position = entry.getValue();
            String string = stop.getStopCode() + "\t" + position.getKey() + "\t" + position.getValue();
            list.add(string);
        }
        return list;
    }

    /**
     * Auxiliary method that exports the current stops position.
     *
     * @return String that contains all the lines to export
     */
    private String exportStopsPositionToString() {
        StringBuilder stringBuilder = new StringBuilder("#\n");
        stringBuilder.append("# data file: ").append(outputName).append("-positions\tXY\n");
        stringBuilder.append("#\n");
        stringBuilder.append("stop_code\tx\ty\n");
        ArrayList<String> list = vertexPositionToData();
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Auxiliary method that returns a List with the vertex data.
     *
     * @return ArrayList with the data of the vertex
     */
    private ArrayList<String> vertexToData() {
        ArrayList<String> list = new ArrayList<>();
        Collection<Vertex<Stop>> vertices = graphDisplay.getGraphComponents().getGraph().vertices();
        for (Vertex<Stop> vStop : vertices) {
            Stop stop = vStop.element();
            String string = stop.getStopCode() + "\t" + stop.getStopName() + "\t" + stop.getLatitude() + "\t" + stop.getLongitude();
            list.add(string);
        }
        return list;
    }

    /**
     * Auxiliary method that exports the stops in String format.
     *
     * @return String with the info
     */
    private String exportStopsToString() {
        StringBuilder stringBuilder = new StringBuilder("#\n");
        stringBuilder.append("# data file: ").append(outputName).append("-stops\n");
        stringBuilder.append("#\n");
        stringBuilder.append("stop_code\tstop_name\tlat\tlon\n");
        ArrayList<String> list = vertexToData();
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Auxiliary method that creates a parent folder.
     *
     * @return TRUE if it's able to create, FALSE otherwise
     */
    private boolean createParentFolder() {
        File file = new File(outputPath);
        return file.mkdir();
    }

    /**
     * Auxiliary method to export stops to a dataset.
     */
    private void exportStopsToDataset() {
        String output = exportStopsToString();
        File file = new File(outputPath + "stops.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Auxiliary method to exports the stops coordinates to a dataset.
     */
    private void exportStopsXYtoDataset() {
        String output = exportStopsPositionToString();
        File file = new File(outputPath + "xy.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Auxiliary method to copy images to a dataset.
     */
    private void copyImagesToDataset() {
        File file = new File(outputPath + "img/");
        file.mkdir();
        ArrayList<String> filesInImages = getFilesInImages();
        filesInImages.forEach(e -> {
            try {
                copyImage(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Auxiliary method to get the files in images.
     *
     * @return ArrayList with the files in images
     */
    private ArrayList<String> getFilesInImages() {
        String dataset = graphDisplay.getGraphComponents().getDataset();
        File images = new File(DATASETS_DEFAULT_PATH + dataset + "/img/");
        String[] fileNames = images.list();
        return new ArrayList<>(Arrays.asList(fileNames));
    }

    /**
     * Auxiliary method to copy an image.
     *
     * @param imageName String the image to copy
     * @throws IOException if there's any problem while copying the image
     */
    private void copyImage(String imageName) throws IOException {
        String dataset = graphDisplay.getGraphComponents().getDataset();
        File fileSrc = new File("./datasets/" + dataset + "/img/" + imageName);
        File fileDest = new File(outputPath + "img/" + imageName);
        Files.copy(fileSrc.toPath(), fileDest.toPath());
    }

    /**
     * Auxiliary method to export the route distance to a String.
     * @return String with the information
     */
    private String exportRouteDistanceToString() {
        StringBuilder stringBuilder = new StringBuilder("#\n");
        stringBuilder.append("# data file: ").append(outputName).append("-routes\n");
        stringBuilder.append("#\n");
        stringBuilder.append("stop_code_start\tstop_code_end\tdistance\n");
        ArrayList<String> list = routePropertyToList("distance");
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Auxiliary method to export the route duration to a String.
     * @return String with the information
     */
    private String exportRouteDurationToString() {
        StringBuilder stringBuilder = new StringBuilder("#\n");
        stringBuilder.append("# data file: ").append(outputName).append("-routes\n");
        stringBuilder.append("#\n");
        stringBuilder.append("stop_code_start\tstop_code_end\tduration\n");
        ArrayList<String> list = routePropertyToList("duration");
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Auxiliary method to export the route property to a dataset.
     *
     * @param type String with the type
     */
    private void exportRoutePropertyToDataset(String type) {
        String output = "";
        File file = null;
        switch (type) {
            case "duration":
                output = exportRouteDurationToString();
                file = new File(outputPath + "routes-duration.txt");
                break;
            case "distance":
                output = exportRouteDistanceToString();
                file = new File(outputPath + "routes-distance.txt");
                break;
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Auxiliary method to put route property in a List.
     *
     * @param type String the type
     * @return ArrayList with the route property
     */
    private ArrayList<String> routePropertyToList(String type) {
        Map<Pair<String, String>, Edge<Route, Stop>> edgesMap = graphDisplay.getGraphComponents().getEdgeMap();
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<Pair<String, String>, Edge<Route, Stop>> entry : edgesMap.entrySet()) {
            String stopCode1 = entry.getKey().getKey();
            String stopCode2 = entry.getKey().getValue();
            int value = type.equals("duration") ? entry.getValue().element().getDuration() : entry.getValue().element().getDistance();
            String string = stopCode1 + "\t" + stopCode2 + "\t" + value;
            list.add(string);
        }

        return list;
    }

    /**
     * Auxiliary method to export the graph to a dataset.
     *
     * @throws DatasetAlreadyExistException if the dataset already exists
     */
    public void exportGraphToDataset() throws DatasetAlreadyExistException {
        if (!createParentFolder()) throw new DatasetAlreadyExistException();
        exportStopsToDataset();
        exportStopsXYtoDataset();
        exportRoutePropertyToDataset("duration");
        exportRoutePropertyToDataset("distance");
        copyImagesToDataset();
    }
}
