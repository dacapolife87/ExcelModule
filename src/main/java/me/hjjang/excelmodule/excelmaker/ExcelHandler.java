package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.annotation.ExcelCellMapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcelHandler<T> {

    private static final String XLS_EXTENSION = ".xlsx";

    public void excelMaker(String fileName,List<T> dataList, Class clazz) throws IllegalAccessException, NoSuchFieldException, IOException {
        File file = new File(fileName + XLS_EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(file)) {

            XSSFWorkbook newWorkBook = new XSSFWorkbook();
            XSSFSheet newSheet = newWorkBook.createSheet();
            Map<Integer, String> headerIndexMap = new HashMap<>();
            List<String> headers = new ArrayList<>();
            Field[] dtoFields = clazz.getDeclaredFields();
            
            getTargetFieldForWriteExcel(dtoFields, headers, headerIndexMap);
            inputHeader(newSheet, headers);
            writeCell(dataList, newSheet, headers, headerIndexMap, clazz);
            newWorkBook.write(fos);
        }

    }

    private void writeCell(List<T> dataList, XSSFSheet newSheet, List<String> headers, Map<Integer, String> headerIndexMap, Class clazz) throws IllegalAccessException, NoSuchFieldException {
        for (int i = 0; i < dataList.size(); i++) {
            XSSFRow bodyRow = newSheet.createRow(i + 1);
            T object = dataList.get(i);
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = bodyRow.createCell(j);

                String fieldName = headerIndexMap.get(j);

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                if (fieldType == LocalDateTime.class) {
                    String date = ((LocalDateTime) field.get(object)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
                    cell.setCellValue(date);
                } else if (fieldType == long.class || fieldType == Long.class) {
                    cell.setCellValue((Long) field.get(object));
                } else if (fieldType == int.class || fieldType == Integer.class) {
                    cell.setCellValue((Integer) field.get(object));
                } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                    cell.setCellValue((Boolean) field.get(object));
                } else {
                    cell.setCellValue((String) field.get(object));
                }

            }
        }
    }

    private void getTargetFieldForWriteExcel(Field[] dtoFields, List<String> headers, Map<Integer, String> headerIndexMap) {
        int index = 0;
        for (Field dtoField : dtoFields) {
            if (dtoField.isAnnotationPresent(ExcelCellMapping.class)) {
                ExcelCellMapping excelCellMapping = dtoField.getAnnotation(ExcelCellMapping.class);
                headers.add(excelCellMapping.name());
                headerIndexMap.put(index, dtoField.getName());
                index++;
            }
        }
    }

    private void inputHeader(XSSFSheet newSheet, List<String> headers) {
        XSSFRow headerRow = newSheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
    }
}
