package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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

	private ObjectInputStream in;
	private State state;
	private int port;
	private HashSet<Integer> link;
	private TreeMap<Marker, Integer> markerMap;
	private boolean initiator;
	private HashMap<Integer, String> hs;

	@SuppressWarnings("javadoc")
	public ServerHandler(Socket client, int port, HashSet<Integer> link, TreeMap<Marker, Integer> markerMap, State state, 
			boolean initiator, HashMap<Integer, String> hs) throws IOException {
		this.port = port;
		this.link = link;
		this.markerMap = markerMap;
		this.state = state;
		this.in = new ObjectInputStream(client.getInputStream());
		this.initiator = initiator;
		this.hs = hs;
	}

	@SuppressWarnings("unqualified-field-access")
	@Override
	public void run() {
		try {
			Message message = (Message) in.readObject();	//il peer riceve il messaggio

			if(message.getMarker().getMarkerID() == 0)	{	//il peer ha ricevuto un messaggio normale
				System.out.println("\nLato Server Peer " + port + ", riceve un messaggio: " + message);
				state.setState(message.getBody());
			}

			else if(message.getMarker().getMarkerID() == 1 && message.getWho() != port ) {	//il peer ha ricevuto un marker di uno snapshot
				int sender = message.getWho();

				System.out.println("\nLato Server Peer " + port + ", riceve un marker dal peer " + message.getWho());
				System.out.println("Lato Server Peer " + port + " MarkerMap: " + markerMap.toString());

				if(markerMap.isEmpty() && !initiator) {			//se il peer non ha ancora registrato il proprio stato locale (diventa rosso)

					synchronized (this) {
						markerMap.put(message.getMarker(), sender);
						state.incNumMarker();
					}
					System.out.println("Lato Server Peer " + port + "  aggiornamento markerMap: " + 
					markerMap.toString() + " nm = " + state.getMarker());
					message.setWho(port);
					new Forwarder(link).sendAll(message, sender);	//invio marker su tutti gli altri canali di uscita
				}
				//il peer è consapevole dello snapshot in atto(la mappa contiene almeno un elemento) ma non ha ancora registrato lo stato dei vicini
				else if(markerMap.containsKey(message.getMarker()) && state.getMarker() < link.size() 
						&& !markerMap.containsValue(sender) && !initiator) {

					synchronized (this) {
						state.incNumMarker();
					}

					System.out.println("Lato Server Peer " + port + "  marker già presente: " + 
					markerMap.toString() + " nm = " + state.getMarker());
				}
				//l'initiator raccoglie lo stato della rete nella struttura dati "hs"
				else if(initiator && !hs.containsKey(sender) && !message.getBody().isEmpty()) {
					hs.put(sender, message.getBody());
					if(hs.keySet().containsAll(link)) {
						hs.put(port, state.getState());
						System.out.println("Initiator: SNAPSHOT FINITO!!" + hs.keySet().toString());

						synchronized (this) {
							new PrintToFile(hs).print();
						}

					}
					else System.out.println("Initiator: " + hs.keySet().toString());
				}
				//il peer ha completato la registrazione dello stato
				if(state.getMarker() == link.size() && !initiator) {
					System.out.println("Lato Server Peer " + port + "  ha finito di registrare il suo stato");
					message.setWho(port);
					new Forwarder(link).sendAll(message);
					new Forwarder().send(new Message(message.getMarker(), state.getState(), port), 
							message.getMarker().getProcessID());
					System.out.println("Lato Server Peer " + port + "  spedisce il suo stato all'Initiator: " + 
					message.getMarker().getProcessID());
					state.incNumMarker();
				}
			}

		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
