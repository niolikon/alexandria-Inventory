package org.niolikon.alexandria.inventory.system.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
    /** */
	private static final long serialVersionUID = -1464966580174742510L;

	public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
