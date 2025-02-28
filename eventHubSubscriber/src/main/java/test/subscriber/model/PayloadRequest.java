package test.subscriber.model;

import lombok.Data;

@Data
public class PayloadRequest {
    private String requestId;
    private String item;
}