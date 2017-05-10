
public class runtimeThr extends Thread
{
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
		while( counter < TOTAL_SERVICE_CALLS )
		{
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
			
			if( (response = AppClient.mResponseQue.poll()) != null )
			{
				// keep track of uThr and get appropriate uThr
				uThr currUThread = AppClient.mUThreads.get( response.mUThreadId );
				currUThread.mResponses.add( response );
				++counter;
			}
		}
	}
	
	public int mClientId, counter;
	LocalToken mCounter;
	private AppClient.SessionInfo mSessInfo;
	
	public final int TOTAL_SERVICE_CALLS;
}