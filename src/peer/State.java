package peer;
/**
 *
 * @author Stefano.
 *         Created 02 nov 2016.
 */
public class State {

	private String state;
	private int nm;

	@SuppressWarnings("javadoc")
	public State() {
		this.state = "";
		this.nm = 0;	//numero di marker ricevuti dal peer durante lo snapshot
	}
	
	@SuppressWarnings("javadoc")
	public State(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	/**
	 * Accoda al campo state della classe la stringa state passata come parametro.
	 *
	 * @param state
	 */
	public void setState(String state) {
		this.state = this.state + state;
	}
	
	/**
	 * Questa funzione incrementa il numero di maker ricevuti dal peer durante lo snapshot
	 *
	 */
	public void incNumMarker() {
		nm++;
	}
	
	public int getMarker() {
		return nm;
	}
	
	public String toString() {
		return "State: " + state + ".";
	}

}
