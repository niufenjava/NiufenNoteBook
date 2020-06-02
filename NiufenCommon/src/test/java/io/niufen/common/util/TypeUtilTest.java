package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Slf4j
public class TypeUtilTest {

    @Test
    public void getClassTest() throws NoSuchFieldException {
        assert TypeTest.class == TypeUtil.getClass(TypeTest.class);

        // public List<T> list = new ArrayList<>();
        Field list = TypeTest.class.getField("list");
        assert List.class == TypeUtil.getClass(list.getGenericType());

        /*
         * class TypeTest<T, V extends @Custom Number & Serializable> {
         *   public T t;
         *   public V v;
         *   public Integer I;
         *   public List<T> list = new ArrayList<>();
         * }
         */
        Field v = TypeTest.class.getField("v");
        Field t = TypeTest.class.getField("t");
        Class<?> vClass = TypeUtil.getClass(v.getGenericType());
        Class<?> tClass = TypeUtil.getClass(t.getGenericType());
        assert Number.class == vClass;
        assert Object.class == tClass;

        // public Map<? super String, ? extends Number> mapWithWildcard;
        Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
        Class<?> mapWithWildcardClass = TypeUtil.getClass(((ParameterizedType) mapWithWildcard.getGenericType()).getActualTypeArguments()[0]);
        assert Object.class == mapWithWildcardClass;
        Class<?> mapWithWildcardClass2 = TypeUtil.getClass(((ParameterizedType) mapWithWildcard.getGenericType()).getActualTypeArguments()[1]);
        assert Number.class == mapWithWildcardClass2;
    }

    @Test
    public void getType() throws NoSuchFieldException {
        /*
         * class TypeTest<T, V extends @Custom Number & Serializable> {
         *   public T t;
         *   public V v;
         *   public Integer I;
         *   public List<T> list = new ArrayList<>();
         * }
         */
        Field tField = TypeTest.class.getField("t");
        Field vField = TypeTest.class.getField("v");
        Field iField = TypeTest.class.getField("i");
        Field listField = TypeTest.class.getField("list");
        Type tType = TypeUtil.getType(tField);
        Type vType = TypeUtil.getType(vField);
        Type iType = TypeUtil.getType(iField);
        Type listType = TypeUtil.getType(listField);

        assert tType instanceof TypeVariable;
        assert vType instanceof TypeVariable;
        assert iType instanceof Class;
        assert listType instanceof ParameterizedType;

        assert "T".equals(tType.getTypeName());
        assert "V".equals(vType.getTypeName());
        assert "java.lang.Integer".equals(iType.getTypeName());
        assert "java.util.List<T>".equals(listType.getTypeName());

        Field tFieldImpl = TypeTestImpl.class.getField("t");
        Field vFieldImpl = TypeTestImpl.class.getField("v");
        Field iFieldImpl = TypeTestImpl.class.getField("i");
        Field listFieldImpl = TypeTestImpl.class.getField("list");
        Type tTypeImpl = TypeUtil.getType(tField);
        Type vTypeImpl = TypeUtil.getType(vField);
        Type iTypeImpl = TypeUtil.getType(iField);
        Type listTypeImpl = TypeUtil.getType(listField);

        assert tTypeImpl instanceof TypeVariable;
        assert vTypeImpl instanceof TypeVariable;
        assert iTypeImpl instanceof Class;
        assert listTypeImpl instanceof ParameterizedType;

        assert "T".equals(tTypeImpl.getTypeName());
        assert "V".equals(vTypeImpl.getTypeName());
        assert "java.lang.Integer".equals(iTypeImpl.getTypeName());
        assert "java.util.List<T>".equals(listTypeImpl.getTypeName());
    }

