package com.wmeup.util;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Title: DeEnCodeUtil</p>
 * <p>Description: 加密解密工具类<p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zy
 * @version 1.0
 * @date 16/5/19
 */

public class DeEnCodeUtil {
    public static String desEnCode(String srcStr) {

        try {
            Key localKey = jdMethod_super("cputest".getBytes());

            Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(1, localKey);
            return byteArr2HexStr(localCipher.doFinal(srcStr.getBytes()));
        } catch (InvalidKeyException localInvalidKeyException) {
            localInvalidKeyException.printStackTrace();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
            localNoSuchPaddingException.printStackTrace();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "0";
    }

    public static String desDeCode(String desStr) {
        try {
            Key localKey = jdMethod_super("cputest".getBytes());

            Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(2, localKey);
            return new String(localCipher.doFinal(hexStr2ByteArr(desStr)));
        } catch (InvalidKeyException localInvalidKeyException) {
            localInvalidKeyException.printStackTrace();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
            localNoSuchPaddingException.printStackTrace();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "0";
    }

    private static Key jdMethod_super(byte[] paramArrayOfByte) throws Exception {
        byte[] arrayOfByte = new byte[8];

        for (int i = 0; (i < paramArrayOfByte.length) && (i < arrayOfByte.length); i++) {
            arrayOfByte[i] = paramArrayOfByte[i];
        }

        SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte, "DES");

        return localSecretKeySpec;
    }

    public static String byteArr2HexStr(byte[] paramArrayOfByte) throws Exception {
        int i = paramArrayOfByte.length;

        StringBuffer localStringBuffer = new StringBuffer(i * 2);
        for (int j = 0; j < i; j++) {
            int k = paramArrayOfByte[j];

            while (k < 0) {
                k += 256;
            }

            if (k < 16) {
                localStringBuffer.append("0");
            }
            localStringBuffer.append(Integer.toString(k, 16));
        }
        return localStringBuffer.toString();
    }

    public static byte[] hexStr2ByteArr(String paramString) throws Exception {
        byte[] arrayOfByte1 = paramString.getBytes();
        int i = arrayOfByte1.length;

        byte[] arrayOfByte2 = new byte[i / 2];
        for (int j = 0; j < i; j += 2) {
            String str = new String(arrayOfByte1, j, 2);
            arrayOfByte2[(j / 2)] = ((byte) Integer.parseInt(str, 16));
        }
        return arrayOfByte2;
    }

}
