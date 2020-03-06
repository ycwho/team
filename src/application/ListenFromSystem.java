package application;

import java.io.BufferedReader;
import java.io.IOException;

public class ListenFromSystem extends Thread{
	public BufferedReader fromServer;
	
	public ListenFromSystem(BufferedReader fromServer) {
		this.fromServer = fromServer;
	}
	
	public void run() {
		while(true) {
			try {
			
				System.out.println("[Server]" + fromServer.readLine());
				
			} catch (IOException e) {
				
				e.printStackTrace();
				break;
			}
		}
	}
	

}
