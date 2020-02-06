package p2pclient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class FileTransferClient { 
    
	private static Socket socket;
	private static Socket socketPeer;
	private static boolean download = false;
	private static String filename;
    public static void main(String[] args) throws Exception{
        
        //Initialize socket
    	Scanner reader = new Scanner(System.in);
    	String host = "141.117.232.33";
    	boolean online = true;
    	
    	try
        {
    		
    		int port = 20300;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            while(online){
            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            System.out.print("Message to send to server: ");
            String m = reader.nextLine();
            if (!m.equals("/logout")){
            	String sendMessage = m + "\n";        
            	bw.write(sendMessage);
            	bw.flush();
            	//Get the return message from the server
            	InputStream is = socket.getInputStream();
            	InputStreamReader isr = new InputStreamReader(is);
            	BufferedReader br = new BufferedReader(isr);
            	String returnMessage = br.readLine();
            	System.out.println(returnMessage);
            	if(download){
            		System.out.println("HTTP 200 OK");
            		download(m, returnMessage);
            		download = false;
            	}
            	if(returnMessage.equals("download what?")){
            		System.out.println("working...");
            		download = true;
            	}
            }
            else{
            	online = false;
            	}
          }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    static void download(String filename, String ip) throws IOException{
    	System.out.println("Attempting to download...");
    	String delims = "[//:]+";
    	String[] tokens = ip.split(delims);
    	String host = tokens[1];
    	System.out.println(host);
    	int port = 20309;
    	System.out.println(port);
    	try
        {
    		InetAddress address = InetAddress.getByName(host);
            socketPeer = new Socket(address, port);
            
            //Send the message to the server
            OutputStream os = socketPeer.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            System.out.print("Message sent to peer: ");
            String sendMessage = filename + "\n";        
        	bw.write(sendMessage);
        	bw.flush();
        	Scanner reader = new Scanner(System.in);
        	System.out.println("HTTP 200 OK");
        	System.out.print("Path to save file: ");
        	String fpath = reader.nextLine();
            byte[] contents = new byte[10000];
        	FileOutputStream fos = new FileOutputStream(fpath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream iss = socketPeer.getInputStream();
            
            //No of bytes read in one read() call
            int bytesRead = 0; 
            
            while((bytesRead=iss.read(contents))!=-1)
                bos.write(contents, 0, bytesRead); 
            
            bos.flush();  
            //Initialize the FileOutputStream to the output file's full path.        
            
            System.out.println("File saved successfully!");
        }
        catch (Exception exception)
        {
        	exception.printStackTrace();
            
        }
        finally
        {
            //Closing the socket
            try
            {
                socketPeer.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

