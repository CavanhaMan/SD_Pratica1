package pratica6;
/************************
*  Rodrigo  CavanhaMan  *
*         IFTM          *
* Sistemas Distribuídos *
*************************
* IMPLEMENTAÇÃO DE RPC em Java - RMI
*************************************/
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class CalcServer extends UnicastRemoteObject implements Calc{
    public CalcServer() throws RemoteException{
        super();
    }
    public static void main(String[] args)  {
        try {
            LocateRegistry.createRegistry(2335);
            CalcServer f = new CalcServer();
            Naming.rebind("//localhost")
        } catch (RemoteException ex) {
            Logger.getLogger(CalcServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public int getSoma(int a, int b) throws RemoteException {
        return(a+b);
    }

    @Override
    public int getSubtracao(int a, int b) throws RemoteException {
        return(a-b);
    }

    @Override
    public int getMultiplicacao(int a, int b) throws RemoteException {
        return(a*b);
    }

    @Override
    public double getDivisao(int a, int b) throws RemoteException {
        if(a!=0) return(a/b);
        else return(0);
    }
}
