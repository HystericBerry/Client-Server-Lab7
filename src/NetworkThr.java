import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * Class for thread that handles requests for services to be fulfilled by server software
 *
 */
public class NetworkThr extends Thread {
	
	String hostAddress;
	ServiceTicket mRequest;
	int portNumber, uId;
	
	/**
	 * Constructor 
	 */
	public NetworkThr( AppClient.SessionInfo sessInfo, ServiceTicket request, int uThreadId )
	{
		this.hostAddress = sessInfo.hostAddress;
		this.mRequest = request;
		this.portNumber = sessInfo.portNumber;
		this.uId = uThreadId;
	}
	
	/**
	 * Implementation of thread run() method
	 * Thread calls for requested service from server and enqueues 
	 * response to responseQue
	 */
	public void run()
	{
		try{
			// Initialize a new Socket
			Socket socket = new Socket( hostAddress, portNumber );
			PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()) );

			out.println( mRequest.mService.getServiceName() ); // sending request TO the Server
			
			mRequest.mMessage = in.readLine();	// read response from server
			socket.close();
			
			// add server response to responseQue
			AppClient.mResponseQue.add( mRequest );
		}
		catch (IOException e){
			System.out.println("Couldn't get I/O for connection to " + hostAddress);
			System.exit(1);
		}	
	}
	
}
