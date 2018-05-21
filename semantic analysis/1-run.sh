make

echo
echo "-----test classSimpleDeclaration-----"
echo
input=testcase/task1/classSimpleDeclaration.java
cat $input
echo
echo "-----res------"
echo
java Task1Main "X" < $input
echo

# echo
# echo "-----test classExtendsDeclaration-----"
# echo
# input=testcase/task1/classExtendsDeclaration.java
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test data member-----"
# echo
# input=testcase/task1/dataMember.java;
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test method-----"
# echo
# input=testcase/task1/method.java;
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test parameter-----"
# echo
# input=testcase/task1/Param.java;
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test local variable-----"
# echo
# input=testcase/task1/LocalVar.java;
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test referenced identifier-----"
# echo
# input=testcase/task1/refId.java
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test unknown identifier-----"
# echo
# input=testcase/task1/Unknown.java
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

# echo
# echo "-----test redeclaration identifier-----"
# echo
# input=testcase/task1/Redecl.java
# cat $input
# echo
# echo "-----res------"
# echo
# java Task1Main "X" < $input
# echo

make clean
