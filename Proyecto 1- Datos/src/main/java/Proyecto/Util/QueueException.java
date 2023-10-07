package Proyecto.Util;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2023-08-15
 *
 * --------------------------------------------------------------------
 */

public class QueueException extends Exception {

    public QueueException(String message) {
        super(message);
    }

    public QueueException() {
        this("Queue Exception");
    }

}
