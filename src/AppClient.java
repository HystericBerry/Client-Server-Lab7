import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppClient
{
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException
	{
		// Check validity of executable arguments
		if (args.length != 3)
		{
			System.out.println("Usage: java AppClient <host ip> <port number> <id>");
			System.exit(1);
		}
		
		String hostAddress = args[0];
		int portNumber = Integer.parseInt( args[1] );
		int id = Integer.parseInt( args[2] );
		String[] request = {"nextEvenFib", "nextLargerRand", "nextPrime"};
		
		try
		{
			// Initialize a new Socket
			Socket socket = new Socket( hostAddress, portNumber );
			PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
			String reply;
			
			out.println(id); // write id
			
			// Send 5 requests for each 3 services.
			for ( int i = 0; i < 3; ++i )
			{
				for ( int j = 0; j < 100; ++j )
				{
					System.out.println("Client requests: " + request[i]);
					out.println(request[i]);
					reply = in.readLine();
					System.out.println("Server responds: " + reply);
				}
			}
			
			// Protocol to end the Session.
			// Technically, this is unnecessary
			System.out.println("Client requests: End Protocol.");
			out.println("End Protocol.");
			reply = in.readLine();
			System.out.println("Server responds: " + reply);
		}
		catch (IOException e)
		{
			System.out.println("Couldn't get I/O for connection to " + hostAddress);
			System.exit(1);
		}	
	}
}
