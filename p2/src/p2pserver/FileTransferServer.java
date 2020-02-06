package p2pserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileTransferServer { 
    
	private static Socket socket;
	
    public static void main(String[] args) {
        //Initialize Sockets
    	int port = 20309;
    	try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("P2P Server Started and listening to the port " + port);
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String mc = br.readLine();
                System.out.println("Message recieved from peer:" + mc);
                upload(socket, mc);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
        
        
    }
    static void upload(Socket socket, String filename) throws IOException{
    	//Specify the file
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis); 
          
        //Get socket's output stream
        OutputStream os = socket.getOutputStream();
                
        //Read File Contents into contents array 
        byte[] contents;
        long fileLength = file.length(); 
        long current = 0;
         
        long start = System.nanoTime();
        while(current!=fileLength){ 
            int size = 10000;
            if(fileLength - current >= size)
                current += size;    
            else{ 
                size = (int)(fileLength - current); 
                current = fileLength;
            } 
            contents = new byte[size]; 
            bis.read(contents, 0, size); 
            os.write(contents);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }   
        
        os.flush(); 
        //File transfer done. Close the socket connection!
        System.out.println("File sent succesfully!");
    }
}
