package org.example.controller;

import org.example.model.PayloadRequest;
import org.example.service.AzureStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IngestionController {
    private final AzureStorageService azureStorageService;

    public IngestionController(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @PostMapping("/uploadToBucket")
    public ResponseEntity<String> uploadContent(@RequestBody PayloadRequest request) throws IOException {
        String fileUrl = azureStorageService.uploadFile(request);
        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(("File uploaded successfully: " + fileUrl));
    }
}
