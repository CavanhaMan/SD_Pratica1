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

public class Cliente {
    public static void main(String[] args){ //não usamos o throw para poder tratar o erro localmente e enviar uma resposta amigavel ao cliente
        try {       //neste caso usaremos o try-catch
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            //e aqui vc pega a data:
            //formatador.format( data );

            //primeiro passo é criar o socket usando localhost ou 127.0.0.1 através da porta 8080 ou outra porta qualquer (no caso 2000).
            Socket conexao = new Socket("localhost",2001);//trocar localhost para outro ip ou host qq
            
            //agora criamos os 2 canais - in e out
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            
            String linha;
            BufferedReader teclado = new BufferedReader (new InputStreamReader(System.in));
            //usaremos um buffer de memória para armazenar a entrada via teclado temporariamente
            
            
            while (true){
                System.out.print("> ");
                linha = teclado.readLine(); //neste caso teremos entrada de dados
                saida.writeUTF(linha);
                linha = entrada.readUTF();
                if(linha.equalsIgnoreCase("")){
                    System.out.println("Conexão encerrada - " + formatador.format(data));
                    break;
                }
                System.out.println(linha);
            }

        } catch (IOException ex) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
}

