package pt.pa.model;

/**
 * Model class that represents a route.
 * <br>
 * Keeps information associated with the files to import.
 * <br>
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class Route {
    private int distance;
    private int duration;

    /**
     * Creates a Route instance.
     *
     * @param distance int the distance
     * @param duration int the duration
     */
    public Route(int distance, int duration) {
        this.distance = distance;
        this.duration = duration;
    }

    /**
     * Returns the distance.
     *
     * @return int the distance
     */
    public int getDistance() { return distance; }

    /**
     * Returns the duration.
     *
     * @return int the duration
     */
    public int getDuration() { return duration; }

    @Override
    public String toString() {
        return "[" + distance + ", " + duration + "]";
    }
}