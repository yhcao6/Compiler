class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac(a));  // param type error
    }
}

class Fac {
    int count;

    public int ComputeFac(int num){
        int num_aux ;
        double x;
        int [] a;
        boolean c;
        Foo f;
        Bar b;

        x = this.Foo();  // Foo can not be resolved
        x = this.y();  // okay

        // 2. If: condition must be boolean type
        if (x){
        } else {
        }

        // okay
        if (c){
        } else {
        }

        // 3. While: condition must be boolean type
        while (x){
        }

        System.out.println(x);  // 4. Print: can only print integer
        System.out.println(0);  // okay

        x = num_aux + 1;  // 5. Assign: okay, int can promoted to double
        num_aux = x - 1;  // 5. Assign: wrong, double can not promoted to int

        x[0] = 1;  // 6. Array Assign: x is not int array
        a[x] = 3;  // 6. Array Assign: index must be integer
        a[0] = x;  // 6. Array Assign: double can not be promoted to int
        a[1] = num_aux; // okay

        c = x && a;  // 7. And: not boolean type
        c = c && num_aux < 3;  // okay

        c = c < x;  // 8. LessThan: c is not of type int (double)
        c = num_aux + 3 * num_aux < c - num_aux;  // 8. wrong

        num_aux = num_aux + c;  // 9. Plus
        num_aux = c + num_aux;  // 9. Plus
        x = num_aux + x;  // okay
        num_aux = num_aux + x;  // wrong

        num_aux = num_aux - c;  // 10. Minus
        num_aux = c - num_aux;  // 10. Minus
        x = num_aux - x;  // okay
        num_aux = num_aux - x;  // wrong

        num_aux = num_aux * c;  // 11. Times
        num_aux = c * num_aux;  // 11. Times
        x = num_aux * x; // okay
        num_aux = num_aux * x;  // wrong

        a = num_aux && x < c + x - c * b + !3;  // if left side is wrong, not check right side
        a = x < c + x - c * b + !3 && num_aux;
        a = !3 + 2 * a - 1 + c < x;
        num_aux = num_aux * c + c;

        num_aux = num_aux[0];  // 12. Array LoopUp: identifier not int array
        num_aux = a[x];  // 12. Array LoopUp: index not int

        num_aux = x.length;  // 13. Array length: identifier not int array

        x = num_aux[0] * a[x] + x.length;

        num_aux = a.foo();  // 14. Call: indentifier is not of Identifier type
        num_aux = f.f();  // 14. Call: method can not be resolved
        num_aux = f.foo();  // param length not equal
        num_aux = f.foo(x, x, x);  // 14. Call: method param not consistent
        num_aux = f.foo(num_aux, x, a);  // okay

        num_aux = a.foo() * f.f() - f.foo() + !f.foo(x, x, x) && f.foo(num_aux, x, a);

        a = new int [x];  // 15. New Array declaration expression must be int
        a = new int [3]; // okay

        c = !x;  // 16. Not: right side must be boolean
        c = !c;  // okay

        f = b;  // 17. asign instance of Bar to instance of Foo
        f = new Foo();  // okay
        f = new Foo() + f.f() * new Foo().foo();

        num_aux = 2 + new Foo().foo(num_aux, x, a);  // okay

        return x;  // 1. Method return type
    }

    public int y(){
        return 0;
    }

}

class Foo extends Fac{

    public int foo(int x, double y, int [] z){
        int num_aux;
        num_aux = this.ComputeFac(num_aux);
        return num_aux;
    }
}

class Bar{
}

