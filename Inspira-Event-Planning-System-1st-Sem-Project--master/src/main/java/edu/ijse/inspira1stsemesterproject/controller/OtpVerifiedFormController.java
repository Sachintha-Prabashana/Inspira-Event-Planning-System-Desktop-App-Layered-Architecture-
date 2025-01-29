package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXButton;
import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.UserBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class OtpVerifiedFormController implements Initializable {

    @FXML
    private JFXButton btnReset;

    @FXML
    private ImageView imgConfirmPasswordView;

    @FXML
    private ImageView imgPasswordView;

    @FXML
    private Label lblLogin;


    @FXML
    private PasswordField pwfConfirmPassword;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private PasswordField pwfPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label lblError;

    //private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    private UserBO userBO = new UserBOImpl();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword.requestFocus();

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty.");
        } else if (!(txtPassword.getText().equals(txtPassword.getText()))) {
            showErrorMessage("*Passwords do not match.");
        } else {
            if (updateUser()) {
                loadUI("/view/LogInPage.fxml");
            } else {
                showErrorMessage("*User not updated.");
            }
        }

    }


    private boolean areFieldsEmpty() {
        return txtPassword.getText().isEmpty() && txtConfirmPassword.getText().isEmpty();
    }


    private boolean updateUser() throws SQLException, ClassNotFoundException {
        ArrayList<UserDto> allUsers = userBO.getAllUsers();
        for (UserDto user : allUsers) {
            if (user.getEmail().equals(ForgotPasswordPageController.emailAddress)) {
                user.setPassword(pwfPassword.getText());
                userBO.updateUser(user);
                return true;
            }
        }
        return false;
    }

    @FXML
    void imgBackOnAction(MouseEvent event) {
        loadUI("/view/LogInPage.fxml");
    }

    @FXML
    void imgConfirmPasswordViewOnClick(MouseEvent event) {
        if (pwfConfirmPassword.isVisible()) {
            txtConfirmPassword.setText(pwfConfirmPassword.getText());
            pwfConfirmPassword.setVisible(false);
            txtConfirmPassword.setVisible(true);
        } else {
            pwfConfirmPassword.setText(txtConfirmPassword.getText());
            txtConfirmPassword.setVisible(false);
            pwfConfirmPassword.setVisible(true);
        }

    }

    @FXML
    void imgPasswordViewOnClick(MouseEvent event) {
        if (pwfPassword.isVisible()) {
            txtPassword.setText(pwfPassword.getText());
            pwfPassword.setVisible(false);
            txtPassword.setVisible(true);
        } else {
            pwfPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false);
            pwfPassword.setVisible(true);
        }

    }

    private void showErrorMessage(String message) {
        lblError.setText(message);
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-alignment: center");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }

    private void loadUI(String resource) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
