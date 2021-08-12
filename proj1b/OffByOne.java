public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int result = x - y;
        return result == 1 || result == -1;
    }
}
