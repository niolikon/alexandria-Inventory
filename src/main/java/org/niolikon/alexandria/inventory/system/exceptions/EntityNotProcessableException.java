package org.niolikon.alexandria.inventory.system.exceptions;

public class EntityNotProcessableException extends RuntimeException {
	
    /** */
	private static final long serialVersionUID = 1690942377521149668L;

	public EntityNotProcessableException() {
        super();
    }

    public EntityNotProcessableException(String message) {
        super(message);
    }

    public EntityNotProcessableException(String message, Throwable cause) {
        super(message, cause);
    }
}
