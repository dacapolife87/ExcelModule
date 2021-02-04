package me.hjjang.excelmodule.excelmaker;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelManager {
    private final XSSFWorkbook workBook;
    private final XSSFSheet xssfSheet;
    private int rowNum = 0;

    public ExcelManager() {
        this.workBook = new XSSFWorkbook();
        this.xssfSheet = workBook.createSheet();
    }

    public XSSFWorkbook getWorkBook() {
        return workBook;
    }

    public void write(FileOutputStream fos) throws IOException {
        workBook.write(fos);
    }

    public XSSFRow newRow() {
        return xssfSheet.createRow(rowNum++);

    }
}
