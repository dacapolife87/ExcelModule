package me.hjjang.excelmodule.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCellMapping {

    String name() default "";
}
