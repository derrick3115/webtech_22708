package com.derrick.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UploadStatusController {
    @GetMapping("/uploadStatus")
    public String uploadStatus(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            // Handle error case
            return "uploadStatusPageWithError";
        }

        // Handle success case
        return "uploadStatusPage";
    }
}
