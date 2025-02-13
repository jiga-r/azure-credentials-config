package test.subscriber;

import com.azure.spring.messaging.AzureHeaders;
import com.azure.spring.messaging.checkpoint.Checkpointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static test.subscriber.Constants.INPUT_CHANNEL;

@Service
@Slf4j
public class EventHubListener {

    @ServiceActivator(inputChannel = INPUT_CHANNEL)
    public void messageReceiver(byte[] payload, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) {
        String message = new String(payload);
        log.info("New message received: '{}'", message);
        checkpointer.success()
                .doOnSuccess(s -> log.info("Message '{}' successfully checkpointed", message))
                .doOnError(e -> log.error("Error found", e))
                .block();
    }
}
