package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum AccountStatus {
    ERR,
    ILL,
    OK
}

public class Account {
    private String parsed = "";
    private String[] input;

    public Account(String input) {
        this.parsed = input;
    }

    public Account(String[] input) {
        this.input = input;

        //make sure the lines length is the same 27 chars everytime
        for (int i = 0; i < input.length; i++) {
            if (input[i].length() < 27) {
                input[i] += " ".repeat(27 - input[i].length());
            }
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < input[0].length(); i += 3) {
            StringBuilder cur = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                cur.append(input[j], i, i + 3);
            }
            res.append(OCR.Identify(cur.toString()));
        }
        this.parsed = res.toString();
    }

    public String[] GetAlternate() {
        List<String> alternates = new ArrayList<String>();
        this.parsed.chars().
        for (int i = 0; i < this.parsed.length(); i++) {
            var n = Character.getNumericValue(this.parsed.charAt(i));
            var alt = Arrays.stream(OCR.GetAlternates(n)).boxed().collect(Collectors.toList());
            int finalI = i;
            alt.forEach(a -> {
                StringBuilder sb = new StringBuilder(this.parsed);
                sb.replace(finalI, finalI + 1, a.toString());
                var newAcc = new Account(sb.toString());
                if (newAcc.IsValid()) {
                    alternates.add(newAcc.Id());
                }
            });
        }
        return alternates.toArray(new String[0]);
    }

    public String Id() {
        return this.parsed;
    }

    public AccountStatus Status() {
        return this.parsed.indexOf("?") > 0 ? AccountStatus.ILL
                : !IsValid() ? AccountStatus.ERR
                : AccountStatus.OK;
    }

    public boolean IsValid() {
        var sum = 0;
        try {
            for (int i = 0, mul = 9; i < this.parsed.length(); i++, mul--) {
                sum += Integer.parseInt(String.valueOf(this.parsed.charAt(i))) * mul;
            }
        } catch (Exception e) {
            return false;
        }
        return sum % 11 == 0;
    }
}
