package com.agenda.domain.service;

import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @InjectMocks
    PacienteService service;

    @Mock
    PacienteRepository repository;


    @Test
    void salvar() {
    }

    @Test
    @DisplayName("Deletar com sucesso paciente!")
    void deletarComSucessoPaciente() {
        Long deleteId = 1L;
        service.deletar(deleteId);
        verify(repository).deleteById(deleteId);
    }

    @Test
    @DisplayName("Listar todos os pacientes com sucesso!")
    void listarTodos() {
        List<Paciente> pacienteList = Arrays.asList(
                new Paciente(1L,"teste1",
                        "sobrenometeste1","teste1@mail.com",
                        "771.595.610-40"),
                new Paciente(2L,"teste2",
                        "sobrenometeste2","teste2@mail.com",
                        "089.694.830-71")
        );
        Mockito.when(repository.findAll()).thenReturn(pacienteList);
        var listPacientes = service.listarTodos();
        Assertions.assertEquals(listPacientes.size(),2);

    }

    @Test
    void buscarPorId() {
    }
}