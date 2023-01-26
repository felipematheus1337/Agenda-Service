package com.agenda.api.controller;


import com.agenda.domain.entity.Paciente;
import com.agenda.domain.entity.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    public ResponseEntity<Paciente> salvar(@RequestBody Paciente paciente) {
       var pacienteSalvo = service.salvar(paciente);
       return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> procurarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
