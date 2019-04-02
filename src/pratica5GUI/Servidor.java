package pratica5GUI;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* SOCKETS USANDO UDP/IP *
*************************/
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class Servidor {
    public static void main(String[] args)  {
        try {
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            MulticastSocket s = new MulticastSocket(4545);
            //DatagramSocket s = new DatagramSocket(4545);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            
            System.out.println(aColor.BLUE + aColor.CYAN_BACKGROUND + "___Servidor ativo!_______________________");
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Servidor aguardando mensagem");
            System.out.println(aColor.BLUE + aColor.YELLOW_BACKGROUND + "Serviço iniciado em " + formatador.format( data ));

            JOptionPane.showMessageDialog(null,"SERVIDOR ATIVO! \nServiço iniciado em " + formatador.format( data ));
            
            DatagramPacket recebe = new DatagramPacket(new byte[512],512);
            //DatagramPacket recebe = new DatagramPacket(new byte[512],512);
            //System.out.println(group.getAllByName("230.0.0.1").length);
            while(true){
                byte[] buf = new byte[512];
                s.receive(recebe);
                System.out.print("Mensagem recebida: ");
                String saida="";
                for(int i=0 ; i<recebe.getLength() ; i++)
                    saida+=String.valueOf((char)recebe.getData()[i]);
                System.out.println(saida);
                DatagramPacket packet = new DatagramPacket(new byte[512], buf.length, group, 4545);
                s.send(packet);
                //recebe.setAddress.getByAddress("10.10.80.0");
                //DatagramPacket resp = new DatagramPacket(recebe.getData(),recebe.getLength(),recebe.getAddress(),recebe.getPort());
                DatagramPacket resp = new DatagramPacket(recebe.getData(),recebe.getLength(),recebe.getAddress(),recebe.getPort());
                s.send(resp);
            }
        } catch (IOException ex) {System.out.println(aColor.RED_BOLD + aColor.YELLOW_BACKGROUND + "ERRO: " + ex.getMessage());}
    }
}
