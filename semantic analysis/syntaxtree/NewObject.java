package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class NewObject extends Exp {
  public Identifier i;
  public int R;
  public int C;
  
  public NewObject(Identifier ai) {
    i=ai;
    R = i.R;
    C = i.C;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
