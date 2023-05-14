package com.derrick.controller;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileDownloadController {
    private static final String DOWNLOAD_DIRECTORY = "images"; // Specify the directory where files are stored

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        // Load file as Resource
        Path filePath = Paths.get(DOWNLOAD_DIRECTORY).resolve(fileName);
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

        // Check if file exists
        if (!resource.exists()) {
            // Handle file not found error
            return ResponseEntity.notFound().build();
        }

        // Set content type and attachment disposition headers
        String contentType = Files.probeContentType(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        // Return the ResponseEntity with the file content
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
