package io.niufen.common.core.annotation;

import io.niufen.common.core.collection.SetUtil;

import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * 组合注解元素
 * 对 JDK 的原生注解机制做一个增强，支持类似 SpringBoot 的组合注解的解析
 * 核心实现：使用了递归获取指定元素上的注解 以及注解的注解，以实现复合注解的获取
 *
 * @author haijun.zhang
 * @date 2020/5/25
 * @time 16:05
 */
public class CombinationAnnotationElement implements AnnotatedElement, Serializable {

    private static final long serialVersionUID = 8990713020674605450L;

    /**
     * 元注解 Set集合
     */
    private static final Set<Class<? extends Annotation>> META_ANNOTATION_SET = SetUtil.newSet();

    /*
     * 类初始化时，将 JDK 默认的元注解设置到 META_ANNOTATION_SET 中
     */
    static {
        META_ANNOTATION_SET.add(Target.class);
        META_ANNOTATION_SET.add(Retention.class);
        META_ANNOTATION_SET.add(Inherited.class);
        META_ANNOTATION_SET.add(Documented.class);
        META_ANNOTATION_SET.add(Native.class);
        META_ANNOTATION_SET.add(Repeatable.class);
        META_ANNOTATION_SET.add(SuppressWarnings.class);
        META_ANNOTATION_SET.add(Override.class);
    }

    /**
     * 注解类型与注解对象 Map
     */
    private Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<>();

    /**
     * 直接注解类型与注解对象 Map
     */
    private Map<Class<? extends Annotation>, Annotation> declaredAnnotationMap = new HashMap<>();

    /**
     * 构造方法
     *
     * @param element 需要解析注解的元素
     *                可以是 Class、Method、Field、Constructor、ReflectPermission
     *                这些都实现了 AnnotatedElement 接口
     */
    public CombinationAnnotationElement(AnnotatedElement element) {
        init(element);
    }

    /**
     * 初始化方法，用于简化构造方法的代码
     *
     * @param element 需要解析注解的元素
     */
    private void init(AnnotatedElement element) {
        final Annotation[] declaredAnnotations = element.getDeclaredAnnotations();
        parseDeclaredAnnotations(declaredAnnotations);

        final Annotation[] annotations = element.getAnnotations();
        if (Arrays.equals(declaredAnnotations, annotations)) {
            this.annotationMap = this.declaredAnnotationMap;
        } else {
            paresAnnotations(annotations);
        }
    }

    /**
     * 递归解析全部注解，知道全部是元注解位置
     *
     * @param annotations 元素上贴的注解集合
     */
    private void parseDeclaredAnnotations(Annotation[] annotations) {
        Class<? extends Annotation> annotationType;
        //
        for (Annotation annotation : annotations) {
            annotationType = annotation.annotationType();
            if (!META_ANNOTATION_SET.contains(annotationType)) {
                declaredAnnotationMap.put(annotationType, annotation);
                parseDeclaredAnnotations(annotationType.getAnnotations());
            }
        }
    }

    /**
     * 递归解析注解，知道全部都是元注解
     *
     * @param annotations 元素上贴的注解集合
     */
    private void paresAnnotations(Annotation[] annotations) {
        Class<? extends Annotation> annotationType;
        for (Annotation annotation : annotations) {
            annotationType = annotation.annotationType();
            if (!META_ANNOTATION_SET.contains(annotationType)) {
                declaredAnnotationMap.put(annotationType, annotation);
                paresAnnotations(annotationType.getDeclaredAnnotations());
            }
        }
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return annotationMap.containsKey(annotationClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Annotation annotation = annotationMap.get(annotationClass);
        return (annotation == null) ? null : (T) annotation;
    }

    @Override
    public Annotation[] getAnnotations() {
        final Collection<Annotation> annotations = this.annotationMap.values();
        return annotations.toArray(new Annotation[0]);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        final Collection<Annotation> annotations = this.declaredAnnotationMap.values();
        return annotations.toArray(new Annotation[0]);
    }
}
