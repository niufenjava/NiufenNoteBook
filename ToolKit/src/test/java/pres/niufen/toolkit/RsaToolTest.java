package pres.niufen.toolkit;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class RsaToolTest {

    @Test
    public void initKey() throws Exception {
        RsaTool.initKey();
    }

    @Test
    public void encryptByPrivateKey() {
        String MODULUS = "e8aa6113470c8c14db9a873edef75fce3945a100785a641ef525d597b45b512e351cab4006c29bf2c268610a6526799fdf3ef1a3a09141b24cbf67819f53123a75c5e7c94169b09f7dab9400d7e7368212f9bcd6757b73637523a460fa6e4f581414179fb98222233022dd119acf3e6ba402a64cae8d4363f401e13b9b116399";
        System.out.println(new BigInteger(MODULUS, 16));
        System.out.println(new BigInteger(MODULUS, 16).toByteArray());
        byte [] array = new BigInteger(MODULUS, 16).toByteArray();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
    }

    @Test
    public void encryptByPrivateKey1() {
    }

    @Test
    public void encryptByPrivateKey2() {
    }

    @Test
    public void encryptByPublicKey() {
    }

    @Test
    public void encryptByPublicKey1() {
    }

    @Test
    public void encryptByPublicKey2() {
    }

    @Test
    public void decryptByPrivateKey() {
    }

    @Test
    public void decryptByPublicKey() {
    }

    @Test
    public void getPrivateKey() {
    }

    @Test
    public void getPublicKey() {
    }

    @Test
    public void main() {
    }
}