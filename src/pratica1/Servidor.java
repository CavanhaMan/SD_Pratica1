package pratica1;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
*/
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args)  {
        
        try {
            
            ServerSocket s = new ServerSocket(2000);//o server socket tem um serviço que espera conexao (implementado abaixo)
            System.out.println("Esperando conexao..........");
            
            Socket conexao = s.accept(); //esta opção faz o servidor ficar rodando esperando conexao
            System.out.println("Conexao aceita - esperando dados....");
            
            //agora criamos os 2 canais - in e out para conexao (não sobre o s)
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());

            for (int i=0 ; i<100000 ; i++){
                int entra = entrada.readInt();
                System.out.println("Entrei");
                saida.writeUTF("Seu dado foi recebido: "+entra);
            }

            
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
}
