package com.agenda.api.controller;


import com.agenda.domain.entity.Agenda;
import com.agenda.domain.service.file.PDFGeneratorService;
import com.agenda.domain.service.impl.GetAgendasSemanaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class AgendaPDFController {

    private final GetAgendasSemanaisService agendasSemanaisService;
    private final PDFGeneratorService pdfGeneratorService;

    @GetMapping("/agendas/semanal/{inicioSemana}")
    public ResponseEntity<byte[]> getAgendaSemanal(@PathVariable("inicioSemana")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime inicioSemana)
            throws IOException {

        List<Agenda> agendasSemanais = agendasSemanaisService.listarAgendaSemanal(inicioSemana);
        byte[] arquivo = pdfGeneratorService.generatePDF(agendasSemanais);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "agenda-semanal.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        var response = new ResponseEntity<>(arquivo, headers, HttpStatus.OK);
        return response;











    }
}
