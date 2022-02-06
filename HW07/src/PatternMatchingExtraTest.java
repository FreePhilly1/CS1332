import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Pattern Matching Extra tests: Some more tests to test more cases for each of the algorithms, KMP, BM, and Rabin Karp.
 * @author Menelik Gebremariam
 * @version 1.0
 */
public class PatternMatchingExtraTest {
    private static final int TIMEOUT = 300;

    private String kmpPattern;
    private String kmpText;
    private String kmpNoMatch;
    private List<Integer> kmpAnswer;
    private List<Integer> kmpPatternEquivalencyAnswer;

    private String fightPattern;
    private String fightText;
    private String fightNoMatch;
    private List<Integer> fightAnswer;

    private String multiplePattern;
    private String multipleText;
    private List<Integer> multipleAnswer;

    private List<Integer> emptyList;

    private CharacterComparator comparator;


    @Before
    public void setUp() {
        kmpPattern = "yypyp";
        kmpText = "yypyyppyypypyppyypyypyp";
        kmpNoMatch = "yppyypyyp";

        kmpAnswer = new LinkedList<>();
        kmpAnswer.add(7);
        kmpAnswer.add(18);

        kmpPatternEquivalencyAnswer = new LinkedList<>();
        kmpPatternEquivalencyAnswer.add(0);

        fightPattern = "fight";
        fightText = "Floyd Merryweather had a fight with Logan Paul where he won the fight?";
        fightNoMatch = "Jake Paul vs Ben Askren was so disappointing.";

        fightAnswer = new LinkedList<>();
        fightAnswer.add(25);
        fightAnswer.add(64);

        multiplePattern = "cg";
        multipleText = "cgfycgppcg";

        multipleAnswer = new LinkedList<>();
        multipleAnswer.add(0);
        multipleAnswer.add(4);
        multipleAnswer.add(8);

        emptyList = new LinkedList<>();

        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMatch1() {
        /*
            pattern: yypyp
            text: yypyyppyypypyppyypyypyp
            indices: 7, 18
            expected total comparison: 33
        */
        /*
            failure table: [0, 1, 0, 1, 0]
            comparisons: 6
         */
        /*
        y | y | p | y | y | p | p | y | y | p | y | p | p | y | y | p | y | y | p | y | p
        --+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---
        comparisons: 27
         */
        assertEquals(kmpAnswer, PatternMatching.kmp(kmpPattern, kmpText,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 33.", 33, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPNoMatch1() {
        /*
            pattern: yypyp
            text: yppyypyyp
            indices: -
            expected total comparison: 15
         */
        /*
            failure table: [0, 1, 0, 1, 0]
            comparisons: 6
         */
        /*
        y | p | p | y | y | p | y | y | p
        --+---+---+---+---+---+---+---+---
        comparisons: 9
         */
        assertEquals(emptyList, PatternMatching.kmp(kmpPattern, kmpNoMatch,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 15.", 15, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches1() {
        /*
            pattern: cg
            text: cgfycgppcg
            indices: 0, 4, 8
            expected total comparison: 11
         */
        /*
            failure table: [0, 0]
            comparisons: 1
         */
        /*
        c | g | f | y | c | g | p | p | c | g
        --+---+---+---+---+---+---+---+---+---
        comparisons: 10
         */
        assertEquals(multipleAnswer, PatternMatching.kmp(multiplePattern,
                multipleText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 11.", 11, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPLongerText1() {
        /*
            pattern: Jake Paul vs Ben Askren was so disappointing.
            text: fight
            indices: -
            expected total comparison: 0
         */
        assertEquals(emptyList, PatternMatching.kmp(fightNoMatch, fightPattern,
                comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPPatternEqualsText1() {
        /*
            pattern: yypyp
            text: yypyp
            indices: -
            expected total comparison: 5 or 9 (if failure table is built)
         */
        assertEquals(kmpPatternEquivalencyAnswer,
                PatternMatching.kmp(kmpPattern, kmpPattern, comparator));
        assertTrue("Comparison count is different than expected",
                comparator.getComparisonCount() == 5 || comparator
                        .getComparisonCount() == 11);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable1() {
        /*
            pattern: yypyp
            failure table: [0, 1, 0, 1, 0]
            comparisons: 6
         */
        int[] failureTable = PatternMatching.buildFailureTable(kmpPattern,
                comparator);
        int[] expected = {0, 1, 0, 1, 0};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 6.", 6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable2() {
        /*
            pattern: cg
            failure table: [0, 0]
            comparisons: 1
         */
        int[] failureTable = PatternMatching.buildFailureTable(multiplePattern,
                comparator);
        int[] expected = {0, 0};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 1.", 1, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMatch1() {
        /*
            pattern: fight
            text: Floyd Merryweather had a fight with Logan Paul where he won the fight?
            indices: 25, 64
            expected total comparisons: 27
            This will show the worst of the KMP algorithm
         */
        assertEquals(fightAnswer, PatternMatching.boyerMoore(fightPattern,
                fightText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 27.", 27, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreNoMatch1() {
        /*
            pattern: fight
            text: Jake Paul vs Ben Askren was so disappointing.
            indices: -
            expected total comparisons: 9
         */
        assertEquals(emptyList, PatternMatching.boyerMoore(fightPattern,
                fightNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches1() {
        /*
            pattern: cg
            text: cgfycgppcg
            indices: 0, 4, 8
            expected total comparisons: 10
         */
        assertEquals(multipleAnswer,
                PatternMatching.boyerMoore(multiplePattern, multipleText,
                        comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 10.", 10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreLongerText1() {
        /*
            pattern: Jake Paul vs Ben Askren was so disappointing.
            text: fight
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.boyerMoore(fightNoMatch,
                fightPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable1() {
        /*
            pattern: fight
            last table: {f : 0, i : 1, g : 2, h : 3, t : 4}
         */
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(fightPattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('f', 0);
        expectedLastTable.put('i', 1);
        expectedLastTable.put('g', 2);
        expectedLastTable.put('h', 3);
        expectedLastTable.put('t', 4);
        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable2() {
        /*
            pattern: cg
            last table: {c : 0. g : 1}
         */
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(multiplePattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('c', 0);
        expectedLastTable.put('g', 1);
        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMatch1() {
        /*
            pattern: fight
            text: Floyd Merryweather had a fight with Logan Paul where he won the fight?
            indices: 25, 64
            expected total comparisons: 10
         */
        assertEquals(fightAnswer, PatternMatching.rabinKarp(fightPattern,
                fightText, comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 10.", 10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMatch2() {
        /*
            pattern: fight
            text: fight
            indices: 0
            expected total comparisons: 5
         */
        List<Integer> fight2Answer = new LinkedList<>();
        fight2Answer.add(0);
        assertEquals(fight2Answer, PatternMatching.rabinKarp(fightPattern,
                fightPattern, comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpNoMatch1() {
        /*
            pattern: fight
            text: Jake Paul vs Ben Askren was so disappointing.
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.rabinKarp(fightPattern,
                fightNoMatch, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMultipleMatches1() {
        /*
            pattern: cg
            text: cgfycgppcg
            indices: 0, 4, 8
            expected total comparisons: 6
         */
        assertEquals(multipleAnswer,
                PatternMatching.rabinKarp(multiplePattern, multipleText,
                        comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 6.", 6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpLongerText1() {
        /*
            pattern: Floyd Merryweather had a fight with Logan Paul where he won the fight?
            text: fight
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.rabinKarp(fightNoMatch,
                fightPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpEqualHash1() {
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
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpEqualHash2() {
        /*
            These are characters with ASCII values as shown, not the characters
            shown. Most do not have actual characters.

            pattern: cg
            text: cgfycgppcg
            indices: 0, 4, 8
            expected total comparisons: 6
         */
        List<Integer> answer = new ArrayList<>();
        answer.add(0);
        answer.add(4);
        answer.add(8);
        assertEquals(answer, PatternMatching.rabinKarp("\u0063\u0067",
                "\u0063\u0067\u0066\u0079\u0063\u0067\u0070\u0070\u0063\u0067", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 6.", 6, comparator.getComparisonCount());
    }
}