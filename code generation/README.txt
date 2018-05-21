a. To run the program:
    1. Make
    2. java Main < input_file > output.asm

    * for testcase 0~27, to test testcase i, you can directly run "make A=i"
    * testcase 28~33 are used to check the exception cases


b.

1. No "extends", so type of an object variable can be determined in compile time.

2. Notice javacc doesn't support data member access like object.attribute,
   1) to write a data member, use attribute = expression()
   2) to read a data member, directly use attribute (attribute is a data member)
   * testcase 26 contains the situation of read/write a data member.


c.

* I have mark the testcase for every requirement appear in the assignment specification.
* doesn't support extends
* testcases are located in test directory
* input directory also contains some test cases and the output is equal to java output

(1) testcase 0

(2) testcase 1, 2, 3, 4, 5

(3) testcase 12, 13, 14, 15

(4) testcase 7, 8, 9, 10

(5) testcase 11

(6) testcase 16

(7) testcase 17

(8) testcase 18, 19, 20

(9) testcase 
    1) 28, 29 (index outbound)
    2) 30, 31, 32 (null pointer, array)
    3) 33 (null pointer, object)

(10) testcase 21

(11) testcase 22

(12) testcase 26

(13) testcase 23, 24

(14) testcase 25

(15) testcase 26, 27

(16) doesn't support extends




d. All code are written by myself.



e.

1. when evaluating an expression, the result will be stored in $v0.

2. Everywhere use temporary register, will firstly store it in stack, after usage, restore it.

3. Using 1 represents true, 0 represents false.

4. ifFlag counts the number of if in the program and named the label of IF block;

5. layout of activation record: 
   1) parameters (p_n, p_(n-1), ..., p_1, this)
   2) ra (0($fp))
   3) local vars, (v_1, v_2, ..., v_n)
   4) save registers

6. an identifierExp can be
   1) parameters
   2) local variables
   3) fields
   need to discuss these three cases to determine the offset

7. whileFlag is similar to "ifFlag", is used to track the number of while and name the label.

8. Object layout
   1) size
   2) attributes (a1, a2, ... an)
   * I didn't use tag and dispatch table since I assume no extends
   * I directly determine the type of object variable, when call a method f, directly jal to that method

9. Multi call
   in testcase 25, there is a Call Exp in form f.m1().m2(), here I used a class variable 
   "lastCallType" to track the last call return type;
   1. n: f.m1().m2();    n.e: f.m1();    n.i: m2
   2. n: f.m1();    n.e: f;    n.i: m1;    lastCallType: type(f.m1())
   3. now I know m2 belong which class from 2, so I can jal to type(f.m1()).m2()

10. instance variable
   Since sometimes we ignore the "this" keyword, when call a method like o.f(), I will pass
   the address of o as the first parameter to f (see 5. layout of activation record)

11. null ponter checking
   1) use a declaraed array variable but this variable not allocate space
       i. look up: array[index]
       ii. assign: array[index]
       iii. length: array.length
   2) use an declaraed object variable but this variable not allocate space
       i. call: object.m()