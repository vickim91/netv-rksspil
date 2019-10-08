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

	public void run() {

		while (true) {

			try {
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = inFromServer.readLine();
				System.out.println("recieved: " + message);
				String[] sSplit = message.split(" ");
				if(sSplit[0].equals("spawn") && sSplit.length ==5)
				{
					Server.addPlayer(message);
				}
				else if (sSplit[0].equals("move"))
				{
					//Server.sendToClients(message);
					Server.movePlayer(message);
				}
				else if (sSplit[0].equals("score"))
				{
					Server.addPointToPlayer(message);
				}
		
//				for (ServerThread s : Server.getThreads()) {
//					System.out.println(s.toString());
//					s.pushMessage(message); //lav en hjælpe metode på serveren der er synkroniseret. 
//				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	public void pushMessage(String message) {
//
//		DataOutputStream outToClient;
//
//		try {
//			outToClient = new DataOutputStream(socket.getOutputStream());
//			outToClient.writeBytes(message + "\n");
//			outToClient.flush();
//			System.out.println("pushed " + message);
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
}