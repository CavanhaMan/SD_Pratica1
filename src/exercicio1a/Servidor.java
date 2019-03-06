package exercicio1a;
/**********************************************
*             Rodrigo  CavanhaMan             *
*                    IFTM                     *
*            Sistemas Distribuídos            *
***********************************************
*Chat Multithread com Socket e Janelas em Java*
***********************************************/
import java.io.*;
import static java.lang.Class.forName;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Servidor extends Thread {

    private static Vector clientes;
    private Socket conexao;
    static String nomeCli;
    private String meuNome;
    
    public Servidor(Socket s){conexao = s;}
    public static void main(String[] args){
    InetAddress endereco_remoto;
    int porta_remota;
    
    try{ 
        FileWriter arq1 = new FileWriter("d:/Server_Conection_Report.txt");
        PrintWriter gravarArq1 = new PrintWriter(arq1);
        
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //e aqui vc pega a data: formatador.format( data );
        
        clientes = new Vector();
        ServerSocket s = new ServerSocket(2000);//o server socket para aguardar conexao
        
        System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Serviço iniciado em " + formatador.format( data ));
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Porta: 2000");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "IP: " + InetAddress.getLocalHost().getHostAddress());
        
        gravarArq1.append("\n___Servidor ativo!_______________________");
        gravarArq1.append("\nServiço iniciado em " + formatador.format( data ));
        gravarArq1.append("\nPorta: 2000");
        gravarArq1.append("\nIP: " + InetAddress.getLocalHost().getHostAddress());
        //JOptionPane.showMessageDialog(null,"Servidor ativo! Porta: 2000. IP: "+InetAddress.getLocalHost().getHostAddress());
        
        while(true){
            System.out.println("___Aguardando conexao____________________");
            Socket conexao = s.accept();//esta opção faz o servidor ficar rodando esperando conexao
            endereco_remoto = conexao.getInetAddress();
            porta_remota = conexao.getPort();
            nomeCli = Cliente.meuNome;

            System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Novo cliente conectado!_______________");
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Conexão iniciada em " + formatador.format( data ));
            //System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome do usuário: " + nomeCli);
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome da maqina remota: " + endereco_remoto.getHostName());
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "IP da maqina remota: " + endereco_remoto.getHostAddress());
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Porta da maqina remota: " + porta_remota);
            
            gravarArq1.append("\n___Novo cliente conectado!_______________");
            gravarArq1.append("\nConexão iniciada em " + formatador.format( data ));
            //gravarArq1.println("Nome do usuário: " + nomeCli);
            gravarArq1.append("\nNome da maqina cliente: " + endereco_remoto.getHostName());
            gravarArq1.append("\nIP da maqina cliente: " + endereco_remoto.getHostAddress());
            gravarArq1.append("\nPorta da maqina clente: " + porta_remota);

            arq1.close();
            
            Thread t = new Servidor(conexao);
            t.start();
        }
      } catch (IOException ex) {
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
        nomeCli = Cliente.meuNome;
        System.out.println(Color.GREEN + Color.YELLOW_BACKGROUND + "Nome do usuário: " + nomeCli);
        System.out.println(Color.RED + Color.YELLOW_BACKGROUND + "Nome do usuário: " + meuNome);

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
                chat.println(nomeCli + acao + linha);
            if (acao.equalsIgnoreCase(Color.RED + " saiu "))
                if (chat == saida)
                    chat.println("");
        }
    }


}