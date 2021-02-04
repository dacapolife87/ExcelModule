package me.hjjang.excelmodule.domain;

import me.hjjang.excelmodule.annotation.DateTimeFormat;
import me.hjjang.excelmodule.annotation.ExcelCellMapping;

import java.time.LocalDateTime;

public class ObjectForTest {

    @ExcelCellMapping(name = "WrapperLong")
    private Long longTypeWrap;

    @ExcelCellMapping(name = "PrimitiveLong")
    private long longTypePrimitive;

    @ExcelCellMapping(name = "StringType")
    private String stringType;

    @ExcelCellMapping(name = "문자열")
    private String stringTypeHeaderKorean;

    @ExcelCellMapping(name = "WrapperInt")
    private Integer intTypeWrap;

    @ExcelCellMapping(name = "PrimitiveInt")
    private int intTypePrimitive;

    @ExcelCellMapping(name = "LocalDateTime")
    private LocalDateTime localDateTime;

    @ExcelCellMapping(name = "LocalDateTimeFormat")
    @DateTimeFormat(format = "yyyy/mm/dd")
    private LocalDateTime localDateTimeFormat;

    public ObjectForTest(Long longTypeWrap, long longTypePrimitive, String stringType, String stringTypeHeaderKorean, Integer intTypeWrap, int intTypePrimitive, LocalDateTime localDateTime, LocalDateTime localDateTimeFormat) {
        this.longTypeWrap = longTypeWrap;
        this.longTypePrimitive = longTypePrimitive;
        this.stringType = stringType;
        this.stringTypeHeaderKorean = stringTypeHeaderKorean;
        this.intTypeWrap = intTypeWrap;
        this.intTypePrimitive = intTypePrimitive;
        this.localDateTime = localDateTime;
        this.localDateTimeFormat = localDateTimeFormat;
    }
}
