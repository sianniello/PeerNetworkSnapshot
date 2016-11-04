package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamTokenizer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer {

	private int port;
	private State state;
	private HashSet<Integer> link;
	private TreeMap<Marker, Integer> markerMap;

	@SuppressWarnings({ "javadoc", "unqualified-field-access", "resource" })
	public Peer(int port) throws IOException, ClassNotFoundException {
		this.port = port;
		state = new State();
		join();
		
		ServerSocket server = new ServerSocket(port);

		(new Thread(new ClientHandler(port, link, state))).start();

		Executor executor = Executors.newFixedThreadPool(1500);

		while(true){
			executor.execute(new ServerHandler(server.accept(), port, link, markerMap, state));
		}

	}

	@SuppressWarnings({ "javadoc", "resource" })
	public void join() throws IOException, ClassNotFoundException{
		Socket client = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		client = new Socket("localhost", 1099);
		out = new ObjectOutputStream(client.getOutputStream());
		in = new ObjectInputStream(client.getInputStream());

		out.writeObject(new InetSocketAddress(port));	//il client invia al joinserver il proprio indirizzo
		link = (HashSet<Integer>) in.readObject();	//il client riceve dal joinserver l'hashset dei suoi vicini
		System.out.print("Sono il client " + port + " i miei vicini sono: " + link.toString());
	}

	public static void main(String[] args) {

		if(args.length<1){
			System.err.println("BAD USAGE: inseri porta");
			System.exit(0);
		}

		try {
			Peer peer = new Peer(Integer.parseInt(args[0]));
		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}



}
