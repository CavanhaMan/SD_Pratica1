package pratica4;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
*/
import pratica3.*;
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

            ServerSocket s = new ServerSocket(2001);//o server socket tem um serviço que espera conexao (implementado abaixo)
            while(true){
                System.out.println("Aguardando conexao..........");

                Socket conexao = s.accept(); //esta opção faz o servidor ficar rodando esperando conexao
                System.out.println("Conexao aceita - " + formatador.format(data));

                //agora criamos os 2 canais - in e out para conexao (não sobre o s)
                DataInputStream entrada = new DataInputStream(conexao.getInputStream());
                DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());

                String linha = entrada.readUTF();
                while(linha!= null && !(linha.trim().equals(""))) {
                    saida.writeUTF(linha);
                    linha = entrada.readUTF();
                }
                saida.writeUTF(linha);
                System.out.println("Conexão encerrada - " + formatador.format(data));

                conexao.close();
            }
            
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
}