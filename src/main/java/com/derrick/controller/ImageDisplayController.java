package com.derrick.controller;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Controller
public class ImageDisplayController {
    private static final String IMAGE_DIRECTORY = "images"; // Specify the directory where images are stored


    @GetMapping("/display/image/{imageName}")
    public ResponseEntity<Resource> displayImage(@PathVariable String imageName) throws IOException {
        // Load image as Resource
        Path imagePath = Paths.get(IMAGE_DIRECTORY).resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        // Check if image exists and is readable
        if (!resource.exists() || !resource.isReadable()) {
            // Handle image not found error
            return ResponseEntity.notFound().build();
        }

        // Set content type header
        String contentType = MediaType.IMAGE_JPEG_VALUE; // Adjust the content type as per your image type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // Return the ResponseEntity with the image content
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
