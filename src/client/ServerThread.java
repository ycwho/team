package client;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Total total = null;
    private Socket client = null;

    public ServerThread(Total total, Socket client,int i) {
				super("ServerThread"+i);
				this.total = total;
				this.client = client;
    }

    public void run() {
				//DataOutputStream toClient;
				//DataInputStream  fromClient;

				try {
						//toClient = new DataOutputStream(client.getOutputStream());
						//fromClient  = new DataInputStream(client.getInputStream());
						BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
						BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
						int i;
						
            // The message 0 means "end" (simple protocol) 
						String get;
						
						System.out.println("waiting for client respond");
						
						while(!(get = fromClient.readLine()).equals(Protocol.DISCONNECTION)) {
							System.out.println(get);
							if(get.equals("hychyc123456")) {
								toClient.write("log success");
								toClient.newLine();
								toClient.flush();
							}else {
								toClient.write("1");
								toClient.newLine();
								toClient.flush();
							}
						}
						toClient.close();
						fromClient.close();
						client.close();
				}
						
/*						while ((i = fromClient.readInt()) != 0) {
                total.add(i);
								System.out.println("Got " + i + " from client"+this.getName());
                System.out.println("Sent " + total.get() + " to client" + this.getName());
								toClient.writeInt(total.get());
						}

						System.out.println("Got " + i + " from client");
						toClient.close();
						fromClient.close();
						client.close();
				} */
				catch (IOException e) {
						System.out.println("Something went wrong. Ending service to client...");
						return;
				}
    }
}
