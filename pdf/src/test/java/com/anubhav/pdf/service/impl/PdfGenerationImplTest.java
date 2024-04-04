package com.anubhav.pdf.service.impl;

import com.anubhav.pdf.model.Application;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@ContextConfiguration(classes = {PdfGenerationImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PdfGenerationImplTest {
    @Autowired
    private PdfGenerationImpl pdfGenerationImpl;
    @Test
    public void testParseThymeLeafTemplate_EMPTY_CHECK() {

        Application application = new Application();
        Assertions.assertEquals("Seller or Buyer cannot be null", pdfGenerationImpl.parseThymeLeafTemplate(application));
    }

    @Test
    public void testParseThymeLeafTemplate_NULLCHECK() {
        Assertions.assertEquals("Application cannot be null", pdfGenerationImpl.parseThymeLeafTemplate(null));
    }

    @Test
    public void testParseThymeLeafTemplate_NO_ITEMS() {
        Application application = new Application();
        application.setSeller("seller");
        application.setBuyer("buyer");
        application.setSellerGSTIN("07AAAPL1234C1Z5");
        application.setBuyerGSTIN("07AAAPL1234C1Z5");
        application.setSellerAddress("sellerAddress");
        application.setBuyerAddress("buyerAddress");
        application.setItems(new ArrayList<>());
        Assertions.assertEquals("Items cannot be null or empty", pdfGenerationImpl.parseThymeLeafTemplate(application));
    }

    @Test
    public void testParseThymeLeafTemplate_INVALID_GSTIN() {
        Application application = new Application();
        application.setSeller("seller");
        application.setBuyer("buyer");
        application.setSellerGSTIN("07AAAPL1234C1Z");
        application.setBuyerGSTIN("07AAAPL1234C1Z");
        application.setSellerAddress("sellerAddress");
        application.setBuyerAddress("buyerAddress");
        application.setItems(new ArrayList<>());
        Assertions.assertEquals("Buyer GSTIN or Seller GSTIN is invalid", pdfGenerationImpl.parseThymeLeafTemplate(application));
    }
}

