import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

public class JunSortingTest {
    private static final int TIMEOUT = 200;
    private SportsNames[] sports;
    private SportsNames[] orders;
    private ComparatorPlus<SportsNames> comp;

    @Before
    public void setUp() {
        /*
            Unsorted

            0 : Soccer
            1 : Football
            2 : Basketball
            3 : Golf
            4 : Tennis
            5 : Badminton
            6 : Racquetball
            7 : Volleyball
            8 : Hockey
         */

        /*
            Sorted

            0 : Badminton
            1 : Basketball
            2 : Football
            3 : Golf
            4 : Hockey
            5 : Racquetball
            6 : Soccer
            7 : Tennis
            8 : Volleyball
         */
        sports = new SportsNames[9];
        sports[0] = new SportsNames("Soccer");
        sports[1] = new SportsNames("Football");
        sports[2] = new SportsNames("Basketball");
        sports[3] = new SportsNames("Golf");
        sports[4] = new SportsNames("Tennis");
        sports[5] = new SportsNames("Badminton");
        sports[6] = new SportsNames("Racquetball");
        sports[7] = new SportsNames("Volleyball");
        sports[8] = new SportsNames("Hockey");

        orders = new SportsNames[9];
        orders[0] = sports[5];
        orders[1] = sports[2];
        orders[2] = sports[1];
        orders[3] = sports[3];
        orders[4] = sports[8];
        orders[5] = sports[6];
        orders[6] = sports[0];
        orders[7] = sports[4];
        orders[8] = sports[7];

        comp = SportsNames.getNameComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(sports, comp);

        for (int i = 0; i < sports.length; i++) {
            assertEquals(orders[i], sports[i]);
        }

        assertArrayEquals(orders, sports);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(sports, comp);

        for (int i = 0; i < sports.length; i++) {
            assertEquals(orders[i], sports[i]);
        }

        assertArrayEquals(orders, sports);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsorted = {17, 743, 672, 780, 917, 743, 623, 288, 432, 281, 76};
        int[] sorted = {17, 76, 281, 288, 432, 623, 672, 743, 743, 780, 917};

        Sorting.lsdRadixSort(unsorted);
        assertArrayEquals(sorted, unsorted);

        unsorted = new int[]{54, 28, 58, 84, 20, 122, -85, 3};
        sorted = new int[]{-85, 3, 20, 28, 54, 58, 84, 122};

        Sorting.lsdRadixSort(unsorted);
        assertArrayEquals(sorted, unsorted);

        unsorted = new int[]{2147483647, -459, -12, 119, 93, 129, 66, -2147483647};
        sorted = new int[]{-2147483647, -459, -12, 66, 93, 119, 129, 2147483647};

        Sorting.lsdRadixSort(unsorted);
        assertArrayEquals(sorted, unsorted);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect() {
        /*
            Sorted

            0 : Badminton
            1 : Basketball
            2 : Football
            3 : Golf
            4 : Hockey
            5 : Racquetball
            6 : Soccer
            7 : Tennis
            8 : Volleyball
         */
        assertEquals(new SportsNames("Volleyball"), Sorting.kthSelect(9, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Tennis"), Sorting.kthSelect(8, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Soccer"), Sorting.kthSelect(7, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Racquetball"), Sorting.kthSelect(6, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Hockey"), Sorting.kthSelect(5, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Golf"), Sorting.kthSelect(4, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Football"), Sorting.kthSelect(3, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Basketball"), Sorting.kthSelect(2, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

        assertEquals(new SportsNames("Badminton"), Sorting.kthSelect(1, sports, comp, new Random(999)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 17 && comp.getCount() != 0);
        comp.clear();

    }

    /**
     *
     *
     * */
    private static class SportsNames {
        private String name;

        /**
         * @param name name
         * */
        public SportsNames(String name) {
            this.name = name;
        }

        /**
         * @return name
         * */
        public String getName() {
            return name;
        }

        /**
         *
         * @return return
         * */
        public static ComparatorPlus<SportsNames> getNameComparator() {
            return new ComparatorPlus<SportsNames>() {
                @Override
                public int compare(SportsNames o1, SportsNames o2) {
                    incrementCount();
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof SportsNames
                    && ((SportsNames) other).name.equals(this.name);
        }


    }

    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * @return count
         * */
        public int getCount() {
            return count;
        }

        /**
         *
         * */
        public void incrementCount() {
            count++;
        }

        /**
         * Make the count 0 to test multiple times in a single method.
         * */
        public void clear() {
            count = 0;
        }
    }
}