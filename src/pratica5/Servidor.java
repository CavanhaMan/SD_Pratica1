package pratica5;
/**********************************************
*             Rodrigo  CavanhaMan             *
*                    IFTM                     *
*            Sistemas Distribuídos            *
***********************************************
*Chat Multithread com Socket e Janelas em Java*
***********************************************/
import pratica4.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Servidor extends Thread {

    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public Servidor(Socket s){conexao = s;}
 
    public static void main(String[] args)  {
     try {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //e aqui vc pega a data: formatador.format( data );
        InetAddress endereco_remoto;
        int porta_remota;
        
        clientes = new Vector();
        ServerSocket s = new ServerSocket(2000);//o server socket para aguardar conexao
        
        FileWriter arq1 = new FileWriter("d:\\Server_Conection_Report.txt");//Abre arquivo de conexão
        PrintWriter gravarArq1 = new PrintWriter(arq1);
        
        System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " Serviço iniciado em " + formatador.format( data ));
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " Porta: 2000 ");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " IP: " + InetAddress.getLocalHost().getHostAddress()+" ");
        gravarArq1.println("___Servidor ativo!_______________________");
        gravarArq1.println(" Serviço iniciado em " + formatador.format( data ));
        gravarArq1.println(" Porta: 2000 ");
        gravarArq1.println(" IP: " + InetAddress.getLocalHost().getHostAddress()+" ");
        //JOptionPane.showMessageDialog(null,"Servidor ativo! Porta: 2000. IP: "+InetAddress.getLocalHost().getHostAddress());
        arq1.close();
        
        while(true){
            FileWriter arq2 = new FileWriter("d:\\Server_Messenger_Report.txt");//Abre arquivo de mensagens
            PrintWriter gravarArq2 = new PrintWriter(arq2);
            
            System.out.println("___Aguardando conexao____________________");
            Socket conexao = s.accept();//esta opção faz o servidor ficar rodando esperando conexao
            System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Novo cliente conectado!_______________");
            endereco_remoto = conexao.getInetAddress();
            porta_remota = conexao.getPort();

            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Conexão iniciada em " + formatador.format( data ));
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome da maqina remota: " + endereco_remoto.getHostName());
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "IP da maqina remota: " + endereco_remoto.getHostAddress());
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Porta da maqina remota: " + porta_remota);
            
            gravarArq2.println("___Novo cliente conectado!_______________");
            gravarArq2.println("Conexão iniciada em " + formatador.format( data ));
            gravarArq2.println("Nome da maqina remota: " + endereco_remoto.getHostName());
            gravarArq2.println("IP da maqina remota: " + endereco_remoto.getHostAddress());
            gravarArq2.println("Porta da maqina remota: " + porta_remota);

            Thread t = new Servidor(conexao);
            t.start();
            
            arq2.close();
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
        while((linha !=null) && (!linha.trim().equals("") && !"".equalsIgnoreCase(linha))){
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