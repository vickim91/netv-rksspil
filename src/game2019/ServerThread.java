package game2019;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		while (true) {

			try {
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = inFromServer.readLine();
				System.out.println("recieved: " + message);

				for (ServerThread s : Server.getThreads()) {
					System.out.println(s.toString());
					s.pushMessage(message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void pushMessage(String message) {

		DataOutputStream outToClient;

		try {
			outToClient = new DataOutputStream(socket.getOutputStream());
			outToClient.writeBytes(message + "\n");
			outToClient.flush();
			System.out.println("pushed " + message);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}