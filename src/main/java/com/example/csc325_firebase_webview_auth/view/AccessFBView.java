package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.Person;
import com.example.csc325_firebase_webview_auth.viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AccessFBView {
    private FirebaseStorageUploader firebaseStorageUploader = new FirebaseStorageUploader();
    private ProfilePictureUploader profilePictureUploader = new ProfilePictureUploader();

    @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    @FXML
    private TextArea outputField;

    @FXML
    private TableView<Person> userTableView;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> majorColumn;
    @FXML
    private TableColumn<Person, Integer> ageColumn;

    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    public void initialize() {
        // Initialize the TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        userTableView.setItems(listOfUsers);
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData();
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
    }

    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("/files/WebContainer.fxml");
    }

    public void addData() {
        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public boolean readFirebase() {
        boolean key = false;
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        List<QueryDocumentSnapshot> documents;

        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                listOfUsers.clear();
                for (QueryDocumentSnapshot document : documents) {
                    outputField.setText(outputField.getText() + document.getData().get("Name") + " , Major: " +
                            document.getData().get("Major") + " , Age: " +
                            document.getData().get("Age") + " \n ");

                    person = new Person(
                            String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString())
                    );

                    listOfUsers.add(person);
                }
                userTableView.setItems(listOfUsers);
                key = true;
            } else {
                System.out.println("No data");
                key = false;
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
            key = false;
        }

        return key;
    }


    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }
    public void uploadProfilePicture(Stage stage, String userId) {
        // Open the file chooser to select the image
        File selectedFile = profilePictureUploader.chooseImage(stage);

        if (selectedFile != null) {
            // Upload the selected image to Firebase Storage
            String downloadUrl = firebaseStorageUploader.uploadProfilePicture(selectedFile, userId);

            if (downloadUrl != null) {
                System.out.println("Profile picture uploaded successfully. Download URL: " + downloadUrl);
            } else {
                System.out.println("Failed to upload the profile picture.");
            }
        } else {
            System.out.println("No file selected.");
        }
    }
    public boolean registerUser() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
            return false;
        }
    }
}
