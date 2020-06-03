package io.niufen.common.core.util;

import io.niufen.common.core.constant.CharConstants;
import io.niufen.common.core.constant.StringConstants;
import org.junit.Assert;
import org.junit.Test;

public class StrUtilTest {

    @Test
    public void isBlank() {
        String blank = "	  　";
        assert StrUtil.isBlank(blank);
        assert StrUtil.isBlank(StringConstants.Mark.SPACE);
        assert StrUtil.isBlank(StringConstants.Mark.SPACE_CN);
        assert !StrUtil.isBlank(blank+"1");

        String blank2 = "\u202a";
        Assert.assertTrue(StrUtil.isBlank(blank2));
    }

    @Test
    public void isBlankIfStr() {
        Object blank = "	  　";
        assert StrUtil.isBlankIfStr(blank);
        assert StrUtil.isBlankIfStr(StringConstants.Mark.SPACE);
        assert StrUtil.isBlankIfStr(StringConstants.Mark.SPACE_CN);
        assert !StrUtil.isBlankIfStr(blank+"1");
    }

    @Test
    public void isNotBlank() {
        String blank = "	  　";
        assert !StrUtil.isNotBlank(blank);
        assert !StrUtil.isNotBlank(StringConstants.Mark.SPACE);
        assert !StrUtil.isNotBlank(StringConstants.Mark.SPACE_CN);
        assert StrUtil.isNotBlank(blank+"1");
    }

    @Test
    public void hasBlank() {
        String blank = "	  　";
        assert StrUtil.hasBlank(blank);
        assert StrUtil.hasBlank(StringConstants.Mark.SPACE);
        assert StrUtil.hasBlank(StringConstants.Mark.SPACE_CN);
        assert StrUtil.hasBlank("a","b","");
        assert !StrUtil.hasBlank("a","b");
    }

    @Test
    public void isAllBlank() {
        String blank = "	  　";
        assert StrUtil.isAllBlank(blank);
        assert StrUtil.isAllBlank(StringConstants.Mark.SPACE);
        assert StrUtil.isAllBlank(StringConstants.Mark.SPACE_CN);
        assert !StrUtil.isAllBlank("a","b","");
        assert StrUtil.isAllBlank(""," ","   ");
    }

    @Test
    public void isEmpty() {
        String blank = "	  　";
        assert !StrUtil.isEmpty(blank);
        assert !StrUtil.isEmpty(StringConstants.Mark.SPACE);
        assert !StrUtil.isEmpty(StringConstants.Mark.SPACE_CN);

        assert StrUtil.isEmpty("");
        assert StrUtil.isEmpty(null);
    }

    @Test
    public void isEmptyIfStr() {

        assert StrUtil.isEmptyIfStr("");
        assert StrUtil.isEmptyIfStr(null);
    }

    @Test
    public void isNotEmpty() {
        assert StrUtil.isNotEmpty(" ");

        assert !StrUtil.isNotEmpty("");
        assert !StrUtil.isNotEmpty(null);
    }

    @Test
    public void emptyIfNull(){
        assert StringConstants.EMPTY.equals(StrUtil.emptyIfNull(null));
        assert StringConstants.EMPTY.equals(StrUtil.emptyIfNull(StringConstants.EMPTY));
        assert "a".equals(StrUtil.emptyIfNull("a"));
    }

    @Test
    public void nullToEmpty(){
        assert StringConstants.EMPTY.equals(StrUtil.nullToEmpty(null));
        assert StringConstants.EMPTY.equals(StrUtil.nullToEmpty(StringConstants.EMPTY));
        assert "a".equals(StrUtil.nullToEmpty("a"));
    }

    @Test
    public void nullToDefault(){
        assert StringConstants.EMPTY.equals(StrUtil.nullToDefault(null,StringConstants.EMPTY));
        assert StringConstants.EMPTY.equals(StrUtil.nullToDefault(StringConstants.EMPTY,""));
        assert "a".equals(StrUtil.nullToDefault("a",""));
    }

