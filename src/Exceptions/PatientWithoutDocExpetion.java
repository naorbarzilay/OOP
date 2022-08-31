package Exceptions;

public class PatientWithoutDocExpetion extends Exception{

	private static final long serialVersionUID = 1L;
	
	public PatientWithoutDocExpetion(String msg){
		super(msg);
	}
}