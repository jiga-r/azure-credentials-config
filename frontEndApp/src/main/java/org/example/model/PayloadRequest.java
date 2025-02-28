package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayloadRequest {
    private String requestId;
    private String payload;
}
