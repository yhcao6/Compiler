package visitor;
import syntaxtree.*;

public class Task1 extends DepthFirstVisitor {

    static Class currClass;
    static Method currMethod;
    static SymbolTable symbolTable;
    String Y;

    public Task1(SymbolTable s, String Y){
        symbolTable = s;
        this.Y = Y;
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        String i1 = n.i1.toString();
        currClass = symbolTable.getClass(i1);
        if (i1.equals(Y)){
            System.out.println(currClass.idRef + ", Class");
        }

        // just hack for main method
        if (Y.equals("main")){
            String idRef = Integer.toString(currClass.idRef) + "1";
            System.out.println(idRef + ", " + currClass.getId() + ", void (String[] " + n.i2.toString() + ")");
        }

        if (n.i2.toString().equals(Y)){
            String idRef = Integer.toString(currClass.idRef) + "11";
            System.out.println(idRef + ", Param, String[], " + currClass.getId() + "::main()");
        }
        n.i2.accept(this);
        n.s.accept(this);

        currMethod = null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        String id = n.i.toString();
        currClass = symbolTable.getClass(id);
        if (currClass.R != n.i.R || currClass.C != n.i.C)
            return;

        if (id.equals(Y)){
            if (symbolTable.getClass(id).R == n.i.R && symbolTable.getClass(id).C == n.i.C){
                System.out.println(currClass.idRef + ", Class");
            }
        }

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }

        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        String id = n.i.toString();
        currClass = symbolTable.getClass(id);
        if (n.i.toString().equals(n.j.toString())){
            System.out.println("At row " + n.i.R + ", col " + n.i.C + 
                    ", class " + n.i.toString() + " can not extens itself");
            return;
        }

        if (currClass.R != n.i.R || currClass.C != n.i.C)
            return;

        if (!symbolTable.containsClass(n.j.toString())){
            unknownMsg(n.j.toString(), n.j.R, n.j.C);
        } else {
            if (n.j.toString().equals(Y))
                printRefId(n.j.R, n.j.C, symbolTable.getClass(n.j.toString()).idRef);
        }

        if (id.equals(Y)){
            if (symbolTable.getClass(id).R == n.i.R && symbolTable.getClass(id).C == n.i.C){
                System.out.print(currClass.idRef + ", Class");
                Class tmp = currClass;
                while (tmp.parent!=null){
                    System.out.print(", " + tmp.parent);
                    if (symbolTable.containsClass(tmp.parent)){
                        tmp = symbolTable.getClass(tmp.parent);
                    } else {
                        System.out.println(" (! No class " + tmp.parent + ")");
                        break;
                    }
                }
                System.out.print("\n");
            }
        }

        n.j.accept(this);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        if (n.t instanceof IdentifierType){
            IdentifierType tmp = (IdentifierType)n.t;
        }
        n.t.accept(this);

        // data member
        if (currMethod == null){
            Variable tmp = currClass.getVar(n.i.toString());
            if (n.i.toString().equals(Y)){
                if (tmp.R == n.i.R && tmp.C == n.i.C){
                    System.out.println(currClass.getVar(n.i.toString()).idRef +
                            ", Data member, " + mapType(n.t) + ", " + currClass.getId());
                }
            }
        } else {
            Variable tmp = symbolTable.getVar(currMethod, currClass, n.i.toString());
            if (n.i.toString().equals(Y)){
                if (tmp.R == n.i.R && tmp.C == n.i.C){
                    System.out.println(tmp.idRef +
                            ", Local, " + mapType(n.t) + ", " + currClass.getId() +
                            "::" + currMethod.getId() + "()");
                }
            }
        }
        n.i.accept(this);
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        if (n.t instanceof IdentifierType){
            IdentifierType tmp = (IdentifierType)n.t;
        }
        n.t.accept(this);

        String id = n.i.toString();
        currMethod = currClass.getMethod(id);
        if (currMethod.R != n.i.R || currMethod.C != n.i.C){
            currMethod = null;
            return;
        }

        Type retType = currMethod.type();

        Method tmp = currClass.getMethod(id);
        if (id.equals(Y)){
            if (tmp.R == n.i.R && tmp.C == n.i.C){
                System.out.print(currMethod.idRef + ", " + currClass.getId() + ", " + mapType(retType) + " (");
                for ( int i = 0; i < n.fl.size(); i++ ) {
                    System.out.print(mapType(n.fl.elementAt(i).t) + " " + n.fl.elementAt(i).i.toString());
                    if (i != n.fl.size() - 1)
                        System.out.print(", ");
                }
                System.out.print(")\n");
            } else {
                currMethod = null;
                return;
            }
        }

        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        n.e.accept(this);
        currMethod = null;
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        if (n.t instanceof IdentifierType){
            IdentifierType tmp = (IdentifierType)n.t;
            if (symbolTable.containsClass(tmp.s) && tmp.s.equals(Y)){
                printRefId(tmp.R, tmp.C, symbolTable.getClass(tmp.s).idRef);
            }
        }
        n.t.accept(this);

