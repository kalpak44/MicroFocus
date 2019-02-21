package com.homework.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ExpressionCalculatorService {
    BigDecimal calculateExpression(String expression);

    void setVariables(Map<String, Object> variables);
}
