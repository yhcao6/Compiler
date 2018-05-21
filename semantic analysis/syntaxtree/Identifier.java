package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Identifier {
  public String s;
  public int R;
  public int C;

  public Identifier(String as) { 
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  public String toString(){
    return s;
  }
}
