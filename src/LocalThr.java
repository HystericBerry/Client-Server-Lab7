
public class LocalThr extends Thread
{
	ServiceTicket mTicket;
	LocalToken mCounter;
	
	public LocalThr( ServiceTicket ticket, LocalToken counter )
	{
		this.mTicket = ticket;
		mCounter = counter;
	}
	
	public void run()
	{
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
		
		AppClient.mResponseQue.add( mTicket );
	}
}
