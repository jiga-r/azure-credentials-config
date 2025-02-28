package org.example.service;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.example.model.PayloadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AzureStorageService {

    private final BlobServiceClient blobServiceClient;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    public AzureStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public String uploadFile(PayloadRequest payloadRequest) throws IOException {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        String blobName = payloadRequest.getRequestId() + "_" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        // Convert content to InputStream
        byte[] payload = payloadRequest.getPayload().getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream dataStream = new ByteArrayInputStream(payload);

        // Upload the content
        blobClient.getBlockBlobClient().upload(dataStream, payload.length, true);

        return blobClient.getBlobUrl();
    }
}
