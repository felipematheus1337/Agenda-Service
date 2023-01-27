package com.agenda.api.mapper;

import com.agenda.api.request.AgendaRequest;
import com.agenda.api.response.AgendaResponse;
import com.agenda.api.response.PacienteResponse;
import com.agenda.domain.entity.Agenda;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaMapper {

    private final ModelMapper modelMapper;

    public Agenda toAgenda(AgendaRequest agendaRequest) {
        return modelMapper.map(agendaRequest, Agenda.class);
    }

    public AgendaResponse toAgendaResponse(Agenda agenda) {
        return modelMapper.map(agenda, AgendaResponse.class);
    }

    public List<AgendaResponse> toListOfAgendaResponse(List<Agenda> agendaList) {
        return agendaList
                .stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());

    }
}
