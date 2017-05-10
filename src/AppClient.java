import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
		
		String hostAddress = args[0];
		int portNumber = Integer.parseInt( args[1] );
		int clientId = Integer.parseInt( args[2] ); // You can give a Client an id number
		
		mRequestQue = new ConcurrentLinkedQueue<ServiceTicket>();
		mResponseQue = new ConcurrentLinkedQueue<ServiceTicket>();
		mUThreads = new ArrayList<uThr>();
		
		AppClient.SessionInfo sessInfo = new AppClient.SessionInfo();
		sessInfo.hostAddress  = hostAddress;
		sessInfo.portNumber = portNumber;
		sessInfo.clientId = clientId;
		
		// create 1 runtimeThr
		// it should exist for one Client Thread
		runtimeThr mRuntimeThread = new runtimeThr( sessInfo );
		mRuntimeThread.start();
		
		// queue can be instantiated in the main
		for( int i = 0; i < NUM_UTHREADS; ++i )
		{
			uThr currUThread = new uThr( i );
			mUThreads.add( currUThread );
			currUThread.start();
		}
	}
	
	public static class SessionInfo
	{
		public String hostAddress;
		public int clientId, portNumber;
	}
	
	public static Queue<ServiceTicket> mRequestQue, mResponseQue;
	public static List<uThr> mUThreads;
	public static final int NUM_UTHREADS = 8;
}
