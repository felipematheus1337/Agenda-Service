package com.agenda.api.response;


import com.agenda.domain.entity.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacientePDFResponse {

    private String nome;
    private String sobrenome;
    private String email;
    private Long count;






}
