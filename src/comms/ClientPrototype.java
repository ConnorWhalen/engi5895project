package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientPrototype {
	
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	
	public ClientPrototype(){
		try{
			socket = new Socket("localhost", 1234);
			System.out.println("client connected successfully");
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
			System.out.println("You can now send and receive text from the server");
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
					System.out.println("SERVER: " + i);
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
		ClientPrototype c = new ClientPrototype();
		c.initComms();
	}

}
