package com.example.demo.configuration;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureCredentialConfig {

    @Bean
    public DefaultAzureCredential defaultAzureCredential(@Value("${spring.cloud.azure.credential.client-id}") String clientId) {
        return new DefaultAzureCredentialBuilder()
                .managedIdentityClientId(clientId)
                .build();
    }
}
