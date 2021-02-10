package me.hjjang.excelmodule.excelmaker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public enum ExcelDataType {
    DOUBLE(Double.TYPE, 0, Arrays.asList(int.class, Integer.class,long.class, Long.class)),
    BOOLEAN(Boolean.TYPE, 0, Arrays.asList(boolean.class, Boolean.class)),
    LOCALDATE(LocalDate.class, 14, Arrays.asList(LocalDate.class)),
    LOCALDATETIME(LocalDateTime.class, 22, Arrays.asList(LocalDateTime.class)),
    CALENDAR(Calendar.class, 22, Arrays.asList(Calendar.class)),
    DATE(Date.class, 14, Arrays.asList(Date.class)),
    STRING(String.class,0,Collections.EMPTY_LIST);

    private Class type;
    private int style;
    private List typeClasses;

    ExcelDataType(Class type, int style, List typeClasses) {
        this.type = type;
        this.style = style;
        this.typeClasses = typeClasses;
    }

    public Class value() {
        return type;
    }

    public short style() {
        return (short)style;
    }
    public static ExcelDataType findByClassType(Class clazz){
        return Arrays.stream(ExcelDataType.values())
                        .filter(typeGroup -> typeGroup.hasClassType(clazz))
                        .findAny()
                        .orElse(STRING);
    }

    public boolean hasClassType(Class clazz){
        return typeClasses.stream()
                .anyMatch(classType -> classType.equals(clazz));
    }
}
