package peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class ClientHandler implements Runnable {
	private int port;	// questa è la porta della parte Server del peer
	private HashSet<Integer> link;
	private State state;
	private int markerId;

	public ClientHandler(int port, HashSet<Integer> link, State state) {
		this.port = port;
		this.state = state;
		this.link = link;
	}

	//	@Override
		public void run() {
	//
	//		for(;;){
	//			try {
	//
	//				Socket client = new Socket("localhost", peerPort);
	//				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
	//				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
	//
	//				out.writeObject(message);
	//				System.out.println("Peer " + peerPort + " ha risposto: " + (String) in.readObject());
	//
	//			} catch (IOException | ClassNotFoundException ex) {
	//				Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
	//			}
	//		}
		}

}
