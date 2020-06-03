package io.niufen.common.core.util;

import io.niufen.common.core.lang.Editor;
import io.niufen.common.core.lang.Filter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

@Slf4j
public class ArrayUtilTest {

    @Test
    public void isEmpty() {
        Object[] nullArray = null;
        assert ArrayUtil.isEmpty(nullArray);
        Object[] emptyArray = {};
        assert ArrayUtil.isEmpty(emptyArray);
        Object o = null;
        assert ArrayUtil.isEmpty(o);

        char[] charNullArray = null;
        char[] charEmptyArray = {};
        assert ArrayUtil.isEmpty(charNullArray);
        assert ArrayUtil.isEmpty(charEmptyArray);

        boolean[] booleanNullArray = null;
        boolean[] booleanEmptyArray = {};
        assert ArrayUtil.isEmpty(booleanNullArray);
        assert ArrayUtil.isEmpty(booleanEmptyArray);

        byte[] byteNullArray = null;
        byte[] byteEmptyArray = {};
        assert ArrayUtil.isEmpty(byteNullArray);
        assert ArrayUtil.isEmpty(byteEmptyArray);

        short[] shortNullArray = null;
        short[] shortEmptyArray = {};
        assert ArrayUtil.isEmpty(shortNullArray);
        assert ArrayUtil.isEmpty(shortEmptyArray);

        int[] intNullArray = null;
        int[] intEmptyArray = {};
        assert ArrayUtil.isEmpty(intNullArray);
        assert ArrayUtil.isEmpty(intEmptyArray);

        long[] longNullArray = null;
        long[] longEmptyArray = {};
        assert ArrayUtil.isEmpty(longNullArray);
        assert ArrayUtil.isEmpty(longEmptyArray);

        float[] floatNullArray = null;
        float[] floatEmptyArray = {};
        assert ArrayUtil.isEmpty(floatNullArray);
        assert ArrayUtil.isEmpty(floatEmptyArray);

        double[] doubleNullArray = null;
        double[] doubleEmptyArray = {};
        assert ArrayUtil.isEmpty(doubleNullArray);
        assert ArrayUtil.isEmpty(doubleEmptyArray);
    }

    @Test
    public void isNotEmpty() {
        Object[] notEmptyArray = {new Object(), new Object()};
        assert ArrayUtil.isNotEmpty(notEmptyArray);

        Object o = notEmptyArray;
        assert ArrayUtil.isNotEmpty(o);

        char[] charNotEmptyArray = {'1', '2'};
        assert ArrayUtil.isNotEmpty(charNotEmptyArray);

        boolean[] booleanNotEmptyArray = {Boolean.FALSE, true};
        assert ArrayUtil.isNotEmpty(booleanNotEmptyArray);

        byte[] byteNotEmptyArray = {1, 2};
        assert ArrayUtil.isNotEmpty(byteNotEmptyArray);

        short[] shortNotEmptyArray = {1, 2};
        assert ArrayUtil.isNotEmpty(shortNotEmptyArray);

        int[] intNotEmptyArray = {1, 2};
        assert ArrayUtil.isNotEmpty(intNotEmptyArray);

        long[] longNotEmptyArray = {1L, 2L};
        assert ArrayUtil.isNotEmpty(longNotEmptyArray);

        float[] floatNotEmptyArray = {1.0f, 2.0f};
        assert ArrayUtil.isNotEmpty(floatNotEmptyArray);

        double[] doubleNotEmptyArray = {1.0, 2.0};
        assert ArrayUtil.isNotEmpty(doubleNotEmptyArray);
    }

    @Test
    public void defaultIfEmpty() {
        String[] array = {"a", "b"};
        String[] defaultArray = {};
        assert array == ArrayUtil.defaultIfEmpty(array, defaultArray);

        String[] array2 = null;
        String[] defaultArray2 = {};
        assert defaultArray2 == ArrayUtil.defaultIfEmpty(array2, defaultArray2);

        String[] array3 = {};
        String[] defaultArray3 = {"1"};
        assert defaultArray3 == ArrayUtil.defaultIfEmpty(array3, defaultArray3);
    }

