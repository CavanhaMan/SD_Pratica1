package pratica5GUI;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* SOCKETS USANDO UDP/IP *
******************tese*******/
import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public static void main(String[] args)  {
        try {
            MulticastSocket s = new MulticastSocket();
            InetAddress group = InetAddress.getByName("230.0.0.1");
            InetAddress dest = InetAddress.getByName("localhost"); //getByAddress("127.0.0.1");
            String envio;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> ");
            envio = teclado.readLine();
            while(!envio.equalsIgnoreCase("")){
                byte[] buffer = envio.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, dest, 4545);
                s.receive(packet);
                //DatagramPacket msg = new DatagramPacket(buffer, buffer.length, dest, 4545);
                s.send(packet);
                //DatagramPacket resposta = new DatagramPacket(new byte[512],512);
                //s.receive(resposta);

                System.out.println(new String(packet.getData()));

                System.out.println("> ");
                envio = teclado.readLine();
            }
            s.close();
        } catch (IOException ex) {System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());}
    }
}
