package com.anubhav.pdf.controller;

import com.anubhav.pdf.model.Application;
import com.anubhav.pdf.service.PdfGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    PdfGeneration pdfGeneration;
    @PostMapping("/generatePdf")
    public String generatePdf(@RequestBody Application application) {
        return pdfGeneration.parseThymeLeafTemplate(application);
    }
}
