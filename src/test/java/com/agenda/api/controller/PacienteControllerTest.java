package com.agenda.api.controller;


import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteRepository  repository;

    @BeforeEach
    void up() {
        Paciente paciente = new Paciente();
        paciente.setNome("Felipe");
        paciente.setSobrenome("Matheus");
        paciente.setCpf("521521");
        paciente.setEmail("felipe@mail.com");
        repository.save(paciente);
    }

    @AfterEach
    void down() {
        repository.deleteAll();
    }


    @Test
    @DisplayName("Listar todos os pacientes")
    void listaPacientes() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Salvar paciente com sucesso")
    void salvarPaciente() {

    }
}