    @Test
    public void hasNull() {
        Integer[] array = {1, 2, 3, null};
        Integer[] notHasNull = {1, 2, 3};
        Integer[] nullArray = null;
        Integer[] emptyArray = {};
        assert ArrayUtil.hasNull(array);
        assert !ArrayUtil.hasNull(notHasNull);
        assert !ArrayUtil.hasNull(nullArray);
        assert !ArrayUtil.hasNull(emptyArray);

    }

    @Test
    public void firstNotNull() {
        Integer[] array = {null, 2, 3, null};

        Integer[] array2 = {null, null};
    }

    @Test
    public void newArray() {
        String[] strArray = ArrayUtil.newArray(String.class, 2);
        assert 2 == strArray.length;
        assert null == strArray[0];
        assert null == strArray[1];
        strArray[0] = "a";
        strArray[1] = "b";
        try {
            strArray[2] = "b";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert "[a, b]".equals(ArrayUtil.toString(strArray));
        }

        Object[] objArray = ArrayUtil.newArray(2);
        assert 2 == objArray.length;
        assert null == objArray[0];
        assert null == objArray[1];
        objArray[0] = "a";
        objArray[1] = 1;
        try {
            objArray[2] = "b";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert "[a, 1]".equals(ArrayUtil.toString(objArray));
        }

    }

    @Test
    public void getComponentType() {
        String[] strArray = {"a", "b"};
        Object[] objArray = {"a", 1};
        assert String.class == ArrayUtil.getComponentType(strArray);
        assert Object.class == ArrayUtil.getComponentType(objArray);
        assert String.class == ArrayUtil.getComponentType(strArray.getClass());
        assert Object.class == ArrayUtil.getComponentType(objArray.getClass());
    }

    @Test
    public void getArrayType() {
        Class<?> arrayType = ArrayUtil.getArrayType(int.class);
        assert int[].class == arrayType;

        arrayType = ArrayUtil.getArrayType(String.class);
        assert String[].class == arrayType;
    }

    @Test
    public void cast() {
        Object[] objects = {1, 2};
        Integer[] intgers = (Integer[]) ArrayUtil.cast(Integer.class, objects);
        assert 2 == intgers.length;
        assert intgers[0] == 1;
        assert intgers[1] == 2;
    }

    @Test
    public void append() {
        String[] array = {"1", "2"};
        String[] newArray = ArrayUtil.append(array, "3", "4");
        log.debug("newArray:{}", ArrayUtil.toString(newArray));
    }

    @Test
    public void setOrAppend() {
        String[] array = {"1", "2", null};
        String[] newArray = ArrayUtil.setOrAppend(array, 2, "3");
        assert "3".equals(newArray[2]);
    }

    @Test
    public void resize() {
        String[] array = {"1", "2"};
        String[] newArray = ArrayUtil.resize(array, 10);
        assert 10 == newArray.length;
        newArray = ArrayUtil.resize(array, 1);
        assert 1 == newArray.length;
        assert "1".equals(array[0]);
    }

    @Test
    public void insert() {
        int index = -13;
        int arrayLen = 10;
        log.debug("index % arrayLen:{}", index % arrayLen);
        index = (index % arrayLen) + arrayLen;
        log.debug("index:{}", index);

        String[] array = {"1", "2"};
        String[] insertArray = (String[]) ArrayUtil.insert(array, 1, "a", "b");
        log.debug(ArrayUtil.toString(insertArray));
        assert 4 == insertArray.length;

        insertArray = (String[]) ArrayUtil.insert(array, 2, "a", "b");
        log.debug(ArrayUtil.toString(insertArray));
        assert 4 == insertArray.length;

        insertArray = (String[]) ArrayUtil.insert(array, 3, "a", "b");
        log.debug(ArrayUtil.toString(insertArray));
        assert 5 == insertArray.length;

        String[] array2 = {"1", "2", "3", "4", "5"};
        insertArray = (String[]) ArrayUtil.insert(array2, -2, "a", "b");
        log.debug(ArrayUtil.toString(insertArray));
        assert 7 == insertArray.length;
    }

