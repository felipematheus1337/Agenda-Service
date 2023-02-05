package com.agenda.domain.service.impl;


import com.agenda.api.response.PacientePDFResponse;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListarPacientesMaiorAgendamentoService {

    private final AgendaRepository agendaRepository;

    public List<PacientePDFResponse> listarPacientesComMaiorAgendamentos(int mes, int qtdPacientes) {
        List<PacientePDFResponse> resultado = agendaRepository.findTopPacientesAgendamentosByMonth(mes);
        System.out.println(resultado);
        log.info(resultado.toString());
        return resultado.stream()
                .limit(qtdPacientes)
                .collect(Collectors.toList());


    }




}
