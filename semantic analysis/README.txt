a. Instructions to run programs:
    1. make
    2. java Task1Main Y < input_file
    3. java Task2Main < input_file
    4. make clean


b. Assumptions. Please check carefully, I make a detail explaination

Declaration IdRef:

1. Class extends
    * if X extends Y, Y extends Z, and Y doesn't exist, then display (IdRef-of-X, Class, Y, (! No Class Y))
    * if X extends X, that is, extends itself, will report "can not extends itself" error

2. Data member
    * if X extends Y, Y extends Z, and instance variable z is only declarated in Z, and the idRef is 11, then 
      wherever z appear in X or Y, the idRef will always be 11;
      But if z is also redeclared in X, then the idRef will change;

3. Method

4. Param
    * If data member and param has same name, then param will hide data member;

5. Local Variable
    * If data member and local variable has same name, then local variable will hide data member;



Referenced Identifier:

1. if X is unknown, then will not display X's idRef;
2. if class X extends Y, instace variable y is defined in Y but not defined in X, suppose idRef of y is 11; 
   if X uses y, (Referenced Identifier), then will show idRef 11;
   if y is defined in X again, then will show new idRef;


unknown identifier:

1. If X is unknown, then only display X is unknown identifier (row, col) and don't display other info about X
2. For "Call" statement like X.Y(), if X is unknown and Y is also unknown, only display X is unknown; if X is known but Y is unknown, will show Y is unknown


Redeclaration identifier:

1. Notice, MiniJava doesn't support overloading, but support override, so if redeclared is method whose name has       been used, will report redeclared error;
2. If a class or a method is redeclared, then everything of this will be ignored.
3. If param and local var has conflict, will report local var redeclared




Type Check:

1. If a method can not be resolved, for example "x = this.foo()", "foo" can not be resolved, firstly report "foo can not be resolved" and then report "assign type error" since we don't know what the return type of "foo"

2. Notice: if in a assignment, rightside has type error, then the program will always report left and right side is not consistent, since right side has some error, we don't know what the exact type it is.

3. For And, if left side is wrong, not check right side; (similar to other binary operator)


Can check follow type error:

1. Method return type error
2. If condition is not boolean type
3. While condition is not boolean type
4. Print argument is not integer
5. Assign two sides type are not consistent (allow (double) = (int), since int can be promoted to be double; but doesn't allow (int) = (double))
6. Array assign: identifier is not int array type; index is not integer; assign value is not integer
7. Two sides of And is not of type boolean
8. Two sides of LessThan is not of type int (double)
9. Two sides of Plus is not of type int (double)
10. Two sides of Minus is not of type int (double)
11. Two sides of Times is not of type int (double)
12. Left side of ArrayLookUp is not int array; Right side of ArrayLookUp is not int
13. Left side of ArrayLength is not int array;
14. Object of Call is not Identifier type; Method of Call can not be resolved; Param of method is not consistent with its signature (first check length if equal, if equal, check type if is consistent)
15. NewArray declaration expression value must be int;
16. Right side of Not is not boolean
17. Assigning an object X to a variable of type Y but X is not an instance of Y




c. Test input files

* All test files are in the directory testcases.
* There are two scripts "1-run.sh" and "2-run.sh", you can run them respectively to go through all testcases, but yo  u should comment the testcases except what you want to test


d. All code are written by myself

