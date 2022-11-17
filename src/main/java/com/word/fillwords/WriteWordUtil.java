package com.word.fillwords;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Admin
 */
public class WriteWordUtil {

    /**
     * word占位用{{object}}比较完美可以填充图片
     *
     * @param filePath
     * @param params
     * @param outFilePath
     * @return
     * @throws Exception
     */
    public static String templateWrite2(String filePath, Map<String, Object> params, String outFilePath) throws Exception {
        XWPFTemplate template = XWPFTemplate.compile(filePath).render(params);
        FileOutputStream out = new FileOutputStream(outFilePath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
        return "";
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "123");//
        params.put("identity", "123");//
        templateWrite2("E:\\1.docx", params, "E:\\7.docx");

    }
}
