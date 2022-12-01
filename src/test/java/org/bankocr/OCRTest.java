package org.bankocr;

import org.junit.jupiter.api.DisplayName;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


class OCRTest {
    public static Map<String, List<String>> alternatives;

    static {
        alternatives = new HashMap<>();
        alternatives.put("0", Arrays.asList("8"));
        alternatives.put("1", Arrays.asList("7"));
        alternatives.put("2", Arrays.asList());
        alternatives.put("3", Arrays.asList("9"));
        alternatives.put("4", Arrays.asList());
        alternatives.put("5", Arrays.asList("6", "9"));
        alternatives.put("6", Arrays.asList("5", "8"));
        alternatives.put("7", Arrays.asList("1"));
        alternatives.put("8", Arrays.asList("0", "6", "9"));
        alternatives.put("9", Arrays.asList("3", "5", "8"));
    }

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

    void toLCD(String in) {
        for (int i = 0; i < 10; i++) {
            assertEquals(numbers[i], OCR.ToLCD(Integer.toString(i)));
        }
        assertThrows(UnsupportedOperationException.class, () -> OCR.ToLCD("-1"));
        assertThrows(UnsupportedOperationException.class, () -> OCR.ToLCD("10"));
        assertThrows(NumberFormatException.class, () -> OCR.ToLCD("34?"));
    }

    @DisplayName("from LCD format")
    @ParameterizedTest(name = "input: {1}")
    @MethodSource("fromLCDArguments")
    void fromLCD(String expected, String input) {
        assertEquals(expected, OCR.FromLCD(input));
    }

    static Stream<Arguments> fromLCDArguments() {
        var args = new ArrayList<Arguments>();
        for (int i = 0; i < 10; i++) {
            args.add(Arguments.of(Integer.toString(i), numbers[i]));
        }
        args.add(Arguments.of("?", numbers[9].replaceFirst("_", " ")));
        return args.stream();
    }
}