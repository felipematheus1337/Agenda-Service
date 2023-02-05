package com.agenda.domain.service.impl;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAgendasSemanaisService {

    private final AgendaRepository agendaRepository;



    public List<Agenda> listarAgendaSemanal(LocalDateTime inicioSemana) {
        LocalDateTime fimDeSemana = inicioSemana.with(DayOfWeek.SUNDAY).with(LocalTime.MAX);
        inicioSemana = inicioSemana.with(LocalTime.MIN);
        return agendaRepository.findByHorarioBetween(inicioSemana, fimDeSemana);

    }
}