    @Test
    public void emptyToDefault(){
        assert "DEFAULT".equals(StrUtil.emptyToDefault(StringConstants.EMPTY,"DEFAULT"));
        assert "test".equals(StrUtil.emptyToDefault(StringConstants.EMPTY,"test"));
        assert "a".equals(StrUtil.emptyToDefault("a",""));
    }

    @Test
    public void blankToDefault(){
        String defaultStr = "空";
        assert defaultStr.equals(StrUtil.blankToDefault(StringConstants.EMPTY,defaultStr));
        assert defaultStr.equals(StrUtil.blankToDefault(StringConstants.Mark.SPACE,defaultStr));
        assert defaultStr.equals(StrUtil.blankToDefault(StringConstants.Mark.SPACE_CN,defaultStr));
    }

    @Test
    public void emptyToNull(){
        assert null == StrUtil.emptyToNull(StringConstants.EMPTY);
        assert StringConstants.Mark.SPACE.equals(StrUtil.emptyToNull(StringConstants.Mark.SPACE));
        assert StringConstants.Mark.SPACE_CN.equals(StrUtil.emptyToNull(StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void hasEmpty(){
        assert StrUtil.hasEmpty("","a","b");
        assert StrUtil.hasEmpty("");
        assert StrUtil.hasEmpty("a",null);

        assert !StrUtil.hasEmpty(" ","a","b");
        assert !StrUtil.hasEmpty("   ");
    }

    @Test
    public void isAllEmpty(){
        assert StrUtil.isAllEmpty("",null);
        assert StrUtil.isAllEmpty("");
        assert StrUtil.isAllEmpty((CharSequence) null);

        assert !StrUtil.isAllEmpty(" ","",null);
    }

    @Test
    public void isNullOrUndefined(){
        assert StrUtil.isNullOrUndefined(null);
        assert StrUtil.isNullOrUndefined("null");
        assert StrUtil.isNullOrUndefined("undefined");
        assert !StrUtil.isNullOrUndefined("");
        assert !StrUtil.isNullOrUndefined(" ");
        assert !StrUtil.isNullOrUndefined("  ");
        assert !StrUtil.isNullOrUndefined("Null");
        assert !StrUtil.isNullOrUndefined("Undefined");
    }

    @Test
    public void isEmptyOrUndefined(){
        assert StrUtil.isEmptyOrUndefined(null);
        assert StrUtil.isEmptyOrUndefined("null");
        assert StrUtil.isEmptyOrUndefined("undefined");
        assert StrUtil.isEmptyOrUndefined("");
        assert !StrUtil.isEmptyOrUndefined(" ");
        assert !StrUtil.isEmptyOrUndefined("  ");
        assert !StrUtil.isEmptyOrUndefined("Null");
        assert !StrUtil.isEmptyOrUndefined("Undefined");
    }

    @Test
    public void isBlankOrUndefined(){
        assert StrUtil.isBlankOrUndefined(null);
        assert StrUtil.isBlankOrUndefined("null");
        assert StrUtil.isBlankOrUndefined("undefined");
        assert StrUtil.isBlankOrUndefined("");
        assert StrUtil.isBlankOrUndefined(" ");
        assert StrUtil.isBlankOrUndefined("  ");
        assert !StrUtil.isBlankOrUndefined("Null");
        assert !StrUtil.isBlankOrUndefined("Undefined");
    }

    @Test
    public void trim(){
        String nullStr = null;
        assert null == StrUtil.trim(nullStr);
        assert StringConstants.EMPTY.equals(StrUtil.trim(""));
        assert StringConstants.EMPTY.equals(StrUtil.trim(" "));
        assert "1".equals(StrUtil.trim(" 1 "));
        assert "1".equals(StrUtil.trim(" 1"));
        assert "1".equals(StrUtil.trim("1 "));
        assert "1 2".equals(StrUtil.trim("1 2"));
        assert "1".equals(StrUtil.trim(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimArray(){
        String[] strArray = {" ",StringConstants.Mark.SPACE_CN,"",null};
        StrUtil.trim(strArray);
        assert StringConstants.EMPTY.equals(strArray[0]);
        assert StringConstants.Mark.SPACE_CN.equals(strArray[1]);
        assert StringConstants.EMPTY.equals(strArray[2]);
        assert null == strArray[3];
    }

    @Test
    public void trimToEmpty(){
        String nullStr = null;
        assert StringConstants.EMPTY.equals(StrUtil.trimToEmpty(nullStr));
        assert StringConstants.EMPTY.equals(StrUtil.trimToEmpty(""));
        assert StringConstants.EMPTY.equals(StrUtil.trimToEmpty(" "));
        assert "1".equals(StrUtil.trimToEmpty(" 1 "));
        assert "1".equals(StrUtil.trimToEmpty(" 1"));
        assert "1".equals(StrUtil.trimToEmpty("1 "));
        assert "1 2".equals(StrUtil.trimToEmpty("1 2"));
        assert "1".equals(StrUtil.trimToEmpty(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimToNull(){
        String nullStr = null;
        assert null == StrUtil.trimToNull(nullStr);
        assert null == StrUtil.trimToNull("");
        assert null == StrUtil.trimToNull(" ");
        assert "1".equals(StrUtil.trimToNull(" 1 "));
        assert "1".equals(StrUtil.trimToNull(" 1"));
        assert "1".equals(StrUtil.trimToNull("1 "));
        assert "1 2".equals(StrUtil.trimToNull("1 2"));
        assert "1".equals(StrUtil.trimToNull(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimStart(){
        String nullStr = null;
        assert null == StrUtil.trimStart(nullStr);
        assert StringConstants.EMPTY.equals(StrUtil.trimStart(""));
        assert StringConstants.EMPTY.equals(StrUtil.trimStart(" "));
        assert "1 ".equals(StrUtil.trimStart(" 1 "));
        assert "1".equals(StrUtil.trimStart(" 1"));
        assert "1 ".equals(StrUtil.trimStart("1 "));
        assert "1 2".equals(StrUtil.trimStart("1 2"));
        assert ("1"+StringConstants.Mark.SPACE_CN).equals(StrUtil.trimStart(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    @Test
    public void trimEnd(){
        String nullStr = null;
        assert null == StrUtil.trimEnd(nullStr);
        assert StringConstants.EMPTY.equals(StrUtil.trimEnd(""));
        assert StringConstants.EMPTY.equals(StrUtil.trimEnd(" "));
        assert " 1".equals(StrUtil.trimEnd(" 1 "));
        assert " 1".equals(StrUtil.trimEnd(" 1"));
        assert "1".equals(StrUtil.trimEnd("1 "));
        assert "1 2".equals(StrUtil.trimEnd("1 2"));
        assert (StringConstants.Mark.SPACE_CN+"1").equals(StrUtil.trimEnd(StringConstants.Mark.SPACE_CN+"1"+StringConstants.Mark.SPACE_CN));
    }

    // --------------------- With ---------------------

    @Test
    public void startWith(){
        assert StrUtil.startWith(" test", CharConstants.SPACE);
        assert !StrUtil.startWith("test", CharConstants.SPACE);
        assert !StrUtil.startWith(null, CharConstants.SPACE);

        assert StrUtil.startWith(" test", " ");
        assert StrUtil.startWith("test", "t");
        assert StrUtil.startWith("test", "");
        assert StrUtil.startWith("", "");
        assert !StrUtil.startWith(null, "");
        assert !StrUtil.startWith("test", "T");

        assert StrUtil.startWithIgnoreCase("test", "t");
        assert StrUtil.startWithIgnoreCase("test", "T");

        assert StrUtil.startWithAny("test", "T","t","");
        assert StrUtil.startWithAny("test", "a","");
        assert !StrUtil.startWithAny("test", "a","b");
    }


    @Test
    public void endWith(){
        assert StrUtil.endWith(" test ", CharConstants.SPACE);
        assert !StrUtil.endWith("test", CharConstants.SPACE);
        assert !StrUtil.endWith(null, CharConstants.SPACE);

        assert StrUtil.endWith(" test ", " ");
        assert StrUtil.endWith("test", "t");
        assert StrUtil.endWith("test", "");
        assert StrUtil.endWith("", "");
        assert !StrUtil.endWith(null, "");
        assert !StrUtil.endWith("", null);
        assert !StrUtil.endWith("test", "T");

        assert StrUtil.endWithIgnoreCase("test", "t");
        assert StrUtil.endWithIgnoreCase("test", "T");

        assert StrUtil.endWithAny("test", "T","t","");
        assert StrUtil.endWithAny("test", "a","");
        assert !StrUtil.endWithAny("test", "a","b");
    }

    @Test
    public void contains(){
        assert StrUtil.contains("abc",'a');
        assert !StrUtil.contains("",' ');
        assert !StrUtil.contains(null,' ');
        assert !StrUtil.contains("abc",' ');


        assert StrUtil.contains("abc","a");
        assert !StrUtil.contains(""," ");
        assert !StrUtil.contains(null," ");
        assert !StrUtil.contains("abc"," ");
    }

    @Test
    public void containsAny(){
        assert StrUtil.containsAny("abc",'a','b','c',' ');
        assert StrUtil.containsAny(" abc",'e','b','c',' ');
        assert !StrUtil.containsAny("",' ');
        assert !StrUtil.containsAny(null,' ');
        assert !StrUtil.containsAny("abc",' ');

        assert StrUtil.containsAny("abc","abc","e");
        assert StrUtil.containsAny(" abc","abcd"," ");
        assert StrUtil.containsAny("abc","");
        assert !StrUtil.containsAny("","");
        assert !StrUtil.containsAny(null,"");
    }


    @Test
    public void containsOnly(){
        assert StrUtil.containsOnly("abc",'a','b','c');
        assert StrUtil.containsOnly(" abc",'a','b','c',' ');
        assert !StrUtil.containsOnly("abc",' ');
        assert !StrUtil.containsOnly(null,' ');
        assert !StrUtil.containsOnly("abc",' ');
    }

    @Test
    public void containsBlank(){
        assert StrUtil.containsBlank("abc ");
        assert StrUtil.containsBlank("a bc");
        assert StrUtil.containsBlank(" abc");
        assert StrUtil.containsBlank("abc"+StringConstants.Mark.SPACE_CN);
        assert !StrUtil.containsBlank(null);
        assert !StrUtil.containsBlank(StringConstants.EMPTY);
    }

    @Test
    public void getContainsStr(){
        assert "C".equals(StrUtil.getContainsStr("ABC","C","B","A"));
        assert " ".equals(StrUtil.getContainsStr(" ABC"," ","B","A"));
        assert null == StrUtil.getContainsStr(null," ","B","A");
    }


    @Test
    public void containsIgnoreCase(){
        assert StrUtil.containsIgnoreCase("abc","A");
        assert StrUtil.containsIgnoreCase("Abc","a");
        assert StrUtil.containsIgnoreCase(null,null);

        assert !StrUtil.containsIgnoreCase(null,"a");
    }


    @Test
    public void containsAnyIgnoreCase(){
        assert StrUtil.containsAnyIgnoreCase("ABC","a","D","E");
        assert StrUtil.containsAnyIgnoreCase(" ABC"," ","b","A");
        assert !StrUtil.containsAnyIgnoreCase(null," ","B","A");
    }

    @Test
    public void getContainsStrIgnoreCase(){
        assert "c".equals(StrUtil.getContainsStrIgnoreCase("ABC","c","B","A"));
        assert !"C".equals(StrUtil.getContainsStrIgnoreCase("ABC","c","B","A"));
        assert " ".equals(StrUtil.getContainsStrIgnoreCase(" ABC"," ","B","A"));
        assert null == StrUtil.getContainsStrIgnoreCase(null," ","B","A");
    }

    // --------------------- remove ---------------------

    @Test
    public void removeAll(){
        assert "abc".equals(StrUtil.removeAll("a-b-c","-"));
        assert "acdacd".equals(StrUtil.removeAll("abcdabcd","b"));
        assert "".equals(StrUtil.removeAll("-","-"));
        assert null == StrUtil.removeAll(null,"-");


        assert "ab".equals(StrUtil.removeAll("a-b-c",'-','c'));
        assert "".equals(StrUtil.removeAll("abcdabcd",'a','b','c','d','e'));
        assert "".equals(StrUtil.removeAll("-",' ','-'));
        assert null == StrUtil.removeAll(null,'-','a');
    }

    @Test
    public void removeAllLineBreaks(){
        String path = "/Users/niufen/Desktop/项目组接入信息";
        String text = "\n/Users/niufen/Desktop/\n项目组接入信息\r\n";
        assert path.equals(StrUtil.removeAllLineBreaks(text));
        assert null == StrUtil.removeAllLineBreaks(null);
    }

    @Test
    public void removePrefix(){
        assert "user".equals(StrUtil.removePrefix("t_user","t_"));
        assert "".equals(StrUtil.removePrefix("t_user","t_user"));
        assert null == StrUtil.removePrefix(null,"t_user");
    }

    @Test
    public void removePrefixIgnoreCase(){
        assert "user".equals(StrUtil.removePrefixIgnoreCase("t_user","T_"));
        assert "".equals(StrUtil.removePrefixIgnoreCase("t_user","t_User"));
        assert "t_user".equals(StrUtil.removePrefixIgnoreCase("t_user",""));
        assert null == StrUtil.removePrefixIgnoreCase(null,"t_user");
    }

    @Test
    public void removeSuffix(){
        assert "user".equals(StrUtil.removeSuffix("t_user","t_"));
        assert "".equals(StrUtil.removeSuffix("t_user","t_user"));
        assert null == StrUtil.removeSuffix(null,"t_user");
    }

    @Test
    public void removeSuffixIgnoreCase(){
        assert "t_".equals(StrUtil.removeSuffixIgnoreCase("t_user","user"));
        assert "".equals(StrUtil.removeSuffixIgnoreCase("t_user","t_User"));
        assert "t_user".equals(StrUtil.removeSuffixIgnoreCase("t_user",""));
        assert null == StrUtil.removeSuffixIgnoreCase(null,"t_user");
    }

    // --------------------- upper & lower ---------------------
    @Test
    public void upperFirst(){
        assert "UserName".equals(StrUtil.upperFirst("userName"));
        assert "啊userName".equals(StrUtil.upperFirst("啊userName"));
        assert "".equals(StrUtil.upperFirst(""));
        assert "UserName".equals(StrUtil.upperFirst("UserName"));
        assert null == StrUtil.upperFirst(null);
    }

    @Test
    public void lowerFirst(){
        assert "userName".equals(StrUtil.lowerFirst("UserName"));
        assert "啊userName".equals(StrUtil.lowerFirst("啊userName"));
        assert "".equals(StrUtil.lowerFirst(""));
        assert "userName".equals(StrUtil.lowerFirst("userName"));
        assert null == StrUtil.lowerFirst(null);
    }

    // --------------------- remove & upper & lower ---------------------

    @Test
    public void removePreAndLowerFirst(){
        assert "name".equals(StrUtil.removePreAndLowerFirst("setName",3));
        assert "setName".equals(StrUtil.removePreAndLowerFirst("setName",9));
        assert "n".equals(StrUtil.removePreAndLowerFirst("setN",3));
        assert "set".equals(StrUtil.removePreAndLowerFirst("set",3));
        assert null == StrUtil.removePreAndLowerFirst(null,3);

        assert "name".equals(StrUtil.removePreAndLowerFirst("setName","set"));
        assert "ame".equals(StrUtil.removePreAndLowerFirst("setName","setN"));
        assert "n".equals(StrUtil.removePreAndLowerFirst("setN","set"));
        assert "set".equals(StrUtil.removePreAndLowerFirst("set","set"));
        assert null == StrUtil.removePreAndLowerFirst(null,"set");

    }

    @Test
    public void upperFirstAndAddPrefix(){
        assert "setName".equals(StrUtil.upperFirstAndAddPre("name","set"));
        assert "getUserName".equals(StrUtil.upperFirstAndAddPre("userName","get"));
        assert "getSetN".equals(StrUtil.upperFirstAndAddPre("setN","get"));
        assert "Set".equals(StrUtil.upperFirstAndAddPre("set",""));
        assert null == StrUtil.upperFirstAndAddPre(null,"");
    }

    @Test
    public void removeSuffixAndLowerFirst(){
        assert "userName".equals(StrUtil.removePreAndLowerFirst("UserNameDesc","Desc"));
        assert "userName".equals(StrUtil.removePreAndLowerFirst("userNameA","A"));
        assert "set".equals(StrUtil.removePreAndLowerFirst("Set",""));
        assert null == StrUtil.removePreAndLowerFirst(null,"");
    }

    @Test
    public void getGeneralField(){
        assert "userName".equals(StrUtil.getGeneralField("setUserName"));
        assert "userName".equals(StrUtil.getGeneralField("getUserName"));
        assert "set".equals(StrUtil.getGeneralField("set"));
        assert null == StrUtil.getGeneralField(null);
    }

    @Test
    public void genSetter(){
        assert "setUserName".equals(StrUtil.genSetter("userName"));
        assert "setUserName".equals(StrUtil.genSetter("UserName"));
        assert "setSet".equals(StrUtil.genSetter("set"));
        assert null == StrUtil.genSetter(null);
    }

    @Test
    public void genGetter(){
        assert "getUserName".equals(StrUtil.genGetter("userName"));
        assert "getUserName".equals(StrUtil.genGetter("UserName"));
        assert "getSet".equals(StrUtil.genGetter("set"));
        assert null == StrUtil.genGetter(null);
    }

    // --------------------- strip ---------------------
    @Test
    public void strip(){
        assert "b".equals(StrUtil.strip("aba","a"));
        assert "b".equals(StrUtil.strip("ab","a"));
        assert "b".equals(StrUtil.strip("ba","a"));
        assert null == StrUtil.strip(null,"a");
        assert "aba".equals(StrUtil.strip("aba",""));
        assert "aba".equals(StrUtil.strip("aba",null));


        assert "b".equals(StrUtil.strip("aba","a","a"));
        assert "".equals(StrUtil.strip("ab","a","b"));
        assert "ab".equals(StrUtil.strip("ab","b","a"));
        assert "b".equals(StrUtil.strip("ba","a","a"));
        assert null == StrUtil.strip(null,"a","a");
        assert "aba".equals(StrUtil.strip("aba","",""));
        assert "aba".equals(StrUtil.strip("aba",null,null));
    }

    @Test
    public void stripIgnoreCase(){
        assert "b".equals(StrUtil.stripIgnoreCase("aba","A"));
        assert "b".equals(StrUtil.stripIgnoreCase("Ab","a"));
        assert "b".equals(StrUtil.stripIgnoreCase("ba","A"));
        assert null == StrUtil.stripIgnoreCase(null,"A");
        assert "abA".equals(StrUtil.stripIgnoreCase("abA",""));
        assert "aBa".equals(StrUtil.stripIgnoreCase("aBa",null));


        assert "b".equals(StrUtil.stripIgnoreCase("aba","A","a"));
        assert "".equals(StrUtil.stripIgnoreCase("AB","a","b"));
        assert "ab".equals(StrUtil.stripIgnoreCase("ab","B","A"));
        assert "b".equals(StrUtil.stripIgnoreCase("ba","a","a"));
        assert null == StrUtil.stripIgnoreCase(null,"a","a");
        assert "aba".equals(StrUtil.stripIgnoreCase("aba","",""));
        assert "aba".equals(StrUtil.stripIgnoreCase("aba",null,null));
    }

    @Test
    public void addPrefixIfNot(){
        assert "t_user".equals(StrUtil.addPrefixIfNot("user","t_"));
        assert "t_user".equals(StrUtil.addPrefixIfNot("t_user","t_"));
        assert "user".equals(StrUtil.addPrefixIfNot("user",null));
        assert null == StrUtil.addPrefixIfNot(null,"");
    }

    @Test
    public void addSuffixIfNot(){
        assert "userDesc".equals(StrUtil.addSuffixIfNot("user","Desc"));
        assert "userDesc".equals(StrUtil.addSuffixIfNot("userDesc","Desc"));
        assert "user".equals(StrUtil.addSuffixIfNot("user",null));
        assert null == StrUtil.addSuffixIfNot(null,"");
    }
}
