import java.util.HashSet;

/**
 *
 * @author Stefano.
 *         Created 02 nov 2016.
 */
public class Forwarder {

	private HashSet<Integer> link;
	
	public Forwarder(HashSet<Integer> link) {
		this.link = link;
	}

	public void send(Message message, int port) {
		//TODO
	}
	
	public void sendAll(Message message) {
		//TODO
	}
	
	public void sendAll(Message message, int port) {
		//TODO
	}
	
}
