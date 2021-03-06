package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.annotation.ExcelCellMapping;
import me.hjjang.excelmodule.excelmaker.enumtype.ExcelDataType;
import me.hjjang.excelmodule.excelmaker.sheetarea.ExcelHeader;
import me.hjjang.excelmodule.excelmaker.sheetarea.ExcelSheetData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ExcelHandler<T> {

    private static final String FILE_DOT = ".";
    private static final String METHOD_SET_CELL_VALUE = "setCellValue";
    private static final String FILE_EXTENSION_XLSX = "xlsx";
    private static final String FILE_EXTENSION_XLS = "xls";

    private String excelType;

    private ExcelHandler(String excelType) {
        this.excelType = excelType;
    }

    public static ExcelHandler createXLSX() {
        return new ExcelHandler(FILE_EXTENSION_XLSX);
    }

    public static ExcelHandler createXLS() {
        return new ExcelHandler(FILE_EXTENSION_XLS);
    }


    public void excelMaker(String fileName, List<T> dataList, Class clazz) throws IllegalAccessException, NoSuchFieldException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        File file = newExcelFile(fileName, excelType);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            ExcelManager excelManager = ExcelManagerFactory.create(excelType);
            ExcelHeader excelHeader = getTargetFieldForWriteExcel(clazz);
            ExcelSheetData excelSheetData = new ExcelSheetData(dataList);

            writeData(excelManager, excelHeader, excelSheetData);
            excelManager.write(fos);
        }
    }

    private File newExcelFile(String fileName, String excelType) {
        return new File(fileName + FILE_DOT + excelType);
    }

    private void writeData(ExcelManager excelManager, ExcelHeader excelHeader, ExcelSheetData excelSheetData) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        inputHeader(excelManager, excelHeader.getHeaders());

        for (int i = 0; i < excelSheetData.dataSize(); i++) {
            writeRow(excelManager, excelHeader, excelSheetData.data(i));
        }
    }

    private void writeRow(ExcelManager excelManager, ExcelHeader excelHeader, Object object) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Row bodyRow = excelManager.newRow();
        for (int j = 0; j < excelHeader.headerSize(); j++) {
            Cell cell = bodyRow.createCell(j);
            Field field = getField(excelHeader.getClassType(), excelHeader.getFieldName(j));
            inputCelLValue(field, cell, object, excelManager.getWorkBook());
        }
    }

    private void inputCelLValue(Field field, Cell cell, Object object, Workbook workbook) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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

    private void setCellStype(Field field, Cell cell, Workbook workbook) {
        ExcelCellMapping excelCellMapping = field.getDeclaredAnnotation(ExcelCellMapping.class);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(excelCellMapping.type().style());
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
        Row headerRow = excelManager.newRow();
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
    }
}
