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

public class Servidor {
    public static void main(String[] args)  {
        try {
            DatagramSocket s = new DatagramSocket(4545);
            System.out.println(aColor.BLUE + aColor.CYAN_BACKGROUND + "___Servidor ativo!_________________");
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Servidor aguardando mensagem");
            
            DatagramPacket recebe = new DatagramPacket(new byte[5000],5000);
            while(true){
                s.receive(recebe);
                System.out.print(aColor.BLUE + "Mensagem recebida: ");
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
//https://docs.oracle.com/javase/tutorial/networking/datagrams/broadcasting.html
//https://www.baeldung.com/java-broadcast-multicast
//https://www.guj.com.br/t/broadcast-resolvido/130238
//https://notepad.pw/keilane

//CRIAR INTERFACE GRAFICA:
//BOTAO ENVIAR MONTA O BUFFER E MONTA O DATAGRAMA
//BOTAO SAIR FAZ O S.CLOSE();