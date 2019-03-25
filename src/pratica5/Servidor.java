package pratica5;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* SOCKETS USANDO UDP/IP *
*************************/
import pratica5.aColor;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    public static void main(String[] args)  {
        try {
            DatagramSocket s = new DatagramSocket(4545);
            System.out.println(aColor.BLUE + aColor.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Servidor aguardando mensagem");
            
            DatagramPacket recebe = new DatagramPacket(new byte[512],512);
            while(true){
                s.receive(recebe);
                System.out.print("Mensagem recebida: ");
                for(int i=0 ; i<recebe.getLength() ; i++)
                    System.out.print((char)recebe.getData()[i]);
                System.out.println();
                //recebe.setAddress.getByAddress("10.10.80.0");
                //DatagramPacket resp = new DatagramPacket(recebe.getData(),recebe.getLength(),recebe.getAddress(),recebe.getPort());
                DatagramPacket resp = new DatagramPacket(recebe.getData(),recebe.getLength(),recebe.getAddress(),recebe.getPort());
                s.send(resp);
            }
        } catch (IOException ex) {System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());}
    }
}
