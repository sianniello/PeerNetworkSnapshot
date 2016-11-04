package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerHandler implements Runnable {

	private Socket client;
	private ObjectOutputStream out;
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

		out = new ObjectOutputStream(client.getOutputStream());
		in = new ObjectInputStream(client.getInputStream());

	}

	@Override
	public void run() {

		try {
			Message message = (Message) in.readObject();	//il peer riceve il messaggio
			System.out.println("Lato Server Peer " + port + ", ho ricevuto: " + message);

			if(message.getMarker().getMarkerID() == 0)
				state.setState(message.getBody());	//accodo il body del messaggio allo stato (aggiorno stato)

			if(message.getMarker().getMarkerID() == 1)
				if(markerMap.containsKey(message.getMarker())) 
					if(message.getMarker().getProcessID() == port) {
						new Forwarder(link).sendAll(message, message.getWho());
					}
					else {
						markerMap.put(message.getMarker(), port);	//aggiungo il marker alla treemap e aggiorno il campo who
					}

		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
