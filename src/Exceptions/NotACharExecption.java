package Exceptions;

import Utils.ReleaseNote;

public class NotACharExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotACharExecption(String msg){
		super(msg);
	}
}
