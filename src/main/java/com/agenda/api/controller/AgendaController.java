package com.agenda.api.controller;

import com.agenda.api.mapper.AgendaMapper;
import com.agenda.api.request.AgendaRequest;
import com.agenda.api.response.AgendaResponse;
import com.agenda.domain.entity.Agenda;
import com.agenda.domain.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaService service;

    private final AgendaMapper mapper;


    @GetMapping
    public ResponseEntity<List<AgendaResponse>> buscarTodos() {
        List<Agenda> agendas = service.listarTodos();
        var agendasList = mapper.toListOfAgendaResponse(agendas);
        return ResponseEntity.status(HttpStatus.OK).body(agendasList);


    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id) {
        Optional<Agenda> optAgenda = service.buscarPorId(id);

        if(optAgenda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
    }

        @PostMapping
        public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest agendaRequest) {
            Agenda agenda = mapper.toAgenda(agendaRequest);
            Agenda agendaSalva = service.salvar(agenda);
            AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSalva);

            return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);

        }
}
