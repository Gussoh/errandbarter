/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter;

/**
 *
 * @author Gussoh
 */
public class OperationException extends Exception {

    private boolean cancelled = false;

    public OperationException(String s) {
        super(s);
    }

    /**
     * Use if operation was cancelled
     * @param cancelled
     */
    public OperationException(boolean cancelled) {
        super("Operation Cancelled");
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
