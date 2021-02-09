package me.hjjang.excelmodule.excelmaker.sheetarea;

import java.util.List;

public class ExcelSheetData<T> {

    private List<T> dataList;

    public ExcelSheetData(List<T> dataList) {
        this.dataList = dataList;
    }

    public int dataSize() {
        return dataList.size();
    }

    public Object data(int index) {
        return dataList.get(index);
    }
}
