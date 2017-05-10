
public class LocalToken
{
	/**
	 * Constructor initializing count values
	 */
	public LocalToken()
	{
		oddCount = 1;
		evenCount = 0;
	}
	
	/**
	 * Returns the  next odd integer as a string
	 */
	public synchronized String nextOdd()
	{
		int temp = oddCount;
		oddCount = temp + 2;
		
		return Integer.toString(temp);
	}
	
	/**
	 * Returns the  next even integer as a string
	 */
	public synchronized String nextEven()
	{
		int temp = evenCount;
		evenCount = temp + 2;
		
		return Integer.toString(temp);
	}
	
	int oddCount, evenCount;
}