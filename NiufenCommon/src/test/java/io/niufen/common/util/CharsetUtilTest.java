package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

@Slf4j
public class CharsetUtilTest {

    @Test
    public void charset() {
        assert CharsetUtil.charset("GBK") instanceof Charset;
        assert CharsetUtil.charset("GBK").name().equals("GBK");
        assert CharsetUtil.charset("UTF8").name().equals("UTF-8");
        assert CharsetUtil.charset("UTF-8").name().equals("UTF-8");
        assert CharsetUtil.charset("ISO-8859-1").name().equals("ISO-8859-1");
        assert CharsetUtil.charset("ISO8859-1").name().equals("ISO-8859-1");
        assert CharsetUtil.charset("").name().equals("UTF-8");
        try {
            CharsetUtil.charset("aaaa");
        }catch (UnsupportedCharsetException e){
            assert e instanceof UnsupportedCharsetException;
        }
    }

    @Test
    public void parse() {
        assert CharsetUtil.parse("AAAA") == CharsetUtil.systemCharset();
        assert CharsetUtil.parse("gbk") == CharsetUtil.CHARSET_GBK;
        assert CharsetUtil.parse("UTF8") == CharsetUtil.CHARSET_UTF_8;
        assert CharsetUtil.parse("aaa", CharsetUtil.systemCharset()) == CharsetUtil.systemCharset();
    }

    @Test
    public void convert() throws UnsupportedEncodingException {

        String srcStr = "我不是乱码";
        log.debug("srcStr:{}",srcStr);
        // 转换后result为乱码
        String result = CharsetUtil.convert(srcStr, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
        log.debug("ISO_8859_1:{}",result);
        String raw = CharsetUtil.convert(result, CharsetUtil.ISO_8859_1, "UTF-8");
        log.debug("UTF-8:{}",raw);
        Assert.assertEquals(raw, srcStr);

        String luanma = CharsetUtil.convert(srcStr, CharsetUtil.GBK, "UTF-8");
        log.debug("luanma:{}",luanma);
    }

    @Test
    public void convertFile() {
    }

    @Test
    public void systemCharsetName() {
        assert "UTF-8".equals(CharsetUtil.systemCharsetName());
    }

    @Test
    public void systemCharset() {
        assert CharsetUtil.CHARSET_UTF_8 == CharsetUtil.systemCharset();
    }

    @Test
    public void defaultCharsetName() {
        assert "UTF-8".equals(CharsetUtil.defaultCharsetName());
    }

    @Test
    public void defaultCharset() {
        assert CharsetUtil.CHARSET_UTF_8 == CharsetUtil.defaultCharset();
    }
}
