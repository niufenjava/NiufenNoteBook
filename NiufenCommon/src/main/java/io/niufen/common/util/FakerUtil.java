package io.niufen.common.util;

/**
 * 测试数据生成器
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 14:19
 */
import com.github.javafaker.Faker;
import io.niufen.common.constant.StringConstants;
import io.niufen.common.enums.SexEnum;

import java.util.Locale;

/**
 * 随机生成中文姓名，性别，Email，手机号，住址
 * @author X-rapido
 */
public class FakerUtil {
    public static final Faker CN_FAKER = new Faker(new Locale(StringConstants.ZH_CN));
    public static final Faker EN_FAKER = new Faker();

    public static final String[] SYS_LOG_CONTENT_ARRAY = {"删除用户","新增用户","更新用户","查询用户"};

    public static final String[] HTTP_REQUEST_TYPE_ARRAY = {"POST","DELETE","GET","PATCH"};

    public static final String[] JAVA_METHOD_ARRAY = {"page","list","detail","delete"};

    public static String sysLogContent(){
        int index=(int)(Math.random()*SYS_LOG_CONTENT_ARRAY.length);
        return SYS_LOG_CONTENT_ARRAY[index];
    }

    public static String httpRequestType(){
        int index=(int)(Math.random()*HTTP_REQUEST_TYPE_ARRAY.length);
        return HTTP_REQUEST_TYPE_ARRAY[index];
    }

    public static String javaMethodName(){
        int index=(int)(Math.random()*JAVA_METHOD_ARRAY.length);
        return JAVA_METHOD_ARRAY[index];
    }

    /**
     * 在 start 到 end 范围内所及生成一个数字
     * 例如：start=0; end=100; 返回可能是99 不可能是101
     * @param start 起始数字
     * @param end 结束数字
     * @return 单个数字
     */
    public static int numberRandom(int start, int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    public static String nameCN(){
        return CN_FAKER.name().name();
    }

    public static String nameEN(){
        return EN_FAKER.name().name();
    }

    public static String firstNameEN(){
        return EN_FAKER.name().firstName().toLowerCase();
    }

    public static Integer sex(){
        return SexEnum.randomIndex();
    }

    public static String namePinYin(){
        return PinYinUtil.toPinYin(nameCN());
    }

    public static String cellPhone(){
        return CN_FAKER.phoneNumber().cellPhone();
    }

    public static String email(){
        return EN_FAKER.internet().emailAddress();
    }

    public static String password(){
        return EN_FAKER.internet().password();
    }

    public static String idNumberEN(){
        return EN_FAKER.idNumber().valid();
    }

    public static String idNumberCN(){
        return CN_FAKER.idNumber().invalid();
    }

    public static String ip(){
        return EN_FAKER.internet().ipV4Address();
    }

    public static String uri(){
        return EN_FAKER.internet().url();
    }

    public static Long costTime(){
        return EN_FAKER.random().nextLong();
    }

    public static String uuid(){
        return EN_FAKER.internet().uuid();
    }
}
