package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Formal {
  public Type t;
  public Identifier i;
 
  public Formal(Type at, Identifier ai) {
    t=at; i=ai;
  }

  public Formal(Type at) {
    t=at; i=null;
  }

  public Formal(Identifier ai) {
    t=null; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
