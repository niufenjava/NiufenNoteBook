package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

@Slf4j
public class CharsetUtilsTest {

    @Test
    public void charset() {
        assert CharsetUtils.charset("GBK") instanceof Charset;
        assert CharsetUtils.charset("GBK").name().equals("GBK");
        assert CharsetUtils.charset("UTF8").name().equals("UTF-8");
        assert CharsetUtils.charset("UTF-8").name().equals("UTF-8");
        assert CharsetUtils.charset("ISO-8859-1").name().equals("ISO-8859-1");
        assert CharsetUtils.charset("ISO8859-1").name().equals("ISO-8859-1");
        assert CharsetUtils.charset("").name().equals("UTF-8");
        try {
            CharsetUtils.charset("aaaa");
        }catch (UnsupportedCharsetException e){
            assert e instanceof UnsupportedCharsetException;
        }
    }

    @Test
    public void parse() {
        assert CharsetUtils.parse("AAAA") == CharsetUtils.systemCharset();
        assert CharsetUtils.parse("gbk") == CharsetUtils.CHARSET_GBK;
        assert CharsetUtils.parse("UTF8") == CharsetUtils.CHARSET_UTF_8;
        assert CharsetUtils.parse("aaa",CharsetUtils.systemCharset()) == CharsetUtils.systemCharset();
    }

    @Test
    public void convert() throws UnsupportedEncodingException {

        String srcStr = "我不是乱码";
        log.debug("srcStr:{}",srcStr);
        // 转换后result为乱码
        String result = CharsetUtils.convert(srcStr, CharsetUtils.UTF_8, CharsetUtils.ISO_8859_1);
        log.debug("ISO_8859_1:{}",result);
        String raw = CharsetUtils.convert(result, CharsetUtils.ISO_8859_1, "UTF-8");
        log.debug("UTF-8:{}",raw);
        Assert.assertEquals(raw, srcStr);

        String luanma = CharsetUtils.convert(srcStr, CharsetUtils.GBK, "UTF-8");
        log.debug("luanma:{}",luanma);
    }

    @Test
    public void convertFile() {
    }

    @Test
    public void systemCharsetName() {
        assert "UTF-8".equals(CharsetUtils.systemCharsetName());
    }

    @Test
    public void systemCharset() {
        assert CharsetUtils.CHARSET_UTF_8 == CharsetUtils.systemCharset();
    }

    @Test
    public void defaultCharsetName() {
        assert "UTF-8".equals(CharsetUtils.defaultCharsetName());
    }

    @Test
    public void defaultCharset() {
        assert CharsetUtils.CHARSET_UTF_8 == CharsetUtils.defaultCharset();
    }
}
