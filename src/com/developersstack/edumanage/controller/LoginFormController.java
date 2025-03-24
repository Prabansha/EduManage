package com.developersstack.edumanage.controller;

import com.developersstack.edumanage.db.Database;
import com.developersstack.edumanage.model.User;
import com.developersstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void forgotPasswordOnActoin(ActionEvent actionEvent) {
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().toLowerCase();
        String password = txtPassword.getText().trim();
//        find the user => (1000)
        Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email)).findFirst();
        if (selectedUser.isPresent()) {
            if (new PasswordManager().checkPassword(password,selectedUser.get().getPassword())) {
                setUi("DashboardForm");
            }else{
                new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,String.format("user not found (%s)",email)).show();
        }

    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
