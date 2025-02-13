package com.example.demo;

import com.example.demo.service.BlobStorageService;
import com.example.demo.service.EventHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventhub")
public class EventHubController {

    @Autowired
    private EventHubService eventHubService;

    @Autowired
    private BlobStorageService blobStorageService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        eventHubService.sendMessage(message);
       // blobStorageService.uploadToStorage(message);
        return "Message sent to Event Hub: " + message;
    }
}