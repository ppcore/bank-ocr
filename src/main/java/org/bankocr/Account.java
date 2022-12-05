package org.bankocr;

import java.util.ArrayList;
import java.util.List;

/**
 * Account status enum<br/>
 * ERR [ERROR] the account input contains an error and an alternative could be identified<br/>
 * ILL [ILLEGAL] the account input could be parsed<br/>
 * AMB [AMBIGUOUS] the account input contains an error, and its valid alternatives are more than one<br/>
 * OK [OK] the account input was parsed without any issues<br/>
 */
enum AccountStatus {
    ERR,
    ILL,
    AMB,
    OK
}

public class Account {
    private final String parsed;
    private final String[] chars = new String[9];

    public Account(String input) {
        this.parsed = input;
    }

    public Account(String[] input) {

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
            this.chars[res.length()] = cur.toString();
            res.append(OCR.FromLCD(cur.toString()));
        }
        this.parsed = res.toString();
    }

    public String[] Alternatives() {
        List<String> alternates = new ArrayList<>();
        for (int i = 0; i < this.chars.length; i++) {
            int finalI = i;
            OCR.Alternates(this.chars[i]).forEach(c -> {
                StringBuilder sb = new StringBuilder(this.parsed);
                sb.replace(finalI, finalI + 1, OCR.FromLCD(c));
                String p = sb.toString();
                if (new Account(p).IsValid()) {
                    alternates.add(p);
                }
            });
        }
        return alternates.toArray(new String[0]);
    }

    public String Id() {
        return this.parsed;
    }

    public AccountStatus Status() {
        if (this.IsValid())
            return AccountStatus.OK;
        var alternatives = this.Alternatives();
        if (alternatives.length == 0)
            return AccountStatus.ILL;
        if (alternatives.length > 1)
            return AccountStatus.AMB;
        return AccountStatus.ERR;
    }

    public boolean IsValid() {
        var sum = 0;
        for (int i = 0, mul = 9; i < this.parsed.length(); i++, mul--) {
            if ((this.parsed.charAt(i) - 48) < 0 || (this.parsed.charAt(i) - 48) > 9)
                return false;
            sum += (this.parsed.charAt(i) - 48) * mul;
        }
        return sum % 11 == 0;
    }

    public String ToString() {
        return this.parsed;
    }

    public String Report() {
        var extra = "";
        if (this.Status() == AccountStatus.AMB) {
            extra = " => " + String.join(", ", this.Alternatives());
        }
        if (this.Status() == AccountStatus.ERR) {
            extra = " => " + Alternatives()[0];
        }
        return String.format("%s %s%s", this.parsed, this.Status(), extra);
    }
}
