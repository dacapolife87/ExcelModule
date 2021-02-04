package me.hjjang.excelmodule.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateTimeFormat {

    String format() default "yy/d/m h:mm";
}
