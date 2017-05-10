
public class runtimeThr extends Thread
{
	public runtimeThr( AppClient.SessionInfo sessInfo )
	{
		mSessInfo = sessInfo;
		mClientId = sessInfo.clientId;
		mCounter = new LocalToken();
	}
	
	public void run()
	{
		while( true )
		{
			ServiceTicket request = null, response = null;
			if( (request = AppClient.mRequestQue.poll()) != null )
			{
				switch( request.mService )
				{
				case NEXTEVEN:
				case NEXTODD:
					LocalThr currLocalThr = new LocalThr( request, mCounter );
					currLocalThr.start();
					break;
				case NEXTEVENFIB:
				case NEXTLARGERRAND:
				case NEXTPRIME:
					// create network instance here
					NetworkThr netThread = new NetworkThr( mSessInfo , request.mService, request.mUThreadId ); // host address, port number, request
					netThread.start();
					break;
				default:
					throw new RuntimeException("Cannot create a local or network thread because the requested service is invalid.");
				}
					
			}
			
			if( (response = AppClient.mResponseQue.poll()) != null )
			{
				// keep track of uThr and get appropriate uThr
				uThr currUThread = AppClient.mUThreads.get( response.mUThreadId );
				currUThread.mResponses.add( response );
			}
		}
	}
	
	public int mClientId;
	LocalToken mCounter;
	private AppClient.SessionInfo mSessInfo;
}