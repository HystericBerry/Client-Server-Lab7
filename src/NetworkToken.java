import java.math.BigInteger;
import java.util.Random;

public class NetworkToken
{
	/**
	 * This class represents a Slient's Session state with the server.
	 */
	public NetworkToken()
	{
		mFib1 = new BigInteger("0"); mFib2 = new BigInteger("1");
		mPrime = new BigInteger("1");
		mRand = 0;
	}
	
	/**
	 * This method returns the next fibonacci whether it is even or odd.
	 * 
	 * @return the next fibonacci number - regardless of even or odd.
	 */
	public synchronized BigInteger getNextFib()
	{
		BigInteger temp1 = mFib1;
		BigInteger temp2 = mFib2;
		
		// shift newer Fibonacci numbers down
		mFib2 = temp1.add( temp2 );
		mFib1 = temp2;
		return mFib2;
	}
	
	/**
	 * Gets the next random number larger than the previous. If the max integer 
	 * is reached, this function wraps back around.
	 * 
	 * @return the next random larger number.
	 */
	public synchronized String nextRand()
	{
		// Using current date/ time is technically not safe and not random.
		Random rand = new Random( System.currentTimeMillis() );
		int temp = mRand;
		if( temp == Integer.MAX_VALUE - 1 )
			temp = 0; // wrap arround if max
		
		this.mRand = temp + rand.nextInt( Integer.MAX_VALUE - temp );
		return Integer.toString( this.mRand );
	}
	
	/**
	 * This method increments a counter. This counter keeps track of which number to 
	 * check next for its primality.
	 * 
	 * @return the next number to check for primality
	 */
	public synchronized BigInteger getAndIncrement()
	{
		BigInteger temp = new BigInteger( mPrime.toString() );
		mPrime = temp.add( BigInteger.ONE );
		return mPrime;
	}
	
	private BigInteger mFib1, mFib2; // current even fibonacci
	private int mRand; // lowerbound random
	private BigInteger mPrime; // current prime
}