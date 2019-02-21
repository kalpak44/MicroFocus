package com.homework.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpressionCalculatorServiceImpl implements ExpressionCalculatorService {
    /**
     * Basic mathematical operations and their priorities.
     *
     * @see #sortingStation(String, java.util.Map)
     */
    public static final Map<String, Integer> MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap<>();
        MAIN_MATH_OPERATIONS.put("abs", 1);
        MAIN_MATH_OPERATIONS.put("sizeof", 1);
        MAIN_MATH_OPERATIONS.put("*", 2);
        MAIN_MATH_OPERATIONS.put("/", 2);
        MAIN_MATH_OPERATIONS.put("+", 3);
        MAIN_MATH_OPERATIONS.put("-", 3);
    }

    /**
     * Converts an expression from infix notation to reverse polish notation  (RPN) using  Edsger Dijkstra  algorithm.
     * A distinctive feature of the reverse polish notation is that all arguments (or operands) are located before the
     * operation. This eliminates the need for brackets.
     *
     * For example, an expression written in infix notation as 3 * (4 + 7) would look like 3 4 7 + *
     * in RPN the bracket characters are subject to change.
     * <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">More about RPN</a>.
     *
     * @param expression  infix expression
     * @param operations  operators used in the expression
     * @param leftBracket opening bracket.
     * @return transformed expression to RPN..
     */
    private String sortingStation(String expression, Map<String, Integer> operations, String leftBracket) {
        if (expression == null || expression.length() == 0)
            throw new IllegalStateException("Expression isn't specified.");
        if (operations == null || operations.isEmpty())
            throw new IllegalStateException("Operations aren't specified.");
        // The output string, divided into "characters" - the operation and operands.
        List<String> out = new ArrayList<>();
        // Stack of operations.
        Stack<String> stack = new Stack<>();
        // Remove spaces from expression.
        expression = expression.replace(" ", "");

        //A set of "characters" that are not operands (operations and parentheses).
        Set<String> operationSymbols = new HashSet<>(operations.keySet());
        operationSymbols.add(leftBracket);
        operationSymbols.add(")");

        // The index on which finished parsing the line at the last iteration.
        int index = 0;
        // Sign of the need to search for the next item.
        boolean findNext = true;
        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // Search for the next operation or bracket.
            for (String operation : operationSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }
            // Operation not found.
            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {
                // If the operator or parenthesis is preceded by an operand, add it to the output string.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }
                // Handling operators and brackets.
                // opening bracket
                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                }
                // closing bracket
                else if (nextOperation.equals(")")) {
                    while (!stack.peek().equals(leftBracket)) {
                        out.add(stack.pop());
                        if (stack.empty()) {
                            throw new IllegalArgumentException("Unmatched brackets");
                        }
                    }
                    stack.pop();
                }
                // operation
                else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            (operations.get(nextOperation) >= operations.get(stack.peek()))) {
                        out.add(stack.pop());
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }
        // Adding operands to the output string after the last operand.
        if (index != expression.length()) {
            out.add(expression.substring(index));
        }
        // Convert the output list to the original string.
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        StringBuilder result = new StringBuilder();
        if (!out.isEmpty())
            result.append(out.remove(0));
        while (!out.isEmpty())
            result.append(" ").append(out.remove(0));

        return result.toString();
    }

    /**
     * Converts an expression from infix notation to reverse polish notation  (RPN) using  Edsger Dijkstra  algorithm.
     * A distinctive feature of the reverse polish notation is that all arguments (or operands) are located before the
     * operation. This eliminates the need for brackets.
     *
     * For example, an expression written in infix notation as 3 * (4 + 7) would look like 3 4 7 + *
     * in RPN the bracket characters are subject to change.
     * <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">More about RPN</a>.
     *
     * @param expression  infix expression
     * @param operations  operators used in the expression
     * @return transformed expression to RPN..
     */
    private String sortingStation(String expression, Map<String, Integer> operations) {
        return sortingStation(expression, operations, "(");
    }

    /**
     * Computes the value of an expression written in infix notation. The expression may contain brackets, numbers with
     * floating point, a few basic mathematical operands, variables and two simples functions abs and sizeof.
     *
     * @param expression expression.
     * @return calculation result.
     */
    @Override
    public BigDecimal calculateExpression(String expression) {
        String rpn = sortingStation(expression, MAIN_MATH_OPERATIONS);
        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack<Object> stack = new Stack<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // Операнд.
            if (!MAIN_MATH_OPERATIONS.keySet().contains(token)) {
                if (variables.containsKey(token)) {
                    Object o = variables.get(token);
                    if (o instanceof String) {
                        stack.push(o.toString());
                    } else {
                        stack.push(new BigDecimal(o.toString()));
                    }
                } else {
                    if (isNumeric(token)) {
                        stack.push(new BigDecimal(token));
                    } else {
                        stack.push(token);
                    }
                }
            } else {
                if (token.equals("*")) {
                    BigDecimal operand2 = (BigDecimal) stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : (BigDecimal) stack.pop();
                    stack.push(operand1.multiply(operand2));
                } else if (token.equals("/")) {
                    BigDecimal operand2 = (BigDecimal) stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : (BigDecimal) stack.pop();
                    stack.push(operand1.divide(operand2));
                } else if (token.equals("+")) {
                    BigDecimal operand2 = (BigDecimal) stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : (BigDecimal) stack.pop();
                    stack.push(operand1.add(operand2));
                } else if (token.equals("-")) {
                    BigDecimal operand2 = (BigDecimal) stack.pop();
                    BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : (BigDecimal) stack.pop();

                    stack.push(operand1.subtract(operand2));
                } else if (token.equals("abs")) {
                    BigDecimal operand2 = (BigDecimal) stack.pop();
                    stack.push(operand2.abs());
                } else if (token.equals("sizeof")) {
                    Object o = stack.pop();
                    if (o instanceof String) {
                        stack.push(new BigDecimal(o.toString().length() + ""));
                    } else {
                        throw new IllegalArgumentException("Expression syntax error. sizeof() parameter need to be a string");
                    }
                }
            }
        }
        if (stack.size() != 1)
            throw new IllegalArgumentException("Expression syntax error.");
        return (BigDecimal) stack.pop();
    }

    /**
     * Helper methods
     */
    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private Map<String, Object> variables = new HashMap<>();

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}