import syntaxtree.*;
import visitor.*;
import myparser.*;

public class Task1Main {
    public static void main(String [] args) {
        try {
            String Y = args[0];
            Program root = new MiniJavaParser(System.in).Goal();

            BuildSymbolTableVisitor buildSymTab = new BuildSymbolTableVisitor();
            root.accept(buildSymTab);

            Task1 t1 = new Task1(buildSymTab.getSymTab(), Y);
            root.accept(t1);

        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