        Variable tmp = currMethod.getParam(n.i.toString());
        if (n.i.toString().equals(Y)){
            if (tmp.R == n.i.R && tmp.C == n.i.C){
                System.out.println(currMethod.getParam(n.i.toString()).idRef + ", Param, " +
                        mapType(n.t) + ", " + currClass.getId() + "::" + currMethod.getId() + "()");
            }
        }

        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {
    }

    public void visit(DoubleType n) {
    }

    // String s;
    public void visit(IdentifierType n) {
        if (!symbolTable.containsClass(n.s))
            unknownMsg(n.s, n.R, n.C);
        else if(n.s.equals(Y)){
            int idRef = symbolTable.getClass(n.s).idRef;
            printRefId(n.R, n.C, idRef);
        }
    }

    // StatementList sl;
    public void visit(Block n) {
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        if (!containId(n.i.toString()))
            unknownMsg(n.i.toString(), n.i.R, n.i.C);
        else if (n.i.toString().equals(Y)){
            int idRef = getIdRef(n.i.toString());
            printRefId(n.i.R, n.i.C, idRef);
        }
        n.i.accept(this);
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        if (!containId(n.i.toString()))
            unknownMsg(n.i.toString(), n.i.R, n.i.C);
        else if (n.i.toString().equals(Y)){
            int idRef = getIdRef(n.i.toString());
            printRefId(n.i.R, n.i.C, idRef);
        }
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        Class tmpClass = null;
        if (n.e instanceof This)
            tmpClass = currClass;
        else if (n.e instanceof NewObject){
            String classId = ((NewObject)n.e).i.toString();
            tmpClass = symbolTable.getClass(classId);
        }
        else if (n.e instanceof IdentifierExp){
            String classId = ((IdentifierExp)n.e).s;
            tmpClass = symbolTable.getClass(classId);
        }

        n.e.accept(this);

        if (tmpClass != null){
            if (symbolTable.getMethod(n.i.toString(), tmpClass.getId()) == null)
                unknownMsg(n.i.toString(), n.i.R, n.i.C);
        }

        // don't require method name
        // if (n.i.toString().equals(Y)){
        //     int idRef = currClass.getMethod(n.i.toString()).idRef;
        //     printRefId(n.i.R, n.i.C, idRef);
        // }

        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {
    }

    public void visit(True n) {
    }

    public void visit(False n) {
    }


    // String s;
    public void visit(IdentifierExp n) {
        if (!containId(n.s))
            unknownMsg(n.s, n.R, n.C);
        else if (n.s.equals(Y)){
            int idRef = getIdRef(n.s);
            printRefId(n.R, n.C, idRef);
        }
    }

    public void visit(This n) {
    }

    // Exp e;
    public void visit(NewArray n) {
        n.e.accept(this);
    }

    // Identifier i;
    public void visit(NewObject n) {
        Class tmpClass = symbolTable.getClass(n.i.toString());
        if (tmpClass == null){
            unknownMsg(n.i.toString(), n.i.R, n.i.C);
        }
        else if (n.i.toString().equals(Y)){
            int idRef = symbolTable.getClass(n.i.toString()).idRef;
            printRefId(n.i.R, n.i.C, idRef);
        }
        n.i.accept(this);
    }

    // Exp e;
    public void visit(Not n) {
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
    }

    public String mapType(Type t){
        String res = "";
        if (t instanceof IntegerType)
            res = "int";
        if (t instanceof BooleanType)
            res = "boolean";
        if (t instanceof DoubleType)
            res = "double";
        if (t instanceof IdentifierType)
            res = ((IdentifierType)t).s;
        if (t instanceof IntArrayType)
            res = "int []";
        return res;
    }

    public void printRefId(int R, int C, int idRef){
        System.out.println(R + ", " + C + ": " + idRef);
    }

    public void unknownMsg(String id, int R, int C){
        System.out.println(id + ": Unknown identifier (" + R + ", " + C + ")");
    }

    // normal cases
    public int getIdRef(String id){
        return symbolTable.getVar(currMethod, currClass, id).idRef;
    }

    public boolean containId(String id){
        return symbolTable.getVarType(currMethod, currClass, id) != null;
    }
}



