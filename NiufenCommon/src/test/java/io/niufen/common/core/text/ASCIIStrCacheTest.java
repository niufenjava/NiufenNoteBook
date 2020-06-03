package io.niufen.common.core.text;

import io.niufen.common.core.constant.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ASCIIStrCacheTest {

    @Test
    public void testToString() {
        // 空字符
        char nullChar = 0;
        assert "\u0000".equals(ASCIIStrCache.toString(nullChar));

        // 空格
        char space = 32;
        assert StringConstants.Mark.SPACE.equals(ASCIIStrCache.toString(space));

        // 叹号
        char exclamationMark = 33;
        assert StringConstants.Mark.EXCLAMATION_MARK.equals(ASCIIStrCache.toString(exclamationMark));

        // 双引号
        char doubleQuote = 34;
        assert StringConstants.Mark.DOUBLE_QUOTE.equals(ASCIIStrCache.toString(doubleQuote));

        // 井号
        char numberSign = 35;
        assert StringConstants.Mark.NUMBER_SIGN.equals(ASCIIStrCache.toString(numberSign));

        // 美元符
        char dollarSign = 36;
        assert StringConstants.Mark.DOLLAR_SIGN.equals(ASCIIStrCache.toString(dollarSign));

        // 百分号
        char percent = 37;
        assert StringConstants.Mark.PERCENT.equals(ASCIIStrCache.toString(percent));

        // and符
        char andSymbol = 38;
        assert StringConstants.Mark.AND_SYMBOL.equals(ASCIIStrCache.toString(andSymbol));

        // 单引号
        char singleQuote = 39;
        assert StringConstants.Mark.SINGLE_QUOTE.equals(ASCIIStrCache.toString(singleQuote));

        // 左括号
        char leftParenthesis = 40;
        assert StringConstants.Mark.LEFT_PARENTHESIS.equals(ASCIIStrCache.toString(leftParenthesis));

        // 右括号
        char rightParenthesis = 41;
        assert StringConstants.Mark.RIGHT_PARENTHESIS.equals(ASCIIStrCache.toString(rightParenthesis));

        // 星号
        char asterisk = 42;
        assert StringConstants.Mark.ASTERISK.equals(ASCIIStrCache.toString(asterisk));

        // 加号
        char plus = 43;
        assert StringConstants.Mark.PLUS.equals(ASCIIStrCache.toString(plus));

        // 逗号
        char comma = 44;
        assert StringConstants.Mark.COMMA.equals(ASCIIStrCache.toString(comma));

        // 减号
        char minus = 45;
        assert StringConstants.Mark.MINUS.equals(ASCIIStrCache.toString(minus));

        // 句号
        char dot = 46;
        assert StringConstants.Mark.DOT.equals(ASCIIStrCache.toString(dot));

        // 斜杠
        char forwardSlash = 47;
        assert StringConstants.Mark.FORWARD_SLASH.equals(ASCIIStrCache.toString(forwardSlash));

        // 0
        char one = 48;
        assert "0".equals(ASCIIStrCache.toString(one));

        // 0  -  9
        // 48 -  57

        // 9
        char nine = 57;
        assert "9".equals(ASCIIStrCache.toString(nine));

        // 冒号
        char colon = 58;
        assert StringConstants.Mark.COLON.equals(ASCIIStrCache.toString(colon));

        // 分号
        char semicolon = 59;
        assert StringConstants.Mark.SEMICOLON.equals(ASCIIStrCache.toString(semicolon));

        // 小于号
        char lessThanSign = 60;
        assert StringConstants.Mark.LESS_THAN_SIGN.equals(ASCIIStrCache.toString(lessThanSign));

        // 等于
        char equal = 61;
        assert StringConstants.Mark.EQUAL.equals(ASCIIStrCache.toString(equal));

        // 大于号
        char greaterThanSign = 62;
        assert StringConstants.Mark.GREATER_THAN_SIGN.equals(ASCIIStrCache.toString(greaterThanSign));

        // 问号
        char questionMark = 63;
        assert StringConstants.Mark.QUESTION_MARK.equals(ASCIIStrCache.toString(questionMark));

        // 艾特
        char atSymbol = 64;
        assert StringConstants.Mark.AT_SYMBOL.equals(ASCIIStrCache.toString(atSymbol));

        // A
        char A = 65;
        assert "A".equals(ASCIIStrCache.toString(A));

        // Z
        char Z = 90;
        assert "Z".equals(ASCIIStrCache.toString(Z));

        // 左中括号
        char leftSquare = 91;
        assert StringConstants.Mark.LEFT_SQUARE.equals(ASCIIStrCache.toString(leftSquare));

        // 反斜杠
        char backwardSlash = 92;
        assert StringConstants.Mark.BACKWARD_SLASH.equals(ASCIIStrCache.toString(backwardSlash));

        // 右中括号
        char rightSquare = 93;
        assert StringConstants.Mark.RIGHT_SQUARE.equals(ASCIIStrCache.toString(rightSquare));

        // 折引号
        char circumflex = 94;
        assert StringConstants.Mark.CIRCUMFLEX.equals(ASCIIStrCache.toString(circumflex));

        // 下划线
        char underline = 95;
        assert StringConstants.Mark.UNDERLINE.equals(ASCIIStrCache.toString(underline));

        // 反引号
        char backQuote = 96;
        assert StringConstants.Mark.BACK_QUOTE.equals(ASCIIStrCache.toString(backQuote));

        // a
        char a = 97;
        assert "a".equals(ASCIIStrCache.toString(a));

        // z
        char z = 122;
        assert "z".equals(ASCIIStrCache.toString(z));

        // 左大括号
        char leftBrace = 123;
        assert StringConstants.Mark.LEFT_BRACE.equals(ASCIIStrCache.toString(leftBrace));

        // 管道符
        char pipe = 124;
        assert StringConstants.Mark.PIPE.equals(ASCIIStrCache.toString(pipe));

        // 右大括号
        char rightBrace = 125;
        assert StringConstants.Mark.RIGHT_BRACE.equals(ASCIIStrCache.toString(rightBrace));

        // 波浪号
        char tilde = 126;
        assert StringConstants.Mark.TILDE.equals(ASCIIStrCache.toString(tilde));
    }

    @Test
    public void print(){
        ASCIIStrCache.print();
    }

    @Test
    public void noAscii(){
        char a = 12312;
        log.debug(ASCIIStrCache.toString(a));
    }
}
