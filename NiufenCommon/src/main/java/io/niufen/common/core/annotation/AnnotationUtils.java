package io.niufen.common.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 注解通用工具类
 * 快速获取注解对象、注解值等工具封装
 *
 * @author haijun.zhang
 * @date 2020/5/23
 * @time 15:15
 */
public class AnnotationUtils {

    /**
     * 将指定的被注解的元素转换为组合注解元素
     * @param annotatedElement 注解元素
     * @return 组合注解元素
     */
    public static CombinationAnnotationElement toCombination(AnnotatedElement annotatedElement){
        // 如果已经是一个组合注解元素，直接强制转换；
        if(annotatedElement instanceof CombinationAnnotationElement){
            return (CombinationAnnotationElement)(annotatedElement);
        }
        // 如果是元素注解类型，那么 创建一个
        // TODO 这里是不是可以优化一下，不要每次都new，而是使用集合直接获取
        return new CombinationAnnotationElement(annotatedElement);
    }

    /**
     * 获取指定注解
     *
     * @param annotatedElement {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param isToCombination 是否为转换为组合注解
     * @return 注解对象数组
     */
    public static Annotation[] getAnnotations(AnnotatedElement annotatedElement, Boolean isToCombination){
        if(null == annotatedElement) {
            return null;
        }
        if(isToCombination){
            return toCombination(annotatedElement).getAnnotations();
        }
        return annotatedElement.getAnnotations();
    }

    /**
     * 获取指定注解，包括组合注解中的子注解
     * @param annotatedElement {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型 Annotation.class
     * @return 注解对象
     */
    public static <A extends Annotation> A getAnnotation(AnnotatedElement annotatedElement, Class<A> annotationType){
        if(null == annotatedElement){
            return null;
        }
        return toCombination(annotatedElement).getAnnotation(annotationType);
    }
}
