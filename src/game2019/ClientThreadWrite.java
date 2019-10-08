package game2019;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThreadWrite extends Thread{
	
	private Socket clientSocket;
	
	public ClientThreadWrite(Socket socket)
	{
		this.clientSocket = socket;
	}
	
	// sender navn og position lige nu er det bare for start position, men kan evt
	// udvides som en mere generelt implementation
	public void sendNameAndPos(String name, int x, int y, String direction) throws Exception {
		// System.out.println(clientSocket);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("move "+name + " " + x + " " + y + " " + direction + "\n");
		outToServer.flush();
	}
	//en metode til at sende ændring i point
	public void sendPoints(String name, int points) throws IOException {
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("score "+ name + " " + points +"\n");
		outToServer.flush();
	}
	public void spawnPlayer(String name, int x, int y, String direction) throws IOException
	{
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes("spawn " + name + " " + x + " " + y + " " + direction + "\n");
		outToServer.flush();
	}
	

}
