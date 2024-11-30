package com.registration.exception;



public class EmailExistException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7083687516688692671L;

	public EmailExistException(String message) {
        super(message);
    }
	
}
