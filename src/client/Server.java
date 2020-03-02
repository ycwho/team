package client;
import java.net.*;
import java.io.*;

/* Server - a simple example of a server, that accumulates a total
   Protocol: 
   - A client can send a non-zero integer, which gets added to the total.
   - The new total is sent back to the client.
   - Sending a zero terminates the connection.
   - Sending anything else is an error.
 */

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

				// This will be shared by all clients:
        Total total = new Total();

        // Open a server socket:
        try {
            serverSocket = new ServerSocket(50000);
        } 
				catch (IOException e) {
            System.err.println("Couldn't listen on port: 50000.");
            System.exit(-1);
        }

				// Listen to the socket, accepting connections from new clients,
				// and running a new thread to serve each new client:
				try {
						int i =0;
						while (true) {
							i++;
							
							
								Socket clientSocket = serverSocket.accept();
								
								System.out.println("get "+" clients" + i);
								// get stuck until somebody connects
                ServerThread s = new ServerThread(total, clientSocket, i);
								s.start();
						}
        } 
        catch (Exception e) {

				}
    }
}
