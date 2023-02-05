package com.agenda.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String cpf;

    @Override
    public String toString() {
        return "Nome: " + nome + "\n"
                + "Sobrenome: " + sobrenome + "\n"
                + "E-mail: " + email + "\n"
                + "CPF : " + cpf;

    }
}
