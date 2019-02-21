package com.homework;

import com.homework.model.Expression;
import com.homework.service.ExpressionCalculatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculate")
@Api(value = "/calculate", description = "Simple math expression calculator")
public class CalculatorController {
    @Autowired
    private ExpressionCalculatorService expressionCalculatorService;
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Expression calculate(@RequestBody Expression expression) {
        expressionCalculatorService.setVariables(expression.getVariables());
        expression.setResult(expressionCalculatorService.calculateExpression(expression.getExpression()).intValue());
        return expression;
    }
}