package pres.niufen.toolkit;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * 非对称加密算法RSA算法组件
 * 非对称算法一般是用来传送对称加密算法的密钥来使用的RSA算法只需要一方构造密钥
 *
 * @author haijun.zhang
 */

public class RsaTool {
    
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RsaTool.class);

    /**
     * 非对称加密算法使用 RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 公钥名称
     */
    private static final String PUBLIC_KEY_NAME = "RSAPublicKey";

    /**
     * 私钥名称
     */
    private static final String PRIVATE_KEY_NAME = "RSAPrivateKey";

    /**
     * 模数-16进制-可以弄到配置文件中
     */
    private static final String MODULUS_HEX = "e8aa6113470c8c14db9a873edef75fce3945a100785a641ef525d597b45b512e351cab4006c29bf2c268610a6526799fdf3ef1a3a09141b24cbf67819f53123a75c5e7c94169b09f7dab9400d7e7368212f9bcd6757b73637523a460fa6e4f581414179fb98222233022dd119acf3e6ba402a64cae8d4363f401e13b9b116399";

    /**
     * 模数-10进制-可以弄到配置文件中
     */
    private static final String MODULUS_ASCII = "e8aa6113470c8c14db9a873edef75fce3945a100785a641ef525d597b45b512e351cab4006c29bf2c268610a6526799fdf3ef1a3a09141b24cbf67819f53123a75c5e7c94169b09f7dab9400d7e7368212f9bcd6757b73637523a460fa6e4f581414179fb98222233022dd119acf3e6ba402a64cae8d4363f401e13b9b116399";

    /**
     * 指数-16进制-可以弄到配置文件中
     */
    private static final String EXPONENT_HEX = "2e28af3dadd8d569c5efb0694ebef128eebfdba3463d58a3b3ab1fe70b071ae1f9f3881e19b93e4b393a901609df4a3de7828b5a151efc53ad5a43e4b981379a34aae4466b5c8b4c575264a6b563548d06f9308a61f8bfc1884c46d88a5b494cc6cb70a7266d15da7c60e2a7b679b9a83b016e75e5820c965c79767a34eff421";

    /**
     * 指数-10进制-可以弄到配置文件中
     */
    private static final String EXPONENT_ASCII = "e8aa6113470c8c14db9a873edef75fce3945a100785a641ef525d597b45b512e351cab4006c29bf2c268610a6526799fdf3ef1a3a09141b24cbf67819f53123a75c5e7c94169b09f7dab9400d7e7368212f9bcd6757b73637523a460fa6e4f581414179fb98222233022dd119acf3e6ba402a64cae8d4363f401e13b9b116399";

    public static String hexToAscii(String strHEX){
        return new BigInteger(strHEX,16).toString();
    }

    /**
     * 生成RSA 密钥对，公钥、私钥
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {

        // 1、实例化密钥生成器、 初始化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);

        // 2、生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 3、获取甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        LOGGER.info("-------------------生成公钥-----------------------");
        LOGGER.info("公钥：{}",Base64.encodeBase64String(publicKey.getEncoded()));
        LOGGER.info("公钥加密算法：{}",publicKey.getAlgorithm());
        LOGGER.info("公钥加密指数：{}",publicKey.getPublicExponent());
        LOGGER.info("公钥模数：{}",publicKey.getModulus());
        LOGGER.info("公钥格式：{}",publicKey.getFormat());

        // 4、获取甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        LOGGER.info("-------------------生成私钥-----------------------");
        LOGGER.info("私钥：{}",Base64.encodeBase64String(privateKey.getEncoded()));
        LOGGER.info("私钥加密算法：{}",privateKey.getAlgorithm());
        LOGGER.info("私钥加密指数：{}",privateKey.getPrivateExponent());
        LOGGER.info("私钥模数：{}",privateKey.getModulus());
        LOGGER.info("私钥格式：{}",privateKey.getFormat());

        // 5、将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<String, Object>(16);
        keyMap.put(PUBLIC_KEY_NAME, publicKey);
        keyMap.put(PRIVATE_KEY_NAME, privateKey);
        return keyMap;

    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus 模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus,16);
            BigInteger b2 = new BigInteger(exponent,16);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public static String encryptByPrivateKey(String data,String key) throws Exception {
        byte[] code = RsaTool.encryptByPrivateKey(data.getBytes(), key.getBytes());
        return Base64.encodeBase64String(code);
    }

//    public static String encryptByPrivateKey(String data) throws Exception {
//        return encryptByPrivateKey(data,PRIVATE_KEY);
//    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    public static String encryptByPublicKey(String data,String key) throws Exception {
        byte[] code = RsaTool.encryptByPublicKey(data.getBytes(), key.getBytes());
        return Base64.encodeBase64String(code);
    }

//    public static String encryptByPublicKey(String data) throws Exception {
//        return encryptByPublicKey(data,PRIVATE_KEY);
//    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY_NAME);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY_NAME);
        return key.getEncoded();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //初始化密钥
        //生成密钥对
        Map<String, Object> keyMap = RsaTool.initKey();
        //公钥
        byte[] publicKey = RsaTool.getPublicKey(keyMap);

        //私钥
        byte[] privateKey = RsaTool.getPrivateKey(keyMap);
        System.out.println("公钥：/n" + Base64.encodeBase64String(publicKey));
        System.out.println("私钥：/n" + Base64.encodeBase64String(privateKey));

        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "1qaz2wsx";
        System.out.println("/n===========甲方向乙方发送加密数据==============");
        System.out.println("原文:" + str);
        //甲方进行数据的加密
        byte[] code1 = RsaTool.encryptByPrivateKey(str.getBytes(), privateKey);
        System.out.println("加密后的数据：" + Base64.encodeBase64String(code1));
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        //乙方进行数据的解密
        byte[] decode1 = RsaTool.decryptByPublicKey(code1, publicKey);
        System.out.println("乙方解密后的数据：" + new String(decode1) + "/n/n");

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("===========反向进行操作，乙方向甲方发送数据==============/n/n");

        str = "乙方向甲方发送数据RSA算法";

        System.out.println("原文:" + str);

        //乙方使用公钥对数据进行加密
        byte[] code2 = RsaTool.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据：" + Base64.encodeBase64String(code2));

        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");

        //甲方使用私钥对数据进行解密
        byte[] decode2 = RsaTool.decryptByPrivateKey(code2, privateKey);

        System.out.println("甲方解密后的数据：" + new String(decode2));
    }
}