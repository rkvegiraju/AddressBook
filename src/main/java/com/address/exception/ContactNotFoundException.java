package com.address.exception;

public class ContactNotFoundException extends Exception {
   
	private static final long serialVersionUID = -8907767902552258753L;

	public ContactNotFoundException(String message) {
        super(message);
    }

}
