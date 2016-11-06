package peer;
/**
 *
 * @author Stefano.
 *         Created 02 nov 2016.
 */
public class State {

	private String state;

	@SuppressWarnings("javadoc")
	public State() {
		this.state = "";
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
	;
	public String toString() {
		return "State: " + state + ".";
	}

}
