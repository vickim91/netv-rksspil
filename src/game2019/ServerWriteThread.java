package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWriteThread extends Thread {
	private Socket socket;
	
	public ServerWriteThread(Socket socket)
	{
		this.socket = socket;
	}



public void pushMessage(String message) {

	DataOutputStream outToClient;

	try {
		outToClient = new DataOutputStream(socket.getOutputStream());
		outToClient.writeBytes(message + "\n");
		outToClient.flush();
		//system.out.println("pushed " + message);

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
}