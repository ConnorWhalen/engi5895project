package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ServerPrototype {
	
	private ServerSocket serverSocket;
	private Socket socket;
	private OutputStream outStream;
	private InputStream inStream;
	private DataOutputStream out;
	private DataInputStream in;
	
	public ServerPrototype(){
		try{
			serverSocket = new ServerSocket(1234);
			boolean attempting = true;
			int input;
			while(attempting){
				serverSocket.setSoTimeout(5000);
				try{
					socket = serverSocket.accept();
					System.out.println("server connected successfully");
					attempting = false;
				}
				catch (SocketTimeoutException e){
					System.out.println("Server timed out. Press r to retry, anything else to quit");
					input = System.in.read();
					if(input != 'r'){
						System.out.println("quitting");
						attempting = false;
					}
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void initComms(){
		try{
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			Thread inputThread = new Thread(new Runnable(){
				public void run(){
					readInput();
				}
			});
			
			Thread outputThread = new Thread(new Runnable(){
				public void run(){
					writeOutput();
				}
			});
			inputThread.start();
			outputThread.start();
			System.out.println("You can now send and receive text from the client");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void readInput(){
		try {
			boolean eof = false;
			while (!eof) {
				try {
					String i = in.readUTF();
					System.out.println("CLIENT: " + i);
				} catch (EOFException e) {
					eof = true;
				}
			}
			System.out.println("Read all data from the pipe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeOutput(){
		try {
			Scanner scan = new Scanner(System.in);
			out.writeUTF(scan.next());
			out.flush();
			System.out.println("Sent all data to the pipe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerPrototype s = new ServerPrototype();
		s.initComms();
	}
}