package com.len;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class JiaMi {
    private static final String IV_STRING = "sdf4ddfsFD86Vdf2";
    private static final String encoding = "gbk";

    public static void main(String[] args) {
        try {
            saveDecryptFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveDecryptFile() throws Exception {
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            FileInputStream fs = new FileInputStream(new File("C:\\Users\\90411\\Desktop\\123.txt"));
            isr = new InputStreamReader(fs,"gbk");
            File outf = new File("C:\\Users\\90411\\Desktop\\ret.txt");

            FileOutputStream fos = new FileOutputStream(outf);
            osw  =new OutputStreamWriter(fos,"gbk");
            char[] c = new char[1024*10];
            StringBuffer sb = new StringBuffer();
            int size = 0;
            while ((size= isr.read(c)) != -1){
                String s = new String(c,0,size);
                sb.append(s);
            }
            String [] str = sb.toString().split("#123#");
            for(int i=0;i<str.length;i++){
                String ret = decryptAES(str[i],"dong123456123456");
                osw.flush();
                osw.write(ret);
            }

        }finally {
            isr.close();
            osw.close();
        }
    }

    public static String encryptAES(String content, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] byteContent = content.getBytes(encoding);
        // 注意，为了能与 iOS 统一
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = key.getBytes(encoding);
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = IV_STRING.getBytes(encoding);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        // 同样对加密后数据进行 base64 编码
        String base64 = new Base64().encodeToString(encryptedBytes);
        //进行url编码 去掉= ? &
        return URLEncoder.encode(base64, encoding);
    }

    public static String decryptAES(String content, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        //URL解码
        content = URLDecoder.decode(content, encoding);
        // base64 解码
        byte[] encryptedBytes = Base64.decodeBase64(content);
        byte[] enCodeFormat = key.getBytes(encoding);
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = IV_STRING.getBytes(encoding);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] result = cipher.doFinal(encryptedBytes);
        return new String(result, encoding);
    }
}
