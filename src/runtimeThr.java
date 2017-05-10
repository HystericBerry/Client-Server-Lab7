/**
 * This class represents the middle layer responsible for maintaining the request and response queues.
 */
public class runtimeThr extends Thread
{
	/**
	 * This constructor creates an instance of a runtime thread.
	 * 
	 * @param sessInfo is the Client Session Information that contains host address, 
	 * port number and client id.
	 */
	public runtimeThr( AppClient.SessionInfo sessInfo )
	{
		counter = 0;

		TOTAL_SERVICE_CALLS = AppClient.NUM_UTHREADS * uThr.TOTAL_REQUESTS;
		
		mSessInfo = sessInfo;
		mClientId = sessInfo.clientId;
		mCounter = new LocalToken();
	}
	
	public void run()
	{
		// we know how many service calls to expect
		// unfortunately, we did not have any timeout codes in the case a service call
		// fails for some reason (due to code complexity)
		while( counter < TOTAL_SERVICE_CALLS )
		{
			// Depending on the ServiceTicket at the front, create an instance of 
			// LocalThr or NetworkThr.
			ServiceTicket request = null, response = null;
			if( (request = AppClient.mRequestQue.poll()) != null )
			{
				switch( request.mService )
				{
				case NEXTEVEN:
				case NEXTODD:
					new LocalThr( request, mCounter ).start();
					break;
				case NEXTEVENFIB:
				case NEXTLARGERRAND:
				case NEXTPRIME:
					// create network instance here
					new NetworkThr( mSessInfo , request, request.mUThreadId ).start();
					break;
				default:
					throw new RuntimeException("Cannot create a local or network thread "
							+ "because the requested service is invalid.");
				}
					
			}
			
			// Notify the uThr at the front of the queue if the queue is not null.
			if( (response = AppClient.mResponseQue.poll()) != null )
			{
				// keep track of uThr and get appropriate uThr
				uThr currUThread = AppClient.mUThreads.get( response.mUThreadId );
				currUThread.mResponses.add( response ); // "Notifies" the uUtr.
				++counter;
			}
		}
	}
	
	public int mClientId, counter;
	LocalToken mCounter;
	private AppClient.SessionInfo mSessInfo;
	
	public final int TOTAL_SERVICE_CALLS;
}