public class OffByN implements CharacterComparator {
    private int N;
    public OffByN(int n) {
        N = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int result = x - y;
        return result == N || result == -N;
    }
}
