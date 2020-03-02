package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	public static void main(String[] args) throws IOException {
		try (ServerSocket listener = new ServerSocket(50000)) {
	         System.out.println("Server Running");
	         ExecutorService pool = Executors.newFixedThreadPool(20);
	         while (true) {
	             pool.execute(new Login(listener.accept()));
	             
	         }
	     }
	}

	 
}

