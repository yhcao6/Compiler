package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class False extends Exp {
  public int R;
  public int C;
  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
