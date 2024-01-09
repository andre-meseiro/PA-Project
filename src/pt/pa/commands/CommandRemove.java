package pt.pa.commands;
import pt.pa.graph.Edge;
import pt.pa.view.routemanage.RouteInvalidOperation;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.controller.*;

/**
 * This class represents the removal of a command.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class CommandRemove implements Command{
    public RouteManagerController controller;
    public ImportToGraph model;
    private String backupInitialStop;
    private String backupEndStop;
    private int backupDistance;
    private int backupDuration;

    /**
     * Creates a CommandRemove instance.
     *
     * @param controller RouteManagerController controller
     * @param model      ImportToGraph model
     * @param iStop      Stop initial stop
     * @param eStop      Stop final stop
     */

    public CommandRemove(RouteManagerController controller, ImportToGraph model, String iStop, String eStop) {
        this.controller = controller;
        this.model = model;
        backupInitialStop = iStop;
        backupEndStop = eStop;
    }

    @Override
    public void execute() {
        Edge<Route, Stop> backupRoute = controller.doRemoveRoute();
        backupDistance = backupRoute.element().getDistance();
        backupDuration = backupRoute.element().getDuration();
    }

    @Override
    public void undo() {
        try {
            model.addRoute(backupInitialStop, backupEndStop, backupDistance, backupDuration);
        } catch (RouteInvalidOperation e) {
            throw new RuntimeException(e);
        }
    }
}
