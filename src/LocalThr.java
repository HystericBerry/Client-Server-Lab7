
public class LocalThr extends Thread
{
	ServiceTicket mTicket;
	LocalToken mCounter;
	
	/**
	 * Constructor
	 */
	public LocalThr( ServiceTicket ticket, LocalToken counter )
	{
		this.mTicket = ticket;
		mCounter = counter;
	}
	
	/**
	 * Implementation of thread run() method
	 * Thread calls for requested service locally and enqueues response to responseQue
	 */
	public void run()
	{
		// calls nextEven() or nextOdd() based on service type 
		switch( mTicket.mService )
		{
		case NEXTEVEN:
			mTicket.mMessage = mCounter.nextEven();
			break;
		case NEXTODD:
			mTicket.mMessage = mCounter.nextOdd();
			break;
		default:
			throw new RuntimeException("Error occured: Service Requested is neither nextEven nor nextOdd.\n");
		}
		
		AppClient.mResponseQue.add( mTicket );	// adds response to queue
	}
}
