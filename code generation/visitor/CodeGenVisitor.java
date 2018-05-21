package visitor;

import syntaxtree.*;
import java.io.PrintWriter;

public class CodeGenVisitor extends DepthFirstVisitor {

  static Class currClass;
  static Method currMethod;
  static SymbolTable symbolTable;
  PrintWriter out;
  int ifFlag;
  int whileFlag;
  String lastCallType;
 
  public CodeGenVisitor(SymbolTable s, PrintWriter out) {
    symbolTable = s;
    this.out = out;

    ifFlag = 0;
    whileFlag = 0;
  }

  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n) {
    // Data segment 
    out.println(
      ".data\n" +
      "newline: .asciiz \"\\n\"\n" +    // to be used by cgen for "System.out.println()"
      "msg_index_out_of_bound_exception: .asciiz \"Index out of bound exception\\n\"\n" +   
      "msg_null_pointer_exception: .asciiz \"Null pointer exception\\n\"\n" +  
      "\n" +
      ".text\n" 
    );

    n.m.accept(this);

    out.println(  // Code to terminate the program
      "# exit\n" +
      "li $v0, 10\n" +
      "syscall\n"
    );

    // Code for all methods
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.elementAt(i).accept(this);
    }

    // Code for some utility functions 
    cgen_supporting_functions();
  }
  
  // Identifier i1,i2;
  // VarDeclList vl;
  // Statement s;
  public void visit(MainClass n) {
    out.println("# main");
    String i1 = n.i1.toString();
    currClass = symbolTable.getClass(i1);
    currMethod = currClass.getMethod("main");   // This is a hack (treat main() as instance method.)
 
    // Can ignore the parameter of main()
  
    // Info about local variables are kept in "currMethod"

    // Generate code to reserve space for local variables in stack
    // Optionally, generate code to reserve space for temps

    out.println("move $fp, $sp");
    out.println("sw $ra, 0($fp)");
    int num_vars = currMethod.vars.size();
    out.println("addi $sp, $sp, -" + (4*num_vars + 4));

    n.s.accept(this);
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n) {
    String label = n.i.s;
    out.println(label + ":");
    out.println("sw $ra, 0($sp)");
    out.println("addi $sp, $sp, -4");

    currClass =  symbolTable.getClass(n.i.toString());
    int num_fields = currClass.fields.size();
    out.println("li $a0, " + num_fields);
    out.println("jal _alloc_new_object");

    out.println("addi $sp, $sp, 4");
    out.println("lw $ra, 0($sp)");
    out.println("jr $ra");

    for ( int i = 0; i < n.ml.size(); i++ ) {
      n.ml.elementAt(i).accept(this);
    }
  }
 
  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  // cgen: t i(fl) { vl sl return e; }
  public void visit(MethodDecl n) {
    currMethod = currClass.getMethod(n.i.s);
    String label = currClass.getId() + "_" + currMethod.getId();
    out.println(label + ":");

    out.println("move $fp, $sp");

    int num_vars = currMethod.vars.size();
    out.println("addi $sp, $sp, -" + (num_vars*4+4));
    out.println("sw $ra, 0($fp)");

    for ( int i = 0; i < n.sl.size(); i++ ) {
      n.sl.elementAt(i).accept(this);
    }

    n.e.accept(this);

    out.println("lw $ra, 0($fp)");
    out.println("addi $sp, $sp, " + (num_vars*4+4));
    out.println("jr $ra");

    currMethod = null;
  }

  // Exp e;
  // Statement s1,s2;
  // cgen: if (e) s1 else s2
  public void visit(If n) {
    ifFlag += 1;
    String label = "If" + ifFlag;
    out.println(label + ":");
    n.e.accept(this);
    out.println("beq $v0, $0, " + label + "_false");
    out.println("j " + label + "_true");

    out.println(label + "_true:");
    n.s1.accept(this);
    out.println("j " + label + "_end");

    out.println(label + "_false:");
    n.s2.accept(this);
    out.println("j " + label + "_end");

    out.println(label + "_end:");
  }

  // Exp e;
  // Statement s;
  // cgen: while (e) s;
  public void visit(While n) {
    whileFlag += 1;
    String label = "While" + whileFlag;
    out.println(label + ":");
    n.e.accept(this);
    out.println("beq $v0, $0, " + label + "_end");
    out.println("j " + label + "_loop");

    out.println(label + "_loop:");
    n.s.accept(this);
    out.println("j " + label);

    out.println(label + "_end:");
  }

  // Exp e;
  // cgen: System.out.println(e)
  public void visit(Print n) {
    out.println("# print");
    n.e.accept(this);
    out.println("move $a0, $v0");
    out.println("jal _print_int");
  }
  
  // Identifier i;
  // Exp e;
  // cgen: i = e
  public void visit(Assign n) {
    out.println("# Assign " + n.i.s);
    if (currMethod.containsVar(n.i.s)){
      int offset = currMethod.getVar(n.i.s).offset;
      n.e.accept(this);
      out.println("sw $v0, " + offset + "($fp)");
    } else if (currMethod.containsParam(n.i.s)){
      int offset = currMethod.getParam(n.i.s).offset;
      n.e.accept(this);
      out.println("sw $v0, " + offset + "($fp)");
    } else if (currClass.containsVar(n.i.s)){
      out.println("sw $t0, 0($sp)");
      out.println("addi $sp, $sp, -4");

      out.println("lw $t0, " + "4($fp)" + "  # load this");

      int offset = currClass.getVar(n.i.s).offset;

      n.e.accept(this);
      out.println("sw $v0, " + offset + "($t0)");

      out.println("addi $sp, $sp, 4");
      out.println("lw $t0, 0($sp)");
    }
  }

  // Identifier i;
  // Exp e1,e2;
  // cgen: i[e1] = e2
  public void visit(ArrayAssign n) {
    out.println("# ArrayAssign " + n.i.s);

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");
    out.println("sw $t1, 0($sp)");
    out.println("addi $sp, $sp, -4");

    int offset = 0;
    if (currMethod.containsVar(n.i.s)){
      offset = currMethod.getVar(n.i.s).offset;
      out.println("lw $t0, " + offset + "($fp)");
    } else if (currMethod.containsParam(n.i.s)){
      offset = currMethod.getParam(n.i.s).offset;
      out.println("lw $t0, " + offset + "($fp)");
    } else if (currClass.containsVar(n.i.s)){
      out.println("lw $t0, " + "4($fp)" + "  # load this");
      offset = currClass.getVar(n.i.s).offset;
      out.println("lw $t0, " + offset + "($t0)");  // array address
    }

    // null pointer check
    out.println("beq $t0, $0, _null_pointer_exception");

    n.e1.accept(this);
    // outbound check
    out.println("lw $t1, 0($t0)");
    out.println("bge $v0, $t1, _array_index_out_of_bound_exception");

    out.println("li $t1, 4");
    out.println("addi $v0, $v0, 1");
    out.println("mult $v0, $t1");
    out.println("mflo $v0");
    out.println("add $t0, $t0, $v0");

    n.e2.accept(this);
    out.println("sw, $v0, 0($t0)");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t1, 0($sp)");
    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1 && e2
  public void visit(And n) {
    out.println("# And");

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    out.println("move $t0, $v0");
    n.e2.accept(this);
    out.println("and $v0, $t0, $v0");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1 < e2
  public void visit(LessThan n) {
    out.println("# LessThan");

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    out.println("move $t0, $v0");
    n.e2.accept(this);
    out.println("slt $v0, $t0, $v0");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1 + e2
  public void visit(Plus n) {
    out.println("# Plus");

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    out.println("move $t0, $v0");
    n.e2.accept(this);
    out.println("add $v0, $t0, $v0");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1 - e2
  public void visit(Minus n) {
    out.println("# Minus");

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    out.println("move $t0, $v0");
    n.e2.accept(this);
    out.println("sub $v0, $t0, $v0");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1 * e2
  public void visit(Times n) {
    out.println("# Times");

    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    out.println("move $t0, $v0");
    n.e2.accept(this);
    out.println("mult $t0, $v0");
    out.println("mflo $v0");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e1,e2;
  // cgen: e1[e2]
  public void visit(ArrayLookup n) {
    out.println("sw $t0, 0($sp)");
    out.println("addi $sp, $sp, -4");
    out.println("sw $t1, 0($sp)");
    out.println("addi $sp, $sp, -4");

    n.e1.accept(this);
    // null pointer check
    out.println("beq $v0, $0, _null_pointer_exception  # array lookup");
    out.println("move $t0, $v0");

    n.e2.accept(this);
    // outbound check
    out.println("lw $t1, 0($t0)");
    out.println("bge $v0, $t1, _array_index_out_of_bound_exception");

    out.println("li $t1, 4");
    out.println("addi $v0, $v0, 1");
    out.println("mult $v0, $t1");
    out.println("mflo $v0");
    out.println("add $t0, $t0, $v0");
    out.println("lw $v0, 0($t0)");

    out.println("addi $sp, $sp, 4");
    out.println("lw $t1, 0($sp)");
    out.println("addi $sp, $sp, 4");
    out.println("lw $t0, 0($sp)");
  }

  // Exp e;
  // cgen: e.length
  public void visit(ArrayLength n) {
    n.e.accept(this);
    // null pointer check
    out.println("beq $v0, $0, _null_pointer_exception  # array lookup");

    out.println("lw $v0, 0($v0)");
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  // cgen: e.i(el)
  public void visit(Call n) {
    String c = "";
    if (n.e instanceof NewObject){
      c = ((NewObject)n.e).i.s;
      out.print("# Call: NewObject, " + "new " + c + ".");
    } else if (n.e instanceof This){
      c = currClass.getId();
      out.print("# Call: This, " + c + ".");
    } else if (n.e instanceof IdentifierExp){
      String id = ((IdentifierExp)n.e).s;
      Type t = symbolTable.getVar(currMethod, currClass, id).type();
      c = ((IdentifierType)t).s;
      out.print("# Call: Identifier exp, " + id + ".");
    } else if (n.e instanceof Call){
      n.e.accept(this);
      c = lastCallType;
      out.print("# Call: " + ((Call)n.e).i.s + ".");
    }

    String m = n.i.s;
    out.println(m + "()");

    String label = c + "_" + m;
    lastCallType = mapType(symbolTable.getMethodType(m, c));
    
    out.println("sw $fp, 0($sp)");
    out.println("addi $sp, $sp, -4");

    int num_params = n.el.size();

    for (int i = num_params - 1; i >= 0; i--){
      n.el.elementAt(i).accept(this);
      out.println("sw $v0, 0($sp)");
      out.println("addi $sp, $sp, -4");
    }

    n.e.accept(this);
    // check null pointer
    out.println("beq $v0, $0, _null_pointer_exception  # object call");

    out.println("sw $v0, 0($sp)");
    out.println("addi $sp, $sp, -4");  // this

    out.println("jal " + label);

    out.println("addi $sp, $sp, " + (num_params*4+4));

    // load fp
    out.println("addi $sp, $sp, 4");
    out.println("lw $fp, 0($sp)");
  }

  // Exp e;
  // cgen: new int [e]
  public void visit(NewArray n) {
    out.println("# NewArray");
    n.e.accept(this);
    out.println("move $a0, $v0");
    out.println("jal _alloc_int_array");
  }

  // Identifier i;
  // cgen: new n
  public void visit(NewObject n) {
    String label = n.i.s;
    out.println("jal " + label);
  }

  // Exp e;
  // cgen: !e
  public void visit(Not n) {
    out.println("# Not");
    n.e.accept(this);
    out.println("seq $v0, $v0, $0");
  }

  // cgen: this
  public void visit (This n) {
    out.println("lw $v0, 4($fp)");
  }

  // int i;
  // cgen: Load immediate the value of n.i
  public void visit(IntegerLiteral n) {
    out.println("li $v0, " + n.i + "  # load " + n.i);
  }

  // cgen: Load immeidate the value of "true"
  public void visit(True n) {
    out.println("li $v0, 1" + "  # load true");
  }

  // cgen: Load immeidate the value of "false"
  public void visit(False n) {
    out.println("li $v0, 0" + "  # load false");
  }

  // String s;
  // cgen: Load the value of the variable n.s (which can be a local variable, parameter, or field)
  public void visit(IdentifierExp n) {
    if (currMethod.containsVar(n.s)){
      out.println("# load local variable " + n.s);
      int offset = currMethod.getVar(n.s).offset;
      out.println("lw $v0, " + offset + "($fp)");
    } else if (currMethod.containsParam(n.s)){
      out.println("# load parameter" + n.s);
      int offset = currMethod.getParam(n.s).offset;
      out.println("lw $v0, " + offset + "($fp)");
    } else if (currClass.containsVar(n.s)){
      out.println("# load field" + n.s);
      out.println("lw $v0, " + "4($fp)");
      int offset = currClass.getVar(n.s).offset;
      out.println("lw $v0, " + offset + "($v0)");
    }
  }

  public String mapType(Type t){
    String type = "";
    if (t instanceof IdentifierType){
      type = ((IdentifierType)t).s;
    } else if (t instanceof BooleanType){
      type = "boolean";
    } else if (t instanceof IntegerType){
      type = "int";
    } else if (t instanceof IntArrayType){
      type = "intArray";
    }
    return type;
  }

  void cgen_supporting_functions() {
    out.println(
     "_print_int: # System.out.println(int)\n" +
     "li $v0, 1\n" +
     "syscall\n" +
     "la $a0, newline\n" +
     "li $a1, 1\n" +
     "li $v0, 4   # print newline\n" +
     "syscall\n" +
     "jr $ra\n"
    );

    out.println(
      "_null_pointer_exception:\n" +
      "la $a0, msg_null_pointer_exception\n" + 
      "li $a1, 23\n" +
      "li $v0, 4\n" +
      "syscall\n" +
      "li $v0, 10\n" +
      "syscall\n" 
    );

    out.println(
      "_array_index_out_of_bound_exception:\n" +
      "la $a0, msg_index_out_of_bound_exception\n" + 
      "li $a1, 29\n" +
      "li $v0, 4\n" +
      "syscall\n" +
      "li $v0, 10\n" +
      "syscall\n" 
    );

    out.println(
      "_alloc_int_array: # new int [$a0]\n" +
      "addi $a2, $a0, 0  # Save length in $a2\n" +
      "addi $a0, $a0, 1  # One more word to store the length\n" +
      "sll $a0, $a0, 2   # multiple by 4 bytes\n" +
      "li $v0, 9         # allocate space\n" +
      "syscall\n" +
      "\n" + 
      "sw $a2, 0($v0)    # Store array length\n" +
      "addi $t1, $v0, 4  # begin address = ($v0 + 4); address of the first element\n" +
      "add $t2, $v0, $a0 # loop until ($v0 + 4*(length+1)), the address after the last element\n" +
      "\n" +
      "_alloc_int_array_loop:\n" +
      "beq $t1, $t2, _alloc_int_array_loop_end\n" +
      "sw $0, 0($t1)\n"+
      "addi $t1, $t1, 4\n" +
      "j _alloc_int_array_loop\n" +
      "_alloc_int_array_loop_end:\n" +
      "\n" +
      "jr $ra\n"
    );

    out.println(
      "_alloc_new_object: # new object\n" +
      "addi $a2, $a0, 0  # Save length in $a2\n" +
      "addi $a0, $a0, 1  # One more word to store the length\n" +
      "sll $a0, $a0, 2   # multiple by 4 bytes\n" +
      "li $v0, 9         # allocate space\n" +
      "syscall\n" +
      "\n" + 
      "sw $a2, 0($v0)    # Store array length\n" +
      "addi $t1, $v0, 4  # begin address = ($v0 + 4); address of the first element\n" +
      "add $t2, $v0, $a0 # loop until ($v0 + 4*(length+1)), the address after the last element\n" +
      "\n" +
      "_alloc_new_object_loop:\n" +
      "beq $t1, $t2, _alloc_new_object_loop_end\n" +
      "sw $0, 0($t1)\n"+
      "addi $t1, $t1, 4\n" +
      "j _alloc_new_object_loop\n" +
      "_alloc_new_object_loop_end:\n" +
      "\n" +
      "jr $ra\n"
    );

  }
}

