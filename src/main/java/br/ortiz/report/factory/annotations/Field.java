package br.ortiz.report.factory.annotations;

import br.ortiz.report.factory.processor.enums.MaskEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Field {
    public String format() default "";
    public MaskEnum maskType() default MaskEnum.NONE;
}
