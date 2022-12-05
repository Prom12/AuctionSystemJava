package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface File extends Remote {

    String getname(String str, NameClient client) throws RemoteException;
    public String getFile() throws RemoteException;
    public List<AuctionItem> getActiveItems() throws RemoteException;
    String createItem(AuctionItem auctionItem) throws RemoteException;
    void registerClient(NameClient clientToRegister) throws RemoteException;
    String sendItemToBid(AuctionItem auctionItem, int selectedItem,UserDetails userDetail) throws RemoteException;
}
