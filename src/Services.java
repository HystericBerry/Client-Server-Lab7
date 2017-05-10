import java.math.BigInteger;

public class Services
{
	/**
	 * Invokes a Service given the Client's String request.
	 * 
	 * @param service is the name of the Service method to invoke.
	 * @param token is the representation of a Client's Stateful Session.
	 * @return
	 */
	public static String invoke( String service, NetworkToken token )
	{
		switch( service )
		{
		case "nextEvenFib":
			return Services.nextEvenFib( token );
		case "nextLargerRand":
			return Services.nextLargerRand( token );
		case "nextPrime":
			return Services.nextPrime( token );
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
	public static String nextEvenFib( NetworkToken token )
	{
		BigInteger nextFib = null;
		do
		{
			nextFib = new BigInteger( token.getNextFib().toString() );
		} while( !isEven( nextFib ) ); // while Fib is odd.
		return nextFib.toString();
	}
	
	public static Boolean isEven ( BigInteger decimal ) {
		String value = decimal.toString();
		Character end = value.charAt( value.length() - 1 );
		Integer i = new Integer(end);
		
		return ( i & 1 ) == 0;
	}
	
	/**
	 * Returns the next Larger Random number given the Session's previous state.
	 * 
	 * @param token is the representation of a Client's Stateful Session.
	 * @return the next Larger Random number given the Session's previous state.
	 */
	public static String nextLargerRand( NetworkToken token )
	{
		return token.nextRand();
	}
	
	/**
	 * Returns the next Prime number given the Session's previous state.
	 * 
	 * @param token is the representation of a Client's Stateful Session.
	 * @return the next Prime number given the Session's previous state.
	 */
	public static String nextPrime( NetworkToken token )
	{
		BigInteger nextInt = null;
		do
		{
			nextInt = new BigInteger( token.getAndIncrement().toString() );
		} while( !Services.isPrime( nextInt ) );
		return nextInt.toString();
	}
	
	/**
	 * Internal helper function to determine if a number is prime.
	 * 
	 * @param n is the number to check whether it is prime or not.
	 * @return true if n is even and false if n is odd.
	 */
	public static boolean isPrime( BigInteger number )
	{
		if( !number.isProbablePrime(5) )
			return false;
		
	    BigInteger two = BigInteger.valueOf(2);
	    if( !two.equals(number) && BigInteger.ZERO.equals(number.mod(two)) )
	    	return false;
	    
	    BigInteger i;
	    for( i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two) )
	    {
	    	if ( BigInteger.ZERO.equals( number.mod(i) ) )
	            return false;
	    }
	    return true;
	}
}