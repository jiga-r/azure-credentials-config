package test.subscriber.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlobRequest {
    private String topic;
    private String subject;
    private String eventType;
    private String id;
    private String dataVersion;
    private String metadataVersion;
    private String eventTime;
    private BlobData data;
}
