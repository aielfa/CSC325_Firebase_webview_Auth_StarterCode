package com.example.csc325_firebase_webview_auth.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen {

    private UserManager userManager;
    private App app;

    public MenuScreen(UserManager userManager, App app) {
        this.userManager = userManager;
        this.app = app;
    }

    // Create the menu scene
    public Scene createMenuScene(Stage stage) {
        // Create buttons
        Button signInButton = new Button("Sign In");
        Button registerButton = new Button("Register");

        // Event handlers for buttons
        signInButton.setOnAction(e -> {
            SignInForm signInForm = new SignInForm(userManager, app);
            stage.setScene(signInForm.createSignInForm(stage));
        });

        registerButton.setOnAction(e -> {
            RegistrationForm registrationForm = new RegistrationForm(userManager, app);
            stage.setScene(registrationForm.createRegistrationForm(stage));
        });

        // Set up layout using VBox
        VBox layout = new VBox(10);
        layout.getChildren().addAll(signInButton, registerButton);
        layout.setAlignment(Pos.CENTER);

        // Create a scene and return it
        return new Scene(layout, 300, 200);
    }
}
