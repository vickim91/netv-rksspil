package game2019;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptThread {
	private Server server;
	
	public AcceptThread(Server server)
	{
		this.server = server;
	}
	
	public void run()
	{
		ServerSocket welcomeSocket = server.getWelcomeSocket();
		try {
			Socket connectionSocket = welcomeSocket.accept();
			server.setConnectionSocket(connectionSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
