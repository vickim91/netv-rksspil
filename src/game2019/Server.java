package game2019;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	private static String[] ips;
	private static ArrayList<ServerWriteThread> writeThreads = new ArrayList();
	private static ArrayList<ServerThread> readThreads = new ArrayList();
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
		if(!writeThreads.contains(serverWriteThread))
		{
			writeThreads.add(serverWriteThread);
			//system.out.println("NUMBER OF THREADS " + threads.size());
		}
		if(!readThreads.contains(serverThread))
		{
			readThreads.add(serverThread);
			//system.out.println("NUMBER OF THREADS " + threads.size());
		}
		
		
		serverThread.start();
	
		}
	}
	public static synchronized void sendToClients(String message)
	{
		for (ServerWriteThread t : writeThreads)
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

		sendToClients(message);

	}
	public static void movePlayer(String message)
	{
		boolean allReady = true;
		for(ServerThread t : readThreads)
		{
			allReady=t.isReady();
		}
		if(allReady)
		sendToClients(message);
	
		
	}

	public HashMap<String, Integer> getPlayerScores()
	{
		return this.playerScores;
	}

	public static void initIPs() {
	
		ips = new String[] {"localhost"};
	}
	
	public ServerSocket getWelcomeSocket() {
		return welcomeSocket;
	}
	
	public void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	public static ArrayList<ServerWriteThread> getThreads() {
		return writeThreads;
	}
}