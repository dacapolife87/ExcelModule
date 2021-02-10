package me.hjjang.excelmodule.excelmaker;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public interface ExcelManager {

    Workbook getWorkBook();
    void write(FileOutputStream fos) throws IOException;
    Row newRow();
}
