import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * HW7 JUnit test for PatternMatching class.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 */
public class JunPatternMatchingTest {
    private static final int TIMEOUT = 200;

    // Pattern and text for kmp & boyer moore
    private String pattern;
    private String text;
    private String pattern1;
    private String text1;
    private String wrong;
    private String wrong1;
    private List<Integer> ans;
    private List<Integer> ans1;

    // Pattern and text for rabin karp
    private String rkPattern;
    private String rkText;
    private String rkWrong;
    private List<Integer> rkAns;

    private List<Integer> empty;

    private CharacterComparator comparator;

    @Before
    public void setUp() {
        wrong = "aabcaabd";
        wrong1 = "there is no such thing as match";

        pattern = "test";
        text = "this is a test text";
        ans = new ArrayList<>();
        ans.add(10);

        pattern1 = "aaba";
        text1 = "aabaacaadaabaaba";
        ans1 = new ArrayList<>();
        ans1.add(0);
        ans1.add(9);
        ans1.add(12);

        rkPattern = "dba";
        rkText = "ccaccaaedbadbcdba";
        rkWrong = "aac";
        rkAns = new ArrayList<>();
        rkAns.add(8);
        rkAns.add(14);

        empty = new ArrayList<>();

        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void kmp() {
        /*
            pattern: test
            text: this is a test text
         */
        assertEquals(ans, PatternMatching.kmp(pattern, text, comparator));
        assertTrue("Comparator not used.", comparator.getComparisonCount() != 0);
        assertEquals("Counts: " + comparator.getComparisonCount() + ". Should be 23.",
                23, comparator.getComparisonCount());

        comparator.clear(); // Personally made this method to test every kmp matching in single method.
        //Just set comparisonCount to 0 in the CharacterComparator class.

        /*
            pattern: aaba
            text: aabaacaadaabaaba
         */
        assertEquals(ans1, PatternMatching.kmp(pattern1, text1, comparator));
        assertTrue("Comparator not used.", comparator.getComparisonCount() != 0);
        assertEquals("Counts: " + comparator.getComparisonCount() + ". Should be 24.",
                24, comparator.getComparisonCount());

        comparator.clear();

        /*
            Another multiple match test for reassurance.
         */
        String a = "ab";
        String b = "abab";

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(2);

        assertEquals(list, PatternMatching.kmp(a, b, comparator));
        assertTrue("Comparator not used.", comparator.getComparisonCount() != 0);
        assertEquals(5, comparator.getComparisonCount());

        comparator.clear();

        /*
            No match case
            pattern: aabcaabd
            text: aabaacaadaabaaba
         */
        assertEquals(empty, PatternMatching.kmp(wrong, text1, comparator));
        assertTrue("Comparator not used.", comparator.getComparisonCount() != 0);
        assertEquals("Counts: " + comparator.getComparisonCount() + ". Should be 23.",
                23, comparator.getComparisonCount());

        comparator.clear();

        /*
            Longer text
         */
        assertEquals(empty, PatternMatching.kmp(text, pattern, comparator));
        assertEquals(0, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void buildLastTable() {
        /*
            pattern: test
         */
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('t', 3);
        expected.put('e', 1);
        expected.put('s', 2);
        Map<Character, Integer> actual = PatternMatching.buildLastTable(pattern);

        assertEquals(expected, actual);

        expected.clear();
        actual.clear();

        /*
            pattern: aaba
         */
        expected.put('a', 3);
        expected.put('b', 2);
        actual = PatternMatching.buildLastTable(pattern1);

        assertEquals(expected, actual);

        expected.clear();
        actual.clear();

        /*
            pattern: aabcaabd
         */
        expected.put('a', 5);
        expected.put('b', 6);
        expected.put('c', 3);
        expected.put('d', 7);
        actual = PatternMatching.buildLastTable(wrong);

        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void boyerMoore() {
        /*
            pattern: test
            text: this is a test text
         */
        assertEquals(ans, PatternMatching.boyerMoore(pattern, text, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(11, comparator.getComparisonCount());

        comparator.clear();

        /*
            pattern: aaba
            text: aabaacaadaabaaba
         */
        assertEquals(ans1, PatternMatching.boyerMoore(pattern1, text1, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(20, comparator.getComparisonCount());

        comparator.clear();

        /*
            No match case
            pattern: aabcaabd
            text: aabaacaadaabaaba
         */
        assertEquals(empty, PatternMatching.boyerMoore(wrong, text1, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(6, comparator.getComparisonCount());

        comparator.clear();

        /*
            Longer text
         */
        assertEquals(empty, PatternMatching.boyerMoore(text, pattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void rabinKarp() {
        /*
            pattern: dba
            text: ccaccaaedbadbcdba
         */
        assertEquals(rkAns, PatternMatching.rabinKarp(rkPattern, rkText, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(6, comparator.getComparisonCount());

        comparator.clear();

        /*
            pattern: test
            text: this is a test text
         */
        assertEquals(ans, PatternMatching.rabinKarp(pattern, text, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(4, comparator.getComparisonCount());

        comparator.clear();

        /*
            pattern: aaba
            text: aabaacaadaabaaba
         */
        assertEquals(ans1, PatternMatching.rabinKarp(pattern1, text1, comparator));
        assertTrue(comparator.getComparisonCount() != 0);
        assertEquals(12, comparator.getComparisonCount());

        comparator.clear();

        /*
            No match case
            pattern: aabcaabd
            text: aabaacaadaabaaba
         */
        assertEquals(empty, PatternMatching.rabinKarp(wrong, text1, comparator));
        assertEquals(0, comparator.getComparisonCount());

        /*
            Longer text
         */
        assertEquals(empty, PatternMatching.rabinKarp(text, pattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void rabinKarpEqualHash() {
        /*
            These are characters with ASCII values as shown, not the characters
            shown. Most do not have actual characters.

            pattern: 011
            text: 00(114)011
            indices: 2
            expected total comparisons: 5
         */
        List<Integer> answer = new ArrayList<>();
        answer.add(3);
        assertEquals(answer, PatternMatching.rabinKarp("\u0000\u0001\u0001",
                "\u0000\u0000\u0072\u0000\u0001\u0001", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());

        answer.clear();
        comparator.clear();


    }
}