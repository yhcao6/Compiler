class Factorial{
    public static void main(String[] a){
        System.out.println(0);
    }
}

class X{
    int x;
    double x;  // data member redecl
    Y y;
    double y;

    public int foo(int x, double x){  // param redecl
        int x;  // local var redecl
        double x;
        x = x + 2;
        return x;
    }

    public int foo(){  // redecl, no method overload skip this method
        int x;
        double x;
        return 0;
    }

    public int f(){
        int x;  // local variable redecl
        double x;
        return 0;
    }

    // not affect normal function
    public int fac(int x, double y){
        x = this.foo(x, y);
        System.out.println(x);
        while (x < 10){
            if (x < 5){
                x = x + 1;
            } else {
                x = x - 1;
                x = x * 2;
            }
        }
        return x;
    }
}


// class redecl
class X extends Y{
    public int y(int x){
        return x;
    }
}

class Y{
}

class Y{
}
