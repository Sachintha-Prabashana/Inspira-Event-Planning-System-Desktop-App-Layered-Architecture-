package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.UserBOImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class SecondRegisterPageController {

    @FXML
    private Button btnRegister;

    @FXML
    private Label lblAlreadyAcc;

    @FXML
    private Label lblConPass;

    @FXML
    private Label lblLoggedIn;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblRegister;

    @FXML
    private Label lblUsername;

    @FXML
    private AnchorPane secondPane;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtConPass;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    private final UserBO userBO = new UserBOImpl();

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}";

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{5,15}$";

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        saveUser();
    }

    private void saveUser() throws SQLException, ClassNotFoundException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty.");
        } else if (!isValidUsername(txtUsername.getText())) {
            showErrorMessage("*Username must be 5-15 characters, containing only letters, digits, or underscores.");
        } else if (!isValidPassword(txtPassword.getText())) {
            showErrorMessage("*Password must be at least 8 characters long, contain a digit, a lowercase letter, an uppercase letter, and a special character.");
        } else if (!txtPassword.getText().equals(txtConPass.getText())) {
            showErrorMessage("*Confirm password does not match.");
        } else {
            FirstRegisterPageController.userDto.setUsername(txtUsername.getText());
            FirstRegisterPageController.userDto.setPassword(txtConPass.getText());

            if (userBO.saveUser(FirstRegisterPageController.userDto)) {
                loadUI("/view/LogInPage.fxml");
            } else {
                showErrorMessage("*User not saved.");
            }
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean isValidUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }

    private boolean areFieldsEmpty() {
        return txtPassword.getText().isEmpty() || txtConPass.getText().isEmpty() || txtUsername.getText().isEmpty();
    }

    @FXML
    void lblLoggedInOnClick(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    private void loadUI(String resource) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(resource));
            secondPane.getChildren().setAll(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
        }
    }

    private void showErrorMessage(String msg) {
        lblError.setText(msg);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }

    public void imgBackOnAction(MouseEvent mouseEvent) {
        loadUI("/view/FirstRegisterPage.fxml");
    }
}
