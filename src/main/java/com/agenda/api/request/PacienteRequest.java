package com.agenda.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteRequest {


    @NotBlank(message = "Nome do paciente é obrigatório.")
    private String nome;
    @NotBlank(message = "Sobrenome do paciente é obrigatório.")
    private String sobrenome;

    @Email
    private String email;
    @NotBlank(message = "CPF do paciente é obrigatório.")
    @CPF
    private String cpf;
}
