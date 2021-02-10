package me.hjjang.excelmodule.excelmaker.type;

import me.hjjang.excelmodule.excelmaker.ExcelManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class HSSFExcelManager implements ExcelManager {
    private final HSSFWorkbook workBook;
    private final HSSFSheet hssfSheet;
    private int rowNum = 0;

    public HSSFExcelManager() {
        this.workBook = new HSSFWorkbook();
        this.hssfSheet = workBook.createSheet();
    }

    public HSSFWorkbook getWorkBook() {
        return workBook;
    }

    public void write(FileOutputStream fos) throws IOException {
        workBook.write(fos);
    }

    public HSSFRow newRow() {
        return hssfSheet.createRow(rowNum++);

    }
}
