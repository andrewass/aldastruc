package string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestPalindromeTest {

    @Test
    public void shouldReturnInputStringAsLongestPalindromicSubstring(){
        var word = "saippuakivikauppias";
        var result = LongestPalindrome.run(word);
        assertEquals(word, result);
    }

    @Test
    public void shouldReturnFirstMatchWhenMultipleValidResults(){
        var word = "aaaabcdxxxxefyyyy";
        var result = LongestPalindrome.run(word);
        assertEquals("aaaa",result);
    }

    @Test
    public void shouldReturnSingleCharacterWhenInputIsStrictlyUniqueCharacters(){
        var word = "abcdefghijklmnopqrstuv";
        var result = LongestPalindrome.run(word);
        assertEquals("a", result);
    }

    @Test
    public void shouldReturnEmptyResultWhenInputIsEmpty(){
        var word = "";
        var result = LongestPalindrome.run(word);
        assertEquals(result,word);
    }

    @Test
    public void shouldReturnSingleCharacterWhenInputIsSingleCharacter(){
        var word = "e";
        var result = LongestPalindrome.run(word);
        assertEquals(word, result);
    }
}