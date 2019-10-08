package game2019;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private static String[] ips;
	private static ArrayList<ServerThread> threads = new ArrayList();
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


		ServerThread serverThread = new ServerThread(connectionSocket);
		
		if(!threads.contains(serverThread))
		{
			threads.add(serverThread);
			System.out.println("NUMBER OF THREADS " + threads.size());
		}
		
		serverThread.start();
	
		}
	}
	public static synchronized void sendToClients(String message)
	{
		for (ServerThread t : threads)
		{
			t.pushMessage(message);
		}
	}
	
	public static void initIPs() {
		//initialize Ips array
		//ips = new String[] {"10.24.64.192", "10.24.2.36", "10.24.4.217"};
		ips = new String[] {"localhost"};
	}
	
	public ServerSocket getWelcomeSocket() {
		return welcomeSocket;
	}
	
	public void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	public static ArrayList<ServerThread> getThreads() {
		return threads;
	}
}