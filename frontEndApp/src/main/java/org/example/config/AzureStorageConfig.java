package org.example.config;

import com.azure.identity.DefaultAzureCredential;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

    @Bean
    public BlobServiceClient blobServiceClient(DefaultAzureCredential defaultAzureCredential,
                                               @Value("${spring.cloud.azure.storage.blob.endpoint}") String storageEndpoint) {

        // Build the BlobServiceClient
        return new BlobServiceClientBuilder()
                .endpoint(storageEndpoint)
                .credential(defaultAzureCredential)
                .buildClient();
    }
}