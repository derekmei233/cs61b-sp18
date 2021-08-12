package MyList;

public class IntList{
    int first;
    IntList rest;
    /**return the ith entry of the MyList.IntList using recurrsion */
    public IntList(int pfirst, IntList prest){
        first = pfirst;
        rest = prest;
    }
    public int get(int i ){
        if (i == 0){
            return first;
        } else { 
            return rest.get(i - 1);
        }
    }
    /**return the size of MyList.IntList using recurrsion */
    public int recc_size(){
        if (this.rest == null) {
            return 1;
        } else {
            return 1 + this.rest.recc_size();
        }
    }
    /**return the size of MyList.IntList using iteration */
    public int iter_size(){
        IntList pointerIL = this;
        int sz = 0;
        while (pointerIL != null){
            sz += 1;
            pointerIL = pointerIL.rest;
        }
        return sz;
    }

    public static void main(String[] args){
        IntList L = new IntList(15, null);
        L = new IntList(10, L);
        L = new IntList(5, L);
        System.out.println(L.iter_size());
        System.out.println(L.recc_size());
        System.out.println(L.get(2));
    }
}