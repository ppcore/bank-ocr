package org.bankocr;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void getAlternate() {
    }

    @Test
    void TestToString() {
        assertEquals(new Account(new String[]{
                " _  _  _  _  _  _  _  _  _ ",
                " _  _  _  _  _  _  _  _  _ ",
                " _  _  _  _  _  _  _  _  _ "
        }).ToString().length(), 9);
    }

    @DisplayName("Test account status")
    @ParameterizedTest(name = "expected: {0} - input: {1}")
    @MethodSource("TestStatusInputs")
    void TestStatus(AccountStatus expected, String[] input) {
        assertEquals(expected, new Account(input).Status());
    }

    static Stream<Arguments> TestStatusInputs() {
        var args = new ArrayList<Arguments>();
        args.add(Arguments.of(
                AccountStatus.AMB,
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _  _",
                        "|_||_||_||_||_||_||_||_||_|",
                        " _| _| _| _| _| _| _| _| _|"}));
        args.add(Arguments.of(
                AccountStatus.OK,
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _    ",
                        "| || || || || || || ||_   |",
                        "|_||_||_||_||_||_||_| _|  |"}));
        args.add(Arguments.of(
                AccountStatus.ILL,
                (Object) new String[]{
                        "    _  _  _  _  _  _     _",
                        "|_||_|| || ||_   |  |  | _",
                        "  | _||_||_||_|  |  |  | _|"}));
        args.add(Arguments.of(
                AccountStatus.ERR,
                (Object) new String[]{
                        "                           ",
                        "  |  |  |  |  |  |  |  |  |",
                        "  |  |  |  |  |  |  |  |  |"}));
        return args.stream();
    }

    @DisplayName("Test account validity")
    @ParameterizedTest(name = "input: {1}")
    @MethodSource("TestIsValidArguments")
    void TestIsValid(Boolean expected, String[] input) {
        assertEquals(expected, new Account(input).IsValid());
    }

    static Stream<Arguments> TestIsValidArguments() {
        var args = new ArrayList<Arguments>();
        args.add(Arguments.of(
                false,
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _  _",
                        "|_||_||_||_||_||_||_||_||_|",
                        " _| _| _| _| _| _| _| _| _|"}));
        args.add(Arguments.of(
                true,
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _    ",
                        "| || || || || || || ||_   |",
                        "|_||_||_||_||_||_||_| _|  |"}));
        args.add(Arguments.of(
                false,
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _    ",
                        "| || || || || |  || ||_   |",
                        "|_||_||_||_||_||_||_| _|  |"}));
        return args.stream();
    }

    @DisplayName("Test account id pattern")
    @ParameterizedTest(name = "input: {1}")
    @MethodSource("TestIdArguments")
    void TestId(String expected, String[] input) {
        Account a = new Account(input);
        assertTrue(Pattern.matches("[0-9?]{9}", a.Id()));
        assertEquals(expected, a.Id());
    }

    static Stream<Arguments> TestIdArguments() {
        var args = new ArrayList<Arguments>();
        args.add(Arguments.of(
                "999999999",
                (Object) new String[]{
                        " _  _  _  _  _  _  _  _  _",
                        "|_||_||_||_||_||_||_||_||_|",
                        " _| _| _| _| _| _| _| _| _|"}));
        args.add(Arguments.of(
                "99?99999?",
                (Object) new String[]{
                        " _  _     _  _  _  _  _   ",
                        "|_||_||_||_||_||_||_||_|| |",
                        " _| _| _| _| _| _| _| _|| |"}));
        return args.stream();
    }

    @DisplayName("Test account report string")
    @ParameterizedTest(name = "input: {1}")
    @MethodSource("TestReportArguments")
    void TestReport(String[] input) {
        var account = new Account(input);
        var report = account.Report();
        assertTrue(report.startsWith(account.Id()));
        assertTrue(report.contains(account.Status().toString()));
    }

    static Stream<Arguments> TestReportArguments() {
        return Stream.of(
                Arguments.of((Object) new String[]{
                        " _  _  _  _  _  _  _  _  _",
                        "|_||_||_||_||_||_||_||_||_|",
                        " _| _| _| _| _| _| _| _| _|"}),
                Arguments.of((Object) new String[]{
                        " _  _  _  _  _  _  _  _    ",
                        "| || || || || || || ||_   |",
                        "|_||_||_||_||_||_||_| _|  |"}),
                Arguments.of((Object) new String[]{
                        "                           ",
                        "  |  |  |  |  |  |  |  |  |",
                        "  |  |  |  |  |  |  |  |  |"})
        );
    }

}