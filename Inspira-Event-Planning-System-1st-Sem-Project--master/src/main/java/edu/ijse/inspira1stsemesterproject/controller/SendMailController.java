package edu.ijse.inspira1stsemesterproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;

    @Setter
    private String supplierEmail;

    @FXML
    void sendUsingGmailOnAction(ActionEvent event) {
        if(customerEmail == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Email Not Set");
            alert.showAndWait();
            return;
        }

//        if(supplierEmail == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Supplier Email Not Set");
//            alert.showAndWait();
//            return;
//        }
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        if (customerEmail != null) {
            sendEmailWithGmail(customerEmail, subject, body);
        } else if (supplierEmail != null) {
            sendEmailWithGmail(supplierEmail, subject, body);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unknown email context");
            alert.showAndWait();
        }
    }

    void sendEmailWithGmail(String to, String subject, String body) {
        final String FROM = "sachinthaprabhashana2003@gmail.com";
        String PASSWORD = "gytfvkavorwdbept";

        // Create a new Properties object to hold configuration settings for the SMTP (Simple Mail Transfer Protocol) connection
        Properties props = new Properties();

        // Enable SMTP authentication. This requires the server to authenticate the sender before sending emails.
        props.put("mail.smtp.auth", "true");

        // Enable STARTTLS, which upgrades an insecure connection to a secure one (TLS encryption).
        props.put("mail.smtp.starttls.enable", "true");

        // Specify the SMTP server host. For Gmail, it is "smtp.gmail.com".
        props.put("mail.smtp.host", "smtp.gmail.com");

        // Specify the port to use for SMTP. Port 587 is used for TLS connections. Alternatively, port 465 can be used for SSL.
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {

            // Replace with your email and app password
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM,PASSWORD); // Replace with your email and password
            }
        });

        try {
            // Create a new MimeMessage object to represent the email message.
            Message message = new MimeMessage(session);

            // Set the sender's email address using the `from` parameter.
            message.setFrom(new InternetAddress(FROM));

            // Set the recipient(s) of the email. The `to` parameter is parsed to handle multiple recipients, if necessary.
            // `Message.RecipientType.TO` defines that this is the primary recipient (To field).
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the subject of the email using the `subject` parameter.
            message.setSubject(subject);

            // Set the body (content) of the email using the `messageBody` parameter.
            message.setText(body);

            // Send the email message using the `Transport.send()` method.
            // This sends the email through the SMTP server configured in the session.
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }

}
