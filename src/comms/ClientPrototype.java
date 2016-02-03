package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/* **************************************************************
 * Prototype for client/server communication.
 * On creation attempts to connect to a server.
 * Takes console input and sends it to the server.
 * Receives and prints input sent from the server to the console.
 */
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
				    while (!socket.isClosed()){
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
                    while (!socket.isClosed()){
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
			System.out.println("You can now send and receive text from the server. Send q to shut down.");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void readInput(){
		try {
            String i = in.readUTF();
            if (i.equals("q")){
                shutdown();
            }
            else{
                System.out.println("SERVER: " + i);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeOutput(){
		try {
		    if (reader.hasNext()){
                String next = reader.next();
                out.writeUTF(next);
                out.flush();
                if (next.equals("q")){
                    shutdown();
                }
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private void shutdown(){
        try{
            System.out.println("Shutting Down");
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		ClientPrototype c = new ClientPrototype();
		c.initComms();
	}

}
