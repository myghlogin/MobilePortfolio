package com.interactiveball.api.exception;

/**
 * @author Mike
 * Exception should be thrown always when the control flow is passed to unknown branch. 
 * For example consider the enumeration with following elements {A, B, C} and use code for
 * verifying all of them like this: 
 * 
 * 		if(x == E.A){...}
 * 		else if(x == E.B){...}
 * 		else if(x == E.C){...}
 * 		else{
 * 			throw new UnknownCaseException();
 * 		}
 * 
 * So you will always be informed about the transition to the branch ELSE.
 * It is very conveniently when you can add a new element in enumeration in the future so as
 * you be able to get an exception for a new element if you didn't edit if-else operator.     
 */
public final class UnknownCaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnknownCaseException(String detailMessage) {
		super(detailMessage);
	}

	public UnknownCaseException() {
		super();
	}
	
	
}
