import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Instantiates runtimeThr and uThr threads, 
 * and the two queues hold service requests and responses
 */
public class AppClient
{
	public static void main(String[] args) throws IOException
	{
		// Check validity of executable arguments
		if (args.length != 3)
		{
			// Directions
			System.out.println("Usage: java AppClient <host ip> <port number> <id>");
			System.exit(1);
		}
		
		// get info from string arguments
		String hostAddress = args[0];
		int portNumber = Integer.parseInt( args[1] );
		int clientId = Integer.parseInt( args[2] ); // You can give a Client an id number
		
		// the queues that hold the service requests and responses, respectively
		mRequestQue = new ConcurrentLinkedQueue<ServiceTicket>();
		mResponseQue = new ConcurrentLinkedQueue<ServiceTicket>();
		
		// array of uThr threads
		mUThreads = new ArrayList<uThr>();
		
		// info needed for service requests to server software
		AppClient.SessionInfo sessInfo = new AppClient.SessionInfo();
		sessInfo.hostAddress  = hostAddress;
		sessInfo.portNumber = portNumber;
		sessInfo.clientId = clientId;
		
		// create and start 1 runtimeThr
		// it should exist for one Client Thread
		runtimeThr mRuntimeThread = new runtimeThr( sessInfo );
		mRuntimeThread.start();
		
		// create and start multiple uThr
		for( int i = 0; i < NUM_UTHREADS; ++i )
		{
			uThr currUThread = new uThr( i );
			mUThreads.add( currUThread );
			currUThread.start();
		}
	}
	
	/**
	 *	Struct-like class containing info needed by NetworkThr threads
	 */
	public static class SessionInfo
	{
		public String hostAddress;
		public int clientId, portNumber;
	}
	
	public static Queue<ServiceTicket> mRequestQue, mResponseQue;
	public static List<uThr> mUThreads;
	public static final int NUM_UTHREADS = 8;
}
