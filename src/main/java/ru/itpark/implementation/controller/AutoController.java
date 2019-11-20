package ru.itpark.implementation.controller;

import lombok.RequiredArgsConstructor;
import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.model.Auto;
import ru.itpark.implementation.service.AutoService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AutoController {
  private final AutoService service;

  public List<Auto> doSearch(String name) {
    return service.searchByName(name);
  }

  public List<Auto> getAll() {
    return service.getAll();
  }

  public void handlePost(HttpServletRequest request) throws IOException, ServletException {
    final String name = request.getParameter("name");
    final String description = request.getParameter("description");
    final Part part = request.getPart("file");

    String imageUrl = service.writeFile(part);
    service.create(name, description, imageUrl);
  }

  public void getFile(String id, ServletOutputStream outputStream) {
    try {
      service.readFile(id, outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Auto getById(Integer id) {
    return service.searchById(id);
  }
}
