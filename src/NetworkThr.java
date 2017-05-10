import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkThr implements Runnable {
	
	String hostAddress, request;
	int portNumber;
	
	public NetworkThr(String hostAddress, int portNumber, String request){
		this.hostAddress = hostAddress;
		this.request = request;
		this.portNumber = portNumber;
	}
	
	public void run() {
		try{
			// Initialize a new Socket
			Socket socket = new Socket( hostAddress, portNumber );
			PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()) );

			out.println(request);
			String reply = in.readLine();
			
			AppClient.returnQue.add(reply);
		}
		catch (IOException e){
			System.out.println("Couldn't get I/O for connection to " + hostAddress);
			System.exit(1);
		}	
	}
	
//	public static void main(String args[]) {
//		Runnable t1 = new NetworkThr(hostAddress, portNumber, request);
//		new Thread(t1).start();
//	}
	
}
