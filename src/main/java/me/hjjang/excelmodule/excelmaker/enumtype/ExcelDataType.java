package me.hjjang.excelmodule.excelmaker.enumtype;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public enum ExcelDataType {
    DOUBLE(Double.TYPE, Arrays.asList(int.class, Integer.class,long.class, Long.class)),
    BOOLEAN(Boolean.TYPE, Arrays.asList(boolean.class, Boolean.class)),
    LOCALDATE(LocalDate.class, Arrays.asList(LocalDate.class)),
    LOCALDATETIME(LocalDateTime.class, Arrays.asList(LocalDateTime.class)),
    CALENDAR(Calendar.class, Arrays.asList(Calendar.class)),
    DATE(Date.class, Arrays.asList(Date.class)),
    STRING(String.class,Collections.EMPTY_LIST);

    private Class type;
    private List typeClasses;

    ExcelDataType(Class type, List typeClasses) {
        this.type = type;
        this.typeClasses = typeClasses;
    }

    public Class value() {
        return type;
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
