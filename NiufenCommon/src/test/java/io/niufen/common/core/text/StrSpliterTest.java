package io.niufen.common.core.text;

import io.niufen.common.core.constant.StringConstants;
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

}
