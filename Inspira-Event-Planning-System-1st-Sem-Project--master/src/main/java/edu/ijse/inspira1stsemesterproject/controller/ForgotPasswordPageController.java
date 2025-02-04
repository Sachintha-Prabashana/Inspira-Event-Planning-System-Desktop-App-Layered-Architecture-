package edu.ijse.inspira1stsemesterproject.controller;

import com.jfoenix.controls.JFXButton;
import edu.ijse.inspira1stsemesterproject.bo.BOFactory;
import edu.ijse.inspira1stsemesterproject.bo.UserBO;
import edu.ijse.inspira1stsemesterproject.bo.impl.UserBOImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ForgotPasswordPageController implements Initializable {

    @FXML
    private AnchorPane paneForgotPass;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtEmail;

    SendMailController sendMailController = new SendMailController();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public static String emailAddress = "";
    public static String otpGenerated = "0000";// Default value

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneForgotPass.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                btnSubmit.fire();
            }
        });
    }

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        emailAddress = txtEmail.getText();

        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields are empty");
            return; // Exit the method if fields are empty
        }

        if (!isValidEmailAddress()) {
            showErrorMessage("*Invalid email address");
            return; // Exit the method if the email is invalid
        }

        // Generate OTP and assign it
        otpGenerated = generateOTP();

        // Send OTP via email
        String recipientEmail = emailAddress;
        sendMailController.sendEmailWithGmail(recipientEmail, "Your OTP Code", otpGenerated);

        loadUI("/view/OtpForm.fxml");
    }


    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 1000 + random.nextInt(9000); // Generates a 4-digit OTP
        return String.valueOf(otp);
    }

    private boolean isValidEmailAddress() throws SQLException, ClassNotFoundException {
        return userBO.isEmailExists(txtEmail.getText());
    }

    private boolean areFieldsEmpty() {
        return txtEmail.getText().isEmpty();
    }

    private void loadUI(String resource) {
        paneForgotPass.getChildren().clear();
        try {
            paneForgotPass.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
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


    public void imgBackOnClick(MouseEvent mouseEvent) {
        loadUI("/view/LogInPage.fxml");
    }
}
