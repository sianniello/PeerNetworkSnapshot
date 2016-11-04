package peer;

import java.io.Serializable;

public class Marker implements Serializable{

	int processID;
	int markerID;
	
	public Marker() {
		
	}
	
	public Marker(int processID, int markerID) {
		this.processID = processID;
		this.markerID = markerID;
	}
	
	public int getProcessID() {
		return processID;
	}
	
	public int getMarkerID() {
		return markerID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + markerID;
		result = prime * result + processID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marker other = (Marker) obj;
		if (markerID != other.markerID)
			return false;
		if (processID != other.processID)
			return false;
		return true;
	}
	
}
