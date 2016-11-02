/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joinserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JoinServer {

	private int port;
	private ServerSocket server;
	private TreeMap<Integer, HashSet<Integer>> map;

	public JoinServer(int port) throws IOException {
		this.port = port;
		server = new ServerSocket(port);
		map = new TreeMap<>();
		loadMap();
		System.out.println("Server listening at: " + port);
		System.out.println("Map: " + map.toString());
	}

	public void loadMap() throws IOException {
		FileReader fr = new FileReader("map.txt");
		String[] str;
		HashSet<Integer> hs = new HashSet<>();
		fr = new FileReader("map.txt");
		BufferedReader br = new BufferedReader(fr);
		String s = br.readLine();
		while(s != null) {
			str = s.split("\t");
			for(int i = 1; i < str.length; i++)
				hs.add(Integer.parseInt(str[i]));
			map.put(Integer.parseInt(str[0]), (HashSet<Integer>) hs.clone());
			hs.clear();
			s = br.readLine();
		}
	}

	public void execute() throws IOException{

		Socket client = null;
		
		Executor executor = Executors.newFixedThreadPool(1000);

		while(true){
			client = server.accept();
			executor.execute(new ServerHandler(client, map));
		}

	}

	public static void main(String[] args) throws IOException {

		JoinServer jserver = new JoinServer(1099);

		try {
			jserver.execute();
		} catch (IOException ex) {
			Logger.getLogger(JoinServer.class.getName()).log(Level.SEVERE, null, ex);
		}finally{
			if(! jserver.server.isClosed()) 
				jserver.server.close();
		}
	}


}
