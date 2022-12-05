package client;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import shared.AuctionItem;
import shared.UserDetails;



public class Client {
    public static UserDetails RegisterUsers(Scanner ScannerInput,String[] userType ){
        System.out.println("Who are you ? \nPlease Type in :");

            for(int userOption = 1;userOption < 3; userOption++)
                System.out.print(userOption+" for "+ userType[userOption-1]+"\n");
            
            String userResponse = ScannerInput.nextLine();
            switch (userResponse) {
                case "1":
                    UserDetails Buyer = new UserDetails("Moses","a@gmail.com","44545",userType[0],"12345");
                    return Buyer;
                case "2":
                    UserDetails Seller = new UserDetails("Jeremy","a@gmail.com","44545",userType[1],"125");
                    return Seller;
                default:
                    System.out.println("Acceptable responses are 1 or 2 : Please try again");
                    return RegisterUsers(ScannerInput,userType);
        }
        
    }
    

    public static void Seller(Scanner ScannerInput,UserDetails  User,ClientFile client) throws RemoteException{
        System.out.println("Hello "+ User.name +" What would you like to do ?");
        System.out.println("1. Create an Item");
        System.out.println("2. Deal with closed Items");

            int UserChoice = ScannerInput.nextInt();
            if(UserChoice == 1){
                // create item and send
                AuctionItem itemToSend = new AuctionItem();
                ScannerInput.nextLine();
                System.out.println("Kindly provide the following details on the item you would like to create \n ");
                
                System.out.println("Item name : \n");
                itemToSend.itemName = ScannerInput.nextLine();


                System.out.println("Item starting price : \n");
                itemToSend.startingPrice = ScannerInput.nextInt();

                System.out.println("Item reserved price : ");
                itemToSend.reservedPrice = ScannerInput.nextInt();

                System.out.println("Item Auction time frame eg.10 : ");
                System.out.println("Auction time is received in seconds eg. 10sec. Please do not add the sec, just the number is alright ");
                itemToSend.activeFor = ScannerInput.nextInt();

                itemToSend.sellerId = User.userId;
                System.out.println(client.CreateItem(itemToSend)); 
            }else if(UserChoice == 2){
                // 


            }else{
                Seller(ScannerInput,User,client);
            }
        
        
    }
    
    public static void Buyer(Scanner ScannerInput,UserDetails  User,ClientFile client)throws RemoteException{
        int currentItemState = 0;
        System.out.println("Hello "+ User.name +" What would you like to do ?");
        System.out.println("1. Get Active Auctions To Bid");

        int UserChoice = ScannerInput.nextInt();

        if(UserChoice == 1){
            //Get All items 
            List<AuctionItem> allItems = client.GetActiveItems();
            
            if(allItems.size() == 0)
                System.out.println("No active Items to bid for at the Moment");

            System.out.print("Item Name : Starting Price");
            for(AuctionItem model : allItems) {
                currentItemState++;
                
                System.out.print(currentItemState +". "+ model.itemName +" : " + model.startingPrice);
            }
            
            System.out.println("Select an item to bid on by typing in the number");
            int selectedItem = ScannerInput.nextInt();
            if(selectedItem  > allItems.size())return;
            // Bidding with selected Item
            System.out.println("How much are you bidding");
                allItems.get(selectedItem-1).BiddingAmount = ScannerInput.nextInt();
                System.out.println(client.Bid(allItems.get(selectedItem-1), selectedItem,User));

        }else{
            Buyer(ScannerInput,User,client);
        }
    }
    
    public static void printSummary(){
    }
    public static void main(String [] args) throws RemoteException, NotBoundException{

        System.setProperty("java.security.policy","D:\\Extra Learn\\Masters\\Sem 2\\Distributed System\\RMI2\\RMI\\all.policy");
        if(System.getSecurityManager() == null){

            System.setSecurityManager(new RMISecurityManager());
        }
        ClientFile client = new ClientFile();
        client.startClient();
        Scanner ScannerInput = new Scanner(System.in);
        String[] userType = {"Buyer","Seller"}; 
        UserDetails  User;
        
        
        System.out.println("");
        System.out.println("A Program that takes simulates an auction system. Type / Press (exit or Ctrl C) to stop Program");
        System.out.println("");
        
        while(true){
            try {
                System.out.println("Registering User\n");
                  User = RegisterUsers(ScannerInput,userType);
  
                    switch (User.userType) {
                        case "Buyer":
                            // Buyer
                            Buyer(ScannerInput,User,client);
                            break;
                        default:
                            // Seller
                            Seller(ScannerInput,User,client);
                            break;
                        }
            } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
            }
            
           
                      
            // if(line.equalsIgnoreCase("exit")) break;
            // try {
            //     String result =  client.FileRe(line);
            //     System.out.println("Result > " + result);
            // } catch (Exception e) {
            //     System.out.println("Error: " + e.getMessage());
            // }
            
            // printSummary();
        }




    }
    
    
}
