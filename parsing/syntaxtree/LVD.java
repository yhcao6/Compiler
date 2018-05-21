package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class LVD extends Statement {
  public Type t;
  public StatementList sl;

  public LVD(Type at, StatementList asl) {
    t=at; sl=asl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

