package com.word.aposeCrack;

import com.aspose.words.License;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.InputStream;

/**
 * @ClassName: Crack.java
 * @Description:  对aspose-words-22.11-jdk17.jar去除数量限制和水印
 * @Author: yml
 * @CreateDate: 2022/11/17
 * @UpdateUser: yml
 * @UpdateDate: 2022/11/17
 * @Version: 1.0.0
 */
public class Crack {

    public static void main(String[] args) {
        //try(InputStream is = Crack.class.getClassLoader().getResourceAsStream("license.xml")) {
        //    License license = new License();
        //    license.setLicense(is);
        //} catch (Exception e) {
        //    throw new RuntimeException(e);
        //}
        modifyWordsJar();
    }

    /**
     * 修改words jar包里面的校验
     */
    public static void modifyWordsJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("C:\\Users\\Administrator\\Downloads\\aspose-words-22.12-jdk17.jar");
            //获取指定的class文件对象
            CtClass zzYgOClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzYvW");
            //从class对象中解析获取指定的方法
            CtMethod[] methodA = zzYgOClass.getDeclaredMethods("zzjS");
            //遍历重载的方法
            /**
             * this.zzWn1 = new java.util.Date(Long.MAX_VALUE);//Date赋值最大值
             * this.zzWHy = zzZy.zzWrO;//直接返回验证成功的执行
             * zzYRG = this;//直接返回验证成功的执行
             * {this.zzZ2a = new java.util.Date(Long.MAX_VALUE);this.zzWHy = com.aspose.words.zzZy.zzWrO;zzYRG = this;}
             */
            for (CtMethod ctMethod : methodA) {
                CtClass[] ps = ctMethod.getParameterTypes();
                if ("zzjS".equals(ctMethod.getName()) && ps[0].getName().contains("InputStream")) {
                    System.out.println("ps[0].getName==" + ps[0].getName());
                    //替换指定方法的方法体
                    ctMethod.setBody("{this.zzZxB = new java.util.Date(253402300799999L);this.zzWbe = com.aspose.words.zzZ8h.zzW8b;zzZAO = this;}");
                }
            }
            //这一步就是将破译完的代码放在桌面上
            zzYgOClass.writeFile("C:\\Users\\Administrator\\Downloads\\");

            //获取指定的class文件对象
            CtClass zzZUiClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzZ6D");
            //从class对象中解析获取指定的方法
            CtMethod methodB = zzZUiClass.getDeclaredMethod("zzZ1U");
            //替换指定方法的方法体
            methodB.setBody("{return 256;}");
            //这一步就是将破译完的代码放在桌面上
            zzZUiClass.writeFile("C:\\Users\\Administrator\\Downloads\\");

            // 除jar包里面的.RSA和.SF文件
        } catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }

}
