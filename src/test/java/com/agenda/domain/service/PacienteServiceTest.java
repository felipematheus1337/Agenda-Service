package com.agenda.domain.service;

import com.agenda.domain.entity.Agenda;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @InjectMocks
    PacienteService service;

    @Mock
    PacienteRepository repository;

    @Captor
    ArgumentCaptor<Paciente> pacienteCaptor;


    @Test
    @DisplayName("Deve salvar paciente com sucesso!")
    void deveSalvarPacienteComSucesso() {
        Paciente paciente = new Paciente(1L,"teste",
                "sobrenome teste",
                "teste@mail.com","622.192.220-80");
        boolean cpfExistsTest = false;

        Mockito.when(repository.findByCpf("622.192.220-80")).thenReturn(Optional.of(paciente));

        service.salvar(paciente);

        Mockito.verify(repository).save(pacienteCaptor.capture());
        Paciente savedPaciente = pacienteCaptor.getValue();

        Assertions.assertEquals(savedPaciente.getClass(),Paciente.class);
        Assertions.assertEquals(savedPaciente.getId(),paciente.getId());

    }

    @Test

    @Test
    @DisplayName("Deletar com sucesso paciente!")
    void deletarComSucessoPaciente() {
        Long deleteId = 1L;
        service.deletar(deleteId);
        verify(repository).deleteById(deleteId);
    }

    @Test
    @DisplayName("Listar todos os pacientes com sucesso!")
    void listarTodosComSucesso() {
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
    void buscarPorIdComSucesso() {
        Long searchId = 1L;
        Paciente pacienteTeste = new Paciente(searchId,
                "teste","sobreteste",
                "teste@mail.com","785.307.580-48");
        Mockito.when(repository.findById(searchId)).thenReturn(Optional.of(pacienteTeste));


       var optPaciente =  service.buscarPorId(searchId);
       verify(repository).findById(searchId);
       assertEquals(optPaciente.get().getId(),searchId);
       assertEquals(optPaciente.get().getClass(),Paciente.class);


    }



}