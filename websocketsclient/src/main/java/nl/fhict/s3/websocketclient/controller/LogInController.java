package nl.fhict.s3.websocketclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    private Button btnLogIn;

    public void btnLogInClicked(ActionEvent actionEvent) {
    }

    public void btnRegisterClicked(ActionEvent actionEvent) {
    }

    private void navigateToPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/" + page));


            //Open and show the RegisterUser window
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();

            //Close the current window
            Stage thisStage = (Stage)btnLogIn.getScene().getWindow();
            thisStage.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
