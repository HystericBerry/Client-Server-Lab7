import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkThr extends Thread {
	
	String hostAddress;
	uThr.ServiceName request;
	int portNumber, uId;
	
	public NetworkThr( AppClient.SessionInfo sessInfo, uThr.ServiceName request, int uThreadId )
	{
		this.hostAddress = sessInfo.hostAddress;
		this.request = request;
		this.portNumber = sessInfo.portNumber;
		this.uId = uThreadId;
	}
	
	public void run()
	{
		try{
			// Initialize a new Socket
			Socket socket = new Socket( hostAddress, portNumber );
			PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()) );

			out.println( request.getServiceName() ); // sending request TO the Server
			
			ServiceTicket respTicket = new ServiceTicket( uId ); // THIS ID NEEDS TO BE UTHR ID
			
			respTicket.mMessage = in.readLine();
			
			
			AppClient.mResponseQue.add( respTicket );
		}
		catch (IOException e){
			System.out.println("Couldn't get I/O for connection to " + hostAddress);
			System.exit(1);
		}	
	}
	
}
