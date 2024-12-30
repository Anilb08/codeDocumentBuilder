package com.cdb.service;

import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final CodeParser codeParser;

    // Inject CodeParser into DocumentService
    public DocumentService(CodeParser codeParser) {
        this.codeParser = codeParser;
    }

    public String generateDocumentation(String code) {
        // Use CodeParser to parse the code and generate documentation
        return codeParser.parseCode(code);
    }
}
