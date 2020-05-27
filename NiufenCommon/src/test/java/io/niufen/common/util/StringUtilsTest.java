package io.niufen.common.util;

import io.niufen.common.constant.CharConstants;
import io.niufen.common.constant.StringConstants;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void isBlank() {
        String blank = "	  　";
        assert StringUtils.isBlank(blank);
        assert StringUtils.isBlank(StringConstants.Mark.SPACE);
        assert StringUtils.isBlank(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isBlank(blank+"1");

        String blank2 = "\u202a";
        Assert.assertTrue(StringUtils.isBlank(blank2));
    }

    @Test
    public void isBlankIfStr() {
        Object blank = "	  　";
        assert StringUtils.isBlankIfStr(blank);
        assert StringUtils.isBlankIfStr(StringConstants.Mark.SPACE);
        assert StringUtils.isBlankIfStr(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isBlankIfStr(blank+"1");
    }

    @Test
    public void isNotBlank() {
        String blank = "	  　";
        assert !StringUtils.isNotBlank(blank);
        assert !StringUtils.isNotBlank(StringConstants.Mark.SPACE);
        assert !StringUtils.isNotBlank(StringConstants.Mark.SPACE_CN);
        assert StringUtils.isNotBlank(blank+"1");
    }

    @Test
    public void hasBlank() {
        String blank = "	  　";
        assert StringUtils.hasBlank(blank);
        assert StringUtils.hasBlank(StringConstants.Mark.SPACE);
        assert StringUtils.hasBlank(StringConstants.Mark.SPACE_CN);
        assert StringUtils.hasBlank("a","b","");
        assert !StringUtils.hasBlank("a","b");
    }

    @Test
    public void isAllBlank() {
        String blank = "	  　";
        assert StringUtils.isAllBlank(blank);
        assert StringUtils.isAllBlank(StringConstants.Mark.SPACE);
        assert StringUtils.isAllBlank(StringConstants.Mark.SPACE_CN);
        assert !StringUtils.isAllBlank("a","b","");
        assert StringUtils.isAllBlank(""," ","   ");
    }

    @Test
    public void isEmpty() {
        String blank = "	  　";
        assert !StringUtils.isEmpty(blank);
        assert !StringUtils.isEmpty(StringConstants.Mark.SPACE);
        assert !StringUtils.isEmpty(StringConstants.Mark.SPACE_CN);

        assert StringUtils.isEmpty("");
        assert StringUtils.isEmpty(null);
    }

    @Test
    public void isEmptyIfStr() {

        assert StringUtils.isEmptyIfStr("");
        assert StringUtils.isEmptyIfStr(null);
    }

    @Test
    public void isNotEmpty() {
        assert StringUtils.isNotEmpty(" ");

        assert !StringUtils.isNotEmpty("");
        assert !StringUtils.isNotEmpty(null);
    }

    @Test
    public void emptyIfNull(){
        assert StringConstants.EMPTY.equals(StringUtils.emptyIfNull(null));
        assert StringConstants.EMPTY.equals(StringUtils.emptyIfNull(StringConstants.EMPTY));
        assert "a".equals(StringUtils.emptyIfNull("a"));
    }

    @Test
    public void nullToEmpty(){
        assert StringConstants.EMPTY.equals(StringUtils.nullToEmpty(null));
        assert StringConstants.EMPTY.equals(StringUtils.nullToEmpty(StringConstants.EMPTY));
        assert "a".equals(StringUtils.nullToEmpty("a"));
    }

    @Test
    public void nullToDefault(){
        assert StringConstants.EMPTY.equals(StringUtils.nullToDefault(null,StringConstants.EMPTY));
        assert StringConstants.EMPTY.equals(StringUtils.nullToDefault(StringConstants.EMPTY,""));
        assert "a".equals(StringUtils.nullToDefault("a",""));
    }

    @Test
    public void emptyToDefault(){
        assert "DEFAULT".equals(StringUtils.emptyToDefault(StringConstants.EMPTY,"DEFAULT"));
        assert "test".equals(StringUtils.emptyToDefault(StringConstants.EMPTY,"test"));
        assert "a".equals(StringUtils.emptyToDefault("a",""));
    }

    @Test
    public void blankToDefault(){
        String defaultStr = "空";
        assert defaultStr.equals(StringUtils.blankToDefault(StringConstants.EMPTY,defaultStr));
        assert defaultStr.equals(StringUtils.blankToDefault(StringConstants.Mark.SPACE,defaultStr));
        assert defaultStr.equals(StringUtils.blankToDefault(StringConstants.Mark.SPACE_CN,defaultStr));
    }

    @Test
    public void emptyToNull(){
        assert null == StringUtils.emptyToNull(StringConstants.EMPTY);
        assert StringConstants.Mark.SPACE.equals(StringUtils.emptyToNull(StringConstants.Mark.SPACE));
        assert StringConstants.Mark.SPACE_CN.equals(StringUtils.emptyToNull(StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void hasEmpty(){
        assert StringUtils.hasEmpty("","a","b");
        assert StringUtils.hasEmpty("");
        assert StringUtils.hasEmpty("a",null);

        assert !StringUtils.hasEmpty(" ","a","b");
        assert !StringUtils.hasEmpty("   ");
    }

    @Test
    public void isAllEmpty(){
        assert StringUtils.isAllEmpty("",null);
        assert StringUtils.isAllEmpty("");
        assert StringUtils.isAllEmpty((CharSequence) null);

        assert !StringUtils.isAllEmpty(" ","",null);
    }

    @Test
    public void isNullOrUndefined(){
        assert StringUtils.isNullOrUndefined(null);
        assert StringUtils.isNullOrUndefined("null");
        assert StringUtils.isNullOrUndefined("undefined");
        assert !StringUtils.isNullOrUndefined("");
        assert !StringUtils.isNullOrUndefined(" ");
        assert !StringUtils.isNullOrUndefined("  ");
        assert !StringUtils.isNullOrUndefined("Null");
        assert !StringUtils.isNullOrUndefined("Undefined");
    }

    @Test
    public void isEmptyOrUndefined(){
        assert StringUtils.isEmptyOrUndefined(null);
        assert StringUtils.isEmptyOrUndefined("null");
        assert StringUtils.isEmptyOrUndefined("undefined");
        assert StringUtils.isEmptyOrUndefined("");
        assert !StringUtils.isEmptyOrUndefined(" ");
        assert !StringUtils.isEmptyOrUndefined("  ");
        assert !StringUtils.isEmptyOrUndefined("Null");
        assert !StringUtils.isEmptyOrUndefined("Undefined");
    }

    @Test
    public void isBlankOrUndefined(){
        assert StringUtils.isBlankOrUndefined(null);
        assert StringUtils.isBlankOrUndefined("null");
        assert StringUtils.isBlankOrUndefined("undefined");
        assert StringUtils.isBlankOrUndefined("");
        assert StringUtils.isBlankOrUndefined(" ");
        assert StringUtils.isBlankOrUndefined("  ");
        assert !StringUtils.isBlankOrUndefined("Null");
        assert !StringUtils.isBlankOrUndefined("Undefined");
    }

    @Test
    public void trim(){
        String nullStr = null;
        assert null == StringUtils.trim(nullStr);
        assert StringConstants.EMPTY.equals(StringUtils.trim(""));
        assert StringConstants.EMPTY.equals(StringUtils.trim(" "));
        assert "1".equals(StringUtils.trim(" 1 "));
        assert "1".equals(StringUtils.trim(" 1"));
        assert "1".equals(StringUtils.trim("1 "));
        assert "1 2".equals(StringUtils.trim("1 2"));
        assert "1".equals(StringUtils.trim(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimArray(){
        String[] strArray = {" ",StringConstants.Mark.SPACE_CN,"",null};
        StringUtils.trim(strArray);
        assert StringConstants.EMPTY.equals(strArray[0]);
        assert StringConstants.Mark.SPACE_CN.equals(strArray[1]);
        assert StringConstants.EMPTY.equals(strArray[2]);
        assert null == strArray[3];
    }

    @Test
    public void trimToEmpty(){
        String nullStr = null;
        assert StringConstants.EMPTY.equals(StringUtils.trimToEmpty(nullStr));
        assert StringConstants.EMPTY.equals(StringUtils.trimToEmpty(""));
        assert StringConstants.EMPTY.equals(StringUtils.trimToEmpty(" "));
        assert "1".equals(StringUtils.trimToEmpty(" 1 "));
        assert "1".equals(StringUtils.trimToEmpty(" 1"));
        assert "1".equals(StringUtils.trimToEmpty("1 "));
        assert "1 2".equals(StringUtils.trimToEmpty("1 2"));
        assert "1".equals(StringUtils.trimToEmpty(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimToNull(){
        String nullStr = null;
        assert null == StringUtils.trimToNull(nullStr);
        assert null == StringUtils.trimToNull("");
        assert null == StringUtils.trimToNull(" ");
        assert "1".equals(StringUtils.trimToNull(" 1 "));
        assert "1".equals(StringUtils.trimToNull(" 1"));
        assert "1".equals(StringUtils.trimToNull("1 "));
        assert "1 2".equals(StringUtils.trimToNull("1 2"));
        assert "1".equals(StringUtils.trimToNull(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimStart(){
        String nullStr = null;
        assert null == StringUtils.trimStart(nullStr);
        assert StringConstants.EMPTY.equals(StringUtils.trimStart(""));
        assert StringConstants.EMPTY.equals(StringUtils.trimStart(" "));
        assert "1 ".equals(StringUtils.trimStart(" 1 "));
        assert "1".equals(StringUtils.trimStart(" 1"));
        assert "1 ".equals(StringUtils.trimStart("1 "));
        assert "1 2".equals(StringUtils.trimStart("1 2"));
        assert ("1"+StringConstants.Mark.SPACE_CN).equals(StringUtils.trimStart(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimEnd(){
        String nullStr = null;
        assert null == StringUtils.trimEnd(nullStr);
        assert StringConstants.EMPTY.equals(StringUtils.trimEnd(""));
        assert StringConstants.EMPTY.equals(StringUtils.trimEnd(" "));
        assert " 1".equals(StringUtils.trimEnd(" 1 "));
        assert " 1".equals(StringUtils.trimEnd(" 1"));
        assert "1".equals(StringUtils.trimEnd("1 "));
        assert "1 2".equals(StringUtils.trimEnd("1 2"));
        assert (StringConstants.Mark.SPACE_CN+"1").equals(StringUtils.trimEnd(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    // --------------------- With ---------------------

    @Test
    public void startWith(){
        assert StringUtils.startWith(" test", CharConstants.SPACE);
        assert !StringUtils.startWith("test", CharConstants.SPACE);
        assert !StringUtils.startWith(null, CharConstants.SPACE);

        assert StringUtils.startWith(" test", " ");
        assert StringUtils.startWith("test", "t");
        assert StringUtils.startWith("test", "");
        assert StringUtils.startWith("", "");
        assert !StringUtils.startWith(null, "");
        assert !StringUtils.startWith("test", "T");

        assert StringUtils.startWithIgnoreCase("test", "t");
        assert StringUtils.startWithIgnoreCase("test", "T");

        assert StringUtils.startWithAny("test", "T","t","");
        assert StringUtils.startWithAny("test", "a","");
        assert !StringUtils.startWithAny("test", "a","b");
    }


    @Test
    public void endWith(){
        assert StringUtils.endWith(" test ", CharConstants.SPACE);
        assert !StringUtils.endWith("test", CharConstants.SPACE);
        assert !StringUtils.endWith(null, CharConstants.SPACE);

        assert StringUtils.endWith(" test ", " ");
        assert StringUtils.endWith("test", "t");
        assert StringUtils.endWith("test", "");
        assert StringUtils.endWith("", "");
        assert !StringUtils.endWith(null, "");
        assert !StringUtils.endWith("", null);
        assert !StringUtils.endWith("test", "T");

        assert StringUtils.endWithIgnoreCase("test", "t");
        assert StringUtils.endWithIgnoreCase("test", "T");

        assert StringUtils.endWithAny("test", "T","t","");
        assert StringUtils.endWithAny("test", "a","");
        assert !StringUtils.endWithAny("test", "a","b");
    }

    @Test
    public void contains(){
        assert StringUtils.contains("abc",'a');
        assert !StringUtils.contains("",' ');
        assert !StringUtils.contains(null,' ');
        assert !StringUtils.contains("abc",' ');


        assert StringUtils.contains("abc","a");
        assert !StringUtils.contains(""," ");
        assert !StringUtils.contains(null," ");
        assert !StringUtils.contains("abc"," ");
    }

    @Test
    public void containsAny(){
        assert StringUtils.containsAny("abc",'a','b','c',' ');
        assert StringUtils.containsAny(" abc",'e','b','c',' ');
        assert !StringUtils.containsAny("",' ');
        assert !StringUtils.containsAny(null,' ');
        assert !StringUtils.containsAny("abc",' ');

        assert StringUtils.containsAny("abc","abc","e");
        assert StringUtils.containsAny(" abc","abcd"," ");
        assert StringUtils.containsAny("abc","");
        assert !StringUtils.containsAny("","");
        assert !StringUtils.containsAny(null,"");
    }


    @Test
    public void containsOnly(){
        assert StringUtils.containsOnly("abc",'a','b','c');
        assert StringUtils.containsOnly(" abc",'a','b','c',' ');
        assert !StringUtils.containsOnly("abc",' ');
        assert !StringUtils.containsOnly(null,' ');
        assert !StringUtils.containsOnly("abc",' ');
    }

    @Test
    public void containsBlank(){
        assert StringUtils.containsBlank("abc ");
        assert StringUtils.containsBlank("a bc");
        assert StringUtils.containsBlank(" abc");
        assert StringUtils.containsBlank("abc"+StringConstants.Mark.SPACE_CN);
        assert !StringUtils.containsBlank(null);
        assert !StringUtils.containsBlank(StringConstants.EMPTY);
    }

    @Test
    public void getContainsStr(){
        assert "C".equals(StringUtils.getContainsStr("ABC","C","B","A"));
        assert " ".equals(StringUtils.getContainsStr(" ABC"," ","B","A"));
        assert null == StringUtils.getContainsStr(null," ","B","A");
    }


    @Test
    public void containsIgnoreCase(){
        assert StringUtils.containsIgnoreCase("abc","A");
        assert StringUtils.containsIgnoreCase("Abc","a");
        assert StringUtils.containsIgnoreCase(null,null);

        assert !StringUtils.containsIgnoreCase(null,"a");
    }


    @Test
    public void containsAnyIgnoreCase(){
        assert StringUtils.containsAnyIgnoreCase("ABC","a","D","E");
        assert StringUtils.containsAnyIgnoreCase(" ABC"," ","b","A");
        assert !StringUtils.containsAnyIgnoreCase(null," ","B","A");
    }

    @Test
    public void getContainsStrIgnoreCase(){
        assert "c".equals(StringUtils.getContainsStrIgnoreCase("ABC","c","B","A"));
        assert !"C".equals(StringUtils.getContainsStrIgnoreCase("ABC","c","B","A"));
        assert " ".equals(StringUtils.getContainsStrIgnoreCase(" ABC"," ","B","A"));
        assert null == StringUtils.getContainsStrIgnoreCase(null," ","B","A");
    }

    // --------------------- remove ---------------------

    @Test
    public void removeAll(){
        assert "abc".equals(StringUtils.removeAll("a-b-c","-"));
        assert "acdacd".equals(StringUtils.removeAll("abcdabcd","b"));
        assert "".equals(StringUtils.removeAll("-","-"));
        assert null == StringUtils.removeAll(null,"-");


        assert "ab".equals(StringUtils.removeAll("a-b-c",'-','c'));
        assert "".equals(StringUtils.removeAll("abcdabcd",'a','b','c','d','e'));
        assert "".equals(StringUtils.removeAll("-",' ','-'));
        assert null == StringUtils.removeAll(null,'-','a');
    }

    @Test
    public void removeAllLineBreaks(){
        String path = "/Users/niufen/Desktop/项目组接入信息";
        String text = "\n/Users/niufen/Desktop/\n项目组接入信息\r\n";
        assert path.equals(StringUtils.removeAllLineBreaks(text));
        assert null == StringUtils.removeAllLineBreaks(null);
    }

    @Test
    public void removePrefix(){
        assert "user".equals(StringUtils.removePrefix("t_user","t_"));
        assert "".equals(StringUtils.removePrefix("t_user","t_user"));
        assert null == StringUtils.removePrefix(null,"t_user");
    }

    @Test
    public void removeBlank(){
        assert "t_user".equals(StringUtils.removeBlank(" t_ u ser "));
        assert "".equals(StringUtils.removeBlank(" "));
        assert null == StringUtils.removeBlank(null);
    }

    @Test
    public void removePrefixIgnoreCase(){
        assert "user".equals(StringUtils.removePrefixIgnoreCase("t_user","T_"));
        assert "".equals(StringUtils.removePrefixIgnoreCase("t_user","t_User"));
        assert "t_user".equals(StringUtils.removePrefixIgnoreCase("t_user",""));
        assert null == StringUtils.removePrefixIgnoreCase(null,"t_user");
    }

    @Test
    public void removeSuffix(){
        assert "user".equals(StringUtils.removeSuffix("t_user","t_"));
        assert "".equals(StringUtils.removeSuffix("t_user","t_user"));
        assert null == StringUtils.removeSuffix(null,"t_user");
    }

    @Test
    public void removeSuffixIgnoreCase(){
        assert "t_".equals(StringUtils.removeSuffixIgnoreCase("t_user","user"));
        assert "".equals(StringUtils.removeSuffixIgnoreCase("t_user","t_User"));
        assert "t_user".equals(StringUtils.removeSuffixIgnoreCase("t_user",""));
        assert null == StringUtils.removeSuffixIgnoreCase(null,"t_user");
    }

    // --------------------- upper & lower ---------------------
    @Test
    public void upperFirst(){
        assert "UserName".equals(StringUtils.upperFirst("userName"));
        assert "啊userName".equals(StringUtils.upperFirst("啊userName"));
        assert "".equals(StringUtils.upperFirst(""));
        assert "UserName".equals(StringUtils.upperFirst("UserName"));
        assert null == StringUtils.upperFirst(null);
    }

    @Test
    public void lowerFirst(){
        assert "userName".equals(StringUtils.lowerFirst("UserName"));
        assert "啊userName".equals(StringUtils.lowerFirst("啊userName"));
        assert "".equals(StringUtils.lowerFirst(""));
        assert "userName".equals(StringUtils.lowerFirst("userName"));
        assert null == StringUtils.lowerFirst(null);
    }

    // --------------------- remove & upper & lower ---------------------

    @Test
    public void removePreAndLowerFirst(){
        assert "name".equals(StringUtils.removePreAndLowerFirst("setName",3));
        assert "setName".equals(StringUtils.removePreAndLowerFirst("setName",9));
        assert "n".equals(StringUtils.removePreAndLowerFirst("setN",3));
        assert "set".equals(StringUtils.removePreAndLowerFirst("set",3));
        assert null == StringUtils.removePreAndLowerFirst(null,3);

        assert "name".equals(StringUtils.removePreAndLowerFirst("setName","set"));
        assert "ame".equals(StringUtils.removePreAndLowerFirst("setName","setN"));
        assert "n".equals(StringUtils.removePreAndLowerFirst("setN","set"));
        assert "set".equals(StringUtils.removePreAndLowerFirst("set","set"));
        assert null == StringUtils.removePreAndLowerFirst(null,"set");

    }

    @Test
    public void upperFirstAndAddPrefix(){
        assert "setName".equals(StringUtils.upperFirstAndAddPrefix("name","set"));
        assert "getUserName".equals(StringUtils.upperFirstAndAddPrefix("userName","get"));
        assert "getSetN".equals(StringUtils.upperFirstAndAddPrefix("setN","get"));
        assert "Set".equals(StringUtils.upperFirstAndAddPrefix("set",""));
        assert null == StringUtils.upperFirstAndAddPrefix(null,"");
    }

    @Test
    public void removeSuffixAndLowerFirst(){
        assert "userName".equals(StringUtils.removeSuffixAndLowerFirst("UserNameDesc","Desc"));
        assert "userName".equals(StringUtils.removeSuffixAndLowerFirst("userNameA","A"));
        assert "set".equals(StringUtils.removeSuffixAndLowerFirst("Set",""));
        assert null == StringUtils.removeSuffixAndLowerFirst(null,"");
    }

    @Test
    public void getGeneralField(){
        assert "userName".equals(StringUtils.getGeneralField("setUserName"));
        assert "userName".equals(StringUtils.getGeneralField("getUserName"));
        assert "set".equals(StringUtils.getGeneralField("set"));
        assert null == StringUtils.getGeneralField(null);
    }

    @Test
    public void genSetter(){
        assert "setUserName".equals(StringUtils.genSetter("userName"));
        assert "setUserName".equals(StringUtils.genSetter("UserName"));
        assert "setSet".equals(StringUtils.genSetter("set"));
        assert null == StringUtils.genSetter(null);
    }

    @Test
    public void genGetter(){
        assert "getUserName".equals(StringUtils.genGetter("userName"));
        assert "getUserName".equals(StringUtils.genGetter("UserName"));
        assert "getSet".equals(StringUtils.genGetter("set"));
        assert null == StringUtils.genGetter(null);
    }

    // --------------------- strip ---------------------
    @Test
    public void strip(){
        assert "b".equals(StringUtils.strip("aba","a"));
        assert "b".equals(StringUtils.strip("ab","a"));
        assert "b".equals(StringUtils.strip("ba","a"));
        assert null == StringUtils.strip(null,"a");
        assert "aba".equals(StringUtils.strip("aba",""));
        assert "aba".equals(StringUtils.strip("aba",null));


        assert "b".equals(StringUtils.strip("aba","a","a"));
        assert "".equals(StringUtils.strip("ab","a","b"));
        assert "ab".equals(StringUtils.strip("ab","b","a"));
        assert "b".equals(StringUtils.strip("ba","a","a"));
        assert null == StringUtils.strip(null,"a","a");
        assert "aba".equals(StringUtils.strip("aba","",""));
        assert "aba".equals(StringUtils.strip("aba",null,null));
    }

    @Test
    public void stripIgnoreCase(){
        assert "b".equals(StringUtils.stripIgnoreCase("aba","A"));
        assert "b".equals(StringUtils.stripIgnoreCase("Ab","a"));
        assert "b".equals(StringUtils.stripIgnoreCase("ba","A"));
        assert null == StringUtils.stripIgnoreCase(null,"A");
        assert "abA".equals(StringUtils.stripIgnoreCase("abA",""));
        assert "aBa".equals(StringUtils.stripIgnoreCase("aBa",null));


        assert "b".equals(StringUtils.stripIgnoreCase("aba","A","a"));
        assert "".equals(StringUtils.stripIgnoreCase("AB","a","b"));
        assert "ab".equals(StringUtils.stripIgnoreCase("ab","B","A"));
        assert "b".equals(StringUtils.stripIgnoreCase("ba","a","a"));
        assert null == StringUtils.stripIgnoreCase(null,"a","a");
        assert "aba".equals(StringUtils.stripIgnoreCase("aba","",""));
        assert "aba".equals(StringUtils.stripIgnoreCase("aba",null,null));
    }

    @Test
    public void addPrefixIfNot(){
        assert "t_user".equals(StringUtils.addPrefixIfNot("user","t_"));
        assert "t_user".equals(StringUtils.addPrefixIfNot("t_user","t_"));
        assert "user".equals(StringUtils.addPrefixIfNot("user",null));
        assert null == StringUtils.addPrefixIfNot(null,"");
    }

    @Test
    public void addSuffixIfNot(){
        assert "userDesc".equals(StringUtils.addSuffixIfNot("user","Desc"));
        assert "userDesc".equals(StringUtils.addSuffixIfNot("userDesc","Desc"));
        assert "user".equals(StringUtils.addSuffixIfNot("user",null));
        assert null == StringUtils.addSuffixIfNot(null,"");
    }
}
