package com.agenda.api.controller;


import com.agenda.api.mapper.PacienteMapper;
import com.agenda.api.request.PacienteRequest;
import com.agenda.api.response.PacienteResponse;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper pacienteMapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar(@Valid @RequestBody PacienteRequest pacienteRequest) {
       Optional<PacienteResponse> optPaciente = Stream.of(pacienteRequest)
               .map(req -> pacienteMapper.toPaciente(pacienteRequest))
               .map(service::salvar)
               .map(pacienteMapper::toPacienteResponse)
               .findFirst();
       return ResponseEntity.status(HttpStatus.CREATED).body(optPaciente.get());
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> procurarTodos() {
        var pacienteResponse = service.listarTodos()
                .stream()
                .map(pacienteMapper::toPacienteResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {
     var paciente = service.buscarPorId(id);

        return paciente
                .map(pacienteMapper::toPacienteResponse)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> alterar(@PathVariable Long id,@Valid @RequestBody PacienteRequest pacienteRequest) {
        var pacienteResponse = Stream.of(pacienteRequest)
                .map(pacienteMapper::toPaciente)
                .map(s -> service.alterar(id,s))
                .map(pacienteMapper::toPacienteResponse)
                .findFirst();
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
     service.deletar(id);
     return ResponseEntity.noContent().build();
    }


}
