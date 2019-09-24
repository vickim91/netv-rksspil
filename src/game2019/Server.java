package game2019;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static String[] ips;
	private static ServerSocket welcomeSocket;
	private static Socket connectionSocket;
	public static void main(String[] args) throws Exception {
		System.out.println("server running");
		initIPs();
		
		String clientSentence;
		String serverSentence;
		welcomeSocket = new ServerSocket(2000);

		while (true) {
		Socket connectionSocket = welcomeSocket.accept();
		ServerRead threadRead = new ServerRead(connectionSocket);
		ThreadWrite threadWrite = new ThreadWrite(connectionSocket);
		threadRead.start();
		threadWrite.start();	
		}

	
	}
	
	public static void initIPs() {
		//initialize Ips array
		ips = new String[] {"10.24.64.192", "10.24.2.36", "10.24.4.217"};
	}
	public ServerSocket getWelcomeSocket()
	{
		return welcomeSocket;
	}
	public void setConnectionSocket(Socket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}
	public static String[] getIps()
	{
		return ips;
	}
}