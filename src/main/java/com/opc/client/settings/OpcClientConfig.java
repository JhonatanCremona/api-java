package com.opc.client.settings;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpcClientConfig {
    @Bean
    public static OpcUaClient opcUaClient() throws Exception {
        String endpointUrl = "opc.tcp://192.168.1.12:4840";
        OpcUaClient opcUaClient = null;

        try {
            opcUaClient = OpcUaClient.create(endpointUrl);
            opcUaClient.connect().get();
            return opcUaClient;
        }catch (Exception e){
            throw new RuntimeException("OPC-UA server connection failed!", e);
        }
    }
}
