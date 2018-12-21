//---------------------------------------------------------------
//         Project
//         KnockServer.java
//         Tells endless Knock! Knock! jokes to KnockClient.
//---------------------------------------------------------------
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class KnockServer {
    public static void main(String[] args) throws IOException {
        //args = new String[]{Integer.toString(getPortNumber())};
        args = new String[]{"3309"};
        int portNumber = Integer.parseInt(args[0]);
        
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
        
            String inputLine, outputLine;
            
            // Initiate conversation with client
            ServerProtocol sp = new ServerProtocol();
            outputLine = sp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = sp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    public static int getPortNumber() {
        int number;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Please enter a port number: ");
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number! ");
                System.out.print("Please enter a port number: ");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);
        return number;
    }
}