package nl.fhict.s3.websocketclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.fhict.s3.restclient.UserRestClient;
import nl.fhict.s3.restshared.User;

import java.io.IOException;

public class LogInController {
    @FXML
    private Button btnLogIn;
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField tfPassword;
    private UserRestClient userRestclient;

    public void initialize() {
        userRestclient = new UserRestClient();
    }

    public void btnLogInClicked(ActionEvent actionEvent) {
        User user = new User(tfUserName.getText(), tfPassword.getText());
        if(userRestclient.authenticateUser(user) != null){
            navigateToPage("BuildingItemUI.fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Check credentials");
            alert.setHeaderText("Username or password incorrect");
            alert.showAndWait();
        }
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
