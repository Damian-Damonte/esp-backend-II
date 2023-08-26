package com.example.apipersona.controller;

import com.example.apipersona.client.PersonaClient;
import com.example.apipersona.model.Documento;
import com.example.apipersona.model.Persona;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class PersonaController {
    private final PersonaClient personaClient;

    public PersonaController(PersonaClient personaClient) {
        this.personaClient = personaClient;
    }

    @GetMapping
    public List<Documento> getAllDocumentos() {
        ResponseEntity<List<Persona>> response = personaClient.getAllPersonas();
        System.out.println("Persona instance port: " + response.getHeaders().get("port"));

        return response.getBody().stream().map(
                persona -> Documento.builder()
                        .id(persona.getId())
                        .numero(Math.round(Math.random() * 1000000))
                        .persona(persona)
                        .build()).toList();
    }

    @GetMapping("/{id}")
    public Documento getDocumentoById(@PathVariable Long id) {
        ResponseEntity<Persona> response = personaClient.getPersonaById(id);
        System.out.println("Persona instance port: " + response.getHeaders().get("port"));


        Persona persona = response.getBody();
        return Documento.builder()
                .id(persona.getId())
                .numero(persona.getNombre().equals("Desconocido") ? 99999 : Math.round(Math.random() * 1000000))
                .persona(persona)
                .build();
    }
}
