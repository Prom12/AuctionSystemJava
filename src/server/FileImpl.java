package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import rsa.rsa;
import shared.AuctionItem;
import shared.File;
import shared.NameClient;
import shared.UserDetails;

public class FileImpl implements File{

    private List<NameClient> clientsForBroadcast;
    private List<AuctionItem> AuctionItemList;
    private List<AuctionItem> itemListBidList;

    public String file;
    // Reading File Utility Class
    public class FileUtil{
        // Read file From Text (Input) and encrypt
        public static String readTextFile(String fileName) throws IOException{
            String content = new String(Files.readAllBytes(Paths.get(fileName)));

            // 
            rsa rsa = new rsa();
            rsa.initFromStrings();
            try {
                return rsa.encrypt(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.out.println("ppppppppp :"+ rsa.rsaPublic.getEncoded() +" hgjhjh :"+ rsa.rsaPrivate.getEncoded());

            return content;
        }
        // Read content of file line by line and store in a list
        public static List<String> readTextFileByLines(String fileName) throws IOException {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            return lines;
        }
    
        // Write Content to Text File (Output)
        public static void writeToTextFile(String fileName, String content) throws IOException {
            Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
        }
        
    }

    public FileImpl() throws RemoteException{
       // Export File Location Using UnicastRemoteObject class
       UnicastRemoteObject.exportObject(this, 0); 

       clientsForBroadcast = new ArrayList<>(); 
       AuctionItemList = new ArrayList<>(); 
       itemListBidList = new ArrayList<>(); 

    }

 
    @Override
    public String getname(String str,NameClient client) throws RemoteException {
        
        String result = str.toUpperCase();
        // Do Something here
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        updateClients(result, client);
        return str.toUpperCase();
    }

    private void updateClients(String result, NameClient dontBroadcastToMe) {
        for ( NameClient client: clientsForBroadcast) {
            if(client.equals(dontBroadcastToMe)) continue;

            try {
                client.update(result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFile() throws RemoteException {
        String fileName = "D:\\Extra Learn\\Masters\\Sem 2\\Distributed System\\RMI\\RMI\\src\\Files\\Test.txt";
        
        String inputFile = "";
        try {
            inputFile = FileUtil.readTextFile(fileName);     
        } catch (IOException e) {
            e.printStackTrace();
        }
     

        return inputFile;
    }

    @Override
    public void registerClient(NameClient clientToRegister) {
        clientsForBroadcast.add(clientToRegister);
    }


    @Override
    public String createItem(AuctionItem auctionItem) throws RemoteException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        
        AuctionItem Item  = new AuctionItem();
            Item.auctionId =  Integer.toString(Item.randomNumberGenerator());
            
        AuctionItemList.add(auctionItem);

        // updateClients( "Item Successfully Created", client);
        // Item.createItem("1", auctionItem.startingPrice, auctionItem.reservedPrice, 0.0, auctionItem.sellerId, auctionItem.itemName);
        return "Item Successfully Created";
        
    }


    @Override
    public   List<AuctionItem> getActiveItems() throws RemoteException {
        
        return AuctionItemList;
    }


    @Override
    public String sendItemToBid(AuctionItem itemBid, int selectedItem, UserDetails userDetail) {
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        
        if(itemBid.startingPrice > itemBid.BiddingAmount) return "Sorry, Bidding amount is too low ";

        itemBid.buyerName = userDetail.name;
        itemBid.buyerPhoneNo = userDetail.phoneNo;
        itemBid.selectedItemIndex = selectedItem;
        itemListBidList.add(itemBid);
        
        return "You have bid "+ itemBid.BiddingAmount+ " on " + itemBid.itemName + "Successfully";
    }

    
}
