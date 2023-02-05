package com.agenda.domain.service;


import com.agenda.domain.entity.Paciente;
import com.agenda.domain.repository.PacienteRepository;
import com.agenda.domain.service.impl.PacienteServiceImpl;
import com.agenda.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @InjectMocks
    PacienteServiceImpl service;

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

        when(repository.findByCpf("622.192.220-80")).thenReturn(Optional.of(paciente));

        service.salvar(paciente);

        verify(repository).save(pacienteCaptor.capture());
        Paciente savedPaciente = pacienteCaptor.getValue();

        Assertions.assertThat(savedPaciente.getClass()).isEqualTo(Paciente.class);
        Assertions.assertThat(savedPaciente.getId()).isEqualTo(paciente.getId());

    }

    @Test
    @DisplayName("Deve gerar exceção ao salvar um paciente com cpf já cadastrado")
    void deveLancarExcecaoAoCadastrarPacienteComCpfJaCadastrado() {
        Paciente paciente = new Paciente();
        paciente.setCpf("734.426.970-85");
        paciente.setId(1L);

        Paciente pacienteExistente = new Paciente();
        pacienteExistente.setCpf("734.426.970-85");
        pacienteExistente.setId(2L);
        var optionalPaciente = Optional.of(pacienteExistente);

        when(repository.findByCpf(paciente.getCpf())).thenReturn(optionalPaciente);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.salvar(paciente);
        });

        verify(repository, never()).save(any(Paciente.class));
        assertEquals("Cpf já cadastrado!",exception.getMessage());



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
    void listarTodosComSucesso() {
        List<Paciente> pacienteList = Arrays.asList(
                new Paciente(1L,"teste1",
                        "sobrenometeste1","teste1@mail.com",
                        "771.595.610-40"),
                new Paciente(2L,"teste2",
                        "sobrenometeste2","teste2@mail.com",
                        "089.694.830-71")
        );
        when(repository.findAll()).thenReturn(pacienteList);
        var listPacientes = service.listarTodos();
        assertEquals(listPacientes.size(),2);

    }

    @Test
    @DisplayName("Deve retornar um paciente com sucesso")
    void buscarPorIdComSucesso() {
        Long searchId = 1L;
        Paciente pacienteTeste = new Paciente(searchId,
                "teste","sobreteste",
                "teste@mail.com","785.307.580-48");
        when(repository.findById(searchId)).thenReturn(Optional.of(pacienteTeste));


       var optPaciente =  service.buscarPorId(searchId);
       verify(repository).findById(searchId);
       assertEquals(optPaciente.get().getId(),searchId);
       assertEquals(optPaciente.get().getClass(),Paciente.class);


    }

    @Test
    @DisplayName("Deve atualizar com sucesso um paciente!")
    void deveAtualizarComSucessoPaciente() {
        Long pacienteId = 1L;
        var paciente = new Paciente();
        paciente.setId(1L);
        paciente.setCpf("834.347.440-61");
        when(service.buscarPorId(pacienteId)).thenReturn(Optional.of(paciente));
        when(service.salvar(paciente)).thenReturn(paciente);


        var pacienteAtualizado = service.alterar(pacienteId ,paciente);


       assertEquals(pacienteAtualizado.getCpf(),paciente.getCpf());
        assertThat(pacienteAtualizado.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar paciente que não está cadastrado")
    void deveLancarExcecaoAoAtualizarPacienteNaoCadastrado() {
        Long searchId = 5L;
        Paciente paciente = new Paciente();
        paciente.setId(searchId);
        paciente.setCpf("11.111.111-11");
        Mockito.when(service.buscarPorId(searchId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.alterar(searchId,paciente);
        });

        assertEquals(exception.getMessage(),"Paciente não cadastrado!");
        verify(repository,never()).save(paciente);
        verify(repository).findById(5L);
    }






}