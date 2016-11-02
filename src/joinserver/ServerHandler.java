/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joinserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniele
 */
class ServerHandler implements Runnable {
     private Socket client;
     private ObjectOutputStream out;
     private ObjectInputStream in;
	private TreeMap<Integer, HashSet<Integer>> map;
     
     
    public ServerHandler(Socket client, TreeMap<Integer, HashSet<Integer>> map) throws IOException {
        this.client = client;
        this.map = map;
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        
    }

    @Override
    public void run() {
    
         try {
             InetSocketAddress address = (InetSocketAddress) in.readObject();
             System.out.println("Server " + Thread.currentThread().getName() + " ricevuto: " + address.toString()); 
             
             out.writeObject(map.get(address.getPort()));
             System.out.println("Il Server risponde: " + map.get(address.getPort()).toString()); 
             
         } catch (IOException | ClassNotFoundException ex) {
             Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
