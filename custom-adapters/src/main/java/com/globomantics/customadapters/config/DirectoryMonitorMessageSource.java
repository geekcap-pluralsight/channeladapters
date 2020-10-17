package com.globomantics.customadapters.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.messaging.support.MessageBuilder;

public class DirectoryMonitorMessageSource extends AbstractMessageSource<Object> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String directory;
    private Class<?> entityClass;

    public DirectoryMonitorMessageSource() {
    }

    public DirectoryMonitorMessageSource(String directory, Class<?> entityClass) {
        this.directory = directory;
        this.entityClass = entityClass;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    protected Object doReceive() {
        List<Object> results = new ArrayList<>();

        File dir = new File(this.directory);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                try {
                    results.add(objectMapper.readValue(file, entityClass));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return MessageBuilder.withPayload(results).build();
    }

    @Override
    public String getComponentType() {
        return "directory-monitor:inbound-channel-adapter";
    }
}
