package org.gga.graph.exceptions;

/**
 * @author mike.aizatsky
 */
public class NotADagException extends RuntimeException {
    public NotADagException() {
        super();
    }

    public NotADagException(String s) {
        super(s);
    }

    public NotADagException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotADagException(Throwable throwable) {
        super(throwable);
    }
}
