package com.cdb.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CodeParser {

    public String parseCode(String code) {
        // Create an instance of JavaParser
        JavaParser javaParser = new JavaParser();

        // Convert the code string into InputStream using ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));

        // Parse the provided Java code using the instance of JavaParser
        CompilationUnit compilationUnit = javaParser.parse(inputStream).getResult().orElseThrow(() -> new RuntimeException("Failed to parse code"));

        // Extract class names and method names
        StringBuilder documentation = new StringBuilder();

        // Extract classes (filter out non-ClassOrInterfaceDeclarations)
        List<com.github.javaparser.ast.body.TypeDeclaration<?>> types = compilationUnit.getTypes();
        for (com.github.javaparser.ast.body.TypeDeclaration<?> type : types) {
            if (type instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration) type;
                documentation.append("Class: ").append(clazz.getName()).append("\n");

                // Extract methods from the class
                List<MethodDeclaration> methods = clazz.getMethods();
                for (MethodDeclaration method : methods) {
                    documentation.append("  Method: ").append(method.getName()).append("\n");

                    // Optionally, add Javadoc comments
                    if (method.getComment().isPresent()) {
                        JavadocComment javadocComment = (JavadocComment) method.getComment().get();
                        documentation.append("    Javadoc: ").append(javadocComment.getContent()).append("\n");
                    }
                }
            }
        }

        return documentation.toString();
    }
}
