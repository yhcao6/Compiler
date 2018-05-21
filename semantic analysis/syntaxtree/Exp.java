package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public abstract class Exp {
  public int R;
  public int C;
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
}
