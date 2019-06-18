package nl.fhict.s3.websocketshared;

public class Bid {

    private User bidder;
    private Double amount;

    public Bid(User bidder, Double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }
}
