public class Token
{
	/**
	 * This class represents a Session's state.
	 */
	public Token()
	{
		mFib1 = 0; mFib2 = 1;
		mPrime = 1;
		mRand = 0;
	}
	
	public long mFib1, mFib2; // current even fibonacci
	public int mRand; // lowerbound random
	public long mPrime; // current prime
}