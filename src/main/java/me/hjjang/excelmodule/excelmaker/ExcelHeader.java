package me.hjjang.excelmodule.excelmaker;

import java.util.*;

public class ExcelHeader {
    private final Map<Integer, String> headerIndexMap = new HashMap<>();
    private final List<String> headers = new ArrayList<>();
    private Class clazz;

    public ExcelHeader(Class clazz) {
        this.clazz = clazz;
    }

    public void addHeader(String headerName, String fieldName) {
        int index = headers.size();
        headerIndexMap.put(index, fieldName);
        headers.add(headerName);
    }

    public int headerSize() {
        return headers.size();
    }
    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public Class getClassType() {
        return clazz;
    }

    public String getFieldName(int key) {
        return headerIndexMap.get(key);
    }
}
