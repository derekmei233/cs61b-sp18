import java.util.Comparator;
public class Maximizer {
    public static OurComparable max(OurComparable[] items) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            int comp = items[i].compareTo(items[maxDex]);
            if (comp > 0) {
                    maxDex = i;
            }
        }
        return items[maxDex];
    }

    public static void main(String[] args) {
        Dog[] dogs = {new Dog("Ellyse", 3), new Dog("Sture", 9),
                new Dog("Arthemesios", 15)};
        //max implemented by OurComparable se we need static cast to make it into dog( or ourcomparable).
        // ourcomparable type cannot be assigned to Dog class
        Dog maxDog = (Dog) max(dogs);
        maxDog.bark();

        Comparator<Dog> nc = Dog.getNameComparator();
        if (nc.compare(dogs[0], dogs[1]) < 0) {
            dogs[1].bark();
        } else {
            dogs[0].bark();
        }
    }
}
// collection
// comparable interface