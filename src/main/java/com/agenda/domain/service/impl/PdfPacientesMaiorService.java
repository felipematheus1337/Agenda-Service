package com.agenda.domain.service.impl;

import com.agenda.api.response.PacientePDFResponse;
import com.agenda.domain.entity.Paciente;
import com.agenda.domain.service.file.PacienteMaiorTableService;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfPacientesMaiorService {

    private final PacienteMaiorTableService pacienteMaiorTableService;
    public byte[] generatePDF(List<PacientePDFResponse> pacientes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        var document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,out);
        document.open();

        PdfPTable table = pacienteMaiorTableService.createPacienteTable(pacientes);
        document.add(table);

        document.close();
        writer.close();

        return out.toByteArray();
    }
}
