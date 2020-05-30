package io.niufen.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ArrayUtilsTest {

    @Test
    public void isEmpty() {
        Object[] nullArray = null;
        assert ArrayUtils.isEmpty(nullArray);
        Object[] emptyArray = {};
        assert ArrayUtils.isEmpty(emptyArray);
        Object o = null;
        assert ArrayUtils.isEmpty(o);

        char[] charNullArray = null;
        char[] charEmptyArray = {};
        assert ArrayUtils.isEmpty(charNullArray);
        assert ArrayUtils.isEmpty(charEmptyArray);

        boolean[] booleanNullArray = null;
        boolean[] booleanEmptyArray = {};
        assert ArrayUtils.isEmpty(booleanNullArray);
        assert ArrayUtils.isEmpty(booleanEmptyArray);

        byte[] byteNullArray = null;
        byte[] byteEmptyArray = {};
        assert ArrayUtils.isEmpty(byteNullArray);
        assert ArrayUtils.isEmpty(byteEmptyArray);

        short[] shortNullArray = null;
        short[] shortEmptyArray = {};
        assert ArrayUtils.isEmpty(shortNullArray);
        assert ArrayUtils.isEmpty(shortEmptyArray);

        int[] intNullArray = null;
        int[] intEmptyArray = {};
        assert ArrayUtils.isEmpty(intNullArray);
        assert ArrayUtils.isEmpty(intEmptyArray);

        long[] longNullArray = null;
        long[] longEmptyArray = {};
        assert ArrayUtils.isEmpty(longNullArray);
        assert ArrayUtils.isEmpty(longEmptyArray);

        float[] floatNullArray = null;
        float[] floatEmptyArray = {};
        assert ArrayUtils.isEmpty(floatNullArray);
        assert ArrayUtils.isEmpty(floatEmptyArray);

        double[] doubleNullArray = null;
        double[] doubleEmptyArray = {};
        assert ArrayUtils.isEmpty(doubleNullArray);
        assert ArrayUtils.isEmpty(doubleEmptyArray);
    }

    @Test
    public void isNotEmpty() {
        Object[] notEmptyArray = {new Object(), new Object()};
        assert ArrayUtils.isNotEmpty(notEmptyArray);

        Object o = notEmptyArray;
        assert ArrayUtils.isNotEmpty(o);

        char[] charNotEmptyArray = {'1', '2'};
        assert ArrayUtils.isNotEmpty(charNotEmptyArray);

        boolean[] booleanNotEmptyArray = {Boolean.FALSE, true};
        assert ArrayUtils.isNotEmpty(booleanNotEmptyArray);

        byte[] byteNotEmptyArray = {1, 2};
        assert ArrayUtils.isNotEmpty(byteNotEmptyArray);

        short[] shortNotEmptyArray = {1, 2};
        assert ArrayUtils.isNotEmpty(shortNotEmptyArray);

        int[] intNotEmptyArray = {1, 2};
        assert ArrayUtils.isNotEmpty(intNotEmptyArray);

        long[] longNotEmptyArray = {1L, 2L};
        assert ArrayUtils.isNotEmpty(longNotEmptyArray);

        float[] floatNotEmptyArray = {1.0f, 2.0f};
        assert ArrayUtils.isNotEmpty(floatNotEmptyArray);

        double[] doubleNotEmptyArray = {1.0, 2.0};
        assert ArrayUtils.isNotEmpty(doubleNotEmptyArray);
    }

    @Test
    public void defaultIfEmpty() {
        String[] array = {"a", "b"};
        String[] defaultArray = {};
        assert array == ArrayUtils.defaultIfEmpty(array, defaultArray);

        String[] array2 = null;
        String[] defaultArray2 = {};
        assert defaultArray2 == ArrayUtils.defaultIfEmpty(array2, defaultArray2);

        String[] array3 = {};
        String[] defaultArray3 = {"1"};
        assert defaultArray3 == ArrayUtils.defaultIfEmpty(array3, defaultArray3);
    }

    @Test
    public void hasNull() {
        Integer[] array = {1, 2, 3, null};
        Integer[] notHasNull = {1, 2, 3};
        Integer[] nullArray = null;
        Integer[] emptyArray = {};
        assert ArrayUtils.hasNull(array);
        assert !ArrayUtils.hasNull(notHasNull);
        assert !ArrayUtils.hasNull(nullArray);
        assert !ArrayUtils.hasNull(emptyArray);

        assert !ArrayUtils.notHasNull(array);
        assert ArrayUtils.notHasNull(notHasNull);
        assert ArrayUtils.notHasNull(nullArray);
        assert ArrayUtils.notHasNull(emptyArray);
    }

    @Test
    public void firstNotNull() {
        Integer[] array = {null, 2, 3, null};
        assert 2 == ArrayUtils.firstNotNull(array);

        Integer[] array2 = {null, null};
        assert null == ArrayUtils.firstNotNull(array2);
    }

    @Test
    public void newArray() {
        String[] strArray = ArrayUtils.newArray(String.class, 2);
        assert 2 == strArray.length;
        assert null == strArray[0];
        assert null == strArray[1];
        strArray[0] = "a";
        strArray[1] = "b";
        try {
            strArray[2] = "b";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert "[a, b]".equals(ArrayUtils.toString(strArray));
        }

        Object[] objArray = ArrayUtils.newArray(2);
        assert 2 == objArray.length;
        assert null == objArray[0];
        assert null == objArray[1];
        objArray[0] = "a";
        objArray[1] = 1;
        try {
            objArray[2] = "b";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert "[a, 1]".equals(ArrayUtils.toString(objArray));
        }

    }

    @Test
    public void getComponentType() {
        String[] strArray = {"a", "b"};
        Object[] objArray = {"a", 1};
        assert String.class == ArrayUtils.getComponentType(strArray);
        assert Object.class == ArrayUtils.getComponentType(objArray);
        assert String.class == ArrayUtils.getComponentType(strArray.getClass());
        assert Object.class == ArrayUtils.getComponentType(objArray.getClass());
    }

    @Test
    public void getArrayType() {
        Class<?> arrayType = ArrayUtils.getArrayType(int.class);
        assert int[].class == arrayType;

        arrayType = ArrayUtils.getArrayType(String.class);
        assert String[].class == arrayType;
    }

    @Test
    public void toStringTest() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        log.debug("iArray print:{}", iArray); // [0, 1, 2, 3]
        log.debug("oArray print:{}", oArray); // 1
        log.debug("ArrayUtils.toString(iArray):{}", ArrayUtils.toString(iArray));
        log.debug("ArrayUtils.toString(oArray):{}", ArrayUtils.toString(oArray));
        assert "[0, 1, 2, 3]".equals(ArrayUtils.toString(iArray));
        assert "[1, 1, 1.2, false]".equals(ArrayUtils.toString(oArray));

    }

    @Test
    public void isArray() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        assert ArrayUtils.isArray(iArray);
        assert ArrayUtils.isArray(oArray);
        assert !ArrayUtils.isArray(new Object());
    }
}
