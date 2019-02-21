package com.homework;

import com.homework.service.ExpressionCalculatorService;
import com.homework.service.ExpressionCalculatorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressionCalculatorServiceTest {
    private ExpressionCalculatorService expressionCalculatorService;

    @Before
    public void before() {
        this.expressionCalculatorService = new ExpressionCalculatorServiceImpl();
    }

    @Test
    public void checkVariablesInExpression() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("a", 5);
        vars.put("b", -5);
        vars.put("c", "AAAAA");
        expressionCalculatorService.setVariables(vars);
        assertEquals(new BigDecimal("30"), expressionCalculatorService.calculateExpression("5+a*5"));
        assertEquals(new BigDecimal("-20"), expressionCalculatorService.calculateExpression("5+b*5"));
        assertEquals(new BigDecimal("30"), expressionCalculatorService.calculateExpression("5+abs(b)*5"));
        assertEquals(new BigDecimal("25"), expressionCalculatorService.calculateExpression("-25 + sizeof(c) * 10"));
    }

    @Test
    public void checkOperationsPriority() {
        assertEquals(new BigDecimal("30"), expressionCalculatorService.calculateExpression("5+5*5"));
        assertEquals(new BigDecimal("50"), expressionCalculatorService.calculateExpression("(5+5)*5"));
        assertEquals(new BigDecimal("31"), expressionCalculatorService.calculateExpression("5+5*5+sizeof(A)"));
        assertEquals(new BigDecimal("12"), expressionCalculatorService.calculateExpression("(sizeof(AA) + 2) + 2 * abs(4-8)"));
    }

    @Test
    public void checkBasicMathOperations() {
        assertEquals(BigDecimal.TEN, expressionCalculatorService.calculateExpression("5+5"));
        assertEquals(BigDecimal.ZERO, expressionCalculatorService.calculateExpression("12-12"));
        assertEquals(new BigDecimal("2"), expressionCalculatorService.calculateExpression("4/2"));
        assertEquals(new BigDecimal("8"), expressionCalculatorService.calculateExpression("4*2"));
    }

    @Test(expected = ArithmeticException.class)
    public void checkDivToZero() {
        expressionCalculatorService.calculateExpression("4/0");
    }

    @Test
    public void checkAbsFunction() {
        assertEquals(new BigDecimal("8"), expressionCalculatorService.calculateExpression("abs(8)"));
        assertEquals(new BigDecimal("8"), expressionCalculatorService.calculateExpression("abs(-8)"));
        assertEquals(new BigDecimal("8"), expressionCalculatorService.calculateExpression("abs(10-2)"));
    }
    @Test
    public void checkSizeOfFunction() {
        assertEquals(new BigDecimal("2"), expressionCalculatorService.calculateExpression("sizeof(AA)"));
        assertEquals(new BigDecimal("3"), expressionCalculatorService.calculateExpression("sizeof(LLL)"));
        assertEquals(new BigDecimal("5"), expressionCalculatorService.calculateExpression("sizeof(A3B22)"));
    }
}