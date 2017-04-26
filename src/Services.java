import java.util.Random;

public class Services
{
	/**
	 * Invokes a Service given the Client's String request.
	 * 
	 * @param service is the name of the Service method to invoke.
	 * @param token is the representation of a Client's Stateful Session.
	 * @return
	 */
	public static String invoke( String service, Token token )
	{
		switch( service )
		{
		case "nextEvenFib":
			return Services.nextEvenFib( token );
		case "nextLargerRand":
			return Services.nextLargerRand( token );
		case "nextPrime":
			return Services.nextPrime( token );
		case "End Protocol.":
			return "Bye.";
		default:
			throw new IllegalArgumentException( "Unexpected service request occurred: " + service );
		}
	}
	
	/**
	 * Given this Session's current token, the Service library returns 
	 * the next Even Fibonacci number given the Session's previous state.
	 * 
	 * @param token is the representation of a Client's Stateful Session.
	 * @return the next Even Fibonacci number given the Session's previous state.
	 */
	public static String nextEvenFib( Token token )
	{
		do
		{
			// temp variable to store new Fibonacci numbers
			long nextFib = token.mFib1 + token.mFib2;
			
			// shift newer Fibonacci numbers down
			token.mFib1 = token.mFib2;
			token.mFib2 = nextFib;
		} while( (token.mFib2 & 1) != 0 ); // while Fib is odd.
		return Long.toString( token.mFib2 );
	}
	
	/**
	 * Returns the next Larger Random number given the Session's previous state.
	 * 
	 * @param token is the representation of a Client's Stateful Session.
	 * @return the next Larger Random number given the Session's previous state.
	 */
	public static String nextLargerRand( Token token )
	{
		Random rand = new Random();
		
		if( token.mRand == Integer.MAX_VALUE )
			token.mRand = 0; // wrap arround if max
		token.mRand = token.mRand + rand.nextInt( Integer.MAX_VALUE - token.mRand );
		return Integer.toString( token.mRand );
	}
	
	/**
	 * Returns the next Prime number given the Session's previous state.
	 * 
	 * @param token is the representation of a Client's Stateful Session.
	 * @return the next Prime number given the Session's previous state.
	 */
	public static String nextPrime( Token token )
	{
		do
		{
			token.mPrime = token.mPrime + 1;
		} while( !Services.isPrime( token.mPrime ) );
		return Long.toString( token.mPrime );
	}
	
	/**
	 * Internal helper function to determine if a number is prime.
	 * 
	 * @param n is the number to check whether it is prime or not.
	 * @return true if n is even and false if n is odd.
	 */
	private static boolean isPrime( long n )
	{
		for( long i = 2; i < n; ++i )
		{
	        if( n % i == 0 )
	            return false;
	    }
	    return true;
	}
}