package com.example.demo.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class BlobStorageService {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    private final BlobServiceClient blobServiceClient;

    public BlobStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public void uploadToStorage(String message) {
        // Get the container client
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        // Create the container if it doesn't exist
        if (!containerClient.exists()) {
            containerClient.create();
        }

        // Upload the message as a blob
        // Convert the message to a byte array
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        // Upload the message as a blob
        containerClient.getBlobClient("test").upload(new ByteArrayInputStream(messageBytes), messageBytes.length);
    }
}