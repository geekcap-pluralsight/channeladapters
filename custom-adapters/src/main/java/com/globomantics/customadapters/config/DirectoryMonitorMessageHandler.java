package com.globomantics.customadapters.config;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

public class DirectoryMonitorMessageHandler extends AbstractMessageHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String directory;

    public DirectoryMonitorMessageHandler() {
    }

    public DirectoryMonitorMessageHandler(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        UUID uuid = UUID.randomUUID();
        Path path = Paths.get(directory, uuid + ".json");
        try {
            objectMapper.writeValue(path.toFile(), message.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
