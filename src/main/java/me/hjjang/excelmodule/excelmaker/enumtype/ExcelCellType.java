package me.hjjang.excelmodule.excelmaker.enumtype;

public enum ExcelCellType {
    STRING(0),
    DATE(14),
    DATETIME(22);

    private int style;

    ExcelCellType(int style) {
        this.style = style;
    }

    public short style() {
        return (short) style;
    }
}
