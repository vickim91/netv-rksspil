package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerRead extends Thread {
	
	private Socket socket;
	
	public ServerRead(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
		while(true) {
		
			try {
				
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
			String message = inFromServer.readLine();
			System.out.println("recieved: "+message);
			
			for(String s : Server.getIps() )
			{
				Socket tmp = new Socket(s, 2000);
				DataOutputStream out = new DataOutputStream(tmp.getOutputStream());
				
				tmp.close();
				
			}
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}