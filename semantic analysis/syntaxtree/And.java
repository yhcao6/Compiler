package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

// e1 && e2
public class And extends Exp {
  public Exp e1,e2;
  
  public And(Exp ae1, Exp ae2) { 
    e1=ae1; e2=ae2;
    R = e1.R;
    C = e1.C;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
