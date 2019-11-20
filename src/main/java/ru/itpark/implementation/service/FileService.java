package ru.itpark.implementation.service;

import ru.itpark.framework.annotation.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//@Component
public class FileService {
    private final String uploadPath;

    public FileService() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public void readFile(String id, ServletOutputStream os) throws IOException {
        Path path = Paths.get(uploadPath).resolve(id);
        Files.copy(path, os);
    }
    public String writeFile(Part part) throws IOException {
        final String id = UUID.randomUUID().toString();
        part.write(Paths.get(uploadPath).resolve(id).toString());
        return id;
    }
}
