package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IdentifierType extends Type {
  public String s;
  public int R;
  public int C;

  public IdentifierType(String as) {
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
