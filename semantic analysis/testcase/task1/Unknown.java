class Factorial{
    public static void main(String[] a){
        System.out.println(x);
    }
}

class X extends Y{  // extends

    Y y;  // identifier type

    public Y X(Y x){
        z = 3;  // unknown identifier
        x = z + 2;  // unknown identifier expression
        z[3] = 2;  // unknown array assign
        x = new Y().z(ff);  // unknown class
        x = new Y();  // unknown class
        x = this.Z(ff);  // unknown method
        return e;
    }

    public int y(){
        return 0;
    }
}

class F extends X{

    Z z;

    public int x(Foo f){
        f = y;  // okay, superclass
        f = this.y();  // okay, superclass
        f = this.X(z);  // okay, superclass
        return this.y();
    }
}
