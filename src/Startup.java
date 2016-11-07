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
	 * @param args intero, specifica quale peer sarà l'initiator
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("cast")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		if(args.length != 0)
			//il ciclo crea 7 peer di cui uno sarà l'initiator
			for(int i = 0; i <= 7; i++) {
				if(10000 + i == Integer.parseInt(args[0]))
					new Thread((Runnable) new Peer(10000 + i, true)).start();
				else
					new Thread((Runnable) new Peer(10000 + i, false)).start();
			}
		else
			//se non viene specificato nel parametro nessun peer sarà initiator
			for(int i = 0; i <= 7; i++)
				new Thread((Runnable) new Peer(10000 + i, false)).start();
	}
}
