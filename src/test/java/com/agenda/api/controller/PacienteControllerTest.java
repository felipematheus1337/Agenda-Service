package com.agenda.api.controller;


import com.agenda.api.request.PacienteRequest;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import com.agenda.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PacienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

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
    @DisplayName("Salva paciente com sucesso")
    void salvarPaciente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("joao@mail.com")
                .nome("Felipe")
                .sobrenome("silva")
                .cpf("234")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Salva paciente com cpf existente")
    void salvarPacienteComCpfExistente() throws Exception {
        PacienteRequest paciente = PacienteRequest.builder()
                .email("joao@mail.com")
                .nome("Felipe")
                .sobrenome("Matheus")
                .cpf("521521")
                .build();

        String pacienteRequest = mapper.writeValueAsString(paciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pacienteRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andDo(MockMvcResultHandlers.print());
    }
}
