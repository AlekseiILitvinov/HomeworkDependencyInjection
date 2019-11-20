package ru.itpark.implementation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auto {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
}
