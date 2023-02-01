package com.agenda.domain.service;

import com.agenda.domain.repository.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void listarTodos() {
    }

    @Test
    void buscarPorId() {
    }
}