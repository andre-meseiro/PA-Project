package pt.pa.commands;
import java.util.Stack;

/**
 * This class represents the commands used.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class CommandsUsed {
    private Stack<Command> usedCommands = new Stack<>();

    /**
     * Pushes the used command to the stack.
     *
     * @param c Comando Command used
     */
    public void push(Command c) {
        usedCommands.push(c);
    }

    /**
     * Pops the latest command from the stack.
     *
     * @return Comando Command that was done before
     */
    public Command pop() {
        return usedCommands.pop();
    }

    /**
     * Returns if the stack is empty or not.
     *
     * @return TRUE if empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return usedCommands.isEmpty();
    }
}
