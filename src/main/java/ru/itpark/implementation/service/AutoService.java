package ru.itpark.implementation.service;

import ru.itpark.framework.annotation.Component;
import ru.itpark.framework.util.JdbcTemplate;
import ru.itpark.implementation.model.Auto;
import ru.itpark.implementation.repository.AutoRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class AutoService {
    private final AutoRepository repository;
    private final String uploadPath;

    public AutoService(AutoRepository repository){
        this.repository = repository;


        uploadPath = System.getenv("UPLOAD_PATH");
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Auto> searchByName(String name){
        return repository.getAutoByName(name);
    }

    public List<Auto> getAll(){
        return repository.getAll();
    }

    public void create(String name, String description, String imageUrl){
        repository.saveAuto(new Auto(0, name, description, imageUrl));
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

    public Auto searchById(Integer id) {
        return repository.getAutoById(id);
    }
}