    @Test
    public void addAll() {
        String[] a = {"a1", "a2", null};
        String[] b = null;
        String[] c = {"c1", "c2", null};
        String[] array = ArrayUtil.addAll(a, b, c);
        assert 6 == array.length;
        assert "a1".equals(array[0]);
        assert "a2".equals(array[1]);
        assert null == array[2];
        assert "c1".equals(array[3]);
        assert "c2".equals(array[4]);
        assert null == array[5];

        int[] i = {1, 2};
        int[] i2 = null;
        int[] i3 = {4, 5, 6};
        int[] iAll = ArrayUtil.addAll(i, i2, i3);
    }

    @Test
    public void copy() {
        String[] a = {"1", "2"};
        String[] b = new String[2];
        String[] copy = (String[]) ArrayUtil.copy(a, b, 1);
        log.debug("ArrayUtils.toString(copy):{}", ArrayUtil.toString(copy));
        assert 2 == copy.length;
        assert "1".equals(copy[0]);
        assert null == copy[1];


        String[] copy2 = (String[]) ArrayUtil.copy(a, b, 2);
        log.debug("ArrayUtils.toString(copy):{}", ArrayUtil.toString(copy));
        assert 2 == copy.length;
        assert "1".equals(copy[0]);
        assert null == copy[1];
    }

    @Test
    public void cloneTest() {
        String[] a = {"1", "2"};
        String[] b = ArrayUtil.clone(a);
        assert a != b;
        assert a[0].equals(b[0]);
        assert a[1].equals(b[1]);
    }

    @Test
    public void range() {
        int[] range = ArrayUtil.range(5);
        assert 5 == range.length;
        assert 0 == range[0];
        assert 4 == range[4];

        int[] range2 = ArrayUtil.range(1, 6, 2);
        assert 3 == range2.length;
        assert 1 == range2[0];
        assert 3 == range2[1];

    }

    @Test
    public void split() {
        byte[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[][] arraySplit = ArrayUtil.split(array, 3);
        assert 4 == arraySplit.length;
        assert arraySplit[0][0] == 1;
        assert arraySplit[3][1] == 0;
    }


    @Test
    public void filter() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Integer[] filter = ArrayUtil.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assert.assertArrayEquals(filter, new Integer[]{2, 4, 6});


        Integer[] b = {1, 2, 3, 4, 5, 6};
        Integer[] filterB = ArrayUtil.filter(b, (Filter<Integer>) t -> (t % 2 == 0));
        Assert.assertArrayEquals(filterB, new Integer[]{2, 4, 6});
    }

    @Test
    public void edit() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        ArrayUtil.edit(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assert.assertArrayEquals(a, new Integer[]{null, 2, null, 4, null, 6});
    }

    @Test
    public void removeNull() {
        Integer[] iArray = {1, 2, 3, 4, null};
        Integer[] integers = ArrayUtil.removeNull(iArray);
        Assert.assertArrayEquals(integers, new Integer[]{1, 2, 3, 4});
    }

