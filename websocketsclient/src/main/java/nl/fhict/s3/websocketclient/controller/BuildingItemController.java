package nl.fhict.s3.websocketclient.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nl.fhict.s3.websocketclient.LocalData;
import nl.fhict.s3.websocketclient.endpoint.ClientEndpoint;
import nl.fhict.s3.websocketshared.BuildingMaterial;
import nl.fhict.s3.websocketshared.Category;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BuildingItemController implements Observer {

    @FXML
    private ListView<BuildingMaterial> lvActiveOffers;
    @FXML
    private ComboBox<Category> cbMaterialCategory;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfAmount;

    public void initialize() {
        ClientEndpoint.getInstance().addObserver(this);
        ClientEndpoint.getInstance().start();
        cbMaterialCategory.getItems().setAll(Category.values());
    }

    public void btnAddBuildingItemClicked(ActionEvent actionEvent) {
        boolean correctInput = true;
        Category category = cbMaterialCategory.getSelectionModel().selectedItemProperty().getValue();

        try{
            Double.parseDouble(tfPrice.getText());
            Integer.parseInt(tfAmount.getText());
        } catch (Exception e){
            correctInput = false;
            System.out.println(e.getMessage());
        }

        if(correctInput){
            BuildingMaterial bm = new BuildingMaterial(LocalData.getUser(), tfName.getText(), Double.parseDouble(tfPrice.getText()),
                    category, Integer.parseInt(tfAmount.getText()));
            ClientEndpoint.getInstance().addBuildingMaterial(bm);
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Check input");
            alert.setHeaderText("Check your input values");
            alert.showAndWait();
        }

    }

    public void btnBuyBuildingItemClicked(ActionEvent actionEvent) {
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String content = arg.toString();
                Gson gson = new Gson();
                List<BuildingMaterial> bm = gson.fromJson(content, new TypeToken<List<BuildingMaterial>>(){}.getType());
                System.out.println(bm.get(0).toString());
                //lvActiveOffers.getItems().clear();
                //lvActiveOffers.getItems().setAll(bm);
            }
        });
    }
}
