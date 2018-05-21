package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IntegerLiteral extends Exp {
  public int i;
  public int R;
  public int C;

  public IntegerLiteral(int ai) {
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
