
/**
 *
 * @author Stefano.
 *         Created 02 nov 2016.
 */
public class State {

	private String state;

	public State() {
		state = "";
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	;
	public String toString() {
		return "State: " + state + ".";
	}

}
