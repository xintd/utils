package com.word.fillwords;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 读取Excel数据的工具类
 * @Author JCB
 * @Date  2021/1/29
 **/
@Slf4j
public class ReadExcelUtil {

    /**
     * 读取某区域数据
     * @Author JCB
     * @Date  2021/1/29
     **/
    public static List<List> getAreaFromExcel(String path, String sheet, int startRow, int endRow) throws Exception {
        File xlsx = new File(path);
        Workbook workbook = WorkbookFactory.create(xlsx);
        Sheet sheet1 = workbook.getSheet(sheet);
        List<List> totalList = new ArrayList<>();
        for (int i = startRow-1; i < endRow-1; i++) {
            Row row1 = sheet1.getRow(i);
            List<String> list = new ArrayList<>();
            int rowNum = row1.getLastCellNum();
            for (int j = 0; j < rowNum; j++) {
                list.add(getCellValueByCell(row1.getCell(j)));
            }
            totalList.add(list);
        }
        workbook.close();
        log.info("文件名：{}，sheet：{}，startRow：{}，endRow：{}，取值：{}", path, sheet, startRow, endRow, totalList.toString());
        return totalList;
    }

    //获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        CellType cellType = cell.getCellType();

        // 以下是判断数据的类型
        switch (cellType) {
            case NUMERIC: // 数字

                if (CellType.NUMERIC == cellType) {//判断单元格的类型是否则NUMERIC类型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为日期类型
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm");
                        cellValue = formater.format(date);
                    } else {
                        cellValue = cell.getNumericCellValue() + "";
                    }
                }
                break;
            case STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;

        }
        return cellValue;
    }

    /**
     * 根据某行数据生成需要填充数据的map
     * @Author JCB
     * @Date  2021/1/29
     **/
    private static HashMap<String, Object> getMapByList(List<String> row) {
        HashMap<String, Object> map = new HashMap<>();
        String name = row.get(0);
        String identity = row.get(1);
        map.put("name", name);
        map.put("identity", identity);
        return map;
    }


    public static void main(String[] args) throws Exception{
        String path = "E:/1.xlsx";
        HashMap<String, Object> map;
        List<List> areaFromExcel = getAreaFromExcel(path, "Sheet1", 2, 10);
        for (List row : areaFromExcel) {
            map = getMapByList(row);
            WriteWordUtil.templateWrite2("E:/1.docx", map, "E:/" + map.get("name")+".docx");
        }
    }
}
