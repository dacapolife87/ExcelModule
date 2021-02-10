package me.hjjang.excelmodule.excelmaker;

import me.hjjang.excelmodule.excelmaker.type.HSSFExcelManager;
import me.hjjang.excelmodule.excelmaker.type.XSSFExcelManager;

public class ExcelManagerFactory {

    private static final String FILE_EXTENSION_XLSX = "xlsx";
    private static final String FILE_EXTENSION_XLS = "xls";

    public static ExcelManager create(String excelType) {
        ExcelManager excelManager;
        switch (excelType) {
            case FILE_EXTENSION_XLSX:
                excelManager = new XSSFExcelManager();
                break;
            case FILE_EXTENSION_XLS:
                excelManager = new HSSFExcelManager();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + excelType);
        }

        return excelManager;
    }

}
