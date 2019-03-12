package exercicio1b;
/**********************************************
*             Rodrigo  CavanhaMan             *
*                    IFTM                     *
*            Sistemas Distribuídos            *
***********************************************
*Chat Multithread com Socket e Janelas em Java*
***********************************************/
import static exercicio1b.Chat.stIP;
import static exercicio1b.Chat.stNome;
import static exercicio1b.Chat.stPorta;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Cliente extends Thread {
    static String testeIP, testePorta, testeNome;
    private static boolean done = false;
    private Socket conexao;
    static JTextField txtIP;
    static JTextField txtPorta;
    static JTextField txtNome;

    public Cliente (Socket s){conexao = s;}
    
    public static void main(String[] args){
     try {
        testeIP=stIP;
        testePorta=stPorta;
        testeNome=stNome;
        System.out.println(testeIP);
        System.out.println(testePorta);
        System.out.println(testeNome);
        
        FileWriter arquivo = new FileWriter("d:/Server_Messenger_Report.txt");
        PrintWriter gravarArquivo = new PrintWriter(arquivo);
        
        JLabel lblMessage = new JLabel("Configurar os dados de conexão:");
        txtIP = new JTextField("127.0.0.1");   
        txtPorta = new JTextField("2000");
        txtNome = new JTextField("");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        
        Socket conexao = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
        //Socket conexao = new Socket(stIP.getText(),Integer.parseInt(stPorta.getText()));
        
        PrintStream saida = new PrintStream (conexao.getOutputStream());
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println(Color.GREEN + "Seja bem vindo " + txtNome.getText() + "!");
        
        Thread t = new Cliente(conexao); //aqui eu crio a THREAD
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
    
    public void run(){
     try { 
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //formatador.format( data );
        
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
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