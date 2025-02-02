package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.UserBOImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblCreateAcc;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblNotAcc;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtPassword;

    @FXML
    private PasswordField pwfPassword;

    @FXML
    private TextField txtUsername;

    private final UserBOImpl userBO = (UserBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                btnLogin.fire();
            }
        });


    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        navigateToDashBoardPage();
    }

    public void navigateToDashBoardPage() {
        if(areFieldsEmpty()){
            showErrorMessage("Please fill all the required fields");
        }else {
            String password = pwfPassword.getText();
            String username = txtUsername.getText();
            boolean isMatch = false;

            try{
                isMatch = userBO.validateUser(username,password);
            }catch(SQLException e){
                new Alert(Alert.AlertType.ERROR,"Throwing a SQL Exception");
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                new Alert(Alert.AlertType.ERROR,"Throwing a ClassNotFound Exception");
                ex.printStackTrace();
            }
            if(isMatch){
                loadUI("/view/DashBoard.fxml");
            } else{
                showErrorMessage("Provided credentials are incorrect");
            }
        }
    }
    private boolean areFieldsEmpty() {
        return txtUsername.getText().isEmpty() || pwfPassword.getText().isEmpty();
    }

    @FXML
    void lblCreateAccOnClick(MouseEvent event) {
        loadUI("/view/FirstRegisterPage.fxml");

    }

    @FXML
    void lblForgotPasswordOnClick(MouseEvent event) {
        loadUI("/view/ForgotPasswordForm.fxml");

    }

    @FXML
    private void loadUI(String resourse){
        //welcomePane.getChildren().clear();
        try{
            loginPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(resourse));
            load.prefWidthProperty().bind(loginPane.widthProperty());
            load.prefHeightProperty().bind(loginPane.heightProperty());
            loginPane.getChildren().add(load);
        }catch(IOException e){
            new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
            e.printStackTrace();
        }
    }

    @FXML
    void imgEyeOnClick(MouseEvent event) {
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
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lblError.setText("")
        ));
        timeline.play();
    }

}
