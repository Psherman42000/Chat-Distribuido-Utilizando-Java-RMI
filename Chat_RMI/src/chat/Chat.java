package chat;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote{
    void enviar(String user,String msg) throws RemoteException;
    void entrou(String user) throws RemoteException;
    void saiu(String user) throws RemoteException;
    String receber(int n) throws RemoteException;
}
