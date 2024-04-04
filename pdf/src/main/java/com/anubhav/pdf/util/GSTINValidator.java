package com.anubhav.pdf.util;

public class GSTINValidator {
    private static final String REGEX = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";

    private static char calculateCheckDigit(String gstinWithoutCheckDigit) {
        int weightedSum = 0;
        char[] chars = gstinWithoutCheckDigit.toCharArray();

        // Multiplication factors for each digit position (except check digit)
        int[] factors = {2, 4, 8, 1, 5, 9, 7, 3, 6, 1, 2, 4, 8, 1};

        for (int i = 0; i < chars.length - 1; i++) {
            int digit = Character.isDigit(chars[i]) ? Character.getNumericValue(chars[i]) : chars[i] - 'A' + 10;
            weightedSum += digit * factors[i];
        }

        int remainder = weightedSum % 11;
        return (remainder == 0) ? 'Z' : (char) ('0' + (11 - remainder));
    }

    public static boolean isGSTINValid(String gstin) {
        if (gstin == null || gstin.length() != 15) {
            return false;
        }

        if (!gstin.matches(REGEX)) {
            return false;
        }

        char expectedCheckDigit = calculateCheckDigit(gstin.substring(0, 14));
        return expectedCheckDigit == gstin.charAt(14);
    }
}
