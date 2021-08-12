public class fib{
    public static int _fib(int i){
        if ( i == 1 || i == 0 ){
            return i;
        } else{
            return _fib(i - 1) + _fib( i - 2);
        }
    }
    public static void main(String[] args){
        int temp = Integer.parseInt(args[0]);
        int result = _fib(temp);
        System.out.println(result);
    }
}