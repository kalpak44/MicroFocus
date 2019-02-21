# Micro Focus
**Assignment Topic:** Design and implemention an service evaluating algebraic expressions in JAVA.
**Deliverables:** source codes, build script creating an war file deployable on an common servlet container (e.g. Tomcat)
**Assignment:**  Design an input format suitable for representation of algebraic expressions. (JSON).
Input example:
```json
{
	"variables":{
		"a":"AAAAA",
		"b":-5
	},
	"expression":"sizeof(a)+2*b-(abs(3-b)"
}
```
The expression contain:
  - integer constant
  - string constant
  - binary operators +,-,*,/
  - unary operator sizeof (string) – length of the string argument
  - unary operator abs(int)  - absolute value of the integer argument
  
for details please look swagger documenation:
http://[your host]:[port]/calculator-1.0//swagger-ui.html

Then implement an service evaluating the input expression in Java use REST, Spring, JAX-WS.

# build && deploy
For .war file creation use:
```sh
$mvn package
```
To deploy aplication to tomcat server please move */build/calculator-1.0.war* to tomcat **webapp** directory
