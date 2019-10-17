package game2019;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private static Socket clientSocket;
	private static ClientThreadWrite clientThreadWrite;

	public static void Connect(String ip) throws Exception {

		clientSocket = new Socket(ip, 2000);

		ClientThread clientThread = new ClientThread(clientSocket);
		clientThreadWrite = new ClientThreadWrite(clientSocket);

		clientThread.start();
		//system.out.println("client connected");
	}

	// sender navn og position lige nu er det bare for start position, men kan evt
	// udvides som en mere generelt implementation
	public  static void sendNameAndPos(String name, int x, int y, String direction) throws Exception {
		clientThreadWrite.sendNameAndPos(name, x, y, direction);
	}
	public static void sendReady() throws Exception
	{
		clientThreadWrite.sendReady();
	}
	public static void spawnPlayer(String name, int x, int y, String direction) throws IOException
	{
	clientThreadWrite.spawnPlayer(name, x, y, direction);
	}
}