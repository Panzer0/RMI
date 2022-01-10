import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.TreeMap;

public interface Server extends Remote {
    String simplifyText(String text) throws RemoteException;
    HashMap<String, Integer> findMostCommon(String text) throws RemoteException;
}
