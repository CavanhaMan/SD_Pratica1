package pratica4;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
THREAD - técnica para criar fluxos alternativos compartilhando memoria, disco e processador.
*/
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {
    private static boolean done = false;
    private Socket conexao;
    
    public Cliente (Socket s){
        conexao = s;
    }
    
    public static void main(String[] args){
        try {
            //System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "RED COLORED" + Color.RESET + " NORMAL");
            Socket conexao = new Socket("localhost",2000);
            PrintStream saida = new PrintStream (conexao.getOutputStream());
            
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(Color.GREEN + "Digite o seu nome: ");
            String meuNome = teclado.readLine();
            saida.println(meuNome);
            
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