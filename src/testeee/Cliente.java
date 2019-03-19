package testeee;
import java.net.*;
import java.io.*;
import java.util.*;

public class Cliente  {

    private ObjectInputStream sInput;  // to read from the socket
    private ObjectOutputStream sOutput;// to write on the socket
    private Socket socket;

    private ClienteGUI cg;
    
    private String strIP, strNome, strPorta;

    Cliente(String strIP, String strPorta, String strNome) {
        this(strIP, strPorta, strNome, null);
    }

    Cliente(String strIP, String strPorta, String strNome, ClienteGUI cg) {
        this.strIP = strIP;
        this.strPorta = strPorta;
        this.strNome = strNome;
        this.cg = cg;
    }
    
    public boolean start() {
        try {
            socket = new Socket(strIP, Integer.parseInt(strPorta));
        
            String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
            display(msg);
    
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());

            // creates the Thread to listen from the strIP 
            new ListenFromServer().start();
            // Send our strNome to the strIP this is the only message that we
            // will send as a String. All other messages will be ChatMessage objects
            sOutput.writeObject(strNome);
        }
        catch (IOException eIO) {
            display("Exception: " + eIO);
            return false;
        }
        return true;
    }

    /*
     * To send a message to the console or the GUI
     */
    private void display(String msg) {
        if(cg == null)
            System.out.println(msg);    // println in console mode
        else
            cg.append(msg + "\n");      // append to the ClienteGUI JTextArea (or whatever)
    }
    
    /*
     * To send a message to the strIP
     */
    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        }
        catch(IOException e) {
            display("Exception writing to strIP: " + e);
        }
    }

    /*
         * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try { 
            if(sInput != null) sInput.close();
            if(sOutput != null) sOutput.close();
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do
        
        // inform the GUI
        if(cg != null)
            cg.connectionFailed();
            
    }
    /*
     * To start the Cliente in console mode use one of the following command
     * > java Cliente
     * > java Cliente strNome
     * > java Cliente strNome strPortaNumber
     * > java Cliente strNome strPortaNumber strIPAddress
     * at the console prompt
     * If the strPortaNumber is not specified 1500 is used
     * If the strIPAddress is not specified "localHost" is used
     * If the strNome is not specified "Anonymous" is used
     * > java Cliente 
     * is equivalent to
     * > java Cliente Anonymous 1500 localhost 
     * are eqquivalent
     * 
     * In console mode, if an error occurs the program simply stops
     * when a GUI id used, the GUI is informed of the disconnection
     */
    public static void main(String[] args) {
        // default values
        String strPortaNumber = "1500";
        String strIPAddress = "localhost";
        String userName = "Anonymous";

        // depending of the number of arguments provided we fall through
        switch(args.length) {
            case 3:// > javac Cliente strNome strPortaNumber strIPAddr
                strIPAddress = args[2];
            case 2:// > javac Cliente strNome strPortaNumber
                strPortaNumber = args[1];
            case 1: // > javac Cliente strNome
                userName = args[0];
            case 0:// > java Cliente
                break;
            default:// invalid number of arguments
                System.out.println("Usage is: > java Client [strNome] [strPortaNumber] {strIPAddress]");
            return;
        }
        // create the Cliente object
        Cliente client = new Cliente(strIPAddress, strPortaNumber, userName);
        // test if we can start the connection to the Server
        // if it failed nothing we can do
        if(!client.start())
            return;
        
        // wait for messages from user
        Scanner scan = new Scanner(System.in);
        // loop forever for message from the user
        while(true) {
            System.out.print("> ");
            String msg = scan.nextLine();// read message from user
            if(msg.equalsIgnoreCase("LOGOUT")) {// logout if message is LOGOUT
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;// break to do the disconnect
            }
            else if(msg.equalsIgnoreCase("WHOISIN")) {// message WhoIsIn
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));               
            }
            else {  // default to ordinary message
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }
        client.disconnect();    // done disconnect
    }

    /*
     * a class that waits for the message from the strIP and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {
        public void run() {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    // if console mode print the message and add back the prompt
                    if(cg == null) {
                        System.out.println(msg);
                        System.out.print("> ");
                    }
                    else {
                        cg.append(msg);
                    }
                }
                catch(IOException e) {
                    display("Server has close the connection: " + e);
                    if(cg != null) 
                        cg.connectionFailed();
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch(ClassNotFoundException e2) {}
            }
        }
    }
}
