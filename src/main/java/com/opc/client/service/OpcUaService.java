package com.opc.client.service;

import org.apache.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public  class OpcUaService {
    private static final Logger logger = Logger.getLogger(OpcUaService.class);
    @Autowired
    private OpcUaClient opcUaClient;

    public void readOpcUaVariable() throws Exception {
        NodeId node = new NodeId(4,8);
        TimestampsToReturn timestampsToReturn = TimestampsToReturn.Neither;
        try {
            CompletableFuture<DataValue> readFuture = opcUaClient.readValue(0, timestampsToReturn, node);
            DataValue dataValue = readFuture.get();
            logger.info("Valor del nodo: " + dataValue.getValue().getValue());
        } catch (Exception e) {
            logger.error("Failed to read");
            throw new RuntimeException("OPC-UA server connection failed!", e);
        }
    }
    public Double readOpcUaVariableTest() throws Exception {
        NodeId node = new NodeId(2,2);
        TimestampsToReturn timestampsToReturn = TimestampsToReturn.Neither;
        try {
            CompletableFuture<DataValue> readFuture = opcUaClient.readValue(0, timestampsToReturn, node);
            DataValue dataValue = readFuture.get();
            logger.info("Valor del nodo: " + dataValue.getValue().getValue());
            return Double.valueOf(String.valueOf(dataValue.getValue().getValue()));
        } catch (Exception e) {
            logger.error("Failed to read");
            throw new RuntimeException("OPC-UA server connection failed!", e);
        }
    }
    public Boolean readOpcUaVariableTestBoolean() throws Exception {
        NodeId node = new NodeId(2,3);
        TimestampsToReturn timestampsToReturn = TimestampsToReturn.Neither;
        try {
            CompletableFuture<DataValue> readFuture = opcUaClient.readValue(0, timestampsToReturn, node);
            DataValue dataValue = readFuture.get();
            logger.info("Valor del nodo: " + dataValue.getValue().getValue());
            return Boolean.valueOf(String.valueOf(dataValue.getValue().getValue()));
        } catch (Exception e) {
            logger.error("Failed to read");
            throw new RuntimeException("OPC-UA server connection failed!", e);
        }
    }


}
