package com.anubhav.pdf.service;

import com.anubhav.pdf.model.Application;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface PdfGeneration {
    String parseThymeLeafTemplate(Application application);
}
