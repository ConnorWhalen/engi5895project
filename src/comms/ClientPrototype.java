package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientPrototype {
	
	private Socket socket;
	private DataOutputStream out;
    private DataInputStream in;
	private Scanner reader;
	
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
            reader = new Scanner(System.in);
			
			Thread inputThread = new Thread(new Runnable(){
				public void run(){
				    while (true){
				        readInput();
				        try{
				            Thread.sleep(50);
				        } catch (InterruptedException e){
				            e.printStackTrace();
				        }
				    }
				}
			});
			
			Thread outputThread = new Thread(new Runnable(){
				public void run(){
                    while (true){
                        writeOutput();
                        try{
                            Thread.sleep(50);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
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
            String i = in.readUTF();
			System.out.println("SERVER: " + i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeOutput(){
		try {
		    if (reader.hasNext()){
		        out.writeUTF(reader.next());
			    out.flush();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientPrototype c = new ClientPrototype();
		c.initComms();
	}

}
