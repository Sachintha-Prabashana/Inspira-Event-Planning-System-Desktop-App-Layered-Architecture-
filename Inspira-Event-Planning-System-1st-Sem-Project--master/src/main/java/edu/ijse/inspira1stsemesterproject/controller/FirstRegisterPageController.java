package edu.ijse.inspira1stsemesterproject.controller;

import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.UserBOImpl;
import edu.ijse.inspira1stsemesterproject.dto.UserDto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class FirstRegisterPageController {

    @FXML
    private Button btnNext;

    @FXML
    private ImageView imageBack;

    @FXML
    private Label lblAlreadyAcc;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblError;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblLoggedIn;

    @FXML
    private Label lblRegister;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    public static UserDto userDto = new UserDto();

    private UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    @FXML
    void btnNextOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        navigateToSecondRegisterPage();
    }

    private void navigateToSecondRegisterPage() throws SQLException, ClassNotFoundException {
        if(areFieldsEmpty()){
            showErrorMessage("*Required fields cannot be empty");
        }else if(!isValidEmail(txtEmail.getText())){
            showErrorMessage("*This email is not valid");
        }else{
            userDto.setUserId(userBO.getNextUserId());
            userDto.setEmail(txtEmail.getText());
            userDto.setFirstName(txtFirstName.getText());
            userDto.setLastName(txtLastName.getText());

            loadUI("/view/SecondRegisterPage.fxml");
        }
    }

    private boolean areFieldsEmpty() {
        return txtEmail.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty();
    }
    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @FXML
    void imageBackOnClick(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    @FXML
    void lblLoggedInOnClick(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    private void loadUI(String resource) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(resource));
            registerPane.getChildren().setAll(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Throwing a IOException").show();
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
