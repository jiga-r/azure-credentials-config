package test.subscriber.listener;

import com.azure.spring.messaging.AzureHeaders;
import com.azure.spring.messaging.checkpoint.Checkpointer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import test.subscriber.model.BlobRequest;
import test.subscriber.model.PayloadRequest;
import test.subscriber.service.AzureStorageService;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import static test.subscriber.Constants.INPUT_CHANNEL;

@Service
@Slf4j
public class EventHubListener {

    private final AzureStorageService azureStorageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EventHubListener(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @ServiceActivator(inputChannel = INPUT_CHANNEL)
    public void messageReceiver(byte[] payload, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) throws IOException {

        try {
            List<BlobRequest> blobRequests = objectMapper.readValue(payload, new TypeReference<List<BlobRequest>>() {
            });
            log.info("New message received: {}", blobRequests);
            PayloadRequest payloadRequest = azureStorageService.downloadBlobContent(blobRequests.get(0));

            log.info("Downloaded successfully: {}", payloadRequest);

            checkpointer.success()
                    .doOnSuccess(s -> log.info("Message {} successfully checkpointed", blobRequests))
                    .doOnError(e -> log.error("Error found", e))
                    .block();
        } catch (Exception ex) {
            log.error("Exception occurred: ", ex);
        }
    }
}
