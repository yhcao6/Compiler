package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class ForLoop extends Statement {
  public Exp e;
  public Statement s1, s2, s3;

  public ForLoop(Exp ae, Statement as1, Statement as2, Statement as3) {
    e = ae;
    s1 = as1;
    s2 = as2;
    s3 = as3;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

