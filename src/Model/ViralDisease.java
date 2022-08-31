package Model;


import java.io.Serializable;
import java.util.HashSet;

import Utils.Symptoms;

public class ViralDisease extends Disease implements Serializable  {
	private boolean isInfectious;

	public ViralDisease(int id, String name, HashSet<Symptoms> symptoms, boolean isInfectious) {
		super(id, name, symptoms);
		this.isInfectious = isInfectious;
	}
	
	public ViralDisease(int id) {
		super(id);
	}

	public boolean isInfectious() {
		return isInfectious;
	}

	public void setInfectious(boolean isInfectious) {
		this.isInfectious = isInfectious;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
