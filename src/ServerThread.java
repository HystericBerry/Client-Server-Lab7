import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A ServerThread represents a single Client Server Session.
 * 
 */
public class ServerThread extends Thread
{
	public ServerThread( Socket serverSocket )
	{
		this.mSocket = serverSocket;
	}
	
	@Override
	public void run()
	{
        String inputLine, outputLine;
        
        try
        {
        	// Get message from socket/ connection
        	Token token = new Token();
        	PrintWriter out = new PrintWriter( mSocket.getOutputStream(), true );
            BufferedReader in = new BufferedReader( new InputStreamReader(mSocket.getInputStream()) );
            
            // While there is no input left
			while ( (inputLine = in.readLine()) != null )
			{
				outputLine = Services.invoke( inputLine, token );
			    out.println(outputLine);
			    
			    // Technically, this is unnecessary
			    if ( outputLine.equals("Bye.") )
			        break;
			}
			
			// successfully end a Client's session
			in.close();
	        out.close();
	        mSocket.close();
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
	}
	
	private Socket mSocket;
}