    @Test
    public void getClassByField() throws NoSuchFieldException {
        /*
         * class TypeTest<T, V extends @Custom Number & Serializable> {
         *   public T t;
         *   public V v;
         *   public Integer I;
         *   public List<T> list = new ArrayList<>();
         * }
         */
        Field tField = TypeTest.class.getField("t");
        Field vField = TypeTest.class.getField("v");
        Field iField = TypeTest.class.getField("i");
        Field listField = TypeTest.class.getField("list");
        Class<?> tClass = TypeUtil.getClass(tField);
        Class<?> vClass = TypeUtil.getClass(vField);
        Class<?> iClass = TypeUtil.getClass(iField);
        Class<?> listClass = TypeUtil.getClass(listField);

        assert tClass == Object.class;
        assert vClass == Number.class;
        assert iClass == Integer.class;
        assert listClass == List.class;

        assert "Object".equals(tClass.getSimpleName());
        assert "Number".equals(vClass.getSimpleName());
        assert "Integer".equals(iClass.getSimpleName());
        assert "List".equals(listClass.getSimpleName());

        Field tFieldImpl = TypeTestImpl.class.getField("t");
        Field vFieldImpl = TypeTestImpl.class.getField("v");
        Field iFieldImpl = TypeTestImpl.class.getField("i");
        Field listFieldImpl = TypeTestImpl.class.getField("list");
        Class<?> tClassImpl = TypeUtil.getClass(tFieldImpl);
        Class<?> vClassImpl = TypeUtil.getClass(vFieldImpl);
        Class<?> iClassImpl = TypeUtil.getClass(iFieldImpl);
        Class<?> listClassImpl = TypeUtil.getClass(listFieldImpl);

        assert tClassImpl == Object.class;
        assert vClassImpl == Number.class;
        assert iClassImpl == Integer.class;
        assert listClassImpl == List.class;

        assert "Object".equals(tClassImpl.getSimpleName());
        assert "Number".equals(vClassImpl.getSimpleName());
        assert "Integer".equals(iClassImpl.getSimpleName());
        assert "List".equals(listClassImpl.getSimpleName());
    }


    @Test
    public void getParamType() throws NoSuchMethodException {
        /*
        class TypeTest<T, V extends @Custom Number & Serializable> {
            public <Y extends T> T method(Y y,Integer i) {
                t = y;
                return t;
            }
        }
         */
        Method[] method = TypeTest.class.getMethods();

        Type firstParamType = TypeUtil.getFirstParamType(method[0]);
        assert firstParamType instanceof TypeVariable;
        assert "Y".equals(firstParamType.getTypeName());

        Type secondParamType = TypeUtil.getParamType(method[0],1);
        assert secondParamType instanceof Class;
        assert "Integer".equals(((Class) secondParamType).getSimpleName());

        Type[] types = TypeUtil.getParamTypes(method[0]);
        assert 2 == types.length;
        assert types[0] instanceof TypeVariable;
        assert types[1] instanceof Class;
    }


    @Test
    public void getParamClass() throws NoSuchMethodException {
        /*
        class TypeTest<T, V extends @Custom Number & Serializable> {
            public <Y extends T> T method(Y y,Integer i) {
                t = y;
                return t;
            }
        }
         */
        Method[] method = TypeTest.class.getMethods();

        Class<?> firstParamType = TypeUtil.getFirstParamClass(method[0]);
        assert firstParamType == Object.class;
        assert "Object".equals(firstParamType.getSimpleName());

        Class<?> secondParamClass = TypeUtil.getParamClass(method[0],1);
        assert secondParamClass instanceof Class;
        assert "Integer".equals(secondParamClass.getSimpleName());

        Class<?>[] classes = TypeUtil.getParamClasses(method[0]);
        assert 2 == classes.length;
        assert classes[0] == Object.class;
        assert classes[1] == Integer.class;
    }

    @Test
    public void getReturnType() {
        /*
        class TypeTest<T, V extends @Custom Number & Serializable> {
            public <Y extends T> T method(Y y,Integer i) {
                t = y;
                return t;
            }
        }
         */
        Method[] method = TypeTest.class.getMethods();
        Type returnType = TypeUtil.getReturnType(method[0]);
        assert returnType instanceof TypeVariable;
    }


