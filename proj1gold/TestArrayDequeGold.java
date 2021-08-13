import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    
    @Test
    public void testBasicOperations() {
        // Test only removeFirst(), removeLast(), addFirst(), addLast() methods.
        // We tried get() and size() methods are absolutely right before this test.
        StudentArrayDeque<Integer> studentAD = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionAD = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            int flag = StdRandom.uniform(4);
            if (flag == 0) {
                String str = String.format("addFirst(%s)\n",String.valueOf(i));
                sb.append(str);
                studentAD.addFirst(i);
                solutionAD.addFirst(i);
                assertEquals(sb.toString(), solutionAD.size(), studentAD.size());
            } else if (flag == 1) {
                String str = String.format("addLast(%s)\n",String.valueOf(i));
                sb.append(str);
                studentAD.addLast(i);
                solutionAD.addLast(i);
                assertEquals(sb.toString(), solutionAD.size(), studentAD.size());
            } else if (flag == 2 && studentAD.size() != 0) {
                String str = "removeFirst()\n";
                sb.append(str);
                Integer studentR = studentAD.removeFirst();
                Integer solutionR = solutionAD.removeFirst();
                assertEquals(sb.toString(), solutionR, studentR);
                assertEquals(sb.toString(), solutionAD.size(), studentAD.size());
            } else if (flag == 3 && studentAD.size() != 0) {
                String str = "removeLast()\n";
                sb.append(str);
                Integer studentR = studentAD.removeLast();
                Integer solutionR = solutionAD.removeLast();
                assertEquals(sb.toString(), solutionR, studentR);
                assertEquals(sb.toString(), solutionAD.size(), studentAD.size());
                }
            }
        }
    }
