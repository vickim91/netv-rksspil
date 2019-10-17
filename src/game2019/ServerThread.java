package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket;


	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public synchronized void run() {

		while (true) {

			try {
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = inFromServer.readLine();
				//system.out.println("recieved: " + message);
				String[] sSplit = message.split(" ");
			
			
				if(sSplit[0].equals("spawn") && sSplit.length ==5)
				{
					Server.addPlayer(message);
				}
				else if (sSplit[0].equals("move")&& sSplit.length ==5)
				{
					//Server.sendToClients(message);
					Server.movePlayer(message);
			
				}
				else if (message.equals("ready")){
					Server.ready();
				}
				
			
//			
//				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}