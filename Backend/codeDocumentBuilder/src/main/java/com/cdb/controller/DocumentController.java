package com.cdb.controller;

import com.cdb.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/generate-docs")
    public String generateDocumentation(@RequestParam String code) {
        // Call the service to parse the code and generate documentation
        return documentService.generateDocumentation(code);
    }
}
