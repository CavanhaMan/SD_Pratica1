package exercicio1c;
/**********************************************
*             Rodrigo  CavanhaMan             *
*                    IFTM                     *
*            Sistemas Distribuídos            *
***********************************************
*Chat Multithread com Socket e Janelas em Java*
***********************************************/
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Cliente extends Thread {

    static private ObjectInputStream sInput;  // to read from the socket
    static private ObjectOutputStream sOutput;// to write on the socket
    static private Socket socket;

    static String strIP, strPorta, strNome;
    static private ClienteGUI gui;
    private static boolean done = false;

    //public Cliente (Socket s){socket = s;}
    Cliente(String strIP, String strPorta, String strNome, ClienteGUI gui) {
        this.strIP = strIP;
        this.strPorta = strPorta;
        this.strNome = strNome;
        this.gui = gui;
    }

    private static void display(String msg) {
        gui.append(msg + "\n");
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args){
     try {
        FileWriter arquivo = new FileWriter("d:/Server_Messenger_Report.txt");
        PrintWriter gravarArquivo = new PrintWriter(arquivo);
        
        //JLabel lblMessage = new JLabel("Configurar os dados de conexão:");
        //Object[] texts = {lblMessage, strIP, strPorta, strNome};
        //JOptionPane.showMessageDialog(null, texts);
        socket = new Socket(strIP,Integer.parseInt(strPorta));
        
        PrintStream saida = new PrintStream (socket.getOutputStream());
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        //System.out.println(aColor.GREEN + "Seja bem vindo " + strNome + "!");
        String msg = "Conexão aceita! Seja bem vindo " + strNome + "! " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);
        
        sInput  = new ObjectInputStream(socket.getInputStream());
        sOutput = new ObjectOutputStream(socket.getOutputStream());
        
        //Thread t = new Cliente(socket); //aqui eu crio a THREAD
        Thread t = new Cliente(strIP, strPorta, strNome, gui); //aqui eu crio a THREAD
        t.start();                       //aqui eu starto a THREAD
        String linha;
        
        while(true){
            if(done)
                break;
            System.out.print(exercicio1a.Color.RESET + "> ");
            linha = teclado.readLine();
            saida.println(linha);
        }
      } catch (IOException ex) {
        System.out.println(exercicio1a.Color.RED_BOLD + exercicio1a.Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
      }
    }
    
    @Override
    public void run(){
     try { 
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //formatador.format( data );
        
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String linha;
        while(true){
            linha = entrada.readLine();
            if (linha.trim().equals("")){
                System.out.println(exercicio1a.Color.RED + "Conxao encerrada em " + formatador.format(data));
                break;
            }
            System.out.println();
            System.out.println(linha);
            System.out.println(exercicio1a.Color.RESET + "...> ");
        }
        done=true;
            
      } catch (IOException ex) {
        System.out.println(exercicio1a.Color.RED_BOLD + exercicio1a.Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
     }
    }
 }