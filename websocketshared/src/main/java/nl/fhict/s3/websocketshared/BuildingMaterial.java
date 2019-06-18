package nl.fhict.s3.websocketshared;

public class BuildingMaterial implements Buyable {
    private User seller;
    private User buyer;
    private String name;
    private Double price;
    private Category category;
    private int amount;
    private boolean sold;

    public BuildingMaterial(User seller, String name, Double price, Category category, int amount) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.category = category;
        this.amount = amount;
        this.sold = false;
    }

    public User getSeller() {
        return seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public boolean buyBuildingMaterial(User user){
        if(sold != true){
            this.sold = true;
            this.buyer = user;
            return true;
        }

        return false;
    }
}
