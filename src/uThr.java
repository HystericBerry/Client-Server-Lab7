import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This uThr is responsible for randomly requesting 20 different service calls. 
 * This means that for every uThr, 20 different service calls are requested by 
 * 20 different LocalThr and NetworkThr.
 */
public class uThr extends Thread
{
	public static final int TOTAL_REQUESTS = 20;
	
	/**
	 * Creates an instance of uThr and the response queue which acts as 
	 * the Thread's notify queue.
	 * 
	 * @param id the id assigned to the uThread by this instance of Client.
	 */
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
		// We know that every uThr must successfully execute 20 service calls.
		// We did not add time-out code due to complexity and it was not required.
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
			
			// handles service notification
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
	
	/**
	 * A centralized ServiceName enumeration that allows for simple editing/ refactoring.
	 */
	public enum ServiceName
	{
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