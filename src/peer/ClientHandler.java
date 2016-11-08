package peer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;


class ClientHandler implements Runnable {
	private int port;	// questa è la porta della parte Server del peer
	private HashSet<Integer> link;
	private State state;
	private int markerId;
	private Forwarder forwarder;
	private boolean initiator;
	private static boolean enable = true;	//abilita lo snapshot

	@SuppressWarnings("javadoc")
	public ClientHandler(int port, HashSet<Integer> link, State state, boolean initiator) {
		this.port = port;
		this.state = state;
		this.link = link;
		this.initiator = initiator;
		this.forwarder = new Forwarder(link);
	}

	@Override
	public void run() {

		Random random = new Random();

		for(;;)
			try {

				//l'initiator inizia uno snapshot ogni 2' (circa)
				if(initiator && LocalDateTime.now().getMinute() % 2 == 0 && enable) 
					startSnapshot();

				new Forwarder(link).sendAll(new Message(new Marker(port, 0), "Hello! Timestamp: [" + new java.sql.Timestamp(System.currentTimeMillis()) + "] ", port));

				//il client invia messaggi in tempi random tra 5 e 30 secondi
				Thread.sleep(random.nextInt(30001 - 5000) + 5000);

			} catch (InterruptedException | IOException exception) {
				exception.printStackTrace();
			}
	}

	public void startSnapshot() {
		enable = false;
		
		System.out.println("Il peer " + port + " inizia lo snapshot!");

		//lo stato è una risorsa condivisa tra lato client e server del peer
		synchronized (this) {
			state.setState(state.getState());		//il peer registra il suo stato
		}

		try {

			forwarder.sendAll(new Message(new Marker(port, 1), "", port));		//il peer invia in broadcast il marker

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
