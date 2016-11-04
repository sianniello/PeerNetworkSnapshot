package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	@Override
	public void run() {

		try {
			Message message = (Message) in.readObject();	//il peer riceve il messaggio

			if(message.getMarker().getMarkerID() == 0)
				System.out.println("Lato Server Peer " + port + ", riceve: " + message);

			if(message.getMarker().getMarkerID() == 1) {
				System.out.println("Lato Server Peer " + port + ", riceve un marker");
				
				if(state.getState().equals("")) {			//se il peer non ha ancora registrato il proprio stato
					state.setState(message.getBody());		//registra il suo stato
					markerMap.put(message.getMarker(), message.getWho());
					int sender = message.getWho();
					message.setWho(port);
					new Forwarder(link).sendAll(message, sender);
				}
				
				if(markerMap.containsKey(message.getMarker())) 
					if(message.getMarker().getProcessID() == port) {
						new Forwarder(link).sendAll(message, message.getWho());
					}
					else {
						markerMap.put(message.getMarker(), port);	//aggiungo il marker alla treemap e aggiorno il campo who
					}
			}
		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
