package com.example.csc325_firebase_webview_auth.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class ProfilePictureUploader {

    public File chooseImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        return fileChooser.showOpenDialog(stage);
    }
}
