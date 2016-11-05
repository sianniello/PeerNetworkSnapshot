package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Stefano.
 *         Created 05 nov 2016.
 */
class ServerHandler implements Runnable {

	private Socket client;
	private ObjectInputStream in;
	private State state;
	private int port;
	private HashSet<Integer> link;
	private TreeMap<Marker, Integer> markerMap;

	@SuppressWarnings("javadoc")
	public ServerHandler(Socket client, int port, HashSet<Integer> link, TreeMap<Marker, Integer> markerMap, State state) throws IOException {
		this.client = client;
		this.port = port;
		this.link = link;
		this.markerMap = markerMap;
		this.state = state;

		in = new ObjectInputStream(client.getInputStream());

	}

	@SuppressWarnings("unqualified-field-access")
	@Override
	public void run() {

		try {
			Message message = (Message) in.readObject();	//il peer riceve il messaggio

			if(message.getMarker().getMarkerID() == 0)		//il peer ha ricevuto un messaggio normale
				System.out.println("Lato Server Peer " + port + ", riceve: " + message);

			if(message.getMarker().getMarkerID() == 1) {	//il peer ha ricevuto un marker di uno snapshot
				System.out.println("Lato Server Peer " + port + ", riceve un marker");

				int sender = message.getWho();

				if(state.getState().equals("")) {			//se il peer non ha ancora registrato il proprio stato

					synchronized (this) {
						state.setState(message.getBody()); //registra il suo stato
						markerMap.put(message.getMarker(), message.getMarker().getProcessID());
					}

					message.setWho(port);	//aggiorno il mittente del messaggio e inoltro a tutti tranne che al mittente
					new Forwarder(link).sendAll(new Message(new Marker(port, 1), state.getState(), port), sender);
				}

				if(markerMap.containsKey(message.getMarker())) {
					if(message.getMarker().getProcessID() == port) {
						//TODO il peer è consapevole di questo snapshot
					}
				}
				else {

					synchronized (this) {
						markerMap.put(message.getMarker(), message.getMarker().getProcessID()); //aggiungo il marker alla treemap
					}
				}
				if(markerMap.size() == link.size())
					new Forwarder().send(new Message(new Marker(sender, 1), state.getState(), port), sender);
			}
		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
