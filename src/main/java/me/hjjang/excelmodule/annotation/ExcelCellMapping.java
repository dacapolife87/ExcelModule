package me.hjjang.excelmodule.annotation;

import me.hjjang.excelmodule.excelmaker.enumtype.ExcelCellType;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCellMapping {

    String name() default "";

    ExcelCellType type() default ExcelCellType.STRING;
}
