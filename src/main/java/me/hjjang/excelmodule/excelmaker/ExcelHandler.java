package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.annotation.ExcelCellMapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExcelHandler<T> {

    private static final String XLS_EXTENSION = ".xlsx";
    private static final String METHOD_SET_CELL_VALUE = "setCellValue";

    public void excelMaker(String fileName,List<T> dataList, Class clazz) throws IllegalAccessException, NoSuchFieldException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        File file = new File(fileName + XLS_EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ExcelManager excelManager = new ExcelManager();

            ExcelHeader excelHeader = getTargetFieldForWriteExcel(clazz);
            ExcelSheetData excelSheetData = new ExcelSheetData(dataList);

            writeData(excelManager, excelHeader, excelSheetData);
            excelManager.write(fos);
        }

    }

    private void writeData(ExcelManager excelManager, ExcelHeader excelHeader, ExcelSheetData excelSheetData) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        inputHeader(excelManager, excelHeader.getHeaders());

        for (int i = 0; i < excelSheetData.dataSize(); i++) {
            writeRow(excelManager, excelHeader, excelSheetData.data(i));
        }
    }

    private void writeRow(ExcelManager excelManager, ExcelHeader excelHeader, Object object) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        XSSFRow bodyRow = excelManager.newRow();
        for (int j = 0; j < excelHeader.headerSize(); j++) {
            Cell cell = bodyRow.createCell(j);
            Field field = getField(excelHeader.getClassType(), excelHeader.getFieldName(j));
            inputCelLValue(field, cell, object, excelManager.getWorkBook());
        }
    }

    private void inputCelLValue(Field field, Cell cell, Object object, XSSFWorkbook workbook) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        setCellValue(field, cell, object);
        setCellStype(field, cell, workbook);
    }

    private Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    private void setCellValue(Field field, Cell cell, Object object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();
        ExcelDataType dataTypeClazz = ExcelDataType.findByClassType(fieldType);
        Method setCellValue = cell.getClass().getMethod(METHOD_SET_CELL_VALUE, dataTypeClazz.value());
        setCellValue.invoke(cell, field.get(object));
    }

    private void setCellStype(Field field, Cell cell, XSSFWorkbook workbook) {
        ExcelDataType dataTypeClazz = ExcelDataType.findByClassType(field.getType());

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataTypeClazz.style());
        cell.setCellStyle(cellStyle);
    }

    private ExcelHeader getTargetFieldForWriteExcel(Class clazz) {
        Field[] dtoFields = clazz.getDeclaredFields();
        ExcelHeader excelHeader = new ExcelHeader(clazz);

        for (Field dtoField : dtoFields) {
            if (dtoField.isAnnotationPresent(ExcelCellMapping.class)) {
                ExcelCellMapping excelCellMapping = dtoField.getAnnotation(ExcelCellMapping.class);
                excelHeader.addHeader(excelCellMapping.name(), dtoField.getName());
            }
        }
        return excelHeader;
    }

    private void inputHeader(ExcelManager excelManager, List<String> headers) {
        XSSFRow headerRow = excelManager.newRow();
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
    }
}
