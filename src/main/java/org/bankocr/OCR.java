package org.bankocr;

import java.util.ArrayList;
import java.util.List;

public class OCR {

    public static String[] numbers = {
            " _ " +
                    "| |" +
                    "|_|",
            "   " +
                    "  |" +
                    "  |",
            " _ " +
                    " _|" +
                    "|_ ",
            " _ " +
                    " _|" +
                    " _|",
            "   " +
                    "|_|" +
                    "  |",
            " _ " +
                    "|_ " +
                    " _|",
            " _ " +
                    "|_ " +
                    "|_|",
            " _ " +
                    "  |" +
                    "  |",
            " _ " +
                    "|_|" +
                    "|_|",
            " _ " +
                    "|_|" +
                    " _|"};


    @SuppressWarnings("unused")
    public static String ToLCD(String in) {
        int number = Integer.parseInt(in);
        if (number < 0 || number > 9) {
            throw new UnsupportedOperationException(String.format("the input number is should be 0 >= x <= 9, got %s", in));
        }
        return OCR.numbers[number];
    }

    public static String FromLCD(String in) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].equals(in)) {
                return Integer.toString(i);
            }
        }
        return "?";
    }

    /**
     * @param in Input string of OCR characters to identify alternate character(s)
     * @return A list of potential strings of OCR characters with exactly 1 different character from the input
     */
    public static List<String> Alternates(String in) {
        List<String> alts = new ArrayList<>();
        for (int i = 0; i < OCR.numbers.length; i++) {
            int diff = 0;
            for (int j = 0; j < OCR.numbers[i].length() && diff < 2; j++) {
                if (OCR.numbers[i].charAt(j) == in.charAt(j))
                    continue;
                diff++;
            }
            if (diff == 1) {
                alts.add(OCR.numbers[i]);
            }
        }
        return alts;
    }
}
