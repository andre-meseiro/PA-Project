package pt.pa.imports;

import pt.pa.io.imports.ImportData;
import pt.pa.io.imports.InvalidDatasetException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the ImportDataToMap class.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
class ImportDataTest {

    @Test
    void getDataFromFile_Test() {
        List<String> data = null;
        try {
            data = ImportData.getDataFromFile("datasets/demo/stops.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(data);
    }

    @Test
    void loadStopsToMap_Test() {
        Map<String, Stop> stopsMap = null;
        try {
            stopsMap = ImportData.loadStopsToMap("demo");
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(stopsMap);
        assertEquals(13, stopsMap.size());
    }

    @Test
    void loadRoutesDurationToMap_Test() {
        Map<Pair<String, String>, Integer> routesDurationMap = null;
        try {
            routesDurationMap = ImportData.loadRoutesPropertiesToMap("datasets/demo/routes-duration.txt");
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertEquals(14, routesDurationMap.size());
    }

    @Test
    void loadRoutesDistanceToMap_Test() {
        Map<Pair<String, String>, Integer> routesDistanceMap = null;
        try {
            routesDistanceMap = ImportData.loadRoutesPropertiesToMap("datasets/demo/routes-distance.txt");
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertEquals(14, routesDistanceMap.size());
    }

    @Test
    void loadRoutesToMap_Test() {
        Map<Pair<String, String>, Route> routesMap = null;
        try {
            routesMap = ImportData.loadRoutesToMap("demo");
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(routesMap);
        assertEquals(14, routesMap.size());
    }

    @Test
    void loadStopsPosition_Test() {
        Map<String, Pair<Integer, Integer>> stopsPosition = null;
        try {
            stopsPosition = ImportData.loadStopsPosition("demo");
        } catch (InvalidDatasetException e) {
            throw new RuntimeException(e);
        }
        assertEquals(13, stopsPosition.size());
    }
}