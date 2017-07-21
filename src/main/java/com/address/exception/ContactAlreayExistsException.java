package com.address.exception;

public class ContactAlreayExistsException extends Exception {
   
	private static final long serialVersionUID = -8907767902552258753L;

	public ContactAlreayExistsException(String message) {
        super(message);
    }

}
