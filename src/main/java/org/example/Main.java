package org.example;

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

        var lines = ReadFile(inputFilename);
        System.out.println(lines.size() + " total lines");

        for (int i = 0; i < lines.size(); i += 4) {
            accounts.add(new Account(new String[]{
                    lines.get(i),
                    lines.get(i + 1),
                    lines.get(i + 2)
            }));
        }

        StringBuilder sb = new StringBuilder();
        accounts.forEach(a -> sb.append(String.format("%s %s\n", a.Id(), a.Status())));
        Files.write(Paths.get(outputFilename), sb.toString().getBytes());

        System.out.println(String.join(",", accounts.get(22).GetAlternate()));
    }

    public static List<String> ReadFile(String filename) throws IOException {
        var path = Paths.get(filename);
        var lines = Files.readAllLines(path);

        return lines;
    }
}