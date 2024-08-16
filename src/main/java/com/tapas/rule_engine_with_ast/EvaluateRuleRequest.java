package com.tapas.rule_engine_with_ast;

import lombok.Data;

@Data
public class EvaluateRuleRequest {
    private String id;
    private String age;
    private String department;
    private String salary;
    private String experience;
}
