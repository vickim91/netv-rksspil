package game2019;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
	private static Socket clientSocket;

	
	public static void Connect(String ip) throws Exception {
	
	clientSocket = new Socket(ip, 2000);

	ClientThread clientThread = new ClientThread(clientSocket);
	
	clientThread.start();
	System.out.println("client connected");
	}
	
	//sender navn og position lige nu er det bare for start position, men kan evt udvides som en mere generelt implementation
	public static void sendNameAndPos(String name, int x, int y, String direction) throws Exception
	{
		//System.out.println(clientSocket);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(name+" "+x+ " " +y+ " " +direction + "\n");
	}
}