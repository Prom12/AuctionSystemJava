package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NameClient extends Remote{
    
    void update(String result) throws RemoteException;

}
