
package Exceptions;

public class CannotRemoveException extends Exception{

	private static final long serialVersionUID = 1L;

	public CannotRemoveException(String msg){
		super(msg);
	}
}