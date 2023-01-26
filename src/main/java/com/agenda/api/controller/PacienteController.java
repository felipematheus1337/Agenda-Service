package com.agenda.api.controller;


import com.agenda.api.mapper.PacienteMapper;
import com.agenda.api.request.PacienteRequest;
import com.agenda.api.response.PacienteResponse;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.PacienteService;
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
    private final PacienteMapper pacienteMapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar(@RequestBody PacienteRequest pacienteRequest) {
       var pacienteSalvo = service.salvar(pacienteMapper.toPaciente(pacienteRequest));
       return ResponseEntity.status(HttpStatus.CREATED).body(pacienteMapper.toPacienteResponse(pacienteSalvo));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> procurarTodos() {
        return ResponseEntity.ok(pacienteMapper.toListOfPacienteResponse(service.listarTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {
     var paciente = service.buscarPorId(id);

     if(paciente.isEmpty()) {
         return ResponseEntity.notFound().build();
     }

     return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toPacienteResponse(paciente.get()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> alterar(@PathVariable Long id, @RequestBody PacienteRequest pacienteRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteMapper.toPacienteResponse(service.salvar(pacienteMapper.toPaciente(pacienteRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
     service.deletar(id);
     return ResponseEntity.noContent().build();
    }


}
