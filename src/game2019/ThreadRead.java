package game2019;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRead extends Thread{
	
	private Socket socket;
	
	public ThreadRead(Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		while(true)
		{
		try {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = inFromServer.readLine();
			System.out.println("recieved: "+message);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	}

}
