package nl.fhict.s3.websocketshared;

public class Auction implements Buyable {

    private User seller;
    private int duration;
    private int amount;
    private String color;
    private String dahliaType;
    private Double startingPrice;
    private Double highestBid;
    private Boolean ended;

    public Auction(User seller, int duration, int amount, String color, String dahliaType, Double startingPrice){
        this.seller = seller;
        this.duration = duration;
        this.amount = amount;
        this.color = color;
        this.dahliaType = dahliaType;
        this.startingPrice = startingPrice;
        this.highestBid = startingPrice;
        this.ended = false;
    }

    public boolean placeBid(Bid bid){
        Double amount = bid.getAmount();

        if(amount > highestBid && ended == false){
            highestBid = amount;
            return true;
        } else{
            return false;
        }
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    @Override
    public String getName() {
        return this.dahliaType + ", " + this.amount + " kisten";
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public Double getPrice() {
        return this.highestBid;
    }


}
