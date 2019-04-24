package ChatUDP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends Thread {

    private DatagramSocket s;
    private List<Integer> listaDePortas;
    private List<InetAddress> listaDeAddress;

    public Servidor() {
        try {
            s = new DatagramSocket(4546);
            listaDePortas = new ArrayList<Integer>();
            listaDeAddress = new ArrayList<InetAddress>();
        } catch (SocketException ex) {System.out.println(ex.getMessage() + " Local: ChatUDP.Servidor.<init>()");}
    }

    public static void main(String args[]) throws Exception {
        Servidor s = new Servidor();
        s.start();
    }

    public void run() {
        try {
            DatagramSocket s = new DatagramSocket(4545);
            System.out.println("Servidor esperando conexão......");

            DatagramPacket recebido = new DatagramPacket(new byte[1024], 1024);

            while (true) {
                s.receive(recebido);
                if (!listaDePortas.contains(recebido.getPort())) {
                    listaDePortas.add(recebido.getPort());
                    listaDeAddress.add(recebido.getAddress());
                }
                for (int i = 0; i < listaDePortas.size(); i++) {
                    if (listaDePortas.get(i) != recebido.getPort()) {
                        DatagramPacket resp = new DatagramPacket(recebido.getData(), recebido.getLength(), listaDeAddress.get(i), listaDePortas.get(i));
                        s.send(resp);
                    }
                }
                System.out.println();
            }
        } catch (IOException ex) {System.out.println(ex.getMessage());}
    }
}