    @Test
    public void removeEmpty() {
        String[] sArray = {"1", "2", "3", "", null};
        String[] newArray = ArrayUtil.removeEmpty(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3"});
    }

    @Test
    public void removeBlank() {
        String[] sArray = {"1", "2", "3", " ", null};
        String[] newArray = ArrayUtil.removeBlank(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3"});
    }

    @Test
    public void nullToEmpty() {
        String[] sArray = {"1", "2", "3", " ", null};
        String[] newArray = ArrayUtil.nullToEmpty(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3", " ", ""});
    }

    @Test
    public void zip() {
        String[] keys = {"a", "b", "c"};
        String[] values = {"1", "2", "3"};
        Map<String, String> map = ArrayUtil.zip(keys, values);
        assert 3 == map.size();
        assert "1".equals(map.get("a"));
        assert "2".equals(map.get("b"));
        assert "3".equals(map.get("c"));
    }

    @Test
    public void indexOf() {
        String[] strArray = {"a", "b", "c"};
        assert -1 == ArrayUtil.indexOf(strArray, "d");
        assert 0 == ArrayUtil.indexOf(strArray, "a");
        assert 1 == ArrayUtil.indexOf(strArray, "b");
        assert 2 == ArrayUtil.indexOf(strArray, "c");


        char[] charArray = {'a', 'b', 'c'};
        assert -1 == ArrayUtil.indexOf(charArray, 'd');
        assert 0 == ArrayUtil.indexOf(charArray, 'a');
        assert 1 == ArrayUtil.indexOf(charArray, 'b');
        assert 2 == ArrayUtil.indexOf(charArray, 'c');

        byte[] byteArray = {1, 2, 3};
        assert -1 == ArrayUtil.indexOf(byteArray, (byte) 0);
        assert 0 == ArrayUtil.indexOf(byteArray, (byte) 1);
        assert 1 == ArrayUtil.indexOf(byteArray, (byte) 2);
        assert 2 == ArrayUtil.indexOf(byteArray, (byte) 3);

        short[] shortArray = {1, 2, 3};
        assert -1 == ArrayUtil.indexOf(shortArray, (short) 0);
        assert 0 == ArrayUtil.indexOf(shortArray, (short) 1);
        assert 1 == ArrayUtil.indexOf(shortArray, (short) 2);
        assert 2 == ArrayUtil.indexOf(shortArray, (short) 3);

        int[] intArray = {1, 2, 3};
        assert -1 == ArrayUtil.indexOf(intArray, 0);
        assert 0 == ArrayUtil.indexOf(intArray, 1);
        assert 1 == ArrayUtil.indexOf(intArray, 2);
        assert 2 == ArrayUtil.indexOf(intArray, 3);

        long[] longArray = {1, 2, 3};
        assert -1 == ArrayUtil.indexOf(longArray, 0L);
        assert 0 == ArrayUtil.indexOf(longArray, 1L);
        assert 1 == ArrayUtil.indexOf(longArray, 2L);
        assert 2 == ArrayUtil.indexOf(longArray, 3L);

        float[] floatArray = {1f, 2f, 3f};
        assert -1 == ArrayUtil.indexOf(floatArray, 0f);
        assert 0 == ArrayUtil.indexOf(floatArray, 1f);
        assert 1 == ArrayUtil.indexOf(floatArray, 2f);
        assert 2 == ArrayUtil.indexOf(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0};
        assert -1 == ArrayUtil.indexOf(doubleArray, 0.0);
        assert 0 == ArrayUtil.indexOf(doubleArray, 1.0);
        assert 1 == ArrayUtil.indexOf(doubleArray, 2.0);
        assert 2 == ArrayUtil.indexOf(doubleArray, 3.0);
    }

    @Test
    public void lastIndexOf() {
        String[] strArray = {"a", "b", "c", "a"};
        assert 3 == ArrayUtil.lastIndexOf(strArray, "a");
        assert 2 == ArrayUtil.lastIndexOf(strArray, "c");
        assert 1 == ArrayUtil.lastIndexOf(strArray, "b");
        assert -1 == ArrayUtil.lastIndexOf(strArray, "z");


        char[] charArray = {'a', 'b', 'c', 'a'};
        assert -1 == ArrayUtil.lastIndexOf(charArray, 'z');
        assert 3 == ArrayUtil.lastIndexOf(charArray, 'a');
        assert 2 == ArrayUtil.lastIndexOf(charArray, 'c');
        assert 1 == ArrayUtil.lastIndexOf(charArray, 'b');

        byte[] byteArray = {1, 2, 3, 1};
        assert -1 == ArrayUtil.lastIndexOf(byteArray, (byte) 0);
        assert 3 == ArrayUtil.lastIndexOf(byteArray, (byte) 1);
        assert 2 == ArrayUtil.lastIndexOf(byteArray, (byte) 3);
        assert 1 == ArrayUtil.lastIndexOf(byteArray, (byte) 2);

        short[] shortArray = {1, 2, 3, 1};
        assert -1 == ArrayUtil.lastIndexOf(shortArray, (short) 0);
        assert 3 == ArrayUtil.lastIndexOf(shortArray, (short) 1);
        assert 1 == ArrayUtil.lastIndexOf(shortArray, (short) 2);
        assert 2 == ArrayUtil.lastIndexOf(shortArray, (short) 3);

        int[] intArray = {1, 2, 3, 1};
        assert -1 == ArrayUtil.lastIndexOf(intArray, 0);
        assert 3 == ArrayUtil.lastIndexOf(intArray, 1);
        assert 1 == ArrayUtil.lastIndexOf(intArray, 2);
        assert 2 == ArrayUtil.lastIndexOf(intArray, 3);

        long[] longArray = {1, 2, 3, 1};
        assert -1 == ArrayUtil.lastIndexOf(longArray, 0L);
        assert 3 == ArrayUtil.lastIndexOf(longArray, 1L);
        assert 1 == ArrayUtil.lastIndexOf(longArray, 2L);
        assert 2 == ArrayUtil.lastIndexOf(longArray, 3L);

        float[] floatArray = {1f, 2f, 3f, 1f};
        assert -1 == ArrayUtil.lastIndexOf(floatArray, 0f);
        assert 3 == ArrayUtil.lastIndexOf(floatArray, 1f);
        assert 1 == ArrayUtil.lastIndexOf(floatArray, 2f);
        assert 2 == ArrayUtil.lastIndexOf(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0, 1.0};
        assert -1 == ArrayUtil.lastIndexOf(doubleArray, 0.0);
        assert 3 == ArrayUtil.lastIndexOf(doubleArray, 1.0);
        assert 1 == ArrayUtil.lastIndexOf(doubleArray, 2.0);
        assert 2 == ArrayUtil.lastIndexOf(doubleArray, 3.0);
    }

    @Test
    public void indexOfIgnoreCase() {
        String[] strArray = {"a", "b", "c", "A"};
        assert 0 == ArrayUtil.indexOfIgnoreCase(strArray, "A");
        assert 1 == ArrayUtil.indexOfIgnoreCase(strArray, "B");
        assert 2 == ArrayUtil.indexOfIgnoreCase(strArray, "C");
        assert -1 == ArrayUtil.indexOfIgnoreCase(strArray, "z");
    }

    @Test
    public void contains() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtil.contains(strArray, "a");
        assert !ArrayUtil.contains(strArray, "z");


        char[] charArray = {'a', 'b', 'c', 'a'};
        assert ArrayUtil.contains(charArray, 'a');
        assert !ArrayUtil.contains(charArray, 'z');

        byte[] byteArray = {1, 2, 3, 1};
        assert !ArrayUtil.contains(byteArray, (byte) 0);
        assert ArrayUtil.contains(byteArray, (byte) 1);

        short[] shortArray = {1, 2, 3, 1};
        assert !ArrayUtil.contains(shortArray, (short) 0);
        assert ArrayUtil.contains(shortArray, (short) 1);

        int[] intArray = {1, 2, 3, 1};
        assert !ArrayUtil.contains(intArray, 0);
        assert ArrayUtil.contains(intArray, 1);

        long[] longArray = {1, 2, 3, 1};
        assert !ArrayUtil.contains(longArray, 0L);
        assert ArrayUtil.contains(longArray, 1L);

        float[] floatArray = {1f, 2f, 3f, 1f};
        assert !ArrayUtil.contains(floatArray, 0f);
        assert ArrayUtil.contains(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0, 1.0};
        assert !ArrayUtil.contains(doubleArray, 0.0);
        assert ArrayUtil.contains(doubleArray, 3.0);
    }

    @Test
    public void containsAny() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtil.containsAny(strArray, "a", "z");
        assert !ArrayUtil.containsAny(strArray, "z", "k");

        byte[] byteArray = {1, 2, 3, 1};

        int[] intArray = {1, 2, 3, 1};
    }

    @Test
    public void containsIgnoreCase() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtil.containsIgnoreCase(strArray, "A");
        assert !ArrayUtil.containsIgnoreCase(strArray, "z");
    }

