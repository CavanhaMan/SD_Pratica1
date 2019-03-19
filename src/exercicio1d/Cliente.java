package exercicio1d;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Cliente extends Thread {
    static String testeIP, testePorta, testeNome;
    private static boolean done = false;
    private Socket conexao;

    public Cliente (Socket s){conexao = s;}
    
    public static void main(String[] args){
     try {
        testeIP=Chat.stIP;
        testePorta=Chat.stPorta;
        testeNome=Chat.stNome;
        System.out.println(testeIP);
        System.out.println(testePorta);
        System.out.println(testeNome);
        
        FileWriter arquivo = new FileWriter("d:/Server_Messenger_Report.txt");
        PrintWriter gravarArquivo = new PrintWriter(arquivo);
        
        JLabel lblMessage = new JLabel("Configurar os dados de conexão:");
        Object[] texts = {lblMessage, testeIP, testePorta, testeNome};
        JOptionPane.showMessageDialog(null, texts);
        
        Socket conexao = new Socket(testeIP,Integer.parseInt(testePorta));
        //Socket conexao = new Socket(stIP.getText(),Integer.parseInt(stPorta.getText()));
        
        PrintStream saida = new PrintStream (conexao.getOutputStream());
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println(aColor.GREEN + "Seja bem vindo " + testeNome + "!");
        
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