class Test1{
    public static void main(String[] a){
        {
            System.out.println(a);
        }
    }
}

class Task3 {

    public int Operator(){
        int a;
        a = 1 ^^ 2 ^^ 3; // test ^^
	    return a;
    }

    public int ForLoop(){
        int a;
        a = 0;
        for (int i=0, j=0; i+j < 10; i=i+1, j=j+1){
            a = a + 1;
        }
        return a;
    }

    public int MultiForLoop(){
        int a;
        int b;
        a = 0;
        b = 0;
        for (int i = 0; i < 3; i=i+1){
            for (int j = 0; j < 3; j=j+1)
            {
                a = a + 1;
                b = b + 1;
            }
        }
        return a;
    }

}
