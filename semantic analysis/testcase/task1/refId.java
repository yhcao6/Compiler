class Factorial{
    // Param of main
    public static void main(String[] X){
        System.out.println(0);
    }
}

class X{
    int x;
    int X;
    int [] a;
    boolean b;
    Y y;

    // don't show method ref
    public int X(int i, double d){

        a = new int [X];  // 18. NewArray

        // 4. if
        if (X < i){
            System.out.println(X);
        } else {
        }

        // 5. While
        while (X < i){
            X = X + 1;  // 7. Assign
        }

        // 6. Print
        System.out.println(X);

        // 8. Array Assign
        a[X] = X;

        // 9. And
        b = X < x && x < X;

        // 10. LessThan
        b = X < X;

        // 11. Plus
        X = X + X;

        // 12. Minus
        X = X - X;

        // 13. Times
        X = X * X;

        // 14. ArrayLookup
        X = a[X];

        // 15. ArrayLength
        X = a.length;

        // 16. Call
        X = new Y().X(X, x);  // doesn't show method name
        X = this.y();
        X = y.X(X, x);  // Param

        // 19. Not
        b = !X < 3;

        return X;  // 3. return ref
    }

    public int y(){
        return 0;
    }
}

// 1. extends ref
class Y extends X{
    int y;
    X x;  // 2. identifier type ref

    public int X(int x, int y){
        x = new X();  // New Object
        X = 3;  // instace variable, superclass 
        X = this.y();  // method superclass
        return 0;
    }
}
