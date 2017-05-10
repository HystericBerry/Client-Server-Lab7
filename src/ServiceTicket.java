/**
 * This struct keeps track of a Client's Request/ Response.
 */
public class ServiceTicket
{
	public ServiceTicket(int id)
	{
		mUThreadId = id;
	}
	
	// the uThr that requested the service call
	public int mUThreadId;
	// the service call requested (enum)
	public uThr.ServiceName mService;
	// the result of the service call, this value is null 
	// when it is sent as a request and not null when sent
	// back as a response.
	public String mMessage;
}