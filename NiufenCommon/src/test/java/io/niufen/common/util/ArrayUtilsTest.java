package io.niufen.common.util;

import io.niufen.common.lang.Editor;
import io.niufen.common.lang.Filter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

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
    public void cast() {
        Object[] objects = {1, 2};
        Integer[] intgers = (Integer[]) ArrayUtils.cast(Integer.class, objects);
        assert 2 == intgers.length;
        assert intgers[0] == 1;
        assert intgers[1] == 2;
    }

    @Test
    public void append() {
        String[] array = {"1", "2"};
        String[] newArray = ArrayUtils.append(array, "3", "4");
        log.debug("newArray:{}", ArrayUtils.toString(newArray));
    }

    @Test
    public void setOrAppend() {
        String[] array = {"1", "2", null};
        String[] newArray = ArrayUtils.setOrAppend(array, 2, "3");
        assert "3".equals(newArray[2]);
    }

    @Test
    public void resize() {
        String[] array = {"1", "2"};
        String[] newArray = ArrayUtils.resize(array, 10);
        assert 10 == newArray.length;
        newArray = ArrayUtils.resize(array, 1);
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
        String[] insertArray = (String[]) ArrayUtils.insert(array, 1, "a", "b");
        log.debug(ArrayUtils.toString(insertArray));
        assert 4 == insertArray.length;

        insertArray = (String[]) ArrayUtils.insert(array, 2, "a", "b");
        log.debug(ArrayUtils.toString(insertArray));
        assert 4 == insertArray.length;

        insertArray = (String[]) ArrayUtils.insert(array, 3, "a", "b");
        log.debug(ArrayUtils.toString(insertArray));
        assert 5 == insertArray.length;

        String[] array2 = {"1", "2", "3", "4", "5"};
        insertArray = (String[]) ArrayUtils.insert(array2, -2, "a", "b");
        log.debug(ArrayUtils.toString(insertArray));
        assert 7 == insertArray.length;
    }

    @Test
    public void addAll() {
        String[] a = {"a1", "a2", null};
        String[] b = null;
        String[] c = {"c1", "c2", null};
        String[] array = ArrayUtils.addAll(a, b, c);
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
        int[] iAll = ArrayUtils.addAll(i, i2, i3);
    }

    @Test
    public void copy() {
        String[] a = {"1", "2"};
        String[] b = new String[2];
        String[] copy = (String[]) ArrayUtils.copy(a, b, 1);
        log.debug("ArrayUtils.toString(copy):{}", ArrayUtils.toString(copy));
        assert 2 == copy.length;
        assert "1".equals(copy[0]);
        assert null == copy[1];


        String[] copy2 = (String[]) ArrayUtils.copy(a, b, 2);
        log.debug("ArrayUtils.toString(copy):{}", ArrayUtils.toString(copy));
        assert 2 == copy.length;
        assert "1".equals(copy[0]);
        assert null == copy[1];
    }

    @Test
    public void cloneTest() {
        String[] a = {"1", "2"};
        String[] b = ArrayUtils.clone(a);
        assert a != b;
        assert a[0].equals(b[0]);
        assert a[1].equals(b[1]);
    }

    @Test
    public void range() {
        int[] range = ArrayUtils.range(5);
        assert 5 == range.length;
        assert 0 == range[0];
        assert 4 == range[4];

        int[] range2 = ArrayUtils.range(1, 6, 2);
        assert 3 == range2.length;
        assert 1 == range2[0];
        assert 3 == range2[1];

    }

    @Test
    public void split() {
        byte[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[][] arraySplit = ArrayUtils.split(array, 3);
        assert 4 == arraySplit.length;
        assert arraySplit[0][0] == 1;
        assert arraySplit[3][1] == 0;
    }


    @Test
    public void filter() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Integer[] filter = ArrayUtils.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assert.assertArrayEquals(filter, new Integer[]{2, 4, 6});


        Integer[] b = {1, 2, 3, 4, 5, 6};
        Integer[] filterB = ArrayUtils.filter(b, (Filter<Integer>) t -> (t % 2 == 0));
        Assert.assertArrayEquals(filterB, new Integer[]{2, 4, 6});
    }

    @Test
    public void edit() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        ArrayUtils.edit(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assert.assertArrayEquals(a, new Integer[]{null, 2, null, 4, null, 6});
    }

    @Test
    public void removeNull() {
        Integer[] iArray = {1, 2, 3, 4, null};
        Integer[] integers = ArrayUtils.removeNull(iArray);
        Assert.assertArrayEquals(integers, new Integer[]{1, 2, 3, 4});
    }

    @Test
    public void removeEmpty() {
        String[] sArray = {"1", "2", "3", "", null};
        String[] newArray = ArrayUtils.removeEmpty(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3"});
    }

    @Test
    public void removeBlank() {
        String[] sArray = {"1", "2", "3", " ", null};
        String[] newArray = ArrayUtils.removeBlank(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3"});
    }

    @Test
    public void nullToEmpty() {
        String[] sArray = {"1", "2", "3", " ", null};
        String[] newArray = ArrayUtils.nullToEmpty(sArray);
        Assert.assertArrayEquals(newArray, new String[]{"1", "2", "3", " ", ""});
    }

    @Test
    public void zip() {
        String[] keys = {"a", "b", "c"};
        String[] values = {"1", "2", "3"};
        Map<String, String> map = ArrayUtils.zip(keys, values);
        assert 3 == map.size();
        assert "1".equals(map.get("a"));
        assert "2".equals(map.get("b"));
        assert "3".equals(map.get("c"));
    }

    @Test
    public void indexOf() {
        String[] strArray = {"a", "b", "c"};
        assert -1 == ArrayUtils.indexOf(strArray, "d");
        assert 0 == ArrayUtils.indexOf(strArray, "a");
        assert 1 == ArrayUtils.indexOf(strArray, "b");
        assert 2 == ArrayUtils.indexOf(strArray, "c");


        char[] charArray = {'a', 'b', 'c'};
        assert -1 == ArrayUtils.indexOf(charArray, 'd');
        assert 0 == ArrayUtils.indexOf(charArray, 'a');
        assert 1 == ArrayUtils.indexOf(charArray, 'b');
        assert 2 == ArrayUtils.indexOf(charArray, 'c');

        byte[] byteArray = {1, 2, 3};
        assert -1 == ArrayUtils.indexOf(byteArray, (byte) 0);
        assert 0 == ArrayUtils.indexOf(byteArray, (byte) 1);
        assert 1 == ArrayUtils.indexOf(byteArray, (byte) 2);
        assert 2 == ArrayUtils.indexOf(byteArray, (byte) 3);

        short[] shortArray = {1, 2, 3};
        assert -1 == ArrayUtils.indexOf(shortArray, (short) 0);
        assert 0 == ArrayUtils.indexOf(shortArray, (short) 1);
        assert 1 == ArrayUtils.indexOf(shortArray, (short) 2);
        assert 2 == ArrayUtils.indexOf(shortArray, (short) 3);

        int[] intArray = {1, 2, 3};
        assert -1 == ArrayUtils.indexOf(intArray, 0);
        assert 0 == ArrayUtils.indexOf(intArray, 1);
        assert 1 == ArrayUtils.indexOf(intArray, 2);
        assert 2 == ArrayUtils.indexOf(intArray, 3);

        long[] longArray = {1, 2, 3};
        assert -1 == ArrayUtils.indexOf(longArray, 0L);
        assert 0 == ArrayUtils.indexOf(longArray, 1L);
        assert 1 == ArrayUtils.indexOf(longArray, 2L);
        assert 2 == ArrayUtils.indexOf(longArray, 3L);

        float[] floatArray = {1f, 2f, 3f};
        assert -1 == ArrayUtils.indexOf(floatArray, 0f);
        assert 0 == ArrayUtils.indexOf(floatArray, 1f);
        assert 1 == ArrayUtils.indexOf(floatArray, 2f);
        assert 2 == ArrayUtils.indexOf(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0};
        assert -1 == ArrayUtils.indexOf(doubleArray, 0.0);
        assert 0 == ArrayUtils.indexOf(doubleArray, 1.0);
        assert 1 == ArrayUtils.indexOf(doubleArray, 2.0);
        assert 2 == ArrayUtils.indexOf(doubleArray, 3.0);
    }

    @Test
    public void lastIndexOf() {
        String[] strArray = {"a", "b", "c", "a"};
        assert 3 == ArrayUtils.lastIndexOf(strArray, "a");
        assert 2 == ArrayUtils.lastIndexOf(strArray, "c");
        assert 1 == ArrayUtils.lastIndexOf(strArray, "b");
        assert -1 == ArrayUtils.lastIndexOf(strArray, "z");


        char[] charArray = {'a', 'b', 'c', 'a'};
        assert -1 == ArrayUtils.lastIndexOf(charArray, 'z');
        assert 3 == ArrayUtils.lastIndexOf(charArray, 'a');
        assert 2 == ArrayUtils.lastIndexOf(charArray, 'c');
        assert 1 == ArrayUtils.lastIndexOf(charArray, 'b');

        byte[] byteArray = {1, 2, 3, 1};
        assert -1 == ArrayUtils.lastIndexOf(byteArray, (byte) 0);
        assert 3 == ArrayUtils.lastIndexOf(byteArray, (byte) 1);
        assert 2 == ArrayUtils.lastIndexOf(byteArray, (byte) 3);
        assert 1 == ArrayUtils.lastIndexOf(byteArray, (byte) 2);

        short[] shortArray = {1, 2, 3, 1};
        assert -1 == ArrayUtils.lastIndexOf(shortArray, (short) 0);
        assert 3 == ArrayUtils.lastIndexOf(shortArray, (short) 1);
        assert 1 == ArrayUtils.lastIndexOf(shortArray, (short) 2);
        assert 2 == ArrayUtils.lastIndexOf(shortArray, (short) 3);

        int[] intArray = {1, 2, 3, 1};
        assert -1 == ArrayUtils.lastIndexOf(intArray, 0);
        assert 3 == ArrayUtils.lastIndexOf(intArray, 1);
        assert 1 == ArrayUtils.lastIndexOf(intArray, 2);
        assert 2 == ArrayUtils.lastIndexOf(intArray, 3);

        long[] longArray = {1, 2, 3, 1};
        assert -1 == ArrayUtils.lastIndexOf(longArray, 0L);
        assert 3 == ArrayUtils.lastIndexOf(longArray, 1L);
        assert 1 == ArrayUtils.lastIndexOf(longArray, 2L);
        assert 2 == ArrayUtils.lastIndexOf(longArray, 3L);

        float[] floatArray = {1f, 2f, 3f, 1f};
        assert -1 == ArrayUtils.lastIndexOf(floatArray, 0f);
        assert 3 == ArrayUtils.lastIndexOf(floatArray, 1f);
        assert 1 == ArrayUtils.lastIndexOf(floatArray, 2f);
        assert 2 == ArrayUtils.lastIndexOf(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0, 1.0};
        assert -1 == ArrayUtils.lastIndexOf(doubleArray, 0.0);
        assert 3 == ArrayUtils.lastIndexOf(doubleArray, 1.0);
        assert 1 == ArrayUtils.lastIndexOf(doubleArray, 2.0);
        assert 2 == ArrayUtils.lastIndexOf(doubleArray, 3.0);
    }

    @Test
    public void indexOfIgnoreCase() {
        String[] strArray = {"a", "b", "c", "A"};
        assert 0 == ArrayUtils.indexOfIgnoreCase(strArray, "A");
        assert 1 == ArrayUtils.indexOfIgnoreCase(strArray, "B");
        assert 2 == ArrayUtils.indexOfIgnoreCase(strArray, "C");
        assert -1 == ArrayUtils.indexOfIgnoreCase(strArray, "z");
    }

    @Test
    public void contains() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtils.contains(strArray, "a");
        assert !ArrayUtils.contains(strArray, "z");


        char[] charArray = {'a', 'b', 'c', 'a'};
        assert ArrayUtils.contains(charArray, 'a');
        assert !ArrayUtils.contains(charArray, 'z');

        byte[] byteArray = {1, 2, 3, 1};
        assert !ArrayUtils.contains(byteArray, (byte) 0);
        assert ArrayUtils.contains(byteArray, (byte) 1);

        short[] shortArray = {1, 2, 3, 1};
        assert !ArrayUtils.contains(shortArray, (short) 0);
        assert ArrayUtils.contains(shortArray, (short) 1);

        int[] intArray = {1, 2, 3, 1};
        assert !ArrayUtils.contains(intArray, 0);
        assert ArrayUtils.contains(intArray, 1);

        long[] longArray = {1, 2, 3, 1};
        assert !ArrayUtils.contains(longArray, 0L);
        assert ArrayUtils.contains(longArray, 1L);

        float[] floatArray = {1f, 2f, 3f, 1f};
        assert !ArrayUtils.contains(floatArray, 0f);
        assert ArrayUtils.contains(floatArray, 3f);

        double[] doubleArray = {1.0, 2.0, 3.0, 1.0};
        assert !ArrayUtils.contains(doubleArray, 0.0);
        assert ArrayUtils.contains(doubleArray, 3.0);
    }

    @Test
    public void containsAny() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtils.containsAny(strArray, "a", "z");
        assert !ArrayUtils.containsAny(strArray, "z", "k");

        byte[] byteArray = {1, 2, 3, 1};
        assert !ArrayUtils.containsAny(byteArray, (byte) 0, (byte) 10);
        assert ArrayUtils.containsAny(byteArray, (byte) 1, (byte) 0);

        int[] intArray = {1, 2, 3, 1};
        assert !ArrayUtils.containsAny(intArray, 0, 5);
        assert ArrayUtils.containsAny(intArray, 1, 5);
    }

    @Test
    public void containsIgnoreCase() {
        String[] strArray = {"a", "b", "c", "a"};
        assert ArrayUtils.containsIgnoreCase(strArray, "A");
        assert !ArrayUtils.containsIgnoreCase(strArray, "z");
    }

    @Test
    public void wrap() {
        byte[] byteArray = {(byte) 1, (byte) 2};
        Byte[] byteWrapArray = new Byte[]{1, 2};
        Assert.assertArrayEquals(byteWrapArray, ArrayUtils.wrap(byteArray));
        Assert.assertArrayEquals(byteArray, ArrayUtils.unWrap(byteWrapArray));

        short[] shortArray = {(short) 1, (short) 2};
        Short[] shortWrapArray = new Short[]{1, 2};
        Assert.assertArrayEquals(shortWrapArray, ArrayUtils.wrap(shortArray));
        Assert.assertArrayEquals(shortArray, ArrayUtils.unWrap(shortWrapArray));

        int[] intArray = {1, 2};
        Integer[] intWrapArray = new Integer[]{1, 2};
        Assert.assertArrayEquals(intWrapArray, ArrayUtils.wrap(intArray));
        Assert.assertArrayEquals(intArray, ArrayUtils.unWrap(intWrapArray));

        long[] longArray = {1, 2};
        Long[] longWrapArray = new Long[]{1L, 2L};
        Assert.assertArrayEquals(longWrapArray, ArrayUtils.wrap(longArray));
        Assert.assertArrayEquals(longArray, ArrayUtils.unWrap(longWrapArray));

        float[] floatArray = {1.0f, 2.0f};
        Float[] floatWrapArray = new Float[]{1.0f, 2.0f};
        Assert.assertArrayEquals(floatWrapArray, ArrayUtils.wrap(floatArray));
        float[] unwrap = ArrayUtils.unWrap(floatWrapArray);
        assert floatArray[0] == unwrap[0];
        assert floatArray[1] == unwrap[1];

        double[] doubleArray = {1.0, 2.0};
        Double[] doubleWrapArray = new Double[]{1.0, 2.0};
        Assert.assertArrayEquals(doubleWrapArray, ArrayUtils.wrap(doubleArray));
        double[] unwrapDouble = ArrayUtils.unWrap(doubleWrapArray);
        assert doubleArray[0] == unwrapDouble[0];
        assert doubleArray[1] == unwrapDouble[1];

        boolean [] booleanArray = {true,false};
        Boolean[] booleanWrapArray = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
        Assert.assertArrayEquals(booleanWrapArray, ArrayUtils.wrap(booleanArray));
        Assert.assertArrayEquals(booleanArray, ArrayUtils.unWrap(booleanWrapArray));


        char [] charArray = {'1','2'};
        Character[] charWrapArray = new Character[]{'1','2'};
        Assert.assertArrayEquals(charWrapArray, ArrayUtils.wrap(charArray));
        Assert.assertArrayEquals(charArray, ArrayUtils.unWrap(charWrapArray));

        Object charArrayObject = charArray;
        Assert.assertArrayEquals(charWrapArray, ArrayUtils.wrap(charArrayObject));
    }

    @Test
    public void get() {
        String [] strArrays = {"1","2"};
        assert "1".equals(ArrayUtils.get(strArrays,0));
        Assert.assertArrayEquals(strArrays,ArrayUtils.getAny(strArrays,0,1));
    }

    @Test
    public void sub() {
        String [] strArrays = {"1","2","3","4"};
        Assert.assertArrayEquals(new String []{"2","3"},ArrayUtils.sub(strArrays,1,3));
    }

    @Test
    public void join() {
        String [] strArrays = {"1","2","3","4"};
        String str = ArrayUtils.join(strArrays,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);

        str = ArrayUtils.join(strArrays,",","[","]");
        log.debug("str:{}",str);
        assert "[1],[2],[3],[4]".equals(str);

        str = ArrayUtils.join(strArrays,",",t-> t.equals("3")?null:t);
        log.debug("str:{}",str);
        assert "1,2,,4".equals(str);

        long [] longArray = {1L,2L,3L,4L};
        str = ArrayUtils.join(longArray,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);

        int [] intArray = {1,2,3,4};
        str = ArrayUtils.join(intArray,",");
        log.debug("str:{}",str);
        assert "1,2,3,4".equals(str);
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
