package pracADT;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKPopularWords {
    public static void main(String[] words, int k) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String s : words) {
            if (!counts.containsKey(s)) {
                counts.put(s, 1);
            } else {
                counts.put(s, counts.get(s) + 1);
            }
        }
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return counts.get(o2) - counts.get(o1);
            }
        });
        for (String word: counts.keySet()) {
            pq.add(word);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(pq.poll());
        }
    }
}
