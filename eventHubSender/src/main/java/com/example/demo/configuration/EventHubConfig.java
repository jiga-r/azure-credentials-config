package com.example.demo.configuration;

import com.azure.identity.DefaultAzureCredential;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHubConfig {

    @Bean
    public EventHubProducerClient eventHubProducerClient(DefaultAzureCredential credential,
                                                         @Value("${spring.cloud.azure.eventhubs.namespace}") String eventhubNamespace,
                                                         @Value("${spring.cloud.azure.eventhubs.event-hub-name}") String eventhubName) {

        return new EventHubClientBuilder()
                .credential( eventhubNamespace, eventhubName, credential)
                .buildProducerClient();
    }
}