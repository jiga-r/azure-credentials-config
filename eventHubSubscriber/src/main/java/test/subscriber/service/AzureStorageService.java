package test.subscriber.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.subscriber.model.BlobRequest;
import test.subscriber.model.PayloadRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AzureStorageService {

    private final BlobServiceClient blobServiceClient;

    private final Pattern BLOB_URL_PATTERN = Pattern.compile(
            "https://.*\\.blob\\.core\\.windows\\.net/(.*)/(.*)"
    );

    public AzureStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public PayloadRequest downloadBlobContent(BlobRequest blobRequest) {
        // Updated regex pattern to support both formats

        String url = blobRequest.getData().getUrl();

        Matcher matcher = BLOB_URL_PATTERN.matcher(url);

        if (!matcher.matches()) {
            log.error("Invalid Azure Blob URL: {}", url);
        }

        String containerName = matcher.group(1);
        String blobName = matcher.group(2);

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobName);

        PayloadRequest payloadRequest = blobClient.downloadContent().toObject(PayloadRequest.class);

        log.info("Payload Received: {}", payloadRequest);
        return payloadRequest;
    }
}