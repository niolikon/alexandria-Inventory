package org.niolikon.alexandria.inventory.system.exceptions;

public class ApplicationException extends RuntimeException {
	
    /** */
	private static final long serialVersionUID = 1546511869585243010L;

	public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
