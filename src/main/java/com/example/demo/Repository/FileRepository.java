package com.example.demo.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileRepository {
    private final Path filePath = Paths.get("Data", "persons.csv");

    private void ensureFileExist() throws IOException {
        if(Files.notExists(filePath)){
            Files.createFile(filePath);
        }
    }

    public List<String> readAllLines() throws IOException {
        ensureFileExist();
        return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    }
    public void appendNewLine(String line) throws IOException {
        Files.writeString(filePath, line+System.lineSeparator(),StandardCharsets.UTF_8,
                StandardOpenOption.APPEND);
    }

    public void appendAllLines(List<String> lines) throws IOException {
        Files.write(filePath, lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