    @Test
    public void wrap() {
        byte[] byteArray = {(byte) 1, (byte) 2};
        Byte[] byteWrapArray = new Byte[]{1, 2};
        Assert.assertArrayEquals(byteWrapArray, ArrayUtil.wrap(byteArray));
        Assert.assertArrayEquals(byteArray, ArrayUtil.unWrap(byteWrapArray));

        short[] shortArray = {(short) 1, (short) 2};
        Short[] shortWrapArray = new Short[]{1, 2};
        Assert.assertArrayEquals(shortWrapArray, ArrayUtil.wrap(shortArray));
        Assert.assertArrayEquals(shortArray, ArrayUtil.unWrap(shortWrapArray));

        int[] intArray = {1, 2};
        Integer[] intWrapArray = new Integer[]{1, 2};
        Assert.assertArrayEquals(intWrapArray, ArrayUtil.wrap(intArray));
        Assert.assertArrayEquals(intArray, ArrayUtil.unWrap(intWrapArray));

        long[] longArray = {1, 2};
        Long[] longWrapArray = new Long[]{1L, 2L};
        Assert.assertArrayEquals(longWrapArray, ArrayUtil.wrap(longArray));
        Assert.assertArrayEquals(longArray, ArrayUtil.unWrap(longWrapArray));

        float[] floatArray = {1.0f, 2.0f};
        Float[] floatWrapArray = new Float[]{1.0f, 2.0f};
        Assert.assertArrayEquals(floatWrapArray, ArrayUtil.wrap(floatArray));
        float[] unwrap = ArrayUtil.unWrap(floatWrapArray);
        assert floatArray[0] == unwrap[0];
        assert floatArray[1] == unwrap[1];

        double[] doubleArray = {1.0, 2.0};
        Double[] doubleWrapArray = new Double[]{1.0, 2.0};
        Assert.assertArrayEquals(doubleWrapArray, ArrayUtil.wrap(doubleArray));
        double[] unwrapDouble = ArrayUtil.unWrap(doubleWrapArray);
        assert doubleArray[0] == unwrapDouble[0];
        assert doubleArray[1] == unwrapDouble[1];

        boolean [] booleanArray = {true,false};
        Boolean[] booleanWrapArray = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
        Assert.assertArrayEquals(booleanWrapArray, ArrayUtil.wrap(booleanArray));
        Assert.assertArrayEquals(booleanArray, ArrayUtil.unWrap(booleanWrapArray));


        char [] charArray = {'1','2'};
        Character[] charWrapArray = new Character[]{'1','2'};
        Assert.assertArrayEquals(charWrapArray, ArrayUtil.wrap(charArray));
        Assert.assertArrayEquals(charArray, ArrayUtil.unWrap(charWrapArray));

        Object charArrayObject = charArray;
        Assert.assertArrayEquals(charWrapArray, ArrayUtil.wrap(charArrayObject));
    }

