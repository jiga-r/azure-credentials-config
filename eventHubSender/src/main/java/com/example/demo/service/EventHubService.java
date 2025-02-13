package com.example.demo.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHubService {

    private final EventHubProducerClient eventHubProducerClient;

    public EventHubService(EventHubProducerClient eventHubProducerClient)  {
        this.eventHubProducerClient = eventHubProducerClient;
    }

    public void sendMessage(String message) {
        EventData eventData = new EventData(message);
        eventHubProducerClient.send(java.util.Collections.singletonList(eventData));
    }
}