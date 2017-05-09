import java.math.BigInteger;
import java.util.Random;

public class Token
{
	/**
	 * This class represents a Session's state.
	 */
	public Token()
	{
		mFib1 = new BigInteger("0"); mFib2 = new BigInteger("1");
		mPrime = new BigInteger("1");
		mRand = 0;
	}
	
	public synchronized BigInteger getNextFib()
	{
		BigInteger temp1 = mFib1;
		BigInteger temp2 = mFib2;
		
		// shift newer Fibonacci numbers down
		mFib2 = temp1.add( temp2 );
		mFib1 = temp2;
		return mFib2;
	}
	
	public synchronized String nextRand()
	{
		// Technically not safe and not random.
		Random rand = new Random( System.currentTimeMillis() );
		int temp = mRand;
		if( temp == Integer.MAX_VALUE - 1 )
			temp = 0; // wrap arround if max
		
		this.mRand = temp + rand.nextInt( Integer.MAX_VALUE - temp );
		return Integer.toString( this.mRand );
	}
	
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