package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import rsa.rsa;
import shared.AuctionItem;
import shared.File;
import shared.NameClient;
import shared.UserDetails;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientFile implements  NameClient{    
    private File file;

    public ClientFile()throws RemoteException{
        // This Statement Serializes Object
        UnicastRemoteObject.exportObject(this, 0); 
    }
    //  Start Client 
    public void startClient() throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry("localhost",9100);
        // Checking for Server Register that has the name server
        file = (File) registry.lookup("server");
        file.registerClient(this);
    }

    public String FileRe(String argument) throws RemoteException{
        // Reading Shared File responses From Register (In Server)
        String fileContent = file.getFile();
        String name = null;
        try {
            name = file.getname(argument,this);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not contact server");
        }
       
         rsa rsa = new rsa();
         rsa.initFromStrings();

         if(fileContent != null){
            // Decrypted Message
            try {
                System.out.println(rsa.decrypt(fileContent));
            } catch (Exception e) {
                e.getMessage();
            }
         }
         System.out.println("");
         System.out.println("<-------------------------Reading Decrypted Content of File Ended ------------------> ");
        
        return name;
    }

    public String CreateItem(AuctionItem argument) throws RemoteException{
        // Reading Shared File responses From Register (In Server)
        String result = "";
        try {
            result = file.createItem(argument);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not contact server");
        }
       
        return result;
    }

    public  List<AuctionItem> GetActiveItems(){
        // Reading Shared File responses From Register (In Server)
        List<AuctionItem> result;
        try {
            result = file.getActiveItems();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not contact server");
        }
       
        return result;
    }

    public String Bid(AuctionItem auctionItem, int selectedItem, UserDetails userDetail){
        String result;
        try {
            result = file.sendItemToBid(auctionItem,selectedItem,userDetail);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not contact server");
        }
        return result;
    }

    public void update(String result){
        System.out.println("BroadCasted > " + result );
    }

}
