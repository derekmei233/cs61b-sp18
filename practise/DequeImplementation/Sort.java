public class Sort{
    public static void sort(String[] p){
        // find the smallest item
        // move it to front
        // selection sort the rest
        _sort(p, 0);
        }
    // soul of this lecture. resorting help method to execute complicate functionality.(scaffold)
    // a helper for sort method instead of using slicing in python style
    private static void _sort(String[] x, int start){
        int smallestIndex = FindSmallest(x, start);
        swap(x, start, smallestIndex);
        while (start + 1 < x.length) {
            start += 1;
            _sort(x, start);
        }
    }
    public static int FindSmallest(String[] x, int start) {
    // use public temporally here
        int smallestIndex = start;
        for ( ; start < x.length; start += 1){
            //compare String
            int cmp = x[start].compareTo(x[smallestIndex]);
            // if x[i] < x[smallestIndex] -> return -1
            if ( cmp < 0 ) {
                smallestIndex = start;
            }
        }
        return smallestIndex;
    }
    public static void swap(String[] x, int a, int b){
        String temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }
}