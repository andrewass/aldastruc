package string;

import java.util.stream.IntStream;

public class LongestPalindrome {

    private LongestPalindrome() {
    }

    public static String run(String source) {
        var word = preProcessWord(source);
        var lcp = lcp(word);

        return postProcessWord(lcp, word);
    }

    private static String preProcessWord(String word) {
        var length = word.length() * 2 + 1;
        var processedWord = new StringBuilder("(");

        IntStream.range(0, length).forEach(i -> {
            if (i % 2 == 0) {
                processedWord.append("#");
            } else {
                processedWord.append(word.charAt(i / 2));
            }
        });

        return processedWord.append(")").toString();
    }

    private static int[] lcp(String word) {
        var lcp = new int[word.length()];
        var center = 0;
        var rightEdge = 0;

        for (int i = 1; i < word.length() - 1; i++) {
            var mirror = center * 2 - i;
            if (i < rightEdge) {
                lcp[i] = Math.min(rightEdge - i, lcp[mirror]);
            }
            while (word.charAt(i - 1 - lcp[i]) == word.charAt(i + 1 + lcp[i])) {
                lcp[i]++;
            }
            if (i + lcp[i] > rightEdge) {
                center = i;
                rightEdge = i + lcp[i];
            }
        }

        return lcp;
    }

    private static String postProcessWord(int[] lcp, String word) {
        var maxLen = 0;
        var index = 0;

        for (int i = 0; i < word.length(); i++) {
            if (lcp[i] > maxLen) {
                maxLen = lcp[i];
                index = i;
            }
        }

        return word.substring(index - maxLen, index + maxLen).replace("#", "");
    }
}
