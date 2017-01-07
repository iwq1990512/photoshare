package com.wmeup.util.http;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RSASignUtil {
    private String certFilePath = null;
    private String password = null;
    private String hexCert = null;
    //private String rootCertPath = null;

    public RSASignUtil(String certFilePath, String password) {
        this.certFilePath = certFilePath;
        this.password = password;
    }

   /* public RSASignUtil(String rootCertPath) {
        this.rootCertPath = rootCertPath;
    }*/

    public RSASignUtil() {
    }

    public String sign(String indata, String encoding) throws UnsupportedEncodingException {
        String singData = null;
        if(StringUtils.isBlank(encoding)) {
            encoding = "GBK";
        }

        try {
            CAP12CertTool e = new CAP12CertTool(this.certFilePath, this.password);
            X509Certificate cert = e.getCert();
            byte[] si = e.getSignData(indata.getBytes(encoding));
            byte[] cr = cert.getEncoded();
            this.hexCert = HexStringByte.byteToHex(cr);
            singData = HexStringByte.byteToHex(si);
        } catch (CertificateEncodingException var8) {
            var8.printStackTrace();
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
        } catch (SecurityException var10) {
            var10.printStackTrace();
        }

        return singData;
    }

    public String getCertInfo() {
        return this.hexCert;
    }

    public boolean verify(String oridata, String signData, String svrCert, String encoding) {
        boolean res = false;
        if(StringUtils.isBlank(encoding)) {
            encoding = "GBK";
        }

        try {
            byte[] e = HexStringByte.hexToByte(signData.getBytes());
            byte[] inDataBytes = new byte[0];
            inDataBytes = oridata.getBytes(encoding);
            byte[] signaturepem = checkPEM(e);
            if(signaturepem != null) {
                e = Base64.decode(signaturepem);
            }

            X509Certificate cert = this.getCertFromHexString(svrCert);
            if(cert != null) {
                PublicKey pubKey = cert.getPublicKey();
                Signature signet = Signature.getInstance("SHA1WITHRSA");
                signet.initVerify(pubKey);
                signet.update(inDataBytes);
                res = signet.verify(e);
            }
        } catch (InvalidKeyException var12) {
            var12.printStackTrace();
        } catch (NoSuchAlgorithmException var13) {
            var13.printStackTrace();
        } catch (SignatureException var14) {
            var14.printStackTrace();
        } catch (SecurityException var15) {
            var15.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        return res;
    }

    public X509Certificate getCertfromPath(String crt_path) throws SecurityException, IOException {
        X509Certificate cert = null;
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(new File(crt_path));
            CertificateFactory e = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate)e.generateCertificate(inStream);
            return cert;
        } catch (CertificateException var5) {
            throw new SecurityException(var5.getMessage());
        } catch (FileNotFoundException var6) {
            throw new SecurityException(var6.getMessage());
        }finally {
            if(inStream!=null){
                inStream.close();
            }
        }
    }

    public static PublicKey getPublicKeyfromPath(String svrCertpath) throws SecurityException, IOException {
        X509Certificate cert = null;
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(new File(svrCertpath));
            CertificateFactory e = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate)e.generateCertificate(inStream);
        } catch (CertificateException var4) {
            throw new SecurityException(var4.getMessage());
        } catch (FileNotFoundException var5) {
            throw new SecurityException(var5.getMessage());
        }finally {
            if(inStream!=null){
                inStream.close();
            }
        }

        return cert.getPublicKey();
    }

    public boolean verifyCert(X509Certificate userCert, X509Certificate rootCert) throws SecurityException {
        boolean res = false;

        try {
            PublicKey e = rootCert.getPublicKey();
            userCert.checkValidity();
            userCert.verify(e);
            res = true;
            if(!userCert.getIssuerDN().equals(rootCert.getSubjectDN())) {
                res = false;
            }

            return res;
        } catch (CertificateExpiredException var5) {
            throw new SecurityException(var5.getMessage());
        } catch (CertificateNotYetValidException var6) {
            throw new SecurityException(var6.getMessage());
        } catch (InvalidKeyException var7) {
            throw new SecurityException(var7.getMessage());
        } catch (CertificateException var8) {
            throw new SecurityException(var8.getMessage());
        } catch (NoSuchAlgorithmException var9) {
            throw new SecurityException(var9.getMessage());
        } catch (NoSuchProviderException var10) {
            throw new SecurityException(var10.getMessage());
        } catch (SignatureException var11) {
            throw new SecurityException(var11.getMessage());
        }
    }

    private X509Certificate getCertFromHexString(String hexCert) throws SecurityException {
        ByteArrayInputStream bIn = null;
        X509Certificate certobj = null;

        try {
            byte[] e = HexStringByte.hexToByte(hexCert.getBytes());
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            bIn = new ByteArrayInputStream(e);
            certobj = (X509Certificate)fact.generateCertificate(bIn);
            bIn.close();
            bIn = null;
        } catch (CertificateException var16) {
            var16.printStackTrace();
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            try {
                if(bIn != null) {
                    bIn.close();
                }
            } catch (IOException var15) {
                ;
            }

        }

        return certobj;
    }

    public static byte[] checkPEM(byte[] paramArrayOfByte) {
        String str1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/+= \r\n-";

        for(int localStringBuffer = 0; localStringBuffer < paramArrayOfByte.length; ++localStringBuffer) {
            if(str1.indexOf(paramArrayOfByte[localStringBuffer]) == -1) {
                return null;
            }
        }

        StringBuffer var5 = new StringBuffer(paramArrayOfByte.length);
        String str2 = new String(paramArrayOfByte);

        for(int j = 0; j < str2.length(); ++j) {
            if(str2.charAt(j) != 32 && str2.charAt(j) != 13 && str2.charAt(j) != 10) {
                var5.append(str2.charAt(j));
            }
        }

        return var5.toString().getBytes();
    }

    public String getFormValue(String respMsg, String name) {
        String[] resArr = StringUtils.split(respMsg, "&");
        HashMap resMap = new HashMap();

        for(int i = 0; i < resArr.length; ++i) {
            String data = resArr[i];
            int index = StringUtils.indexOf(data, '=');
            String nm = StringUtils.substring(data, 0, index);
            String val = StringUtils.substring(data, index + 1);
            resMap.put(nm, val);
        }

        return (String)resMap.get(name) == null?"":(String)resMap.get(name);
    }

    public String getValue(String respMsg, String name) {
        String[] resArr = StringUtils.split(respMsg, "&");
        HashMap resMap = new HashMap();

        for(int i = 0; i < resArr.length; ++i) {
            String data = resArr[i];
            int index = StringUtils.indexOf(data, '=');
            String nm = StringUtils.substring(data, 0, index);
            String val = StringUtils.substring(data, index + 1);
            resMap.put(nm, val);
        }

        return (String)resMap.get(name) == null?"":(String)resMap.get(name);
    }

    public Map coverString2Map(String respMsg) {
        String[] resArr = StringUtils.split(respMsg, "&");
        HashMap resMap = new HashMap();

        for(int i = 0; i < resArr.length; ++i) {
            String data = resArr[i];
            int index = StringUtils.indexOf(data, '=');
            String nm = StringUtils.substring(data, 0, index);
            String val = StringUtils.substring(data, index + 1);
            resMap.put(nm, val);
        }

        return resMap;
    }


    public static String coverMap2String(Map<String, String> data) {
        TreeMap tree = new TreeMap();
        Iterator it = data.entrySet().iterator();
        while(it.hasNext()) {
            Entry sf = (Entry)it.next();
            String en = "";
            if(!"merchantSign".equals(((String)sf.getKey()).trim()) && !"serverSign".equals(((String)sf.getKey()).trim()) && !"serverCert".equals(((String)sf.getKey()).trim())) {
                //去除包含空的字符串
                if(!org.springframework.util.StringUtils.isEmpty(sf.getValue())){
                    tree.put(sf.getKey(), sf.getValue());
                }
            }
        }

        it = tree.entrySet().iterator();
        StringBuffer sf1 = new StringBuffer();

        while(it.hasNext()) {
            Entry en1 = (Entry)it.next();
            sf1.append((String)en1.getKey() + "=" + (String)en1.getValue() + "&");
        }

        return sf1.substring(0, sf1.length() - 1);
    }

    public static String coverMap2StringObject(Map<String, Object> data) {
        TreeMap tree = new TreeMap();
        Iterator it = data.entrySet().iterator();
        while(it.hasNext()) {
            Entry sf = (Entry)it.next();
            String en = "";
            if(!"merchantSign".equals(((String)sf.getKey()).trim()) && !"serverSign".equals(((String)sf.getKey()).trim()) && !"serverCert".equals(((String)sf.getKey()).trim())) {
                //去除包含空的字符串
                if(!org.springframework.util.StringUtils.isEmpty(sf.getValue())){
                    tree.put(sf.getKey(), sf.getValue());
                }
            }
        }

        it = tree.entrySet().iterator();
        StringBuffer sf1 = new StringBuffer();

        while(it.hasNext()) {
            Entry en1 = (Entry)it.next();
            sf1.append((String)en1.getKey() + "=" + (String)en1.getValue() + "&");
        }

        return sf1.substring(0, sf1.length() - 1);
    }

   /* public static String encryptData(String dataString, String encoding, String svrCertPath) {
        Object data = null;

        try {
            byte[] data1 = encryptedPin(getPublicKeyfromPath(svrCertPath), dataString.getBytes(encoding));
            return new String(base64Encode(data1), encoding);
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public static byte[] encryptedPin(PublicKey publicKey, byte[] plainPin) throws Exception {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());
            e.init(1, publicKey);
            int blockSize = e.getBlockSize();
            int outputSize = e.getOutputSize(plainPin.length);
            int leavedSize = plainPin.length % blockSize;
            int blocksSize = leavedSize != 0?plainPin.length / blockSize + 1:plainPin.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];

            for(int i = 0; plainPin.length - i * blockSize > 0; ++i) {
                if(plainPin.length - i * blockSize > blockSize) {
                    e.doFinal(plainPin, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    e.doFinal(plainPin, i * blockSize, plainPin.length - i * blockSize, raw, i * outputSize);
                }
            }

            return raw;
        } catch (Exception var9) {
            throw new Exception(var9.getMessage());
        }
    }*/

    public static byte[] base64Encode(byte[] inputByte) throws IOException {
        return Base64.encode(inputByte);
    }
}
