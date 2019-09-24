package game2019;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private static String[] ips;
	
	public static void StartServer() throws Exception
	{
		initIPs();
		
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(2000);
		Socket connectionSocket = welcomeSocket.accept();
		ThreadRead threadRead = new ThreadRead(connectionSocket);
		ThreadWrite threadWrite = new ThreadWrite(connectionSocket);
		threadRead.start();
		threadWrite.start();
		
	}
	
	public static void initIPs()
	{
		//initialize Ips array
		ips = new String[] {"10.24.64.192", "10.24.2.36", "10.24.4.217"};
	}

}