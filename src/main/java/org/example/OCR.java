package org.example;

import java.util.HashMap;

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

    private static HashMap<Integer, int[]> alternates = new HashMap<Integer, int[]>();

    public static int[] GetAlternates(int x) {
        return alternates.get(x);
    }

    static {
        alternates.put(0, new int[]{8});
        alternates.put(1, new int[]{7});
        alternates.put(2, new int[]{});
        alternates.put(3, new int[]{9});
        alternates.put(4, new int[]{});
        alternates.put(5, new int[]{6, 9});
        alternates.put(6, new int[]{5, 8});
        alternates.put(7, new int[]{1});
        alternates.put(8, new int[]{0, 6, 9});
        alternates.put(9, new int[]{3, 8});
    }


    public static String Identify(String in) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].equals(in)) {
                return String.format("%d", i);
            }
        }
        return "?";
    }
}
