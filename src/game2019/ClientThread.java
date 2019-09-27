package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket socket;
	
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		System.out.println("client run");
		
		while(true) {
		
			try {

				
		
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String message = inFromServer.readLine();
			System.out.println("client Thread: " +message);
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}