package pt.pa.io.imports;

import javafx.scene.image.Image;
import javafx.util.Pair;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.io.*;
import java.util.*;

/**
 * This class represents the data import.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class ImportData {
    private final static String DEFAULT_DATASET_PATH = "datasets/";

    /**
     * Returns an ArrayList with the available datasets.
     *
     * @return ArrayList with the available datasets
     */
    public static ArrayList<String> listAvailableDatasets() {
        File folder = new File(DEFAULT_DATASET_PATH);
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.list())));
    }

    /**
     * Used to load an image.
     *
     * @param filePath String with the path for the file
     * @return Image the image
     * @throws IOException if something goes wrong while loading the image
     */
    public static Image loadImage(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        Image image = new Image(fileInputStream);
        fileInputStream.close();
        return image;
    }

    /**
     * Parses all lines from a file to a List.
     *
     * @param filename String file to load
     * @throws IOException if the I/O source is invalid
     * @return ArrayList with filtered lines (blank and commented)
     */
    public static ArrayList<String> getDataFromFile(String filename) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (!(line.isEmpty() || line.trim().charAt(0) == '#')) {
                data.add(line);
            }
        }
        bufferedReader.close();
        return data;
    }

    /**
     * Loads all stops to a Map.
     *
     * @param dataset String the dataset to load
     * @throws InvalidDatasetException if the dataset is invalid
     * @return Map with the key being the stop_code and the value being the stop created
     */
    public static Map<String, Stop> loadStopsToMap(String dataset) throws InvalidDatasetException {
        try {
            Map<String, Stop> stopsMap = new HashMap<>();
            List<String> data = getDataFromFile(DEFAULT_DATASET_PATH + dataset + "/stops.txt");
            for (String line : data) {
                String[] splitLine = line.split("\t");
                if (isNumeric(splitLine[splitLine.length - 1])) {
                    String stopCode = splitLine[0];
                    String stopName = splitLine[1];
                    double latitude = Double.parseDouble(splitLine[2]);
                    double longitude = Double.parseDouble(splitLine[3]);
                    Stop stop = new Stop(stopCode, stopName, latitude, longitude);
                    stopsMap.put(stopCode, stop);
                }
            }
            return stopsMap;
        } catch (IOException e) {
            throw new InvalidDatasetException();
        }
    }

    /**
     * Loads all routes to a Map.
     *
     * @param dataset String the dataset to load the duration and the distance of the stops
     * @throws InvalidDatasetException is the dataset is invalid
     * @return Map with the pair of stops as the key and the created route as value
     */
    public static Map<Pair<String, String>, Route> loadRoutesToMap(String dataset) throws InvalidDatasetException {
        Map<Pair<String, String>, Route> routesMap = new HashMap<>();
        Map<Pair<String, String>, Integer> routesDurationMap = loadRoutesPropertiesToMap(DEFAULT_DATASET_PATH + dataset + "/routes-duration.txt");
        Map<Pair<String, String>, Integer> routesDistanceMap = loadRoutesPropertiesToMap(DEFAULT_DATASET_PATH + dataset + "/routes-distance.txt");
        for (Map.Entry<Pair<String, String>, Integer> routeDuration : routesDurationMap.entrySet()) {
            int duration = routeDuration.getValue();
            int distance = routesDistanceMap.get(routeDuration.getKey());
            routesMap.put(routeDuration.getKey(), new Route(distance, duration));
        }
        return routesMap;
    }

    /**
     * Loads to a Map a property (either the distance or the duration) of all routes.
     *
     * @param filename String with the file to load the property from
     * @throws InvalidDatasetException if the dataset is invalide
     * @return Map with the key as the pair of stops and the value the property
     */
    public static Map<Pair<String, String>, Integer> loadRoutesPropertiesToMap(String filename) throws InvalidDatasetException {
        try {
            Map<Pair<String, String>, Integer> routesPropertiesMap = new HashMap<>();
            List<String> data = getDataFromFile(filename);
            for (String line : data) {
                String[] splitLine = line.split("\t");
                if (isNumeric(splitLine[splitLine.length - 1])) {
                    String stop1 = splitLine[0];
                    String stop2 = splitLine[1];
                    int property = Integer.parseInt(splitLine[2]);
                    routesPropertiesMap.put(new Pair<>(stop1, stop2), property);
                }
            }
            return routesPropertiesMap;
        } catch (IOException e) {
            throw new InvalidDatasetException();
        }
    }

    /**
     * Loads to a Map the position (x,y) of the stop in the graph.
     *
     * @param dataset String the dataset to load the positions of the stops
     * @throws InvalidDatasetException if the dataset is invalid
     * @return Map with the stopCode as the key and the position (x,y) as the value
     */
    public static Map<String, Pair<Integer, Integer>> loadStopsPosition(String dataset) throws InvalidDatasetException {
        try {
            Map<String, Pair<Integer, Integer>> stopsPosition = new HashMap<>();
            List<String> data = getDataFromFile(DEFAULT_DATASET_PATH + dataset + "/xy.txt");
            for (String line : data) {
                String[] splitLine = line.split("\t");
                if (isNumeric(splitLine[splitLine.length - 1])) {
                    String stop = splitLine[0];
                    int stopX = Integer.parseInt(splitLine[1]);
                    int stopY = Integer.parseInt(splitLine[2]);
                    stopsPosition.put(stop, new Pair<>(stopX, stopY));
                }
            }
            return stopsPosition;
        } catch (IOException e) {
            throw new InvalidDatasetException();
        }
    }

    /**
     * Auxiliary method to determine if a given string is a number.
     *
     * @param string String the String to test
     * @return TRUE if the string is a number, FALSE otherwise
     */
    private static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException ne) {
            return false;
        }
        return true;
    }
}
