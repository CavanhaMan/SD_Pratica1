package pratica4;
/************************************
*        Rodrigo  CavanhaMan        *
*               IFTM                *
*       Sistemas Distribuídos       *
*************************************
*Chat Multithread com Socket em Java*
*************************************/
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor extends Thread {

    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public Servidor(Socket s){
            conexao = s;
    }
 
    public static void main(String[] args)  {
     try {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //e aqui vc pega a data: formatador.format( data );

        clientes = new Vector();
        ServerSocket s = new ServerSocket(2000);

        System.out.println(Color.BLUE + Color.CYAN_BACKGROUND+ " Servidor ativo! ");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " Porta: 2000 ");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " IP: " + InetAddress.getLocalHost().getHostAddress()+" ");
        //JOptionPane.showMessageDialog(null,"Servidor ativo! Porta: 2000. IP: "+InetAddress.getLocalHost().getHostAddress());
            
        while(true){
            System.out.println(Color.BLUE + "Aguardando conexao....");
            Socket conexao = s.accept();
            System.out.println(Color.GREEN + "Conectou");
            Thread t = new Servidor(conexao);
            t.start();
        }
      } catch (IOException ex) {
        //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
     }
    }
    
    public void run(){
     try {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        PrintStream saida = new PrintStream(conexao.getOutputStream());
            
        meuNome = entrada.readLine();
        if(meuNome == null)
            return;
            
        clientes.add(saida);
        String linha = entrada.readLine();
        while((linha !=null) && (!linha.trim().equals("") && !"Sair".equalsIgnoreCase(linha))){
            sendToAll(saida," disse: ",linha);
            linha = entrada.readLine();
        }
            
        sendToAll(saida," saiu ","do chat!");
            
        clientes.remove(saida);
        conexao.close();
            
      } catch (IOException ex) {
        //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
     }
    }
    //saida => remetente
    //chat  => destinatario
    private void sendToAll(PrintStream saida, String acao, String linha) throws IOException {
        Enumeration e = clientes.elements();
        while(e.hasMoreElements()){
            PrintStream chat = (PrintStream) e.nextElement();
            System.out.println(chat);
            if (chat != saida)
                chat.println(meuNome + acao + linha);
            if (acao.equalsIgnoreCase(Color.RED + " saiu "))
                if (chat == saida)
                    chat.println("");
        }
    }
}