package com.example.csc325_firebase_webview_auth.view;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

public class FirebaseStorageUploader {

    private Storage storage;

    public FirebaseStorageUploader() {
        try {
            // Initialize Firebase Storage
            storage = StorageOptions.newBuilder()
                    .setProjectId("your-firebase-project-id")
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("path/to/your/serviceAccountKey.json")))
                    .build()
                    .getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadProfilePicture(File file, String userId) {
        try {
            // Read the file bytes
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            // Create a BlobId
            BlobId blobId = BlobId.of("your-bucket-name", "profile_pictures/" + userId + "/" + file.getName());

            // Create a BlobInfo object
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();

            // Upload the file to Firebase Storage
            storage.create(blobInfo, fileBytes);

            // Return the download URL
            return "https://storage.googleapis.com/your-bucket-name/profile_pictures/" + userId + "/" + file.getName();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
