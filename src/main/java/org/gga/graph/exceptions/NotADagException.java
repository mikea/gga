package org.gga.graph.exceptions;

/**
 * @author mike.aizatsky
 */
public class NotADagException extends RuntimeException {
    private static final long serialVersionUID = -3990269129576672578L;

    public NotADagException() {
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
