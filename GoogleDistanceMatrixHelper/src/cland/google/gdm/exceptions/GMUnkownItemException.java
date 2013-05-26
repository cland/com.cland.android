package cland.google.gdm.exceptions;

public class GMUnkownItemException extends Exception {

	/**
	 * This exception thown when when JsonPath failes to extract a value from the supplied json data.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GMUnkownItemException(String message) {
		super(message);
	}
	
	public GMUnkownItemException(String message, Throwable e) {
		super(message, e);
	}

	@Override
	public String toString() {
		return "GMUnkownItemException [getMessage()=" + getMessage() + "]";
	}
	
	
	
} //end class
