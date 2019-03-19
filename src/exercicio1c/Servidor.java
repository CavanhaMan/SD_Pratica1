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
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor extends Thread {

    private static ArrayList clientes;
    private Socket conexao;
    private String meuNome;

    public Servidor(Socket s){conexao = s;}
 
    public static void main(String[] args)  {
     InetAddress endereco_remoto;
     int porta_remota;
     
     try {
        FileWriter arquivo = new FileWriter("d:/Server_Conection_Report.txt");
        PrintWriter gravarArquivo = new PrintWriter(arquivo);

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        //formatador.format( data );

        clientes = new ArrayList();
        ServerSocket s = new ServerSocket(2000);

        System.out.println(aColor.BLUE + aColor.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
        System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Serviço iniciado em " + formatador.format( data ));
        System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Porta: 2000");
        System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "IP: " + InetAddress.getLocalHost().getHostAddress());
        
        gravarArquivo.append("\n___Servidor ativo!_______________________");
        gravarArquivo.append("\nServiço iniciado em " + formatador.format( data ));
        gravarArquivo.append("\nPorta: 2000");
        gravarArquivo.append("\nIP: " + InetAddress.getLocalHost().getHostAddress());
            
        while(true){
            System.out.println("___Aguardando conexao____________________");
            Socket conexao = s.accept();
            
            endereco_remoto = conexao.getInetAddress();
            porta_remota = conexao.getPort();

            System.out.println(aColor.BLUE + aColor.CYAN_BACKGROUND + "___Novo cliente conectado!_______________");
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Conexão iniciada em " + formatador.format( data ));
            //System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Nome do usuário: " + nomeCli);
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Nome da maqina remota: " + endereco_remoto.getHostName());
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "IP da maqina remota: " + endereco_remoto.getHostAddress());
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Porta da maqina remota: " + porta_remota);
            
            gravarArquivo.append("\n___Novo cliente conectado!_______________");
            gravarArquivo.append("\nConexão iniciada em " + formatador.format( data ));
            //gravarArquivo.println("Nome do usuário: " + nomeCli);
            gravarArquivo.append("\nNome da maqina cliente: " + endereco_remoto.getHostName());
            gravarArquivo.append("\nIP da maqina cliente: " + endereco_remoto.getHostAddress());
            gravarArquivo.append("\nPorta da maqina clente: " + porta_remota);

            arquivo.close();
            
            Thread t = new Servidor(conexao);
            t.start();
        }
      } catch (IOException ex) {
        System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
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
        System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());
     }
    }
    //saida => remetente
    //chat  => destinatario
    private void sendToAll(PrintStream saida, String acao, String linha) throws IOException {
        Enumeration e = Collections.enumeration(clientes);
        //Enumeration e = clientes.elements();
        while(e.hasMoreElements()){
            PrintStream chat = (PrintStream) e.nextElement();
            System.out.println(chat);
            if (chat != saida)
                chat.println(meuNome + acao + linha);
            if (acao.equalsIgnoreCase(aColor.RED + " saiu "))
                if (chat == saida)
                    chat.println("");
        }
    }
}