    @Test
    public void getReturnClass() {
        /*
        class TypeTest<T, V extends @Custom Number & Serializable> {
            public <Y extends T> T method(Y y,Integer i) {
                t = y;
                return t;
            }
        }
         */
        Method[] method = TypeTest.class.getMethods();
        Class<?> returnClass = TypeUtil.getReturnClass(method[0]);
        assert returnClass == Object.class;
    }

    @Test
    public void getTypeArgumentTest() throws NoSuchFieldException {

        /*
        class TypeTestImpl extends TypeTest<String, Integer>{
            public <X extends Number> TypeTestImpl(X x, String s) {
                super(x, s);
            }
        }
         */
        // 被检查的类型，必须是已经确定泛型类型的类型
        Type typeFirstArgument = TypeUtil.getTypeFirstArgument(TypeTestImpl.class);
        assert typeFirstArgument instanceof Class;
        assert typeFirstArgument == String.class;

        Type typeSecondArgument = TypeUtil.getTypeArgument(TypeTestImpl.class,1);
        assert typeSecondArgument instanceof Class;
        assert typeSecondArgument == Integer.class;

        Type[] types = TypeUtil.getTypeArguments(TypeTestImpl.class);
        assert 2 == types.length;
    }

    @Test
    public void toParameterizedType() {
        Type type = TypeUtil.toParameterizedType(TypeTestImpl.class);
        assert type instanceof ParameterizedType;
        log.error(type.getTypeName());
        assert "io.niufen.common.util.TypeTest<java.lang.String, java.lang.Integer>".equals(type.getTypeName());
        assert 2 == ((ParameterizedType) type).getActualTypeArguments().length;
    }

    @Test
    public void getActualType() {
//        TODO
    }

    @Test
    public void typeVariable() throws NoSuchFieldException {
        Field v = TypeTest.class.getField("v");
        //获取熟悉类型
        TypeVariable typeVariable = (TypeVariable) v.getGenericType();
        log.debug("typeVariable.toString():{}", typeVariable.toString());

        //获取类型变量上界
        for (Type type : Arrays.asList(typeVariable.getBounds())) {
            log.debug("type:{}", type.getTypeName());
        }

        //获取类型变量声明载体
        log.debug("typeVariable.getGenericDeclaration():{}", typeVariable.getGenericDeclaration());

        //1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
        AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();
        for (AnnotatedType annotatedType : Arrays.asList(annotatedTypes)) {
            for (Annotation annotation : annotatedType.getAnnotations()) {
                log.debug(annotation.annotationType().getName());
            }
        }
    }

    @Test
    public void typeVariable2() throws NoSuchFieldException {
        //****************************TypeVariable************************
        Field v = TypeTest.class.getField("v");//用反射的方式获取属性 public V v;
        TypeVariable typeVariable = (TypeVariable) v.getGenericType();//获取属性类型
        System.out.println("TypeVariable1:" + typeVariable);
        // 获取类型变量上界
        System.out.println("TypeVariable2:" + Arrays.asList(typeVariable.getBounds()));
        System.out.println("TypeVariable3:" + typeVariable.getGenericDeclaration());//获取类型变量声明载体

        //1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
        AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();
        System.out.println("TypeVariable4:" + Arrays.asList(annotatedTypes) + " : " +
                Arrays.asList(annotatedTypes[0].getAnnotations()));
        System.out.println("TypeVariable5:" + typeVariable.getName());
    }

