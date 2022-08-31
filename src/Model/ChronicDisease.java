package Model;


import java.util.HashSet;

import Utils.Symptoms;

public class ChronicDisease extends Disease {
	private boolean isInheritence;

	public ChronicDisease(int id, String name, HashSet<Symptoms> symptoms, boolean isInheritence) {
		super(id, name, symptoms);
		this.isInheritence = isInheritence;
	}
	
	public ChronicDisease(int id) {
		super(id);
	}

	public boolean isInheritence() {
		return isInheritence;
	}

	public void setInheritence(boolean isInheritence) {
		this.isInheritence = isInheritence;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
