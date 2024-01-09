package pt.pa.commands;

import pt.pa.graph.Edge;
import pt.pa.view.routemanage.RouteInvalidOperation;
import pt.pa.io.imports.ImportToGraph;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.controller.*;

/**
 * This class represents the addition of a command.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class CommandAdd implements Command{
    private Edge<Route, Stop> backup;
    public RouteManagerController controller;
    public ImportToGraph model;

    /**
     * Creates an AddCommand instance.
     *
     * @param controller RouteManagerController controller
     * @param model      ImportToGraph model
     */
    public CommandAdd(RouteManagerController controller, ImportToGraph model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public void execute() {
        backup = controller.doAddRoute();
    }

    @Override
    public void undo(){
        try {
            model.removeRoute(backup.vertices()[0].element().getStopName(), backup.vertices()[1].element().getStopName());
        } catch (RouteInvalidOperation e) {
            throw new RuntimeException(e);
        }
    }

}
