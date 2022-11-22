package com.base64;

import com.google.common.io.ByteStreams;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.UrlBase64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.Security;

/**
 * BASE64编码解码工具包
 * 依赖bcprov-jdk
 *
 * @author koala
 */
public class Base64Util {

    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * BASE64字符串解码为二进制数据
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) {
        return Base64.decode(base64.getBytes());
    }

    /**
     * 二进制数据编码为BASE64字符串
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes));
    }

    /**
     * BASE64 encrypt
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] b = UrlBase64.encode(key);
        return new String(b, "UTF-8");
    }

    /**
     * BASE64 decrypt
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        return UrlBase64.decode(key.getBytes("UTF-8"));
    }

    /**
     * 将文件编码为BASE64字符串
     * 大文件慎用，可能会导致内存溢出
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * BASE64字符串转回文件
     * @param filePath 文件绝对路径
     * @param base64   编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * 文件绝对路径
     * @param linuxDir linux存放目录
     * @param winDir   win存放目录
     * @param fileName 文件名
     * @return String
     */
    public static String filePath(String linuxDir, String winDir, String fileName) {
        StringBuilder bf = new StringBuilder();
        String doubleDiagonal = "\\";
        String signleDoagonal = "/";
        if (doubleDiagonal.equals(File.separator)) {
            //windows
            bf.append(winDir);
        } else if (signleDoagonal.equals(File.separator)) {
            //Linux
            bf.append(linuxDir);
        }
        bf.append(File.separator);
        bf.append(fileName);
        return bf.toString();
    }

    /**
     * 文件转换为二进制数组
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(
                    file); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                data = out.toByteArray();
            } catch (FileNotFoundException e) {
                logger.error("FileNotFoundException is {}", e);
            } catch (IOException e) {
                logger.error("IOException is {}", e);
            }
        }
        return data;
    }

    /**
     * 二进制数据写文件
     * @param bytes    二进制数据
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            if (destFile.createNewFile()){
                logger.info("create file is successful!");
            }
        } catch (IOException e) {
            logger.error("IOException is {}" , e);

        }
        try(
            OutputStream out = new FileOutputStream(destFile)) {
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException is {}" , e);
        } catch (IOException e) {
            logger.error("The IOException is {}" , e);
        }
    }

    public static void main(String[] args) {
        String inFilePath = "Z:\\Temp\\abc.txt";
        String outFilePath = "Z:\\Temp\\efg.txt";

        final byte[] bytes = fileToByte(inFilePath);

        byteArrayToFile(bytes , outFilePath);
    }

    /**
     * Base64字符串转InputStream流对象
     *
     * @param base64string
     * @return : java.io.InputStream
     */
    public static InputStream baseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64string);
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stream;
    }

    /**
     * InputStream流对象转Base64字符串
     *
     * @param inputStream
     * @return : java.lang.String
     */
    public static String inputStreamToBase64(InputStream inputStream) throws IOException {
        byte[] imageBytes = ByteStreams.toByteArray(inputStream);
        inputStream.read(imageBytes, 0, imageBytes.length);
        inputStream.close();
        return org.apache.commons.codec.binary.Base64.encodeBase64String(imageBytes);
    }

}
