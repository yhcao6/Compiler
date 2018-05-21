class Test3{
    public static void main(String[] a){
        {
            System.out.println(a);
        }
    }
}

class A{
    public int foo(int x int y){
        return 3;
    }

    public int foo(int [] x boolean y int z){
        return 3;
    }
}

class B{
    public int foo(int x, int){
        return 3;
    }

    public int foo(int x, y, z){
        return 3;
    }
}

class C{
    public int foo(   (int x){
        return 3;
    }

    public int foo(int x, * a){
        return 3;
    }
}
