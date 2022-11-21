package com.PKCS10;

import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Encoder;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs10.PKCS10;
import sun.security.pkcs10.PKCS10Attribute;
import sun.security.pkcs10.PKCS10Attributes;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.X500Name;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class generates PKCS10 certificate signing request
 *
 * @author
 * @version 1.0
 */
public class GenerateCSR {
    private static KeyPair keypair = null;
    private static KeyPairGenerator keyGen = null;
    private static GenerateCSR gcsr = null;
    private String path = "/usr/local/cer/";
    private static final String KEYSTORE_PASSWORD = "12345678";

    /**
     * 证书提供人：BC
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private GenerateCSR() {
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(2048,new SecureRandom());
        keypair = keyGen.generateKeyPair();
    }

    public static GenerateCSR getInstance() {
        if (gcsr == null)
            gcsr = new GenerateCSR();
        return gcsr;
    }

    public String getCSR(String[] info,String cerPath) throws Exception {
        cerPath = cerPath.isEmpty() ? path : cerPath;
        byte[] csr = generatePKCS10(info[0], info[1], info[2], info[3], info[4], info[5], cerPath);
        Files.write(Paths.get(cerPath + info[0] + ".csr"), csr);
        return new String(csr);
    }

    /**
     * @param CN      Common Name, is X.509 speak for the name that distinguishes
     *                the Certificate best, and ties it to your Organization
     * @param OU      Organizational unit
     * @param O       Organization NAME
     * @param L       Location
     * @param S       State
     * @param C       Country
     * @param cerPath
     */
    byte[] generatePKCS10(String CN, String OU, String O,
                          String L, String S, String C, String cerPath) throws Exception {
        // generate PKCS10 certificate request
        String sigAlg = "SHA256withRSA";
        KeyStore inputKeyStore = KeyStore.getInstance("JKS");
        String jkx_keystore_file = cerPath + CN + ".jks";
        FileInputStream fis = new FileInputStream(jkx_keystore_file);
        char[] nPassword = KEYSTORE_PASSWORD.toCharArray();
        inputKeyStore.load(fis, nPassword);
        Enumeration<String> enumeration = inputKeyStore.aliases();
        String keyAlias = null;
        while (enumeration.hasMoreElements()) {
            keyAlias = enumeration.nextElement();
            if (inputKeyStore.isKeyEntry(keyAlias)) {
                java.security.cert.Certificate certificate = inputKeyStore.getCertificate(keyAlias);
                X509Certificate instance = (X509Certificate) certificate;
                byte[] extensionValue = instance.getExtensionValue(X509Extensions.SubjectKeyIdentifier.getId());
                CertificateExtensions exts = new CertificateExtensions();
                exts.set(SubjectKeyIdentifierExtension.IDENT, new SubjectKeyIdentifierExtension(extensionValue));
                PKCS10Attribute extreq = new PKCS10Attribute(PKCS9Attribute.EXTENSION_REQUEST_OID, exts);
                PKCS10 pkcs10 = new PKCS10(instance.getPublicKey(), new PKCS10Attributes(new PKCS10Attribute[]{extreq}));
                Signature signature = Signature.getInstance(sigAlg);
                Key key = inputKeyStore.getKey(keyAlias, nPassword);
                signature.initSign((PrivateKey) key);
                // common, orgUnit, org, locality, state, country
                // csr文件部门置空，请求生成cer证书人员要求
                X500Name x500Name = new X500Name(CN, "", O, L, S, C);
                pkcs10.encodeAndSign(x500Name, signature);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(bs);
                pkcs10.print(ps);
                byte[] c = bs.toByteArray();
                try {
                    ps.close();
                    bs.close();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                return c;
            }
        }
        return "Fail to generate CSR file".getBytes();
    }

    public static KeyPair getKeypair() {
        return keypair;
    }

    public void storeJKS(String[] info, KeyPair keyPair_user, String certFilePath) {
        certFilePath = certFilePath.isEmpty() ? path : certFilePath;
        KeyStore keyStore;
        try {
            //use exited jks file
            keyStore = KeyStore.getInstance("JKS");
            //generate user‘s keystore by info[8] -----keypair
            keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
            X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
            certGen.setSerialNumber(new BigInteger(info[8]));
            certGen.setIssuerDN(new X509Name("CN=" + info[0] + ",OU=,O=" + info[2] + ",C=" + info[3] + ",L=" + info[4] + ",ST=" + info[3]));
            certGen.setNotBefore(new Date(Long.parseLong(info[6])));
            certGen.setNotAfter(new Date(Long.parseLong(info[7])));
            certGen.setSubjectDN(new X509Name("CN=" + info[0] + ",OU=,O=" + info[2] + ",C=" + info[3] + ",L=" + info[4] + ",ST=" + info[3]));
            certGen.setPublicKey(keyPair_user.getPublic());
            certGen.setSignatureAlgorithm("SHA256WithRSA");
            certGen.addExtension(X509Extensions.SubjectKeyIdentifier.getId(), false,new SubjectKeyIdentifier("aykj".getBytes()));
            X509Certificate[] chain = new X509Certificate[1];
            X509Certificate cert = certGen.generateX509Certificate(keyPair_user.getPrivate(), "BC");
            chain[0] = cert;
            keyStore.setKeyEntry(info[0], keyPair_user.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), chain);
            keyStore.store(Files.newOutputStream(Paths.get(certFilePath + info[0] + ".jks")), KEYSTORE_PASSWORD.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void toPFX(String[] info, String certFilePath) {
        certFilePath = certFilePath.isEmpty() ? path : certFilePath;
        try {
            KeyStore inputKeyStore = KeyStore.getInstance("JKS");
            String jkx_keystore_file = certFilePath + info[0] + ".jks";
            FileInputStream fis = new FileInputStream(jkx_keystore_file);
            char[] nPassword;
            nPassword = KEYSTORE_PASSWORD.toCharArray();
            char[] pfxPassword = generatePwd(info[0]).toCharArray();

            inputKeyStore.load(fis, nPassword);
            fis.close();
            KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");
            Enumeration<String> enums = inputKeyStore.aliases();
            while (enums.hasMoreElements()) {
                String keyAlias = enums.nextElement();
                outputKeyStore.load(null, pfxPassword);
                if (inputKeyStore.isKeyEntry(keyAlias)) {
                    Key key = inputKeyStore.getKey(keyAlias, nPassword);
                    java.security.cert.Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
                    X509Certificate[] chain = new X509Certificate[]{(X509Certificate) certChain[0]};
                    outputKeyStore.setKeyEntry(keyAlias, key, pfxPassword, chain);
                }
                String pfx_keystore_file = certFilePath + info[0] + ".pfx";
                FileOutputStream out = new FileOutputStream(pfx_keystore_file);
                outputKeyStore.store(out, pfxPassword);
                out.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CER证书读取内容并文本存储到数据库
     */
    public void readCertificate(String[] info, String certFilePath){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
            //use exited jks file
            KeyStore keyStore = KeyStore.getInstance("JKS");
            //generate user‘s keystore by info[8] -----keypair
            char[] password = KEYSTORE_PASSWORD.toCharArray();
            keyStore.load(Files.newInputStream(Paths.get(certFilePath + info[0] + ".jks")), password);
            X509Certificate userCert =(X509Certificate) cf.generateCertificate(Files.newInputStream(Paths.get(certFilePath + info[0] + ".cer")));
            boolean verifyUserCert = verifyUserCert(userCert.getPublicKey(), (PrivateKey) keyStore.getKey(info[0], password));
            if(!verifyUserCert){
                throw new Exception("证书密钥不匹配");
            }
            ClassPathResource rootCerFile = new ClassPathResource("cer" + File.separator + "CCS NETCA Root L1.cer");
            ClassPathResource subCerFile = new ClassPathResource("cer" + File.separator + "CCS NETCA L1 Sub1 CA.cer");
            X509Certificate rootCer =(X509Certificate) cf.generateCertificate(rootCerFile.getInputStream());
            X509Certificate subCer =(X509Certificate) cf.generateCertificate(subCerFile.getInputStream());
            keyStore.setCertificateEntry("CCS NETCA L1 Sub1 CA",subCer);
            keyStore.setCertificateEntry("CCS NETCA Root L1",rootCer);
            X509Certificate[] chain = new X509Certificate[3];
            chain[0] = userCert;
            chain[1] = subCer;
            chain[2] = rootCer;
            keyStore.setKeyEntry(info[0], keyStore.getKey(info[0], password), password, chain);
            keyStore.store(Files.newOutputStream(Paths.get(certFilePath + info[0] + ".jks")), password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 验证密钥对
     *
     * @Author: yml
     * @CreateDate: 2022/8/10
     * @UpdateUser: yml
     * @UpdateDate: 2022/8/10
     * @Version: 0.0.1
     * @param publicKey
     * @param privateKey
     * @return : void
     */
    private boolean verifyUserCert(PublicKey publicKey, PrivateKey privateKey) {
        try {
            //创建challenge
            byte[] challenge = new byte[10000];
            ThreadLocalRandom.current().nextBytes(challenge);

            //使用私钥签名challenge
            Signature sig = Signature.getInstance("SHA256WithRSA");
            sig.initSign(privateKey);
            sig.update(challenge);
            byte[] signature = sig.sign();

            //使用公钥验证challenge
            sig.initVerify(publicKey);
            sig.update(challenge);

            return sig.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generatePwd(String name){
        StringBuilder outChars = null;
        try {
            //第一步，获取MessageDigest对象，参数为MD5字符串，表示这是一个MD5算法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            // 第二步，输入源数据，参数类型为byte[]
            String solt = "aykj";
            md5.update(solt.concat(name).getBytes());

            // 第三步，计算MD5值
            /*
             * digest() 方法返回值是一个字节数组类型的 16 位长度的哈希值，通常，我们会
             * 转化为十六进制的 32 位长度的字符串来使用，可以利用 BigInteger 类来做这个转化：
             */
            BigInteger bigInt = new BigInteger(1, md5.digest());
            // 要使用生成 密码 的字符
            String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
                    "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                    "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                    "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                    "U", "V", "W", "X", "Y", "Z"
            };
            long lHexLong = 0x3FFFFFFF & bigInt.longValue();
            outChars = new StringBuilder();
            int sixValue = 8;
            for (int j = 0; j < sixValue; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars.append(chars[(int) index]);
                // 每次循环按位右移 2 位
                lHexLong = lHexLong >> 2;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return outChars.toString();
    }

    public static void main(String[] args) throws Exception {
        GenerateCSR gcsr = GenerateCSR.getInstance();
        //// 时间格式转换为字符串
        //// 现在的时间
        //LocalDateTime dt = LocalDateTime.now();
        //dt = dt.plusYears(1L);
        ////生成公钥
        //String[] info = {"一汽物流（天津）有限公司", "一汽物流（天津）有限公司", "一汽物流（天津）有限公司", "CN", "CN", "CN", String.valueOf(System.currentTimeMillis()), String.valueOf(dt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()), "2"};
        ////生成jks
        //gcsr.storeJKS(info, getKeypair(), "");
        ////生成csr
        //String csr = gcsr.getCSR(info,"");
        //gcsr.readCertificate(info, "");
        //gcsr.toPFX(info, "");
        //byte[] b = Base64Util.fileToByte(gcsr.path + info[0] + ".pfx");
        ////遍历
        //for(int i=0;i<b.length;i++) {
        //    b[i] += 1;
        //}
        //Base64Util.byteArrayToFile(b,gcsr.path + info[0] + ".iyin");
        //byte[] b2 = Base64Util.fileToByte(gcsr.path + info[0] + ".iyin");
        ////遍历
        //for(int i=0;i<b2.length;i++) {
        //    b2[i] -= 1;
        //}
        //Base64Util.byteArrayToFile(b2,gcsr.path + info[0] + "2.pfx");

        gcsr.generateCer();
    }

    private static String CTFC_DOMAIN_NAME = "domainName";			//CN：用户姓名或域名
    private static String CTFC_ORG_UNIT_NAME = "orgUnitName";       //OU：组织单位名称
    private static String CTFC_ORG_NAME = "orgName";                //O：组织名称
    private static String CTFC_COUNTRY_CODE = "countryCode";        //C：单位的两字母国家代码
    private static String CTFC_CITY = "city";                       //L：城市或区域
    private static String CTFC_PROVINCE = "province";               //ST：省份或州

    private static String CTFC_VALID_START_TIME = "validStartTime"; //证书有效起始时间
    private static String CTFC_VALID_END_TIME = "validEndTime";     //证书有效截止时间
    private static String CTFC_SERIAL_NUMBER = "serialNumber";      //序列号域
    private static String CTFC_SIG_AlG = "signatureAlgorithm";      //签名算法
    private static String CTFC_ENCRYPT_TYPE = "encryptType";      	//加密类型
    private static String CTFC_ENCRYPT_NUM = "encryptNum";      	//加密位数
    private static String CTFC_PROVIDER = "provider";      			//提供人
    /**
     * 生成cer证书
     */
    public void generateCer() throws Exception {
        // 现在的时间
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime dt1 = dt.plusYears(1L);
        BigInteger serialNumber = new BigInteger(String.valueOf(System.currentTimeMillis() / 1000L));

        //构建生成证书请求参数
        HashMap<String, Object> infoMap = new HashMap<>();
        infoMap.put(CTFC_DOMAIN_NAME, "yuanml");        //CN：用户姓名或域名
        infoMap.put(CTFC_ORG_UNIT_NAME, "orgUnitName");         //OU：组织单位名称
        infoMap.put(CTFC_ORG_NAME, "orgName");                //O：组织名称
        infoMap.put(CTFC_COUNTRY_CODE, "CN");                //C：单位的两字母国家代码
        infoMap.put(CTFC_CITY, "深圳市");                        //L：城市或区域
        infoMap.put(CTFC_PROVINCE, "广东省");                    //ST：省份或州

        infoMap.put(CTFC_VALID_START_TIME, Date.from(dt.toInstant(ZoneOffset.ofHours(8))));    //证书有效起始时间
        infoMap.put(CTFC_VALID_END_TIME, Date.from(dt1.toInstant(ZoneOffset.ofHours(8))));        //证书有效截止时间
        infoMap.put(CTFC_SERIAL_NUMBER, serialNumber);        //序列号域
        infoMap.put(CTFC_SIG_AlG, "SHA256withRSA");            //签名算法
        infoMap.put(CTFC_ENCRYPT_TYPE, "RSA");                //加密类型
        infoMap.put(CTFC_ENCRYPT_NUM, 2048);                    //加密位数
        infoMap.put(CTFC_PROVIDER, "BC");                        //提供人

        // 生成公钥
        //keyGen.initialize(2048,new SecureRandom());
        //KeyPair keyPair_root = keyGen.generateKeyPair();
        //Files.write(Paths.get(path+ "keyPair_root.key"),keyPair_root.getPrivate().getEncoded());

        keyGen.initialize(2048,new SecureRandom());
        KeyPair keyPair_user = keyGen.generateKeyPair();
        Files.write(Paths.get(path+ "keyPair_user.key"),keyPair_user.getPrivate().getEncoded());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeyRoot = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(path+ "keyPair_root.key")));
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeyRoot);
        X509Certificate cert = generateCert(infoMap, privateKey, keyPair_user.getPublic());
        System.out.println("createCerFile, result=\r\n" + cert);
        //生成原格式 cer证书
        String certPath = path + infoMap.get(CTFC_DOMAIN_NAME) + ".cer";
        //证书可以二进制形式存入库表，存储字段类型为BLOB
        Files.write(Paths.get(certPath),cert.getEncoded());
        //生成base64格式 cer证书
        String certPathBase64 = path + infoMap.get(CTFC_DOMAIN_NAME) + "_base64.cer";
        String encode = new BASE64Encoder().encode(cert.getEncoded());
        String base64EncodeCer = "-----BEGIN CERTIFICATE-----\r\n" + encode + "\r\n-----END CERTIFICATE-----\r\n";
        //证书也可以base64格式存入库表，存储字段类型为BLOB
        //Files.write(Paths.get(certPathBase64),base64EncodeCer.getBytes());
        //System.out.println("createBase64CerFileByDecode, result==\r\n" + base64EncodeCer);
    }

    /**
     * 生成cer证书
     * 生成根证书 私钥和公钥 要使用同一个密钥对
     * 生成子证书 证书颁发者要使用上级证书使用者，并且要使用上级证书私钥
     * @param infoMap
     * @param keyPair_root 证书签名私钥
     * @param keyPair_user 证书公钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws SecurityException
     * @throws SignatureException
     */
    public X509Certificate generateCert(HashMap<String,Object> infoMap, PrivateKey keyPair_root, PublicKey keyPair_user)
            throws InvalidKeyException, NoSuchProviderException, SecurityException, SignatureException {

        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setSerialNumber((BigInteger) infoMap.get(CTFC_SERIAL_NUMBER));

        //证书颁发者，这里自颁发，颁发者和使用者一致，可以只是用户或域名，
        // 也可以是部分或完整的用户信息（OU：组织单位名称、O：组织单位、C：单位的两字母国家代码、L：城市或区域、ST：省份或州）
        //certGen.setIssuerDN(new X509Name("CN=" + (String) infoMap.get(CTFC_DOMAIN_NAME)));
        certGen.setIssuerDN(new X509Name("CN = yuan"));
        //        + ", OU=" + (String) infoMap.get(CTFC_ORG_UNIT_NAME)
        //        + ", O=" + (String) infoMap.get(CTFC_ORG_NAME)
        //        + ", C=" + (String) infoMap.get(CTFC_COUNTRY_CODE)
        //        + ", L=" + (String) infoMap.get(CTFC_CITY)
        //        + ", ST=" + (String) infoMap.get(CTFC_PROVINCE)));

        certGen.setNotBefore((Date) infoMap.get(CTFC_VALID_START_TIME));
        certGen.setNotAfter((Date) infoMap.get(CTFC_VALID_END_TIME));

        //证书使用者，这里自颁发，使用者和颁发者一致，可以只是用户或域名，也可以是完整的用户信息（组织单位名称、组织单位、国家代码、城市或区域、省份或州）
        certGen.setSubjectDN(new X509Name("CN=" + (String) infoMap.get(CTFC_DOMAIN_NAME)));
        //certGen.setSubjectDN(new X509Name("CN=" + (String) infoMap.get(CTFC_DOMAIN_NAME)
        //        + ", OU=" + (String) infoMap.get(CTFC_ORG_UNIT_NAME)
        //        + ", O=" + (String) infoMap.get(CTFC_ORG_NAME)
        //        + ", C=" + (String) infoMap.get(CTFC_COUNTRY_CODE)
        //        + ", L=" + (String) infoMap.get(CTFC_CITY)
        //        + ", ST=" + (String) infoMap.get(CTFC_PROVINCE)));
        certGen.setPublicKey(keyPair_user);
        certGen.setSignatureAlgorithm((String) infoMap.get(CTFC_SIG_AlG));

        return certGen.generateX509Certificate(keyPair_root, (String) infoMap.get(CTFC_PROVIDER));
    }
}
