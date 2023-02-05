package com.agenda.api.controller;

import com.agenda.api.mapper.AgendaMapper;
import com.agenda.api.request.AgendaRequest;
import com.agenda.api.response.AgendaResponse;
import com.agenda.domain.service.impl.AgendaServiceImpl;
import com.agenda.domain.service.impl.AgendaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/agenda")
public class AgendaController {

    private final AgendaServiceImpl service;

    private final AgendaMapper mapper;


    @GetMapping
    public ResponseEntity<List<AgendaResponse>> buscarTodos() {
         var agendasList = service.listarTodos()
                .stream()
                .map(mapper::toAgendaResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(agendasList);


    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id) {

       var agenda = service.buscarPorId(id);

       return agenda
               .map(mapper::toAgendaResponse)
               .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
               .orElseGet(() -> ResponseEntity.notFound().build());


    }

        @PostMapping
        public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest agendaRequest) {
            Optional<AgendaResponse> agendaResponse = Stream.of(agendaRequest)
                    .map(mapper::toAgenda)
                    .map(service::salvar)
                    .map(mapper::toAgendaResponse)
                    .findFirst();
            return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse.get());

        }
}
