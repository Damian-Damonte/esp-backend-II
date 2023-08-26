package com.example.apipersona.controller;

import com.example.apipersona.model.Persona;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final List<Persona> personas = List.of(
            Persona.builder().id(1L).nombre("Juan").edad(34).build(),
                Persona.builder().id(2L).nombre("Carlos").edad(57).build(),
                Persona.builder().id(3L).nombre("Martin").edad(26).build()
        );

    @GetMapping
    public List<Persona> getAllPersonas(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("port", String.valueOf(request.getLocalPort()));
        return personas;
    }

    @GetMapping("/{id}")
    public Persona getPersonaById(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("port", String.valueOf(request.getLocalPort()));

        return personas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(Persona.builder().id(99999L).nombre("Desconocido").edad(0).build());
    }
}
