package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.annotation.DateTimeFormat;
import me.hjjang.excelmodule.annotation.ExcelCellMapping;
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

    private final String excelType;

    public ExcelHandler() {
        System.out.println("ExcelType Set DefaultValue : xlsx !!");
        this.excelType = "xlsx";
    }

    public ExcelHandler(String excelType) {
        this.excelType = excelType;
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

        for (int dataIndex = 0; dataIndex < excelSheetData.dataSize(); dataIndex++) {
            writeRow(excelManager, excelHeader, excelSheetData.data(dataIndex));
        }
    }

    private void writeRow(ExcelManager excelManager, ExcelHeader excelHeader, Object object) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Row bodyRow = excelManager.newRow();
        for (int headerIndex = 0; headerIndex < excelHeader.headerSize(); headerIndex++) {
            Cell cell = bodyRow.createCell(headerIndex);
            Field field = getField(excelHeader.getClassType(), excelHeader.getFieldName(headerIndex));

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
        ExcelDataType dataTypeClazz = ExcelDataType.findByClassType(field.getType());

        CellStyle cellStyle = workbook.createCellStyle();
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
        Row headerRow = excelManager.newRow();
        for (int headerIndex = 0; headerIndex < headers.size(); headerIndex++) {
            Cell cell = headerRow.createCell(headerIndex);
            cell.setCellValue(headers.get(headerIndex));
        }
    }
}
