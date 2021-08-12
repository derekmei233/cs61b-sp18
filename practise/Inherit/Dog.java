import java.util.Comparator;
public class Dog implements OurComparable<Dog> {
    private String name;
    private int size;

    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog o1, Dog o2) {
            return o1.name.compareTo(o2.name);
        }
    }
    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
    public Dog(String n, int s) {
        name = n;
        size = s;
    }
    public void bark() {
        System.out.println(name + " says: bark");
    }

    /** Return -1 if this dog is less than the dog pointed at by o, and so force */
    @Override
    public int compareTo(Dog uddaDog) { // make sure they can go through interface(called by application which is
        //implemented by interface
        if (this.size < uddaDog.size) {
            return -1;
        } else if (this.size == uddaDog.size) {
            return 0;
        }
        return 1;
    }
}
