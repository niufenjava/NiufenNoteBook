package io.niufen.common.util;

import io.niufen.common.collection.EnumerationIterator;
import io.niufen.common.io.IORuntimeException;
import io.niufen.common.io.resource.Resource;

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
public class ResourceUtil {


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
            resources = ClassLoaderUtil.getClassLoader().getResources(resource);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return new EnumerationIterator<>(resources);
    }

    /**
     * 获得资源的URL<br>
     * 路径用/分隔，例如:
     *
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getResource(String resource) throws IORuntimeException {
        return getResource(resource, null);
    }
    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource 资源相对路径
     * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
     * @return {@link URL}
     */
    public static URL getResource(String resource, Class<?> baseClass) {
        return (null != baseClass) ? baseClass.getResource(resource) : ClassLoaderUtil.getClassLoader().getResource(resource);
    }
    public static Resource getResourceObj( ) {

        return null;
    }
}
