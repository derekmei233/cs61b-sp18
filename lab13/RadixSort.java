import edu.princeton.cs.algs4.MinPQ;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] result = asciis.clone();

        int max = maxLength(asciis);
        for (int i = 0; i < max; i++) {
            result = sortHelperLSD(result, i);
        }
        //sortHelperMSD(result, 0, asciis.length, 0);
        return result;
    }
    private static int maxLength(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (String s: asciis) {
            max = s.length() > max ? s.length() : max;
        }
        return max;
    }
    private static int charAtIndex(String s, int index, int maxLength) {
        if (s.length() - 1 >= maxLength - index - 1) {
            return (int) s.charAt(maxLength - index - 1);
        } else {
            return -1;
        }
    }
    private static HashMap<Integer, Integer> mapToSorted(HashMap<Integer, Integer> count) {
        // constant key values -> O(1)
        MinPQ<Integer> minPQ = new MinPQ<>();
        for (int i: count.keySet()) {
            minPQ.insert(i);
        }
        HashMap<Integer, Integer> result = new HashMap<>();
        int c = 0;
        while (!minPQ.isEmpty()) {
            result.put(minPQ.delMin(), c);
            c += 1;
        }
        return result;
    }
    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int max = maxLength(asciis);
        String[] result = new String[asciis.length];
        HashMap<Integer, Integer> count = new HashMap<>();
        for (String s: asciis) {
            int idx = charAtIndex(s, index, max);
            if (count.containsKey(idx)) {
                count.put(idx, count.get(idx) + 1);
            } else {
                count.put(idx, 1);
            }
        }
        HashMap<Integer, Integer> tmp = mapToSorted(count);
        int[] start = new int[tmp.size()];
        int c = 0;
        for (int idx: tmp.keySet()) {
            start[tmp.get(idx)] = c;
            c += count.get(idx);
        }
        for (String ascii : asciis) {
            int cur = charAtIndex(ascii, index, max);
            result[start[tmp.get(cur)]] = ascii;
            start[tmp.get(cur)] += 1;
        }
        return result;
    }
    private static int charAtPos(String s, int pos) {
        if (pos <= s.length() - 1) {
            return (int) s.charAt(pos);
        } else {
            return -1;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end - start <= 1 || index >= maxLength(asciis)) {
            return;
        }

        String[] result = new String[end - start];
        System.arraycopy(asciis, start, result, 0, end - start);
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i = start; i < end; i++) {
            int pos = charAtPos(result[i - start], index);
            if (count.containsKey(pos)) {
                count.put(pos, count.get(pos) + 1);
            } else {
                count.put(pos, 1);
            }
        }
        HashMap<Integer, Integer> tmp = mapToSorted(count);
        int[] startP = new int[tmp.size()];
        int c = 0;
        for (int i: tmp.keySet()) {
            startP[tmp.get(i)] = c;
            c += count.get(i);
        }
        int[] nStartP = startP.clone();
        String[] nResult = new String[result.length];
        for (String s: result) {
            int idx = charAtPos(s, index);
            nResult[startP[tmp.get(idx)]] = s;
            startP[tmp.get(idx)] += 1;
        }
        System.arraycopy(nResult, 0, asciis, start, end - start);
        for (int i: tmp.keySet()) {
            sortHelperMSD(asciis, start + nStartP[tmp.get(i)],
                    start + nStartP[tmp.get(i)] + count.get(i), index + 1);
        }
    }
    public static void main(String[] args) {
        // test case is borrowed from https://github.com/ema00/
        // Berkeley-CS61B-sp18/blob/master/lab13/RadixSort.java
        String[] unsorted0 = {"blala", "asdasc", "fdsd", "eef", "e", "aaaaa", "eea", " s"};
        String[] unsorted1 = {"b", "c", "f", "e", "d", "a"};
        String[] unsorted2 = {"ba", "ca", "fa", "ea", "da", "aa"};
        String[] unsorted3 = {"ab", "ac", "af", "ae", "ad", "aa"};
        String[] unsorted4 = {"abc", "ab", "aba", "ae", "ac", "aa"};
        String[] unsorted5 = {"ab", "abc", "aba", "eef", "ee", "ac", "aa"};
        String[] sorted0 = sort(unsorted0);
        String[] sorted1 = sort(unsorted1);
        String[] sorted2 = sort(unsorted2);
        String[] sorted3 = sort(unsorted3);
        String[] sorted4 = sort(unsorted4);
        String[] sorted5 = sort(unsorted5);
        System.out.println(Arrays.asList(sorted0));
        System.out.println(Arrays.asList(sorted1));
        System.out.println(Arrays.asList(sorted2));
        System.out.println(Arrays.asList(sorted3));
        System.out.println(Arrays.asList(sorted4));
        System.out.println(Arrays.asList(sorted5));
    }
}
