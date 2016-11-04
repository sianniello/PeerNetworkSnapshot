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

	@SuppressWarnings("javadoc")
	public ClientHandler(int port, HashSet<Integer> link, State state) {
		this.port = port;
		this.state = state;
		this.link = link;
		forwarder = new Forwarder(link);
	}

	@Override
	public void run() {

		Random random = new Random();
		
		if(port == 10001 && LocalDateTime.now().getMinute() % 5 == 0)
			startSnapshot();

		for(;;)
			try {
				new Forwarder(link).sendAll(new Message(new Marker(port, 0), "Ciao", port));
				
				//il client invia messaggi in tempi random tra 5 e 60 secondi
				Thread.sleep(random.nextInt(60000 - 5000) + 5000);
				
			} catch (IOException | InterruptedException exception) {
				exception.printStackTrace();
			}
	}

	public void startSnapshot() {
		//TODO aggiorno stato
	}

}
