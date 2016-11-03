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
    
    public ServerHandler(Socket client, int port, HashSet<Integer> link, TreeMap<Marker, Integer> markerMap) throws IOException {
        this.client = client;
        this.port = port;
        this.link = link;
        this.markerMap = markerMap;
        
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        
    }

    @Override
    public void run() {
    
        try {
            String message = (String) in.readObject();
            System.out.println("Lato Server Peer " +  ": ho ricevuto" + message);
            out.writeObject(message);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
