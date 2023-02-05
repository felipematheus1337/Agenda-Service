package com.agenda.api.controller;

import com.agenda.api.response.PacientePDFResponse;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.impl.PdfPacientesMaiorService;
import com.agenda.domain.service.impl.ListarPacientesMaiorAgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class PacientePDFController {

    private final ListarPacientesMaiorAgendamentoService listarPacientes;
    private final PdfPacientesMaiorService pdfPacientesMaiorService;


    @GetMapping("/pacientes/mensal/{mes}/{qtdPacientes}")
    public ResponseEntity<byte[]> getPacientesMaiorAgendamentoMensal(
            @PathVariable("mes") Integer mes,
            @PathVariable("qtdPacientes") Integer qtdPacientes) throws IOException {
             String filename = "pacientes-mes-"+0+mes+".pdf";
             List<PacientePDFResponse> pacientes = listarPacientes.listarPacientesComMaiorAgendamentos(mes,qtdPacientes);
             byte[] arquivo = pdfPacientesMaiorService.generatePDF(pacientes);
             HttpHeaders headers = new HttpHeaders();
             headers.setContentType(MediaType.APPLICATION_PDF);
             headers.setContentDispositionFormData("attachment", filename);
             headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(arquivo,headers, HttpStatus.OK);

    }
}
