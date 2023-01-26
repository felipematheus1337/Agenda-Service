package com.agenda.api.mapper;

import com.agenda.api.request.PacienteRequest;
import com.agenda.api.response.PacienteResponse;
import com.agenda.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private ModelMapper modelMapper;

    public Paciente toPaciente(PacienteRequest pacienteRequest) {
        return modelMapper.map(pacienteRequest, Paciente.class);
    }

    public PacienteResponse toPacienteResponse(Paciente paciente) {
        return modelMapper.map(paciente, PacienteResponse.class);
    }

    public List<PacienteResponse> toListOfPacienteResponse(List<Paciente> pacienteList) {
        return pacienteList
                .stream()
                .map(this::toPacienteResponse)
                .collect(Collectors.toList());

    }
}
