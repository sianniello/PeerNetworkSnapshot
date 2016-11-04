import java.io.IOException;

import joinserver.JoinServer;
import peer.Peer;

/**
 * @author Stefano
 *
 */
public class Startup {

	/**
	 * 
	 */
	public Startup() {
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		for(int i = 0; i <= 7; i++)
			new Thread((Runnable) new Peer(10000 + i)).start();
	}

}