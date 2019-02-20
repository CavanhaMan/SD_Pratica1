package pratica3;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
*/
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args){ //não usamos o throw para poder tratar o erro localmente e enviar uma resposta amigavel ao cliente
        try {       //neste caso usaremos o try-catch
            
            //primeiro passo é criar o socket usando localhost ou 127.0.0.1 através da porta 8080 ou outra porta qualquer (no caso 2000).
            Socket s = new Socket("localhost",2000);//trocar localhost para outro ip ou host qq
            
            //agora criamos os 2 canais - in e out
            DataOutputStream saida = new DataOutputStream(s.getOutputStream());
            DataInputStream entrada = new DataInputStream(s.getInputStream());
            
            for (int i=0 ; i<200 ; i++){
                saida.writeInt(i); //envia e mostra na tela o que foi enviado
                System.out.println("Enviei: "+i);
                String en = entrada.readUTF();//captura o que voltou e mostra na tela o que foi retornado
                System.out.println("Recebi: "+en);
            }

        } catch (IOException ex) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
}

