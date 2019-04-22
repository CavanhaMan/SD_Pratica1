package pratica6;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* IMPLEMENTAÇÃO DE RPC em Java - RMI
*************************************/
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalcClient {
    public static void main(String[] args)  {
        try {
            Calc calculator;
            calculator = (Calc) Naming.lookup("rmi://10.10.80.114:2335/calc");
            int a=3; //n da serie
            int b = 14;
            int f = calculator.getSoma(a,b);
            System.out.println("A soma eh: " +f);
        } catch (NotBoundException ex) {System.out.println("Nao foi possivel achar o objeto remoto no servidor");}
        catch (MalformedURLException ex) {System.out.println("Nao eh um URI RMI valida");}
        catch (RemoteException ex) {System.out.println("Objeto remoto tratou a execucao " + ex);}
    }    
}
