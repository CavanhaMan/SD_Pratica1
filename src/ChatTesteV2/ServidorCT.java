package ChatTesteV2;
/*--------------------*
 * Rodrigo CavanhaMan *
 *--------------------*
https://www.devmedia.com.br/como-criar-um-chat-multithread-com-socket-em-java/33639
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ServidorCT extends Thread {
    private static ArrayList<BufferedWriter>clientes;
    private static ServerSocket server;
    private String nome;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    
    /**
    * Método construtor 
    * @param com do tipo Socket
    */
    public ServidorCT(Socket con){
        this.con = con;
        try {
            in  = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {e.printStackTrace();}                          
    }
    
    /**
      * Método run
      */
    public void run(){
        try{
            String msg;
            OutputStream ou =  this.con.getOutputStream();
            Writer ouw = new OutputStreamWriter(ou);
            BufferedWriter bfw = new BufferedWriter(ouw); 
            clientes.add(bfw);
            nome = msg = bfr.readLine();

            while(!"Sair".equalsIgnoreCase(msg) && msg != null) {           
                msg = bfr.readLine();
                sendToAll(bfw, msg);
                System.out.println(msg);                                              
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    /***
     * Método usado para enviar mensagem para todos os clients
     * @param bwSaida do tipo BufferedWriter
     * @param msg do tipo String
     * @throws IOException
     */
    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;
        for(BufferedWriter bw : clientes){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(nome + " -> " + msg+"\r\n");
                bw.flush(); 
            }
        }
    }

    /***
    * Método main
    * @param args
    */
    public static void main(String []args) {
        InetAddress endereco_remoto;
        int porta_remota;

        try{
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            //Cria os objetos necessário para instânciar o servidor
            JLabel lblMessage = new JLabel("Configurar os dados de conexão:");
            JTextField txtPorta = new JTextField("2000");
            Object[] texts = {lblMessage, txtPorta };  
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null,"Servidor ativo na porta: " + txtPorta.getText() + "\nServiço iniciado em " + formatador.format( data ) + "\nIP: " + InetAddress.getLocalHost().getHostAddress());

            System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Serviço iniciado em " + formatador.format( data ));
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Porta: 2000");
            System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "IP: " + InetAddress.getLocalHost().getHostAddress());
            
            while(true){
                System.out.println("___Aguardando conexao____________________");
                Socket con = server.accept();
            
                endereco_remoto = con.getInetAddress();
                porta_remota = con.getPort();

                System.out.println(Color.BLUE + Color.CYAN_BACKGROUND + "___Novo cliente conectado!_______________");
                System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Conexão iniciada em " + formatador.format( data ));
                //System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome do usuário: " + nomeCli);
                System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Nome da maqina remota: " + endereco_remoto.getHostName());
                System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "IP da maqina remota: " + endereco_remoto.getHostAddress());
                System.out.println(Color.BLUE + Color.YELLOW_BACKGROUND + "Porta da maqina remota: " + porta_remota);
            

                Thread t = new ServidorCT(con);
                t.start();   
            }

        }catch (Exception e) {e.printStackTrace();}
    }// Fim do método main                      
} //Fim da classe

