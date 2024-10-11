package com.example.csc325_firebase_webview_auth.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignInForm {

    private final UserManager userManager;
    private final App app;

    public SignInForm(UserManager userManager, App app) {
        this.userManager = userManager;
        this.app = app;
    }

    public Scene createSignInForm(Stage stage) {

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signInButton = new Button("Sign In");
        Button backButton = new Button("Back");


        signInButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();


            if (userManager.authenticateUser(email, password)) {
                app.showOriginalMainScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Sign In Error", "Invalid credentials.");
            }
        });


        backButton.setOnAction(e -> app.showMainMenu());


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);


        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(signInButton, 1, 2);
        gridPane.add(backButton, 0, 2);

        return new Scene(gridPane, 400, 200);
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
