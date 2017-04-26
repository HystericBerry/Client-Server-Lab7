import java.io.IOException;
import java.net.ServerSocket;

public class AppServer
{
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		// Check validity of executable arguments
		if (args.length != 1)
		{
            System.err.println("Usage: java LoadServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt( args[0] );
        
        try
        {
        	// Start a new Thread to handle each Client separately.
        	ServerSocket serverSocket = new ServerSocket(portNumber);
        	while( true )
        	{
        		new ServerThread( serverSocket.accept() ).start();
        	}
        }
        catch(IOException e)
        {
        	System.out.println("Exception caught when trying to listen on port " 
        			+ portNumber + " or listening for a connection");
              System.out.println(e.getMessage());
              System.exit(1);
        }
	}
}
