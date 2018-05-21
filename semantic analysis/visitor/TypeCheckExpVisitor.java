package visitor;
import syntaxtree.*;

public class TypeCheckExpVisitor extends TypeDepthFirstVisitor {


    // Exp e1,e2;
    public Type visit(And n) {
        if (! (n.e1.accept(this) instanceof BooleanType) ) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of And must be of type boolean");
            return null;
        }
        if (! (n.e2.accept(this) instanceof BooleanType) ) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("Right side of And must be of type boolean");
            return null;
        }
        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        if (! (n.e1.accept(this) instanceof IntegerType) ) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of LessThan must be of type integer");
            return null;
        }
        if (! (n.e2.accept(this) instanceof IntegerType) ) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("Right side of LessThan must be of type integer");
            return null;
        }
        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        boolean hasDouble = false;
        Type t1 = n.e1.accept(this);
        Type t2 = n.e2.accept(this);

        if ( t1 instanceof DoubleType || t2 instanceof DoubleType )
            hasDouble = true;

        if (! ((t1 instanceof IntegerType) || (t1 instanceof DoubleType))) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of Plus must be of type integer or double");
            return null;
        }
        if (! ((t2 instanceof IntegerType) || (t2 instanceof DoubleType))) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("Right side of Plus must be of type integer or double");
            return null;
        }
        if (hasDouble)
            return new DoubleType();
        else
            return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        boolean hasDouble = false;
        Type t1 = n.e1.accept(this);
        Type t2 = n.e2.accept(this);

        if ( t1 instanceof DoubleType || t2 instanceof DoubleType )
            hasDouble = true;

        if (! ((t1 instanceof IntegerType) || (t1 instanceof DoubleType))) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of Minus must be of type integer or double");
            return null;
        }
        if (! ((t2 instanceof IntegerType) || (t2 instanceof DoubleType))) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("Right side of Minus must be of type integer or double");
            return null;
        }
        if (hasDouble)
            return new DoubleType();
        else
            return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        boolean hasDouble = false;
        Type t1 = n.e1.accept(this);
        Type t2 = n.e2.accept(this);

        if ( t1 instanceof DoubleType || t2 instanceof DoubleType )
            hasDouble = true;

        if (! ((t1 instanceof IntegerType) || (t1 instanceof DoubleType))) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of Times must be of type integer or double");
            return null;
        }
        if (! ((t2 instanceof IntegerType) || (t2 instanceof DoubleType))) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("Right side of Times must be of type integer or double");
            return null;
        }
        if (hasDouble)
            return new DoubleType();
        else
            return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        if (! (n.e1.accept(this) instanceof IntArrayType) ) {
            locMsg(n.e1.R, n.e1.C);
            System.out.println("Left side of ArrayLookup must be of type integer array");
            return null;
        }
        if (! (n.e2.accept(this) instanceof IntegerType) ) {
            locMsg(n.e2.R, n.e2.C);
            System.out.println("index of ArrayLookup must be of type integer");
            return null;
        }
        return new IntegerType();
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        if (! (n.e.accept(this) instanceof IntArrayType) ) {
            locMsg(n.e.R, n.e.C);
            System.out.println("Left side of ArrayLength must be of type integer array");
            return null;
        }
        return new IntegerType();
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {

        if (! (n.e.accept(this) instanceof IdentifierType)){
            locMsg(n.e.R, n.e.C);
            System.out.println("method "+ n.i.toString()
                    + " called on something that is not a"+
                    " class or Object.");
            // System.exit(-1);
            return null;
        }

        String mname = n.i.toString();
        String cname = ((IdentifierType) n.e.accept(this)).s;

        Method calledMethod = TypeCheckVisitor.symbolTable.getMethod(mname,cname);
        if (calledMethod == null){
            locMsg(n.i.R, n.i.C);
            System.out.println("Method " + n.i.toString() + " can not be resolved");
            return null;
        }

        if (n.el.size() != calledMethod.params.size()){
            locMsg(n.i.R, n.i.C);
            System.out.println("length of params is not consistent");
        } else {
            for ( int i = 0; i < n.el.size(); i++ ) {
                Type t1 =null;
                Type t2 =null;

                if (calledMethod.getParamAt(i)!=null)
                    t1 = calledMethod.getParamAt(i).type();
                t2 = n.el.elementAt(i).accept(this);
                if (!TypeCheckVisitor.symbolTable.compareTypes(t1,t2)){
                    locMsg(n.el.elementAt(i).R, n.el.elementAt(i).C);
                    System.out.println("Type Error in arguments passed to " +
                            cname+"." +mname);
                    return null;
                }
            }
        }

        return TypeCheckVisitor.symbolTable.getMethodType(mname,cname);
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return new IntegerType();
    }

    public Type visit(True n) {
        return new BooleanType();
    }

    public Type visit(False n) {
        return new BooleanType();
    }

    // String s;
    public Type visit(IdentifierExp n) {
        return TypeCheckVisitor.symbolTable.getVarType(TypeCheckVisitor.currMethod,
                TypeCheckVisitor.currClass,n.s);
    }

    public Type visit(This n) {
        return TypeCheckVisitor.currClass.type();
    }

    // Exp e;
    public Type visit(NewArray n) {

        if (! (n.e.accept(this) instanceof IntegerType) ) {
            locMsg(n.e.R, n.e.C);
            System.out.println("expression of array declaration must be of type integer");
            return null;
        }
        return new IntArrayType();
    }

    // Identifier i;
    public Type visit(NewObject n) {
        return new IdentifierType(n.i.s);
    }

    // Exp e;
    public Type visit(Not n) {
        if (! (n.e.accept(this) instanceof BooleanType) ) {
            locMsg(n.e.R, n.e.C);
            System.out.println("right side of Not must be of type boolean");
            return null;
        }
        return new BooleanType();
    }

    public void locMsg(int R, int C){
        System.out.print("At row " + R + ", column " + C + ", ");
    }

}
//TypeCheckVisitor.






