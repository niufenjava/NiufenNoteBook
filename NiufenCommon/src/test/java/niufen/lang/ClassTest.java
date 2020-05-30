package niufen.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * {@link Class}
 * <blockquote><pre>
 *      public final class Class<T>
 *          extends Object
 *          implements Serializable, GenericDeclaration, Type, AnnotatedElement
 * </pre></blockquote>
 * <p>
 * Type Parameters:
 * T - the type of the class modeled by this Class object.
 * For example, the type of String.class is Class<String>.
 * Use Class<?> if the class being modeled is unknown.
 * T - Class 对象创建的类的类型。
 * 例如，String.class 的类型为 Class<String>.
 * 如果要创建的类未知，可以使用 Use Class<?> 创建
 * <p>
 * Instances of the class Class represent classes and interfaces in a running Java application.
 * 类Class 的实例表示 正在运行的Java应用程序中的类和接口。
 * An enum is a kind of class and an annotation is a kind of interface.
 * 枚举是一种类，注释是一种接口。
 * Every array also belongs to a class that is reflected as a Class object
 * that is shared by all arrays with the same element type and number of dimensions.
 * 每个数组还属于一个反应为Class对象的类，该对象由具有相同元素类型和维数的所有数组共享。
 * The primitive Java types (boolean, byte, char, short, int, long, float, and double),
 * and the keyword void are also represented as Class objects.
 * 原始Java类型以及关键字 void 也表示为 Class 对象。
 * <p>
 * Class has no public constructor.
 * Class 没有公共构造函数。
 * Instead Class objects are constructed automatically by the Java Virtual Machine as classes
 * are loaded and by calls to the defineClass method in the class loader.
 * 取而代之的是，Java虚拟机会在加载类时以及通过在类加载器中调用 defineClass 方法来自动构造 Class对象
 *
 * @author haijun.zhang
 * @date 2020/5/30
 * @time 09:50
 */
@Slf4j
public class ClassTest {

    /**
     * getDeclaredConstructors()：返回一个构造函数数组，如果没有定义，返回默认的空构造函数；如果定义了，只返回定义的
     * <blockquote><pre>
     *      public Constructor<?>[] getDeclaredConstructors()
     *                                          throws SecurityException
     *  </pre></blockquote>
     * <p>
     * Returns an array of Constructor objects reflecting all the constructors
     * declared by the class represented by this Class object.
     * 返回一个构造函数对象数组，该对象数组反映此Class对象表示的类声明的所有构造函数。
     * <p>
     * These are public, protected, default (package) access, and private constructors.
     * 这个构造函数对象数组包括 public\protected\default\private 的构造函数
     * <p>
     * The elements in the array returned are not sorted and are not in any particular order.
     * 返回的数组中的元素没有排序，并且没有任何特定顺序。
     * <p>
     * If the class has a default constructor, it is included in the returned array.
     * 如果这个类没有默认构造函数，则它将包含在返回的数组中。
     * <p>
     * This method returns an array of length 0 if this Class object represents an interface,
     * a primitive type, an array class, or void.
     * 如果此 Class 对象表示接口、原始类型、数组类或 void，则此方法返回长度为 0 的数组
     */
    @Test
    public void getDeclaredConstructors() {
        // 如果你的类中未定义构造函数，则getDeclaredConstructors返回的构造函数数组中有一个默认的构造函数
        Constructor<?>[] noneConstructorClassDeclaredConstructors = NoneConstructorClass.class.getDeclaredConstructors();
        assert 1 == noneConstructorClassDeclaredConstructors.length;
        log.debug("NoneConstructorClass.declaredConstructors[0].getName():{}", noneConstructorClassDeclaredConstructors[0].getName());
        assert "niufen.lang.NoneConstructorClass".equals(noneConstructorClassDeclaredConstructors[0].getName());

        Constructor<?>[] childClassDeclaredConstructors = ChildClass.class.getDeclaredConstructors();
        assert 2 == childClassDeclaredConstructors.length;
        // 通过 constructor.getName() 返回的构造函数名字都一样
        log.debug("ChildClass.declaredConstructors[0].getName():{}", childClassDeclaredConstructors[0].getName());
        log.debug("ChildClass.declaredConstructors[1].getName():{}", childClassDeclaredConstructors[1].getName());

    }

    /**
     * <blockquote><pre>
     *     public Class<?> getComponentType()
     * </pre></blockquote>
     * Returns the Class representing the component type of an array.
     * 返回表示数组的元素类型的Class。
     * If this class does not represent an array class this method returns null.
     * 如果此类不表示数组类，则此方法返回 null
     */
    @Test
    public void getComponentType() {
        int[] iArray = {0, 1, 2, 3};
        Class<?> componentType = iArray.getClass().getComponentType();
        assert componentType == int.class;
    }

    /**
     * clazz1.isAssignableFrom(Class<?> clazz2)
     * 从类继承的角度出发，判断 clazz1 是不是 clazz2 的父类
     * isAssignableFrom()方法与instanceof关键字的区别总结为以下两个点：
     * 1、isAssignableFrom() 方法是从类继承的角度去判断，instanceof 关键字是从实例继承的角度去判断。
     * 2、isAssignableFrom() 方法是判断是否为某个类的父类，instanceof 关键字是判断是否某个类的子类。
     *
     *
     * <blockquote><pre>
     *      public native boolean isAssignableFrom(Class<?> cls);
     * </pre></blockquote>
     */
    @Test
    public void isAssignableFrom() {
        assert ParentClass.class.isAssignableFrom(ChildClass.class);
        assert ParentClass.class.isAssignableFrom(ParentClass.class);
    }

}

class NoneConstructorClass {

}

class ChildClass extends ParentClass {

    private String password;

    public ChildClass(String name, String password) {
        super(name);
        this.password = password;
    }

    public ChildClass(String name) {
        super(name);
    }
}

class ParentClass {
    private String name;

    public ParentClass(String name) {
        this.name = name;
    }
}
