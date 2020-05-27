package io.niufen.common.text;

import io.niufen.common.constant.StringConstants;
import org.junit.Test;

import java.util.List;

public class StringSpliterTest {

    @Test
    public void split() {
        String str = "a-b-c-d-e";
        char separator = '-';
        List<String> splitList = StringSpliter.split(str, separator, -1, false, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));
        assert "d".equals(splitList.get(3));
        assert "e".equals(splitList.get(4));

        str = "a-b-c-d-e";
        splitList = StringSpliter.split(str, separator, 0, false, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c".equals(splitList.get(2));
        assert "d".equals(splitList.get(3));
        assert "e".equals(splitList.get(4));

        str = "a-b-c-d-e";
        splitList = StringSpliter.split(str, separator, 1, false, false, false);
        assert 1 == splitList.size();
        assert str.equals(splitList.get(0));

        str = "a-b-c-d-e";
        splitList = StringSpliter.split(str, separator, 2, false, false, false);
        assert 2 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b-c-d-e".equals(splitList.get(1));

        str = "a-b-c-d-e";
        splitList = StringSpliter.split(str, separator, 3, false, false, false);
        assert 3 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "c-d-e".equals(splitList.get(2));

        str = "a-b- -d-e";
        splitList = StringSpliter.split(str, separator, 5, false, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert " ".equals(splitList.get(2));

        str = "a-b- -d-e";
        splitList = StringSpliter.split(str, separator, 5, true, false, false);
        assert 5 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "".equals(splitList.get(2));

        str = "a-b- -d-e";
        splitList = StringSpliter.split(str, separator, 5, true, true, false);
        assert 4 == splitList.size();
        assert "a".equals(splitList.get(0));
        assert "b".equals(splitList.get(1));
        assert "d".equals(splitList.get(2));
    }

    @Test
    public void splitWhitespace(){
        String str = "a b"+ StringConstants.Mark.SPACE_CN + StringConstants.Mark.SPACE_CN +"c"+""+"d";
        List<String> stringList = StringSpliter.split(str, 0);
        assert 3 == stringList.size();
        assert "a".equals(stringList.get(0));
        assert "b".equals(stringList.get(1));
        assert "cd".equals(stringList.get(2));
    }
}
