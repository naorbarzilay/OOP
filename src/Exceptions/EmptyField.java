package Exceptions;

import Utils.ReleaseNote;

public class EmptyField extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyField(String msg){
		super(msg);
	}
}
