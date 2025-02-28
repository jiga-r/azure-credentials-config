package test.subscriber.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlobData {

    private String api;
    private String clientRequestId;
    private String requestId;
    private String eTag;
    private String contentType;
    private long contentLength;
    private String blobType;
    private String accessTier;
    private String url;
    private String sequencer;
}