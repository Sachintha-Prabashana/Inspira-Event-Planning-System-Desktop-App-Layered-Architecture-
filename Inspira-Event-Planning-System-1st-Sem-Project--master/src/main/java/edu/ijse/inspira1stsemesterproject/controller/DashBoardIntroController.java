package edu.ijse.inspira1stsemesterproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardIntroController {

    @FXML
    private AnchorPane homePane;

    public void imgLogOutOnClick(MouseEvent mouseEvent) {
        navigateTo();

    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInPage.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Image/icon-inspira.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateTo() {
        try {
            Stage stage = (Stage) homePane.getScene().getWindow();
            stage.close();

            start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
