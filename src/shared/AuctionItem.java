package shared;

import java.io.Serializable;
import java.util.Random;

public class AuctionItem implements Serializable{
        public String auctionId = "";
        public String itemName = "";
        public float startingPrice = 0;
        public float reservedPrice = 0;
        public double activeFor = 0.0;
        public boolean deadlineReached = false;
        public String sellerId = "";
        public int selectedItemIndex = 0;
        public String buyerName = "";
        public String buyerPhoneNo = "";
        public double BiddingAmount = 0.0;
        
        
        public AuctionItem createItem(String auctionId,float startingPrice,float reservedPrice,double activeFor,String sellerId,String itemName){
            this.auctionId = auctionId;
            this.itemName = itemName;
            this.startingPrice = startingPrice;
            this.reservedPrice = reservedPrice;
            this.activeFor = activeFor;
            this.sellerId = sellerId;

            return this.groupData();
        }

        public AuctionItem groupData(){
             AuctionItem data = new AuctionItem(); 
            return data;
        }

        public void BidItem(){
            
        }

        public int randomNumberGenerator(){
            Random randI = new Random(); 
            int myRandInt = randI.ints(1, 101).findAny().getAsInt();
            return myRandInt;
        }
        
        
}

