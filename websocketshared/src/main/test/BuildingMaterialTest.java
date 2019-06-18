import nl.fhict.s3.websocketshared.BuildingMaterial;
import nl.fhict.s3.websocketshared.Category;
import nl.fhict.s3.websocketshared.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingMaterialTest {

    User seller;
    User buyer;

    @BeforeEach
    public void setUpTests(){
        seller = new User("Henk");
        buyer = new User("Sjaak");
    }

    @Test
    public void testBuyBuildingMaterial_BuyBuildingMaterial_BuildingMaterialIsBought(){
        BuildingMaterial bm = new BuildingMaterial(seller, "Betonijzer 6mm 3m", 20.00, Category.Construction, 5);
        boolean expected = true;

        boolean actual = bm.buyBuildingMaterial(buyer);

        assertEquals(expected, actual);
    }

    @Test
    public void testBuyBuildingMaterial_BuyBuildingMaterial_BuildingMaterialAlreadySold(){
        User secondBuyer = new User("Peter");
        BuildingMaterial bm = new BuildingMaterial(seller, "Betonijzer 6mm 3m", 20.00, Category.Construction, 5);
        boolean expected = false;

        bm.buyBuildingMaterial(buyer);
        boolean actual = bm.buyBuildingMaterial(secondBuyer);

        assertEquals(expected, actual);
    }

}
