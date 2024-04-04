package com.anubhav.pdf.service.impl;

import com.anubhav.pdf.model.Application;
import com.anubhav.pdf.service.PdfGeneration;
import com.anubhav.pdf.util.GSTINValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;


@Service
@Slf4j
public class PdfGenerationImpl implements PdfGeneration {
    @Override
    public String parseThymeLeafTemplate(Application application) {
        if(application == null) {
            log.error("Application cannot be null");
            return "Application cannot be null";
        }
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        if(application.getSeller() == null || application.getBuyer() == null || application.getItems() == null) {
            log.error("Seller, Buyer or Items cannot be null");
            return "Seller or Buyer cannot be null";
        }
        context.setVariable("seller", application.getSeller());
        context.setVariable("buyer", application.getBuyer());
        context.setVariable("buyerAddress", application.getBuyerAddress());

        if(application.getBuyerGSTIN() == null || application.getSellerGSTIN() == null) {
            log.error("Buyer GSTIN or Seller GSTIN cannot be null");
            return "Buyer GSTIN or Seller GSTIN cannot be null";
        }
        context.setVariable("buyerGstin", application.getBuyerGSTIN());
        context.setVariable("sellerAddress", application.getSellerAddress());
        if(GSTINValidator.isGSTINValid(application.getBuyerGSTIN()) == false || GSTINValidator.isGSTINValid(application.getSellerGSTIN()) == false) {
            log.error("Buyer GSTIN or Seller GSTIN is invalid");
            return "Buyer GSTIN or Seller GSTIN is invalid";
        }
        context.setVariable("sellerGstin", application.getSellerGSTIN());
        if(application.getItems() == null || application.getItems().isEmpty()) {
            log.error("Items cannot be null or empty");
            return "Items cannot be null or empty";
        }
        context.setVariable("items", application.getItems());
        String html = templateEngine.process("thymeleaf_template", context);
        String path = null;
        try {
            path = generatePdfFromHtml(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder response = new StringBuilder();
        return response.append("Pdf generated at: ").append(path).toString();
    }

    private String generatePdfFromHtml(String html) throws IOException {
        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputFolder;
    }

}
