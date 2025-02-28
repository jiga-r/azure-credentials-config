package test.subscriber.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.subscriber.model.BlobRequest;
import test.subscriber.model.PayloadRequest;
import test.subscriber.service.AzureStorageService;

import java.io.IOException;
import java.util.List;

@RestController
public class IngestionController {
    private final AzureStorageService azureStorageService;

    public IngestionController(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @PostMapping("/downloadFromBucket")
    public ResponseEntity<PayloadRequest> uploadContent(@RequestBody List<BlobRequest> request) throws IOException {
        PayloadRequest payloadRequest = azureStorageService.downloadBlobContent(request.get(0));
        return ResponseEntity.ok()
                .body(payloadRequest);
    }
}
