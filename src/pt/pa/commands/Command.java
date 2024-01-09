package pt.pa.commands;

/**
 * Interface Command for the usage of the 'Command' pattern.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */

public interface Command {
    /**
     * Executes a command.
     */
    void execute();

    /**
     * Revert a command (add, remove).
     */
    void undo();
}
