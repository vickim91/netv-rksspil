package game2019;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket socket;
	
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
		//system.out.println("client run");
		
		while(true) {
		
			try {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String message = inFromServer.readLine();
			//system.out.println("client Thread: " +message);
			
				if(message != null){
					Main.readMessagefromClient(message);
					try {
						Client.sendReady();
						//System.out.println("send ready from main");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}