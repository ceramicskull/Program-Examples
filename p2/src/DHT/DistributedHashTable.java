package DHT;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class DistributedHashTable {
	
	static HashMap<String,String> p2p;
	private static Socket socket;
	public static void main(String[] arg) throws IOException, ClassNotFoundException{
		
		establishHash();
		int port = 20300;
		try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("DHT Server Started and listening to the port " + port);
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String mc = br.readLine();
                
                System.out.println("Message received from client: "+ mc);
                switch(mc){
                	case "download":
                			download();
                			break;
                	case "insert":
                			insert();
                			save(port);
                			break;
                	case "updateDHT":
                			updateDHT();
                			break;
                	default:
                		badRequest();
                		break;
                }
    				
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
	static void badRequest() throws IOException{
		String returnMessage = "HTTP 400 Bad Request\n";
		System.out.println("Return Message Sent: " + returnMessage);
       //Sending the response back to the client.
       OutputStream os = socket.getOutputStream();
       OutputStreamWriter osw = new OutputStreamWriter(os);
       BufferedWriter bw = new BufferedWriter(osw);
       bw.write(returnMessage);
       bw.flush();
	}
	static void save(int port){
		FileOutputStream fos = null;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream("dht.tmp");
			try {
				oos = new ObjectOutputStream(fos); 
				oos.writeObject(p2p);
				oos.close();
				System.out.println("Saved successfully!");
			
				String[] host = new String[4];
				host[0] = "141.117.232.32";
				host[1] = "141.117.232.33";
				host[2] = "141.117.232.34";
				host[3] = "141.117.232.35";
				
				Socket[] socket = new Socket[4];
				for(int i = 0; i < 4; i ++){
					String self = InetAddress.getLocalHost().toString();
					self = self.substring(10);
					if(!(host[i]).equals(self)){
						try {
						System.out.println("Working1 " + host[i] + " , " + self + "\n");
						InetAddress address = InetAddress.getByName(host[i]);
						socket[i] = new Socket(address, port);
						upload(socket[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}				
	}
	static void download() throws Exception{
		 String returnMessage = "download what?\n";
		 System.out.println("Return Message Sent: " + returnMessage);
         //Sending the response back to the client.
         OutputStream os = socket.getOutputStream();
         OutputStreamWriter osw = new OutputStreamWriter(os);
         BufferedWriter bw = new BufferedWriter(osw);
         bw.write(returnMessage);
         bw.flush();
         //Getting download response from client
         InputStream is = socket.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader br = new BufferedReader(isr);
         String mc = br.readLine();
         System.out.println("Message received from client: "+ mc);
         if(p2p.get(mc) != null){
        	 returnMessage = p2p.get(mc) + "\n";
         }
         else{
        	 returnMessage = "HTTP 404 Not Found\n";
         }
         System.out.println("Return Message Sent: " + returnMessage);
         bw.write(returnMessage);
         bw.flush();
	}
	static void insert() throws Exception{
		String returnMessage = "insert what? \n";
		 System.out.println("Return Message Sent: " + returnMessage);
        //Sending the response back to the client.
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(returnMessage);
        bw.flush();
        //Getting insert response from client
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String mc = br.readLine();
        System.out.println("Message received from client: "+ mc);
        p2p.put(mc,socket.getRemoteSocketAddress().toString());
        returnMessage = "registered file " + mc + "to IP: " + socket.getRemoteSocketAddress().toString() + "\n";
        System.out.println("Return Message Sent: " + returnMessage);
        bw.write(returnMessage);
        bw.flush();
	}
	static void establishHash() {
		try {
			FileInputStream file = new FileInputStream("dht.tmp");
			ObjectInputStream ois = new ObjectInputStream(file);
			p2p = (HashMap<String,String>) ois.readObject();
			ois.close();
			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No previous database stored... ready for new database \n");
			p2p = new HashMap<String,String>();
		}
	}
	static void upload(Socket DHTsocket) throws IOException{
		//Sending the response back to the client.
		System.out.println("Working2");
        OutputStream os = DHTsocket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("updateDHT\n");
        bw.flush();
        System.out.println("Working3");
        InputStream is = DHTsocket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String mc = br.readLine();
        System.out.println("Message received from other DHT: "+ mc);
        
        
		String filename = "dht.tmp";
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis); 
        
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
            System.out.print("Sending DHT ... "+(current*100)/fileLength+"% complete!");
        }   
        
        os.flush(); 
        //File transfer done. Close the socket connection!
        DHTsocket.close();
        System.out.println("DHT sent succesfully!");
    }
	static void updateDHT() throws Exception{ 
			//Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("Ready to Update DHT\n");
			bw.flush();
			System.out.println("Ready to Update DHT");
            
        	String fpath = "dht.tmp";
            byte[] contents = new byte[10000];
        	FileOutputStream fos = new FileOutputStream(fpath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream iss = socket.getInputStream();
            
            
            //No of bytes read in one read() call
            int bytesRead = 0; 
            
            while((bytesRead=iss.read(contents))!=-1)
                bos.write(contents, 0, bytesRead); 
            
            bos.flush();  
            System.out.println("DHT updated succesfully!");
            //Initialize the FileOutputStream to the output file's full path.        
            establishHash();
    }
}
