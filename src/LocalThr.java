import java.util.Queue;

public class LocalThr implements Runnable{
	
	int value;
	String request;
	
	public LocalThr(String request, int value){
		this.request = request;
		this.value = value;
	}
	
	public void run() {
		String result;
		if (request.equals("nextOdd"))
			result = nextOdd();
		else if (request.equals("nextEven"))
			result = nextEven();
		AppClient.resultQue.add(result);
	}
	
	public String nextOdd()
	{
		if (value % 2 == 0)
			return Long.toString(value + 2 );
		return Long.toString(value + 1);
	}
	
	public String nextEven()
	{
		if (value % 2 == 0)
			return Long.toString(value + 1 );
		return Long.toString(value + 2);
	}
	
//	public static void main(String args[]) {
//		Runnable t1 = new LocalThr(request, value);
//		new Thread(t1).start();
//	}
	
}
