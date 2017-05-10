
public class ServiceTicket
{
	public ServiceTicket(int id)
	{
		mUThreadId = id;
	}
	
	public int mUThreadId;
	public uThr.ServiceName mService;
	public String mMessage;
}