    @Test
    public void get() {
        String [] strArrays = {"1","2"};
        assert "1".equals(ArrayUtil.get(strArrays,0));
        Assert.assertArrayEquals(strArrays, ArrayUtil.getAny(strArrays,0,1));
    }

    @Test
    public void sub() {
        String [] strArrays = {"1","2","3","4"};
        Assert.assertArrayEquals(new String []{"2","3"}, ArrayUtil.sub(strArrays,1,3));
    }

    @Test
    public void join() {
        String [] strArrays = {"1","2","3","4"};
        String str = ArrayUtil.join(strArrays,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);

        str = ArrayUtil.join(strArrays,",","[","]");
        log.debug("str:{}",str);
        assert "[1],[2],[3],[4]".equals(str);

        str = ArrayUtil.join(strArrays,",", t-> t.equals("3")?null:t);
        log.debug("str:{}",str);
        assert "1,2,,4".equals(str);

        long [] longArray = {1L,2L,3L,4L};
        str = ArrayUtil.join(longArray,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);

        int [] intArray = {1,2,3,4};
        str = ArrayUtil.join(intArray,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);
    }

    @Test
    public void toStringTest() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        log.debug("iArray print:{}", iArray); // [0, 1, 2, 3]
        log.debug("oArray print:{}", oArray); // 1
        log.debug("ArrayUtils.toString(iArray):{}", ArrayUtil.toString(iArray));
        log.debug("ArrayUtils.toString(oArray):{}", ArrayUtil.toString(oArray));
        assert "[0, 1, 2, 3]".equals(ArrayUtil.toString(iArray));
        assert "[1, 1, 1.2, false]".equals(ArrayUtil.toString(oArray));

    }

    @Test
    public void isArray() {
        int[] iArray = {0, 1, 2, 3};
        Object[] oArray = {"1", 1, 1.2, Boolean.FALSE};
        assert ArrayUtil.isArray(iArray);
        assert ArrayUtil.isArray(oArray);
        assert !ArrayUtil.isArray(new Object());
    }
}
