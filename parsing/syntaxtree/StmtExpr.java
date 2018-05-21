package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class StmtExpr extends Statement {
  public Identifier i;
  public Exp e1, e2;
  public int type;

  public StmtExpr(Identifier ai, Exp ae) {
    i=ai; e1=ae; 
    type=1;
  }

  public StmtExpr(Identifier ai, Exp ae1, Exp ae2) {
    i=ai; e1=ae1; e2=ae2;
    type=2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

