package me.hjjang.excelmodule.excelmaker.type;

import me.hjjang.excelmodule.excelmaker.ExcelManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XSSFExcelManager implements ExcelManager {
    private final XSSFWorkbook workBook;
    private final XSSFSheet xssfSheet;
    private int rowNum = 0;

    public XSSFExcelManager() {
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
