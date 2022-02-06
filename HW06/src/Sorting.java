import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Phillip Kim
 * @version 1.0
 * @userid pkim90
 * @GTID 903376077
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }
        for (T data : arr) {
            if (data == null) {
                throw new IllegalArgumentException("Data in array cannot be null");
            }
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T copy = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = copy;
                i--;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null");
        }
        for (T data : arr) {
            if (data == null) {
                throw new IllegalArgumentException("Data in array cannot be null");
            }
        }
        if (arr.length == 1) {
            return;
        }
        int length1 = arr.length;
        int midIndex = length1 / 2;
        T[] left = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        T[] right = (T[]) new Object[length1 - midIndex];
        for (int i = midIndex; i < length1; i++) {
            right[i - midIndex] = arr[i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < midIndex && rightIndex < length1 - midIndex) {
            if (comparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
                arr[leftIndex + rightIndex] = left[leftIndex];
                leftIndex++;
            } else {
                arr[leftIndex + rightIndex] = right[rightIndex];
                rightIndex++;
            }
        }
        while (leftIndex < left.length) {
            arr[leftIndex + rightIndex] = left[leftIndex];
            leftIndex++;
        }
        while (rightIndex < right.length) {
            arr[leftIndex + rightIndex] = right[rightIndex];
            rightIndex++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) {
                buckets[i] = new LinkedList<>();
            }
        }
        int k = 0;
        for (int number : arr) {
            int count = 0;
            while (number != 0) {
                count++;
                number /= 10;
            }
            if (count > k) {
                k = count;
            }
        }
        for (int i = 0; i < k; i++) {
            for (int value : arr) {
                int num1 = value;
                int place = 0;
                while (place != i) {
                    place++;
                    num1 /= 10;
                }
                int index = num1 % 10;
                buckets[index + 9].addLast(value);
            }
            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx++] = bucket.remove();
                }
            }
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array, comparator, or random object cannot be null");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k is not in the range of the given array");
        }
        rKthSelect(arr, 0, arr.length - 1, k, comparator, rand);
        return arr[k - 1];
    }

    /**
     * Recursive method that finds the kth smallest element in the array.
     *
     * @param arr array of numbers to examine
     * @param first the first index
     * @param last the last idnex
     * @param k the kth smallest element we are looking for
     * @param comparator comparator to compare elements in the array
     * @param rand random number generator to select the pivot
     * @param <T> data type to sort
     */
    private static <T> void rKthSelect(T[] arr, int first, int last, int k, Comparator<T> comparator, Random rand) {
        if (last - first < 1) {
            return;
        }
        int pivotIdx = rand.nextInt(last - first + 1) + first;
        T pivotVal = arr[pivotIdx];
        T copy = arr[first];
        arr[first] = pivotVal;
        arr[pivotIdx] = copy;
        int i = first + 1;
        int j = last;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                T copy1 = arr[i];
                arr[i] = arr[j];
                arr[j] = copy1;
                i++;
                j--;
            }
        }
        T copy2 = arr[first];
        arr[first] = arr[j];
        arr[j] = copy2;
        if (j == k - 1) {
            return;
        }
        if (j > k - 1) {
            rKthSelect(arr, first, j - 1, k, comparator, rand);
        } else {
            rKthSelect(arr, j + 1, last, k, comparator, rand);
        }
    }
}
