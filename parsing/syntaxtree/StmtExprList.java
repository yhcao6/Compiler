package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class StmtExprList extends Statement {
  public StatementList sl;

  public StmtExprList(StatementList asl) {
    sl=asl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

