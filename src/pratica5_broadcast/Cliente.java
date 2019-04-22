package pratica5_broadcast;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* SOCKETS USANDO UDP/IP *
*************************/
import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public static void main(String[] args)  {
        try {
            DatagramSocket s = new DatagramSocket();
            InetAddress dest = InetAddress.getByName("127.0.0.1"); //getByAddress();
            String envio;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> ");
            envio = teclado.readLine();
            while(!envio.equalsIgnoreCase("")){
                byte[] buffer = envio.getBytes();
                DatagramPacket msg = new DatagramPacket(buffer, buffer.length, dest, 4545);
                s.send(msg);
                DatagramPacket resposta = new DatagramPacket(new byte[5000],5000);
                s.receive(resposta);
                for(int i=0 ; i<resposta.getLength() ; i++)
                    System.out.print((char)resposta.getData()[i]);
                System.out.println();
                System.out.print("> ");
                envio = teclado.readLine();
            }
            s.close();
        } catch (IOException ex) {System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());}
    }
}
