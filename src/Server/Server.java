package Server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Server extends Remote {
    String simplifyText(String text) throws RemoteException;
    HashMap<String, Integer> findMostCommon(String text) throws RemoteException;
    void rotateImage90(String inName, String outName) throws RemoteException, IOException;
}
