//package me.hjjang.excelmodule.domain;
//
//import me.hjjang.excelmodule.annotation.ExcelCellMapping;
//import me.hjjang.excelmodule.excelmaker.enumtype.ExcelCellType;
//
//import java.time.LocalDateTime;
//
//public class ObjectForTest {
//
//    @ExcelCellMapping(name = "WrapperLong")
//    private Long longTypeWrap;
//
//    @ExcelCellMapping(name = "PrimitiveLong")
//    private long longTypePrimitive;
//
//    @ExcelCellMapping(name = "StringType")
//    private String stringType;
//
//    @ExcelCellMapping(name = "문자열")
//    private String stringTypeHeaderKorean;
//
//    @ExcelCellMapping(name = "WrapperInt", type = ExcelCellType.STRING)
//    private Integer intTypeWrap;
//
//    @ExcelCellMapping(name = "PrimitiveInt", type = ExcelCellType.STRING)
//    private int intTypePrimitive;
//
//    @ExcelCellMapping(name = "LocalDateTime", type = ExcelCellType.DATETIME)
//    private LocalDateTime localDateTime;
//
//    public ObjectForTest(Long longTypeWrap, long longTypePrimitive, String stringType, String stringTypeHeaderKorean, Integer intTypeWrap, int intTypePrimitive, LocalDateTime localDateTime) {
//        this.longTypeWrap = longTypeWrap;
//        this.longTypePrimitive = longTypePrimitive;
//        this.stringType = stringType;
//        this.stringTypeHeaderKorean = stringTypeHeaderKorean;
//        this.intTypeWrap = intTypeWrap;
//        this.intTypePrimitive = intTypePrimitive;
//        this.localDateTime = localDateTime;
//    }
//}
