package pratica5;
/**********************************************
*             Rodrigo  CavanhaMan             *
*                    IFTM                     *
*            Sistemas Distribuídos            *
***********************************************
*Chat Multithread com Socket e Janelas em Java*
***********************************************/
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Cliente extends Thread {
    private static boolean done = false;
    private Socket conexao;
    static JTextField txtIP;
    static JTextField txtPorta;
    static JTextField txtNome;
    public static String meuNome = "teste";
    
    public Cliente (Socket s){conexao = s;}
    
    public static void main(String[] args){
      try {
        FileWriter arq2 = new FileWriter("d:\\Server_Messenger_Report.txt");//Abre arquivo de mensagens
        PrintWriter gravarArq2 = new PrintWriter(arq2);
          
        JLabel lblMessage = new JLabel("Configurar os dados de conexão:");
        txtIP = new JTextField("127.0.0.1");   
        txtPorta = new JTextField("2000");
        txtNome = new JTextField("");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        Socket conexao = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
        
        PrintStream saida = new PrintStream (conexao.getOutputStream());
            
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        meuNome = txtNome.getText();
        System.out.println(Color.GREEN + "Seja bem vindo " + txtNome.getText() + "!");
            
        Thread t = new Cliente(conexao); //aqui eu crio a THREAD
        t.start();                       //aqui eu starto a THREAD
        String linha;
            
        while(true){
            if(done)
                break;
            System.out.print(Color.RESET + "> ");
            linha = teclado.readLine();
            saida.println(linha);
        }
            
      } catch (IOException ex) {
        //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
      }
    }
    
    public void run(){
      try { 
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //e aqui vc pega a data: formatador.format( data );

        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        String linha;
        while(true){
            linha = entrada.readLine();
            if (linha.trim().equals("")){
                System.out.println(Color.RED + "Conxao encerrada em " + formatador.format(data));
                break;
            }
            System.out.println();
            System.out.println(linha);
            System.out.println(Color.RESET + "...> ");
        }
        done=true;
            
      } catch (IOException ex) {
        //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
      }
    }
 }