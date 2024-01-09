package pt.pa.model;

/**
 * Model class that represents a stop.
 * <br>
 * Keeps information associated with the files to import.
 * <br>
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class Stop {
    private String stopCode;
    private String stopName;
    private double latitude;
    private double longitude;

    /**
     * Creates a Stop instance.
     *
     * @param stopCode  String the stop code
     * @param stopName  String the stop name
     * @param latitude  int the latitude
     * @param longitude int the longitude
     */
    public Stop(String stopCode, String stopName, double latitude, double longitude) {
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the stop code.
     *
     * @return String the stop code
     */
    public String getStopCode() {
        return stopCode;
    }

    /**
     * Returns the name of the stop.
     *
     * @return String the name of the stop
     */
    public String getStopName() {
        return stopName;
    }

    /**
     * Returns the latitude of the stop.
     *
     * @return String the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the stop.
     *
     * @return String the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return stopCode;
    }

    /**
     * Returns the information of the stop in a String format.
     *
     * @return String the info
     */
    public String toStringDetails() {
        return stopCode + " | " + stopName + " | " + latitude + " | " + longitude;
    }
}