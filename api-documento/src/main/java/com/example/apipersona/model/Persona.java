package com.example.apipersona.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Persona {
    private Long id;
    private String nombre;
    private Integer edad;
}
