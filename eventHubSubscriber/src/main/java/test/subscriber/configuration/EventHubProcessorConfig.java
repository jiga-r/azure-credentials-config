package test.subscriber.configuration;

import com.azure.spring.integration.eventhubs.inbound.EventHubsInboundChannelAdapter;
import com.azure.spring.messaging.eventhubs.core.EventHubsProcessorFactory;
import com.azure.spring.messaging.eventhubs.core.checkpoint.CheckpointConfig;
import com.azure.spring.messaging.eventhubs.core.checkpoint.CheckpointMode;
import com.azure.spring.messaging.eventhubs.core.listener.EventHubsMessageListenerContainer;
import com.azure.spring.messaging.eventhubs.core.properties.EventHubsContainerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import static test.subscriber.Constants.INPUT_CHANNEL;

@Configuration
@Slf4j
public class EventHubProcessorConfig {

    private final String eventhubName;
    private final String consumerGroup;

    public EventHubProcessorConfig(@Value("${spring.cloud.azure.eventhubs.event-hub-name}") String eventhubName,
                                   @Value("${spring.cloud.azure.eventhubs.processor.consumer-group}") String consumerGroup) {
        this.eventhubName = eventhubName;
        this.consumerGroup = consumerGroup;
    }

    @Bean
    public EventHubsMessageListenerContainer messageListenerContainer(EventHubsProcessorFactory processorFactory) {
        EventHubsContainerProperties containerProperties = new EventHubsContainerProperties();
        containerProperties.setEventHubName(eventhubName);
        containerProperties.setConsumerGroup(consumerGroup);
        containerProperties.setCheckpointConfig(new CheckpointConfig(CheckpointMode.MANUAL));

        return new EventHubsMessageListenerContainer(processorFactory, containerProperties);
    }

    @Bean
    public EventHubsInboundChannelAdapter messageChannelAdapter(@Qualifier(INPUT_CHANNEL) MessageChannel inputChannel,
                                                                EventHubsMessageListenerContainer listenerContainer) {
        EventHubsInboundChannelAdapter adapter = new EventHubsInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }
}
