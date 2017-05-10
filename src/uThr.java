import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class uThr extends Thread
{
	private static final int TOTAL_REQUESTS = 20;
	
	// Some code goes here.
	public uThr( int id )
	{
		mId = id;
		counter = 0;
		mResponses = new ConcurrentLinkedQueue<ServiceTicket>();
	}
	
	@Override
	public void run()
	{
		// 20 iterations
		ServiceName[] services = ServiceName.values();
		
		int i = 0;
		while( counter < TOTAL_REQUESTS )
		{
			// requests 20 times
			if( i < TOTAL_REQUESTS )
			{
				Random rand = new Random( System.currentTimeMillis() );
				ServiceTicket ticket = new ServiceTicket( mId );
				
				ticket.mService = services[rand.nextInt(5)]; // problem child?
				
				AppClient.mRequestQue.add( ticket );
				++i;
			}
			
			// response logic goes here
			ServiceTicket response = null;
			if( (response = mResponses.poll()) != null )
			{
				uThr.ServiceName service = response.mService;
				
				if( service == null )
					System.out.println( "Hey... service is null?" );
				
				String result = response.mMessage;
				if( result == null )
					System.out.println( "result was null???" );
				
				
				++counter;
				System.out.printf("uThr<%d> %s : %s\n", response.mUThreadId, service.getServiceName(), result);
			}
			
		}
		// At each iteration a uThr randomly selects one of 5 commands 
		// (nextEven, nextOdd, nextEvenFib, nextLargerRand, nextPrime) 
		// to enqueue in the requestQue, along with any other needed 
		// pieces of data, and waits for the result produced by this 
		// command. After its result is enqueued in the returnQue, 
		// this thread fetches the returned value and outputs on the terminal.
	}
	
	int mId, counter;
	Queue<ServiceTicket> mResponses;
	
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