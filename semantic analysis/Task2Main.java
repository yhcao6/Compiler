import syntaxtree.*;
import visitor.*;
import myparser.*;

public class Task2Main {
    public static void main(String [] args) {
        try {
            Program root = new MiniJavaParser(System.in).Goal();

            BuildSymbolTableVisitor buildSymTab = new BuildSymbolTableVisitor();
            root.accept(buildSymTab);

            // Build the symbol table
            TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(buildSymTab.getSymTab());
            root.accept(typeCheckVisitor);

        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
