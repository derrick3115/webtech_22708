package com.derrick.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Check if the file is empty
        if (file.isEmpty()) {
            // Handle empty file error
            return "redirect:/uploadStatus?error=File is empty";
        }

        // Normalize the file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Save the file on the server
        try {
            // You can change the path where the file will be stored
            // Make sure you have write permissions on the directory
            Files.copy(file.getInputStream(), Paths.get("images", fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Handle file save error
            return "redirect:/uploadStatus?error=Error occurred while saving file";
        }

        // File upload success
        return "redirect:/uploadStatus?success";
    }

}
