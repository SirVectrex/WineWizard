package com.winewizard.winewizard.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class HtmlFileReaderService {

    private final ResourceLoader resourceLoader;

    public HtmlFileReaderService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readHtmlFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);

        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}