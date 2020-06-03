package io.niufen.common.text;

import io.niufen.common.constant.StringConstants;
import org.junit.Test;

import java.util.List;

public class StrSpliterTest {

    @Test
    public void splitPath() {
        // 切分字符串路径，根据当前系统的默认路径进行切分
        String path = "/Users/niufen/Desktop/lucky_order_all.sql";
        List<String> pathList = StrSpliter.splitPath(path);

        assert 4 == pathList.size();
        assert "Users".equals(pathList.get(0));
        assert "niufen".equals(pathList.get(1));
        assert "Desktop".equals(pathList.get(2));
        assert "lucky_order_all.sql".equals(pathList.get(3));

        pathList = StrSpliter.splitPath(path,1);
        assert 1 == pathList.size();
        assert path.equals(pathList.get(0));

        pathList = StrSpliter.splitPath(path,2);
        assert 2 == pathList.size();
        assert "Users".equals(pathList.get(0));
        assert "niufen/Desktop/lucky_order_all.sql".equals(pathList.get(1));

        pathList = StrSpliter.splitPath(path,3);
        assert 3 == pathList.size();
        assert "Users".equals(pathList.get(0));
        assert "niufen".equals(pathList.get(1));
        assert "Desktop/lucky_order_all.sql".equals(pathList.get(2));
    }

    @Test
    public void splitByChar() {

        // 切分字符串，根据给定的字符
        String str = "a-b-c ";
        char separator = '-';
        List<String> splitList = StrSpliter.split(str, separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c ".equals(splitList.get(2));

        // 根据给定的字符，切分字符串，限制分片
        splitList = StrSpliter.splitLimit(str, separator,2);
        assert 2 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b-c ".equals(splitList.get(1));

        // 切分字符串，大小写敏感，去除每个元素两边空白符
        str = "a-- b-c ";
        separator = '-';
        splitList = StrSpliter.splitTrim(str,separator);
        assert 4 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "".equals(splitList.get(1));
        assert "b".equals(splitList.get(2));
        assert "c".equals(splitList.get(3));

        // 根据给定的字符，切分字符串，过滤掉空白元素
        str = "a-- b-c ";
        separator = '-';
        splitList = StrSpliter.splitIgnoreEmpty(str,separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert " b".equals(splitList.get(1));
        assert "c ".equals(splitList.get(2));

        // 根据给定的字符，切分字符串，并去除元素两边空格，并过滤空白符
        str = "a- - b-c ";
        separator = '-';
        splitList = StrSpliter.splitTrimAndIgnoreEmpty(str,separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));

        // 根据给定的字符，切分字符串，忽略大小写
        str = "1A2a3";
        separator = 'a';
        splitList = StrSpliter.splitIgnoreCase(str,separator);
        assert 3 == splitList.size();
        assert "1".equals(splitList.get(0));
        assert "2".equals(splitList.get(1));
        assert "3".equals(splitList.get(2));

        str = "a-b-c-d-e";
        separator = '-';
        splitList = StrSpliter.split(str, separator, -1, false, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));
        assert "d".equals(splitList.get(3));
        assert "e".equals(splitList.get(4));

        str = "a-b-c-d-e";
        splitList = StrSpliter.split(str, separator, 1, false, false, false);
        assert 1 == splitList.size();
        assert str.equals(splitList.get(0));

        str = "a-b-c-d-e";
        splitList = StrSpliter.split(str, separator, 2, false, false, false);
        assert 2 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b-c-d-e".equals(splitList.get(1));
        str = "a-b- -d-e";
        splitList = StrSpliter.split(str, separator, 5, true, true, false);
        assert 4 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "d".equals(splitList.get(2));
    }

    @Test
    public void splitByString() {

        // 切分字符串，根据给定的字符串
        String str = "a-b-c ";
        String separator = "-";
        List<String> splitList = StrSpliter.split(str, separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c ".equals(splitList.get(2));

        str = "a||b||c ";
        separator = "||";
        splitList = StrSpliter.split(str, separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c ".equals(splitList.get(2));

        // 根据给定的字符串，切分字符串，限制分片
        str = "a-b-c ";
        separator = "-";
        splitList = StrSpliter.splitLimit(str, separator,2);
        assert 2 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b-c ".equals(splitList.get(1));

        // 切分字符串，大小写敏感，去除每个元素两边空白符
        str = "a-- b-c ";
        separator = "-";
        splitList = StrSpliter.splitTrim(str,separator);
        assert 4 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "".equals(splitList.get(1));
        assert "b".equals(splitList.get(2));
        assert "c".equals(splitList.get(3));

        // 根据给定的字符串，切分字符串，过滤掉空白元素
        str = "a-- b-c ";
        separator = "-";
        splitList = StrSpliter.splitIgnoreEmpty(str,separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert " b".equals(splitList.get(1));
        assert "c ".equals(splitList.get(2));

        // 根据给定的字符串，切分字符串，并去除元素两边空格，并过滤空白符
        str = "a- - b-c ";
        separator = "-";
        splitList = StrSpliter.splitTrimAndIgnoreEmpty(str,separator);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));

        // 根据给定的字符串，切分字符串，忽略大小写
        str = "1A2a3";
        separator = "a";
        splitList = StrSpliter.splitIgnoreCase(str,separator);
        assert 3 == splitList.size();
        assert "1".equals(splitList.get(0));
        assert "2".equals(splitList.get(1));
        assert "3".equals(splitList.get(2));

        str = "a-b-c-d-e";
        separator = "-";
        splitList = StrSpliter.split(str, separator, -1, false, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));
        assert "d".equals(splitList.get(3));
        assert "e".equals(splitList.get(4));

        str = "a-b-c-d-e";
        splitList = StrSpliter.split(str, separator, 1, false, false, false);
        assert 1 == splitList.size();
        assert str.equals(splitList.get(0));

        str = "a-b-c-d-e";
        splitList = StrSpliter.split(str, separator, 2, false, false, false);
        assert 2 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b-c-d-e".equals(splitList.get(1));
        str = "a-b- -d-e";
        splitList = StrSpliter.split(str, separator, 5, true, true, false);
        assert 4 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "d".equals(splitList.get(2));
    }

    @Test
    public void splitWhitespace() {
        String str = "a b" + StringConstants.Mark.SPACE_CN + StringConstants.Mark.SPACE_CN + "c" + "" + "d";
        List<String> stringList = StrSpliter.splitByWhitespace(str);
        assert 3 == stringList.size();
        assert "a".equals(stringList.get(0));
        assert "b".equals(stringList.get(1));
        assert "cd".equals(stringList.get(2));

        stringList = StrSpliter.splitByWhitespace(str,2);
        assert 2 == stringList.size();
        assert "a".equals(stringList.get(0));
        assert str.substring(2).equals(stringList.get(1));
    }
}
