package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.FirestoreContext;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    private Stage primaryStage;
    private UserManager userManager = new UserManager();

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        if (fxmlLoader.getLocation() == null) {
            throw new IllegalStateException("Location is not set. Check if the FXML file path is correct.");
        }
        return fxmlLoader.load();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();


        showSplashScreen();
    }

    private void showSplashScreen() {

        ImageView splashImage = new ImageView(new Image(getClass().getResourceAsStream("/files/Splash.png")));
        splashImage.setFitWidth(400);
        splashImage.setFitHeight(300);


        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);


        StackPane splashLayout = new StackPane();
        splashLayout.getChildren().addAll(splashImage, progressBar);
        splashLayout.setStyle("-fx-background-color: white");


        Scene splashScene = new Scene(splashLayout, 500, 400);


        primaryStage.setScene(splashScene);
        primaryStage.show();


        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            showMainMenu();
        });
        delay.play();
    }

    void showMainMenu() {
        MenuScreen menu = new MenuScreen(userManager, this);
        Scene menuScene = menu.createMenuScene(primaryStage);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    void showOriginalMainScreen() {
        try {

            Parent originalRoot = FXMLLoader.load(getClass().getResource("/files/AccessFBView.fxml"));

            Scene scene = new Scene(originalRoot, 900, 600);

            scene.getStylesheets().add(getClass().getResource("/files/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSignInForm() {
        SignInForm signInForm = new SignInForm(userManager, this);
        Scene signInScene = signInForm.createSignInForm(primaryStage);
        primaryStage.setScene(signInScene);
        primaryStage.show();
    }

    public void showRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm(userManager, this);
        Scene registrationScene = registrationForm.createRegistrationForm(primaryStage);
        primaryStage.setScene(registrationScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
