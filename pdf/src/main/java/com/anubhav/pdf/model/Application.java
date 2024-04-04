package com.anubhav.pdf.model;

import lombok.Data;

import java.util.List;

@Data
public class Application {
    private String seller;
    private String sellerGSTIN;
    private String sellerAddress;
    private String buyerAddress;
    private String buyerGSTIN;
    private String buyer;
    private List<Item> items;
}
