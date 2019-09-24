package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadWrite extends Thread{

	private Socket socket;
	
	
	public ThreadWrite(Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		while(true)
		{
		try {
		
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String clientSentence = inFromUser.readLine();
			DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
			outToServer.writeBytes(clientSentence + "\n");
			outToServer.flush();

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
	}
	
}
