package nl.fhict.s3.websocketclient.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nl.fhict.s3.websocketclient.endpoint.ClientEndpoint;
import nl.fhict.s3.websocketshared.BuildingMaterial;
import nl.fhict.s3.websocketshared.Category;
import nl.fhict.s3.websocketshared.User;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BuildingItemController implements Observer {

    @FXML
    private ListView<BuildingMaterial> lvActiveOffers;

    public void initialize() {
        ClientEndpoint.getInstance().addObserver(this);
        ClientEndpoint.getInstance().start();
        BuildingMaterial bm = new BuildingMaterial(new User("Henk"), "Balk", 25.00, Category.Construction, 3);
        BuildingMaterial bm2 = new BuildingMaterial(new User("Sjaak"), "IJzer", 10.00, Category.Rebar, 3);
        ClientEndpoint.getInstance().addBuildingMaterial(bm);
        ClientEndpoint.getInstance().addBuildingMaterial(bm2);
    }

    public void btnAddBuildingItemClicked(ActionEvent actionEvent) {
    }

    public void btnBuyBuildingItemClicked(ActionEvent actionEvent) {
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                List<BuildingMaterial> bm = gson.fromJson(arg.toString(), new TypeToken<List<BuildingMaterial>>(){}.getType());
                if (!bm.isEmpty()) {
                    for(BuildingMaterial buildingMaterial : bm)
                    lvActiveOffers.getItems().add(buildingMaterial);
                }
            }
        });
    }
}