    /**
     * 先使用反射获取TypeTest类的List<T> list 和Map<String, T> map属性的类型，
     * 然后调用getGenericType()获取他们的声明类型，他们是ParameterizedType类型，然后调用里面的方法。
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void parameterizedType() throws NoSuchFieldException {
        //*********************************ParameterizedType**********************************************
        // public List<T>[] ltArray;
        Field list = TypeTest.class.getField("list");
        Type genericType1 = list.getGenericType();
        // 参数类型1:java.util.List<T>
        System.out.println("参数类型1:" + genericType1.getTypeName());

        // public Map<String, T> map = new HashMap<>();
        Field map = TypeTest.class.getField("map");
        Type genericType2 = map.getGenericType();
        // 参数类型2:java.util.Map<java.lang.String, T>
        System.out.println("参数类型2:" + genericType2.getTypeName());


        if (genericType2 instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType2;
            Type[] types = pType.getActualTypeArguments();
            //参数类型列表:[class java.lang.String, T]
            System.out.println("参数类型列表:" + Arrays.asList(types));
            //参数原始类型:interface java.util.Map
            System.out.println("参数原始类型:" + pType.getRawType());
            //参数父类类型:null,因为Map没有外部类，所以为null
            System.out.println("参数父类类型:" + pType.getOwnerType());
        }
    }

    @Test
    public void genericArrayType() throws NoSuchFieldException {
        //**********************GenericArrayType*********************
        Field tArray = TypeTest.class.getField("tArray");
        System.out.println("数组参数类型1:" + tArray.getGenericType());
        Field ltArray = TypeTest.class.getField("ltArray");
        System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
        if (tArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
            System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
        }
    }

    @Test
    public void wildcardType() throws NoSuchFieldException {
        // ***************************WildcardType*********************************
        // public Map<? super String, ? extends Number> mapWithWildcard;
        Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
        Type wild = mapWithWildcard.getGenericType();
        // 先获取属性的泛型类型 Map<? super String, ? extends Number>
        if (wild instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) wild;
            //获取<>里面的参数变量 ? super String, ? extends Number
            Type[] actualTypes = pType.getActualTypeArguments();
            System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
            //? super java.lang.String
            WildcardType first = (WildcardType) actualTypes[0];
            //? extends java.lang.Number
            WildcardType second = (WildcardType) actualTypes[1];
            //WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
            System.out.println("WildcardType2: lower:" + Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));
            //WildcardType3: lower:[]  upper:[class java.lang.Number]
            System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));
        }
    }

    /**
     * 可以看到 属性 public TypeTest testClass;
     * 通过getGenericType()获取到的类型就是其原始类型TypeTest。
     * 而属性 public TypeTest<T, Integer> testClass2;
     * 获取到的则是ParameterizedType类型TypeTest<T, java.lang.Integer>。
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void classTest() throws NoSuchFieldException {
        //**********************************Class*********************************
        Field tClass = TypeTest.class.getField("testClass");
        System.out.println("Class1:" + tClass.getGenericType());//获取泛型类型，由于我们这个属性声明时候没有使用泛型，所以会获得原始类型
        Field tClass2 = TypeTest.class.getField("testClass2");
        System.out.println("Class2:" + tClass2.getGenericType());//获取泛型类型


    }
}

class TypeTestImpl extends TypeTest<String, Integer>{
    public <X extends Number> TypeTestImpl(X x, String s) {
        super(x, s);
    }
}

/**
 * 示例类TypeTest 是一个泛型类，
 * 声明了两个泛型参数T 和 V，一个构造函数和一个泛型方法以及若干属性。
 * 如果你看不懂下面这个示例类，说明你对Java 泛型的了解还停留在初级阶段，
 *
 * @param <T>
 * @param <V>
 */
class TypeTest<T, V extends @Custom Number & Serializable> {
    private Number number;
    public T t;
    public V v;
    public Integer i;
    public List<T> list = new ArrayList<>();
    public Map<String, T> map = new HashMap<>();

    public T[] tArray;
    public List<T>[] ltArray;

    public TypeTest testClass;
    public TypeTest<T, Integer> testClass2;

    public Map<? super String, ? extends Number> mapWithWildcard;

    // 泛型构造函数，泛型参数为X
    public <X extends Number> TypeTest(X x, T t) {
        number = x;
        this.t = t;
    }

    // 泛型方法，泛型参数为Y
    public <Y extends T> T method(Y y,Integer i) {
        t = y;
        return t;
    }


}

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
        ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE,
        ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@interface Custom {

}
