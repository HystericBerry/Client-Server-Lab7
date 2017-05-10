import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class uThr extends Thread
{
	public static final int TOTAL_REQUESTS = 20;
	
	public uThr( int id )
	{
		mId = id;
		counter = 0;
		mResponses = new ConcurrentLinkedQueue<ServiceTicket>();
	}
	
	@Override
	public void run()
	{
		int i = 0;
		while( counter < TOTAL_REQUESTS )
		{
			// requests 20 times
			if( i < TOTAL_REQUESTS )
			{
				ServiceTicket ticket = new ServiceTicket( mId );
				ticket.mService = services[rand.nextInt(5)];
				
				AppClient.mRequestQue.add( ticket );
				++i;
			}
			
			// response logic goes here
			ServiceTicket response = null;
			if( (response = mResponses.poll()) != null )
			{
				uThr.ServiceName service = response.mService;
				String result = response.mMessage;
				
				++counter;
				System.out.printf("uThr<%d> %s : %s\n", response.mUThreadId, service.getServiceName(), result);
			}
			
		}
	}
	
	int mId, counter;
	Queue<ServiceTicket> mResponses;
	private static ServiceName[] services = ServiceName.values();
	private static final Random rand = new Random( System.currentTimeMillis() );
	
	public enum ServiceName {
		NEXTEVEN("nextEven"), NEXTODD("nextOdd"), NEXTEVENFIB("nextEvenFib"), 
		NEXTLARGERRAND("nextLargerRand"), NEXTPRIME("nextPrime");
		
		private final String mName;
		private ServiceName( String id ) { this.mName = id; }
		
		public String getServiceName()
		{
			return mName;
		}
	}
}