package org.bankocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {

        var inputFilename = "test-input.txt";
        var outputFilename = "test-output.txt";
        List<Account> accounts = new ArrayList<Account>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get((inputFilename)));
        } catch (Exception e) {
            System.err.println("Failed to read file lines: " + e);
            return;
        }
        for (int i = 0; i < lines.size(); i += 4) {
            accounts.add(new Account(new String[]{
                    lines.get(i),
                    lines.get(i + 1),
                    lines.get(i + 2)
            }));
        }

        StringBuilder sb = new StringBuilder();
        accounts.forEach(a -> sb.append(a.Report() + "\n"));
        Files.write(Paths.get(outputFilename), sb.toString().getBytes());
    }
}