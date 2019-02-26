package pratica4;
/*
+--------------------+
| Rodrigo CavanhaMan |
|        IFTM        |
|SistemasDistribuídos|
+--------------------+
THREAD - técnica para criar fluxos alternativos compartilhando memoria, disco e processador.
*/
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public Servidor(Socket s){
            conexao = s;
    }
 
    public static void main(String[] args)  {
        
        try {
            
            ServerSocket s = new ServerSocket(2000);
            while(true){
                System.out.println("Aguardando conexao....");
                Socket conexao = s.accept();
                Thread t = new Servidor(conexao);
                t.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}