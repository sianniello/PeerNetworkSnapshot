package peer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

/**
 *
 * @author Stefano.
 *         Created 02 nov 2016.
 */
public class Forwarder {

	private HashSet<Integer> link;
	private ObjectOutputStream out;

	@SuppressWarnings("javadoc")
	public Forwarder() {
	}
	
	@SuppressWarnings({ "javadoc" })
	public Forwarder(HashSet<Integer> link) {
		this.link = link;
	}

	/**
	 * Spedizione di un messaggio semplice attraverso una data porta.
	 *
	 * @param message
	 * @param port
	 * @throws IOException
	 */
	@SuppressWarnings({ "unqualified-field-access", "resource" })
	public void send(Message message, int port) throws IOException {
		for(Integer i : link)
			if(i == port) {
				Socket client = new Socket("localhost", i);
				out = new ObjectOutputStream(client.getOutputStream());
				out.writeObject(message);
				client.close();
			}
	}

	/**
	 * Broadcast su tutto il link.
	 *
	 * @param message
	 * @throws IOException
	 */
	@SuppressWarnings("unqualified-field-access")
	public void sendAll(Message message) throws IOException {
		for(Integer i : link) {
			Socket client = new Socket("localhost", i);
			out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(message);
			client.close();
		}
	}

	/**
	 * Broadcast su tutto il link tranne sulla porta da cui si è ricevuto il marker.
	 *
	 * @param message
	 * @param port
	 * @throws IOException
	 */
	@SuppressWarnings("unqualified-field-access")
	public void sendAll(Message message, int port) throws IOException {
		for(Integer i : link) {
			if(i != port) {
				Socket client = new Socket("localhost", i);
				out = new ObjectOutputStream(client.getOutputStream());
				out.writeObject(message);
				client.close();
			}
		}
	}

}
