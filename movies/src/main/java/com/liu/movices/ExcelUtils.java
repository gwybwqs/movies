package com.liu.movices;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: luoben
 * @Date:Created in 2019-04-02 20:45
 * @Modified By:
 */

public class ExcelUtils {

    public static List<List<Object>> readAllExcel(InputStream excelStream) throws Exception {
        return readExcel(excelStream, null);
    }

    public static List<List<Object>> readExcel(InputStream excelStream) throws Exception {
        return readExcel(excelStream, 0);
    }

    public static List<List<Object>> readExcel(InputStream excelStream, Integer sheetIndex) throws Exception {
        List<List<Object>> datas = new ArrayList();
        Workbook workbook = WorkbookFactory.create(excelStream);
        if (sheetIndex == null) {
            int sheetSize = workbook.getNumberOfSheets();
            for (int j = 0; j < sheetSize; j++) {
                readData(j, datas, workbook);
            }
        } else {
            readData(sheetIndex, datas, workbook);
        }
        return datas;
    }

    protected static void readData(int sheetIndex, List<List<Object>> datas, Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            Row row = sheet.getRow(i);
            short cellNum = row.getLastCellNum();
            List<Object> item = new ArrayList(cellNum);
            for (int j = 0; j < cellNum; j++) {
                Cell cell = row.getCell(j);
                Object value = getCellValue(cell);
                item.add(value);
            }
            datas.add(item);
        }
    }

    public static void setCellValue(Cell cell, Object value) {
        if (value != null) {
            if ((value instanceof String)) {
                cell.setCellValue((String) value);
            } else if ((value instanceof Number)) {
                cell.setCellValue(Double.parseDouble(String.valueOf(value)));
            } else if ((value instanceof Boolean)) {
                cell.setCellValue(((Boolean) value).booleanValue());
            } else if ((value instanceof Date)) {
                cell.setCellValue((Date) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }

    public static Object getCellValue(Cell cell) {
        Object value = null;
        if (null != cell) {
            switch (cell.getCellType()) {
                case 3:
                    break;
                case 4:
                    value = Boolean.valueOf(cell.getBooleanCellValue());
                    break;
                case 5:
                    break;
                case 2:
                    Workbook wb = cell.getSheet().getWorkbook();
                    CreationHelper crateHelper = wb.getCreationHelper();
                    FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                    value = getCellValue(evaluator.evaluateInCell(cell));
                    break;
                case 0:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue();
                    } else {
                        value = NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                    break;
                case 1:
                    value = cell.getRichStringCellValue().getString();
                    break;
                default:
                    value = null;
            }
        }
        return value;
    }
}
