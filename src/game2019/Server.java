package game2019;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	private static String[] ips;
	private static ArrayList<ServerWriteThread> threads = new ArrayList();
	private static ServerSocket welcomeSocket;
	private static Socket connectionSocket;
	private static HashMap<String, Integer> playerScores = new HashMap();
	private String[] playerNames;
	private static boolean ready = true;
	public static void main(String[] args) throws Exception {
		//system.out.println("server running");
		initIPs();
		
		String clientSentence;
		String serverSentence;
		welcomeSocket = new ServerSocket(2000);

		while (true) {
		Socket connectionSocket = welcomeSocket.accept();


		ServerThread serverThread = new ServerThread(connectionSocket);
		ServerWriteThread serverWriteThread = new ServerWriteThread(connectionSocket);
		if(!threads.contains(serverWriteThread))
		{
			threads.add(serverWriteThread);
			//system.out.println("NUMBER OF THREADS " + threads.size());
		}
		
		serverThread.start();
	
		}
	}
	public static synchronized void sendToClients(String message)
	{
		for (ServerWriteThread t : threads)
		{
			t.pushMessage(message);
		}
	}
	
	public static void addPlayer(String message)
	{
		String[] sSplit = message.split(" ");
		if(!playerScores.containsKey(sSplit[1]))
		{
		playerScores.put(sSplit[1], 0);
	
		}
//		for (Map.Entry<String, Integer> e : playerScores.entrySet())
//		{
//			sendToClients("spawn " + e.getKey())
//		}
		sendToClients(message);


		
	}
	public static void movePlayer(String message)
	{
		if(ready) {
		sendToClients(message);
		ready = false;
		}
		
	}
	public static synchronized void ready()
	{
		ready = true;
	}
	public HashMap<String, Integer> getPlayerScores()
	{
		return this.playerScores;
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

	public static ArrayList<ServerWriteThread> getThreads() {
		return threads;
	}
}