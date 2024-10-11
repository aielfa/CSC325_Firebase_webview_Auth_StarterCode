package com.example.csc325_firebase_webview_auth.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrationForm {

    private final UserManager userManager;
    private final App app;


    public RegistrationForm(UserManager userManager, App app) {
        this.userManager = userManager;
        this.app = app;
    }


    public Scene createRegistrationForm(Stage stage) {

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");


        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "All fields must be filled.");
            } else if (userManager.registerUser(name, email, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Success", "User registered successfully!");
                app.showSignInForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Email is already in use.");
            }
        });


        backButton.setOnAction(e -> app.showMainMenu());


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);


        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(registerButton, 1, 3);
        gridPane.add(backButton, 0, 3);

        return new Scene(gridPane, 400, 250);
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
