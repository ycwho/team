package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main (String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(50000)) {
			System.out.println("Server is Running");
			ExecutorService pool = Executors.newFixedThreadPool(10);
			int i = 0;
			while (true) {
				i++;
				pool.execute(new ServerThread(serverSocket.accept(), i));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

