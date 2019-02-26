package pratica2;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
*/
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Servidor {
    public static void main(String[] args)  {
        
        try {
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            //e aqui vc pega a data:
            //formatador.format( data );
            
            
            InetAddress endereco_remoto;
            int porta_remota;
            
            ServerSocket s = new ServerSocket(2000);//o server socket tem um serviço que espera conexao (implementado abaixo)
            System.out.println("Esperando conexao..........");
            
            Socket conexao = s.accept(); //esta opção faz o servidor ficar rodando esperando conexao
            System.out.println("Conexao aceita - esperando dados....");
            
            endereco_remoto = conexao.getInetAddress();
            porta_remota = conexao.getPort();
            
            FileWriter arq = new FileWriter("d:\\report.txt");
            PrintWriter gravarArq = new PrintWriter(arq);

            System.out.println("Conexão iniciada em " + formatador.format( data ));
            System.out.println("Nome da maqina remota: " + endereco_remoto.getHostName());
            System.out.println("IP da maqina remota: " + endereco_remoto.getHostAddress());
            System.out.println("Porta da maqina remota: " + porta_remota);

            gravarArq.println("Conexão iniciada em " + formatador.format( data ));
            gravarArq.println("Nome da maqina remota: " + endereco_remoto.getHostName());
            gravarArq.println("IP da maqina remota: " + endereco_remoto.getHostAddress());
            gravarArq.println("Porta da maqina remota: " + porta_remota);
            
            //agora criamos os 2 canais - in e out para conexao (não sobre o s)
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());

            System.out.println("Conexão realizada com sucesso!");
            gravarArq.println("Conexão encerrada em " + formatador.format( data ));
            System.out.println("Conexão encerrada em " + formatador.format( data ));
            for (int i=0 ; i<200000 ; i++){
                int entra = entrada.readInt();
                //System.out.println("Entrei");
                saida.writeUTF("Seu dado foi recebido: "+entra);
            }
            
            arq.close();
            
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
}