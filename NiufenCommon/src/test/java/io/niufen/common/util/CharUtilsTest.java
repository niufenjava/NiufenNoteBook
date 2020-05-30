package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CharUtilsTest {

    @Test
    public void isAscii() {
        assert CharUtils.isAscii('a');
        assert CharUtils.isAscii('A');
        assert CharUtils.isAscii('3');
        assert CharUtils.isAscii('-');
        assert CharUtils.isAscii('\n');
        assert CharUtils.isAscii('\r');
        assert CharUtils.isAscii(' ');
        assert CharUtils.isAscii(']');
        assert CharUtils.isAscii('@');
        assert !CharUtils.isAscii('啊');
    }


    @Test
    public void isAsciiPrintable() {
        assert CharUtils.isAsciiPrintable('a');
        assert CharUtils.isAsciiPrintable('A');
        assert CharUtils.isAsciiPrintable('3');
        assert CharUtils.isAsciiPrintable('-');
        assert !CharUtils.isAsciiPrintable('\n');
        assert !CharUtils.isAsciiPrintable('\r');
        assert CharUtils.isAsciiPrintable(' ');
        assert CharUtils.isAsciiPrintable(']');
        assert CharUtils.isAsciiPrintable('@');
        assert !CharUtils.isAsciiPrintable('啊');

        char zero = 0;
        assert !CharUtils.isAsciiPrintable(zero);
    }

    @Test
    public void isAsciiControl() {
        char zero = 0;
        assert CharUtils.isAsciiControl(zero);
        assert CharUtils.isAsciiControl('\n');
        assert CharUtils.isAsciiControl('\r');

        assert !CharUtils.isAsciiControl('a');
        assert !CharUtils.isAsciiControl('A');
        assert !CharUtils.isAsciiControl('3');
        assert !CharUtils.isAsciiControl('-');
        assert !CharUtils.isAsciiControl('@');
        assert !CharUtils.isAsciiPrintable('啊');
    }

    @Test
    public void isLetter() {

        assert CharUtils.isLetter('a');
        assert CharUtils.isLetter('z');
        assert CharUtils.isLetter('A');
        assert CharUtils.isLetter('Z');

        assert !CharUtils.isLetter('3');
        assert !CharUtils.isLetter('\n');
    }

    @Test
    public void isLetterUpper() {
        char zero = 0;
        assert !CharUtils.isLetterUpper(zero);
        assert !CharUtils.isLetterUpper('\n');
        assert !CharUtils.isLetterUpper('a');
        assert !CharUtils.isLetterUpper('3');

        assert CharUtils.isLetterUpper('A');
        assert CharUtils.isLetterUpper('E');
        assert CharUtils.isLetterUpper('Z');
    }

    @Test
    public void isLetterLower() {
        char zero = 0;
        assert !CharUtils.isLetterLower(zero);
        assert !CharUtils.isLetterLower('\n');
        assert !CharUtils.isLetterLower('A');
        assert !CharUtils.isLetterLower('3');

        assert CharUtils.isLetterLower('a');
        assert CharUtils.isLetterLower('e');
        assert CharUtils.isLetterLower('z');
    }

    @Test
    public void isNumber() {
        char zero = 0;
        assert !CharUtils.isNumber(zero);
        assert !CharUtils.isNumber('\n');
        assert !CharUtils.isNumber('A');
        assert !CharUtils.isNumber('a');


        assert CharUtils.isNumber('3');
        assert CharUtils.isNumber('0');
        assert CharUtils.isNumber('9');
    }

    @Test
    public void isHexChar() {

        assert CharUtils.isHex('A');
        assert CharUtils.isHex('a');
        assert CharUtils.isHex('3');
        assert CharUtils.isHex('0');
        assert CharUtils.isHex('F');
        assert CharUtils.isHex('f');
        assert !CharUtils.isHex('G');
        assert !CharUtils.isHex('H');
    }

    @Test
    public void isLetterOrNumber() {

        assert CharUtils.isLetterOrNumber('A');
        assert CharUtils.isLetterOrNumber('a');
        assert CharUtils.isLetterOrNumber('3');
        assert CharUtils.isLetterOrNumber('0');
        assert CharUtils.isLetterOrNumber('F');
        assert CharUtils.isLetterOrNumber('f');
        assert CharUtils.isLetterOrNumber('Z');
        assert CharUtils.isLetterOrNumber('z');

        assert !CharUtils.isLetterOrNumber('\n');
        assert !CharUtils.isLetterOrNumber('!');
        assert !CharUtils.isLetterOrNumber('#');

    }

    @Test
    public void toStringTest() {

        // A
        char A = 65;
        assert "A".equals(CharUtils.toString(A));

        // Z
        char Z = 90;
        assert "Z".equals(CharUtils.toString(Z));

        // 左中括号
        char leftSquare = 91;
        assert StringConstants.Mark.LEFT_SQUARE.equals(CharUtils.toString(leftSquare));

        // 啊
        char aCN = '啊';
        assert "啊".equals(CharUtils.toString(aCN));
    }

    @Test
    public void isCharClass() {
        char a = 1;
        Character b = 'a';
        String s = "s";

        assert CharUtils.isCharClass(b.getClass());
        assert !CharUtils.isCharClass(s.getClass());
    }

    @Test
    public void isCharObject() {
        char a = 1;
        Character b = 'a';
        String s = "s";

        assert CharUtils.isChar(a);
        assert CharUtils.isChar(b);
        assert !CharUtils.isChar(s);
    }

    @Test
    public void isBlankChar() {
        assert CharUtils.isBlankChar(CharConstants.SPACE);
        assert CharUtils.isBlankChar(CharConstants.SPACE_CN);

        assert CharUtils.isBlankChar('\t');
        assert CharUtils.isBlankChar('\n');
    }

    @Test
    public void isEmoji() {
        String a = "莉🌹😂🤖";
        assert !CharUtils.isEmoji(CharConstants.SPACE);
        assert !CharUtils.isEmoji(' ');
        assert !CharUtils.isEmoji(a.charAt(0));

        assert CharUtils.isEmoji(a.charAt(1));
        assert CharUtils.isEmoji(a.charAt(2));
        assert CharUtils.isEmoji(a.charAt(3));
    }

    @Test
    public void isFileSeparator() {
        String filepath = "/Users/niufen/Desktop/项目组接入信息";
        assert CharUtils.isFileSeparator(filepath.charAt(0));
        assert !CharUtils.isFileSeparator(filepath.charAt(1));
        assert CharUtils.isFileSeparator(filepath.charAt(6));
    }

    @Test
    public void equalsTest() {
        char aChar = 65;
        char A = 'A';
        char a = 'a';
        assert CharUtils.equals(aChar, A);
        assert CharUtils.equals(a, A, Boolean.TRUE);
    }

    @Test
    public void getType() {
        //TODO
        log.debug(CharUtils.getType(55) + "");
        log.debug(CharUtils.getType(90) + "");
    }

    @Test
    public void getDigit16() {
        //TODO
        log.debug(CharUtils.getDigit16(55) + "");
        log.debug(CharUtils.getDigit16(90) + "");
    }


}
