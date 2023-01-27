package com.agenda.api.response;

import com.agenda.domain.entity.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponse {


    private Long id;
    private String descricao;
    private LocalDateTime horario;
    private PacienteResponse paciente;
}
