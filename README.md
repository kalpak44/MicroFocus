# Micro Focus
**Assignment Topic:** Design and implemention an service evaluating algebraic expressions in JAVA.

**Deliverables:** source codes, build script creating an war file deployable on an common servlet container (e.g. Tomcat)

**Assignment:**  Design an input format suitable for representation of algebraic expressions. (JSON).

# Solution

## Reverse Polish notation solution (algorithm)

Reverse Polish notation (RPN), also known as Polish postfix notation or simply postfix notation, is a mathematical notation in which operators follow their operands, in contrast to Polish notation (PN), in which operators precede their operands. It does not need any parentheses as long as each operator has a fixed number of operands.
In comparison testing of reverse Polish notation with algebraic notation, reverse Polish has been found to lead to faster calculations. Because reverse Polish calculators do not need expressions to be parenthesized, fewer operations need to be entered to perform typical calculations. 

```
for each token in the reversed postfix expression:
  if token is an operator:
    push token onto the operator stack
    pending_operand ← False
  else if token is an operand:
    operand ← token
    if pending_operand is True:
      while the operand stack is not empty:
        operand_1 ← pop from the operand stack
        operator ← pop from the operator stack
        operand ← evaluate operator with operand_1 and operand
    push operand onto the operand stack
    pending_operand ← True
result ← pop from the operand stack
```
### Example
The infix expression `((15 ÷ (7 − (1 + 1))) × 3) − (2 + (1 + 1))` can be written like this in reverse Polish notation:

> 15 7 1 1 + − ÷ 3 × 2 1 1 + + −

do POST http://[your host]:[port]/calculator-1.0/calculate

header['**Accept**'] = **application/json**

Input example:
```json
{
	"variables":{
		"a":"AAAAA",
		"b":-5
	},
	"expression":"sizeof(a)+2*b-abs(3-b)"
}
```
The expression contain:
  - integer constant
  - string constant
  - binary operators +,-,*,/
  - unary operator sizeof (string) – length of the string argument
  - unary operator abs(int)  - absolute value of the integer argument
  
 Example response:
 
```json
{
    "variables": {
        "a": "AAAAA",
        "b": -5
    },
    "expression": "sizeof(a)+2*b-abs(3-b)",
    "result": -13
}
```
for details please look swagger documenation:
http://[your host]:[port]/calculator-1.0/swagger-ui.html



# build && deploy
For .war file creation use:
```sh
$mvn package
```
To deploy aplication to tomcat server please move */build/calculator-1.0.war* to tomcat **webapp** directory
