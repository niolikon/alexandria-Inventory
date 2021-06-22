package org.niolikon.alexandria.inventory.system.exceptions;

public class OperationNotAcceptableException extends RuntimeException {
	
    /** */
	private static final long serialVersionUID = 8234897904666665503L;

	public OperationNotAcceptableException() {
        super();
    }

    public OperationNotAcceptableException(String message) {
        super(message);
    }

    public OperationNotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }
}
