package io.niufen.common.util;

import io.niufen.common.collection.EnumerationIterator;
import io.niufen.common.exception.IORuntimeException;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Resource 资源工具类
 *
 * @author haijun.zhang
 * @date 2020/5/29
 * @time 16:22
 */
public class ResourceUtils {


    /**
     * 获取指定路径下的资源Iterator<br>
     * 路径格式必须为目录格式，用 / 分割
     * 例如：
     * config/a
     * spring/xml
     *
     * @param resource 资源路径
     * @return 资源列表
     */
    public static EnumerationIterator<URL> getResourceIterator(String resource) {
        final Enumeration<URL> resources;
        try {
            resources = ClassLoaderUtils.getClassLoader().getResources(resource);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return new EnumerationIterator<>(resources);
    }
}
