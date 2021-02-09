package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.excelmaker.type.HSSFExcelManager;
import me.hjjang.excelmodule.excelmaker.type.XSSFExcelManager;

public class ExcelManagerFactory {

    public static ExcelManager create(String excelType) {
        ExcelManager excelManager;
        switch (excelType) {
            case "xlsx":
                excelManager = new XSSFExcelManager();
                break;
            case "xls":
                excelManager = new HSSFExcelManager();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + excelType);
        }

        return excelManager;
    }

}
