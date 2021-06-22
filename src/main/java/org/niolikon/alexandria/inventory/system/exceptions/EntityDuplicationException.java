package org.niolikon.alexandria.inventory.system.exceptions;

public class EntityDuplicationException extends RuntimeException {
	
    /** */
	private static final long serialVersionUID = -7076254388472242748L;

	public EntityDuplicationException() {
        super();
    }

    public EntityDuplicationException(String message) {
        super(message);
    }

    public EntityDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
