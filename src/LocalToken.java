
public class LocalToken
{
	public LocalToken()
	{
		oddCount = 1;
		evenCount = 0;
	}
	
	public synchronized String nextOdd()
	{
		int temp = oddCount;
		oddCount = temp + 2;
		
		return Integer.toString(temp);
	}
	
	public synchronized String nextEven()
	{
		int temp = evenCount;
		evenCount = temp + 2;
		
		return Integer.toString(temp);
	}
	
	int oddCount, evenCount;
}