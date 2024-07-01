package com.opc.client;

import com.opc.client.service.OpcUaService;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpcClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OpcClientApplication.class, args);
	}


}
