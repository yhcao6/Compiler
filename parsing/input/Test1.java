class Test1{
    public static void main(String[] a){
        {
            System.out.println(a);
        }
    }
}

class Task1 {

    public int Operator(){
        int a;
        a = 1 || 2 || 3; // Or
        a = 1 / 2 / 3; // Integer division
        a = ---1; // Unary minus
        a = 1 ^^ 2 ^^ 3; // Exponetial

        // Exponetial precedence test
        // "*" is precedence 12, "new" is precedence 13
        a = 1 / 2 ^^ new a();
        a = 1 / 2 ^^ new int [1];

        // combine different operator
        a = 1 || 2 && 3;
        a = 1 + 2 * 3 - 4;
        a = 1 + 2 ^^ 3 * 4 / 5;
        a = 1 * (2 + 3) ^^ 4 - 5;
        a = (1 || (2 && (3 < ((4 + 5) - ((6 * 7) / (new int [((!(-8)) ^^ 9)]).length)))));
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

}
