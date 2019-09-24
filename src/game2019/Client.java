package game2019;

import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
	
	private static Socket clientSocket;

	
	public static void Connect(String ip) throws Exception {
	
	Socket clientSocket = new Socket(ip, 6789);
	
	ThreadRead threadRead = new ThreadRead(clientSocket);
	
	threadRead.start();
	}
	
	//sender navn og position lige nu er det bare for start position, men kan evt udvides som en mere generelt implementation
	public static void sendNameAndPos(String name, int x, int y, String direction) throws Exception
	{
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(name+" "+x+ " " +y+ "\n");
	}
}