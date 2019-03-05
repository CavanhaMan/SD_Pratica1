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
    
    public Servidor(Socket s){conexao = s;}
    public static void main(String[] args){
    InetAddress endereco_remoto;
    int porta_remota;

        FileWriter arq1 = null;
     try {
        arq1 = new FileWriter("d:\\Server_Conection_Report.txt");//Abre arquivo de conexão
        PrintWriter gravarArq1 = new PrintWriter(arq1);

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //e aqui vc pega a data: formatador.format( data );
        
        
        clientes = new Vector();
        ServerSocket s = new ServerSocket(2000);//o server socket para aguardar conexao
        
        System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " Serviço iniciado em " + formatador.format( data ));
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " Porta: 2000 ");
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + " IP: " + InetAddress.getLocalHost().getHostAddress()+" ");
        
        gravarArq1.println("___Servidor ativo!_______________________");
        gravarArq1.println(" Serviço iniciado em " + formatador.format( data ));
        gravarArq1.println(" Porta: 2000 ");
        gravarArq1.println(" IP: " + InetAddress.getLocalHost().getHostAddress()+" ");
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
            
            gravarArq1.println("___Novo cliente conectado!_______________");
            gravarArq1.println("Conexão iniciada em " + formatador.format( data ));
            //gravarArq1.println("Nome do usuário: " + nomeCli);
            gravarArq1.println("Nome da maqina cliente: " + endereco_remoto.getHostName());
            gravarArq1.println("IP da maqina cliente: " + endereco_remoto.getHostAddress());
            gravarArq1.println("Porta da maqina clente: " + porta_remota);
        arq1.close();
            Thread t = new Servidor(conexao);
            t.start();
        }
      } catch (IOException ex) {
        //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(Color.RED_BOLD + Color.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
      } finally {try {arq1.close();} catch (Exception ex) {/*ignore*/}}
    }
    
    public void run(){
      try {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        PrintStream saida = new PrintStream(conexao.getOutputStream());
        
        nomeCli = Cliente.meuNome;
        System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome do usuário: " + nomeCli);

        String str = ("Nome do usuário: " + nomeCli);
        File newTextFile = new File("D:/thetextfile.txt");
        FileWriter fw = new FileWriter(newTextFile);
        fw.write(str);
        fw.close();

        String str2 = "SomeMoreTextIsHere";
        File newTextFile2 = new File("D:/thetextfile2.txt");
        FileWriter fw2 = new FileWriter(newTextFile2);
        fw2.write(str2);
        fw2.close();


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
                chat.println(nomeCli + acao + linha);
            if (acao.equalsIgnoreCase(Color.RED + " saiu "))
                if (chat == saida)
                    chat.println("");
        }
    }
}