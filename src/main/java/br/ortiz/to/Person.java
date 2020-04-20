package br.ortiz.to;

import br.ortiz.report.factory.annotations.Field;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
    private Integer id;
    @Field
    private String name;
    @Field
    private Integer age;
    @Field
    private LocalDate birth;
    @Field
    private String email;
}
