package com.common.utils;

import com.google.common.collect.Lists;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/6/18.
 * 常用的字符串处理方法 carter
 */
final public class StringUtils {


    /**
     * 打印出字节数组，方便查看
     * @param bytes
     * @return
     */
    public static String printByteArray(byte[] bytes) {

        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes) {
            stringBuffer.append(b).append(" ");
        }
        return stringBuffer.toString();
    }


    /**
     * 把浮点数转换为只保留两位小数的字符串
     * @param num
     * @return
     */
    public static String getDecimalString(double num)
    {
        if(num== 0d)
        {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String decimalString = decimalFormat.format(num);
        return  decimalString;
    }
    /**
     * 把浮点数转换为只保留两位小数的字符串
     * @param num
     * @return
     */
    public static String getThreeDecimalString(double num)
    {
        if(num== 0d)
        {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        String decimalString = decimalFormat.format(num);
        return  decimalString;
    }


    /**
     * 判断字符串是否是非空或非null
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s)
    {
        return ! isEmpty(s);
    }

    /**
     * 判断字符串是否是空字符串或null
     * @param s
     * @return
     */
    public static boolean isEmpty(Object s)
    {
        if(s == null)
            return true;
        if("".equals(s.toString()) )
            return true;
        return false ;
    }

    /**
     * 把字符串拆分成字符串数组
     * @param splitString  拆分的支付穿
     * @param splitToken   字符串分隔符
     * @return
     */
    public static   List<String> split2List(String splitString, String  splitToken)
    {
        return split2List(splitString,splitToken,false);
    }

    /**
     *  * 把字符串拆分成字符串数组
     * @param splitString  拆分的支付穿
     * @param splitToken   字符串分隔符
     * @param keepEmptyStr  是否保留空字符串
     * @return
     */
    public static   List<String> split2List(String splitString, String  splitToken, boolean keepEmptyStr)
    {
        List<String>  stringList = Lists.newArrayList();
        if(isEmpty(splitString)) return  stringList;
        if(isEmpty(splitToken)) return  stringList;

        StringTokenizer stringTokenizer = new StringTokenizer(splitString,splitToken);

        while (stringTokenizer.hasMoreTokens())
        {
            String str = stringTokenizer.nextToken();
            if(!keepEmptyStr && isEmpty(str))
            {
                continue;
            }
            stringList.add(str);

        }

        return  stringList;

    }

    public static void main(String[] args) {
        String[] array = {"a","b","c","c","d","e","e","e","a"};
        Set<String> set = new HashSet<>();
        for(int i=0;i<array.length;i++){
            set.add(array[i]);
        }
        String[] arrayResult =  set.toArray(new String[set.size()]);
        System.out.println(Arrays.toString(arrayResult));
        System.out.println("**************************");
        System.out.println(arrayResult.length);
    }


}

