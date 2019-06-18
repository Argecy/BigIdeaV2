import nl.fhict.s3.websocketshared.Auction;
import nl.fhict.s3.websocketshared.Bid;
import nl.fhict.s3.websocketshared.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuctionTest {

    Auction auction;
    User buyer;

    @BeforeEach
    public void setUpTests(){
        User seller = new User("Henk");
        buyer = new User("Sjaak");
        auction = new Auction(seller, 60, 5, "Wit", "Snowcap", 23.50);
    }

    @Test
    public void testPlaceBid_PlaceHigherBid_BidIsHighestBid(){
        Double amount = 25.00;
        Bid bid = new Bid(buyer, amount);
        boolean expected = true;

        boolean actual = auction.placeBid(bid);

        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceBid_PlaceLowerBid_BidNotPlaced(){
        Double amount = 20.00;
        Bid bid = new Bid(buyer, amount);
        boolean expected = false;

        boolean actual = auction.placeBid(bid);

        assertEquals(expected, actual);
    }

    @Test
    public void testPlaceBid_PlaceBidAfterAuctionEnded_BidNotPlaced(){
        Double amount = 50.00;
        Bid bid = new Bid(buyer, amount);
        boolean expected = false;

        auction.setEnded(true);

        boolean actual = auction.placeBid(bid);
        assertEquals(expected, actual);
    }
}
