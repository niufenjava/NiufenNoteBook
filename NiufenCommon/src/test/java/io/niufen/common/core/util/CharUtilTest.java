package io.niufen.common.core.util;

import io.niufen.common.core.constant.CharConstants;
import io.niufen.common.core.constant.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CharUtilTest {

    @Test
    public void isAscii() {
        assert CharUtil.isAscii('a');
        assert CharUtil.isAscii('A');
        assert CharUtil.isAscii('3');
        assert CharUtil.isAscii('-');
        assert CharUtil.isAscii('\n');
        assert CharUtil.isAscii('\r');
        assert CharUtil.isAscii(' ');
        assert CharUtil.isAscii(']');
        assert CharUtil.isAscii('@');
        assert !CharUtil.isAscii('啊');
    }


    @Test
    public void isAsciiPrintable() {
        assert CharUtil.isAsciiPrintable('a');
        assert CharUtil.isAsciiPrintable('A');
        assert CharUtil.isAsciiPrintable('3');
        assert CharUtil.isAsciiPrintable('-');
        assert !CharUtil.isAsciiPrintable('\n');
        assert !CharUtil.isAsciiPrintable('\r');
        assert CharUtil.isAsciiPrintable(' ');
        assert CharUtil.isAsciiPrintable(']');
        assert CharUtil.isAsciiPrintable('@');
        assert !CharUtil.isAsciiPrintable('啊');

        char zero = 0;
        assert !CharUtil.isAsciiPrintable(zero);
    }

    @Test
    public void isAsciiControl() {
        char zero = 0;
        assert CharUtil.isAsciiControl(zero);
        assert CharUtil.isAsciiControl('\n');
        assert CharUtil.isAsciiControl('\r');

        assert !CharUtil.isAsciiControl('a');
        assert !CharUtil.isAsciiControl('A');
        assert !CharUtil.isAsciiControl('3');
        assert !CharUtil.isAsciiControl('-');
        assert !CharUtil.isAsciiControl('@');
        assert !CharUtil.isAsciiPrintable('啊');
    }

    @Test
    public void isLetter() {

        assert CharUtil.isLetter('a');
        assert CharUtil.isLetter('z');
        assert CharUtil.isLetter('A');
        assert CharUtil.isLetter('Z');

        assert !CharUtil.isLetter('3');
        assert !CharUtil.isLetter('\n');
    }

    @Test
    public void isLetterUpper() {
        char zero = 0;
        assert !CharUtil.isLetterUpper(zero);
        assert !CharUtil.isLetterUpper('\n');
        assert !CharUtil.isLetterUpper('a');
        assert !CharUtil.isLetterUpper('3');

        assert CharUtil.isLetterUpper('A');
        assert CharUtil.isLetterUpper('E');
        assert CharUtil.isLetterUpper('Z');
    }

    @Test
    public void isLetterLower() {
        char zero = 0;
        assert !CharUtil.isLetterLower(zero);
        assert !CharUtil.isLetterLower('\n');
        assert !CharUtil.isLetterLower('A');
        assert !CharUtil.isLetterLower('3');

        assert CharUtil.isLetterLower('a');
        assert CharUtil.isLetterLower('e');
        assert CharUtil.isLetterLower('z');
    }

    @Test
    public void isNumber() {
        char zero = 0;
        assert !CharUtil.isNumber(zero);
        assert !CharUtil.isNumber('\n');
        assert !CharUtil.isNumber('A');
        assert !CharUtil.isNumber('a');


        assert CharUtil.isNumber('3');
        assert CharUtil.isNumber('0');
        assert CharUtil.isNumber('9');
    }

    @Test
    public void isHexChar() {
//
//        assert CharUtil.isHex('A');
//        assert CharUtil.isHex('a');
//        assert CharUtil.isHex('3');
//        assert CharUtil.isHex('0');
//        assert CharUtil.isHex('F');
//        assert CharUtil.isHex('f');
//        assert !CharUtil.isHex('G');
//        assert !CharUtil.isHex('H');
    }

    @Test
    public void isLetterOrNumber() {

        assert CharUtil.isLetterOrNumber('A');
        assert CharUtil.isLetterOrNumber('a');
        assert CharUtil.isLetterOrNumber('3');
        assert CharUtil.isLetterOrNumber('0');
        assert CharUtil.isLetterOrNumber('F');
        assert CharUtil.isLetterOrNumber('f');
        assert CharUtil.isLetterOrNumber('Z');
        assert CharUtil.isLetterOrNumber('z');

        assert !CharUtil.isLetterOrNumber('\n');
        assert !CharUtil.isLetterOrNumber('!');
        assert !CharUtil.isLetterOrNumber('#');

    }

    @Test
    public void toStringTest() {

        // A
        char A = 65;
        assert "A".equals(CharUtil.toString(A));

        // Z
        char Z = 90;
        assert "Z".equals(CharUtil.toString(Z));

        // 左中括号
        char leftSquare = 91;
        assert StringConstants.Mark.LEFT_SQUARE.equals(CharUtil.toString(leftSquare));

        // 啊
        char aCN = '啊';
        assert "啊".equals(CharUtil.toString(aCN));
    }

    @Test
    public void isCharClass() {
        char a = 1;
        Character b = 'a';
        String s = "s";

        assert CharUtil.isCharClass(b.getClass());
        assert !CharUtil.isCharClass(s.getClass());
    }

    @Test
    public void isCharObject() {
        char a = 1;
        Character b = 'a';
        String s = "s";

        assert CharUtil.isChar(a);
        assert CharUtil.isChar(b);
        assert !CharUtil.isChar(s);
    }

    @Test
    public void isBlankChar() {
        assert CharUtil.isBlankChar(CharConstants.SPACE);
        assert CharUtil.isBlankChar(CharConstants.SPACE_CN);

        assert CharUtil.isBlankChar('\t');
        assert CharUtil.isBlankChar('\n');
    }

    @Test
    public void isEmoji() {
        String a = "莉🌹😂🤖";
        assert !CharUtil.isEmoji(CharConstants.SPACE);
        assert !CharUtil.isEmoji(' ');
        assert !CharUtil.isEmoji(a.charAt(0));

        assert CharUtil.isEmoji(a.charAt(1));
        assert CharUtil.isEmoji(a.charAt(2));
        assert CharUtil.isEmoji(a.charAt(3));
    }

    @Test
    public void isFileSeparator() {
        String filepath = "/Users/niufen/Desktop/项目组接入信息";
        assert CharUtil.isFileSeparator(filepath.charAt(0));
        assert !CharUtil.isFileSeparator(filepath.charAt(1));
        assert CharUtil.isFileSeparator(filepath.charAt(6));
    }

    @Test
    public void equalsTest() {
        char aChar = 65;
        char A = 'A';
        char a = 'a';
//        assert CharUtil.equals(aChar, A);
        assert CharUtil.equals(a, A, Boolean.TRUE);
    }

    @Test
    public void getType() {
        //TODO
        log.debug(CharUtil.getType(55) + "");
        log.debug(CharUtil.getType(90) + "");
    }

    @Test
    public void getDigit16() {
        //TODO
//        log.debug(CharUtil.getDigit16(55) + "");
//        log.debug(CharUtil.getDigit16(90) + "");
    }


}
