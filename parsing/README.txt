a. Description (just summary)

Modified files:

Asgn2.jj (original is called Minijava.jj):


1. Rewrite the whole Expression(), support precedence and associative

print form:

Using parentheses to represent precedence and associativity. For example:
1 + 2 + 3 -> ((1 + 2) + 3)
1 + 2 * 3 -> (1 + (2 * 3))


2. Add FOR loop statement


3. In methodDeclaration add try catch block, in FormalParameter() and FormalParameterRest() add some semantic action to support recover.


Visitor:

1. Add interface in Visitor.java;

2. Task2Visitor.java: For every new added class, implements corresponding visit method;

3. Task3Visitor.java: Compared with Task2Visitor, when visit ForLoop will convert it into while loop and print, when visit Exponetial operator, will define power static method and rewrite it like "1 ^^ 2 -> power(1, 2)"

New added class (syntax tree node):

1. Or.java ("||")
2. IntegerDivision.java ("/")
3. UnaryMinus.java ("-")
4. Exponential.java ("^^")

To support for loop, added these class:
5.1 LVD.java
5.2 LocalVariableDeclaration.java
5.3 StmtExpr.java
5.4 StmtExprList.java


b. Instructions to run program

the test files are put in the input directory, there are three files

Test1.java is for task1 and task2
Test2.java is for task3
Test3.java is for ErrorRecovery

To test task1 and task2, please open Main.java and comment line:
"root.accept(new Task3Visitor());"

Then
1. make
2. java Main < Input/Test1.java
3. make clean

To test task3, please open Main.java and comment line:
"root.accept(new Task2Visitor());"

Then
1. make
2. java Main < Input/Test2.java
3. make clean

To test ErrorRecovery, please open Main.java and comment either line above

Then
1. make
2. java Main < Input/Test3.java
3. make clean


c. assumption:

(1). For unary minus, the expression "-1 * 2" will be regard (-1) * 2;
(2). The "<" has no associativity, so "1 < 2 < 3" will report error;
(3). After error recover, it will continue to print the whole program.
(4). In error recover case(a), my program will insert dumpy ',', for example, if input is "public int foo(int x int y)", my program will print "public int foo(int x, int y)" as well as the corresponding error and location of error.


d. All code are written by myself.


e. 

For task3,
1. Convert "1^^2" to power(1, 2) and define power method in corresponding class;
2. Convert ForLoop into while loop (also in power method);
3. Support nest for loop;

  







