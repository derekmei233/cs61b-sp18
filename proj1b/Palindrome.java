public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dofCharacters = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            dofCharacters.addLast(word.charAt(i));
        }
        return dofCharacters;
    }
    private boolean isPalindromeHelper(Deque<Character> dofc) {
        if (dofc.size() == 0 || dofc.size() == 1) {
            return true;
        } else {
            if (dofc.removeFirst() != dofc.removeLast()) {
                return false;
            } else {
                return isPalindromeHelper(dofc);
            }
        }
    }
    private boolean isPalindromeHelper(Deque<Character> dofc, CharacterComparator cc) {
        if (dofc.size() == 0 || dofc.size() == 1) {
            return true;
        } else {
            if (!cc.equalChars(dofc.removeFirst(), dofc.removeLast())) {
                return false;
            } else {
                return isPalindromeHelper(dofc, cc);
            }
        }
    }
    public boolean isPalindrome(String word) {
        Deque<Character> dofCharacters = wordToDeque(word);
        return isPalindromeHelper(dofCharacters);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dofCharacters = wordToDeque(word);
        return isPalindromeHelper(dofCharacters, cc);
    }
}
