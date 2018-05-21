package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class NewArray extends Exp {
  public Exp e;
  public int R;
  public int C;
  
  public NewArray(Exp ae) {
    e=ae; 
    R = e.R;
    C = e.C;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
