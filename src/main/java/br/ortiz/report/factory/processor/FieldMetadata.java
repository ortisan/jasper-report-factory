package br.ortiz.report.factory.processor;

import lombok.Data;

@Data
public class FieldMetadata {
    private String fieldName;
    private String fieldType;
    private String fieldExpression;
    private String uuid;
}
