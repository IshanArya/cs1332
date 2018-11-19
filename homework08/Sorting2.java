import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Mridul Bansal
 * @userid mbansal31
 * @GTID 903434553
 * @version 1.0
 */
public class Sorting2 {

    /**
     *
     * Swaps elements in array
     * @param arr
     *            the array that will have swapped elements
     * @param a
     *          position of first element
     * @param b 
     *          position of second element
     */
    private static void swap(Object[] arr, int a, int b) {
        Object temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be: in-place stable
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You MUST
     * implement cocktail sort with this optimization
     *
     * @throws IllegalArgumentException
     *             if the array or comparator is null
     * @param <T>
     *            data type to sort
     * @param arr
     *            the array that must be sorted after the method runs
     * @param comparator
     *            the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        int beg = 0;
        int end = arr.length - 1;
        int ls = 0;
        while (ls != -1) {
            ls = -1;
            for (int i = beg + 1; i <= end; i++) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap(arr, i - 1, i);
                    ls = i - 1;
                }
            }
            end = ls;
            if (ls == -1) {
                break;
            }
            ls = -1;
            for (int i = end - 1; i >= beg; i--) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    ls = i + 1;
                }
            }
            beg = ls;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be: in-place stable
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException
     *             if the array or comparator is null
     * @param <T>
     *            data type to sort
     * @param arr
     *            the array that must be sorted after the method runs
     * @param comparator
     *            the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            int curr = i;
            for (int j = i - 1; j >= 0; j--) {
                if (comparator.compare(arr[curr], arr[j]) < 0) {
                    swap(arr, curr, j);
                    curr--;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be: in-place
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException
     *             if the array or comparator is null
     * @param <T>
     *            data type to sort
     * @param arr
     *            the array that must be sorted after the method runs
     * @param comparator
     *            the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be: stable
     *
     * Have a worst case running time of: O(n log n)
     *
     * And a best case running time of: O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException
     *             if the array or comparator is null
     * @param <T>
     *            data type to sort
     * @param arr
     *            the array to be sorted
     * @param comparator
     *            the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        mergeSort(arr, 0, arr.length, comparator);

    }

    /** 
     * Recursively calls mergeSort until subarray size = 0 or 1
     * Then merges the two subarrays
     * @param <T>
     *            data type to sort
     * @param arr
     *            the array to be sorted
     * @param left
     *             the left index of the subarray to be sorted
     * @param right
     *             the right index of the subarray to be sorted
     * @param comparator
     *            the Comparator used to compare the data in arr
     */
    private static <T> void mergeSort(T[] arr, int left, int right,
        Comparator<T> comparator) {
        if (right - left <= 1) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSort(arr, left, middle, comparator);
        mergeSort(arr, middle, right, comparator);
        int lData = 0;
        int rData = 0;
        int lSize = middle - left;
        int rSize = right - middle;
        T[] temp = (T[]) new Object[middle - left];
        T[] temp2 = (T[]) new Object[right - middle];
        for (int i = left; i < middle; i++) {
            temp[i - left] = arr[i];
        }
        for (int i = middle; i < right; i++) {
            temp2[i - middle] = arr[i];
        }
        for (int i = left; i < right; i++) {
            if (lSize > 0 && rSize > 0) {
                if (comparator.compare(temp[lData], temp2[rData]) <= 0) {
                    arr[i] = temp[lData++];
                    lSize--;
                } else {
                    arr[i] = temp2[rData++];
                    rSize--;
                }
            } else if (lSize > 0) {
                arr[i] = temp[lData++];
                lSize--;
            } else {
                arr[i] = temp2[rData++];
                rSize--;
            }
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
     * It should be: stable
     *
     * Have a worst case running time of: O(kn)
     *
     * And a best case running time of: O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs
     *
     * @throws IllegalArgumentException
     *             if the array is null
     * @param arr
     *            the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("cannot sort a null array");
        }
        int longest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            if (temp > 0) {
                temp *= -1;
            }
            if (temp < longest) {
                longest = temp;
            }
        }
        int rep = 0;
        while (longest != 0) {
            rep++;
            longest /= 10;
        }
        int div = 1;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        for (int i = 0; i < rep; i++) {
            for (int j = 0; j < arr.length; j++) {
                buckets[((arr[j] / div) % 10) + 9].add(arr[j]);
            }
            int c = 0;
            for (int j = 0; j < 19; j++) {
                while (!buckets[j].isEmpty()) {
                    arr[c++] = buckets[j].removeFirst();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use the
     * following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be: in-place
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @throws IllegalArgumentException
     *             if the array or comparator or rand is null or k is not in the
     *             range of 1 to arr.length
     * @param <T>
     *            data type to sort
     * @param k
     *            the index to retrieve data from + 1 (due to 0-indexing) if the
     *            array was sorted; the 'k' in "kth select"; e.g. if k == 1,
     *            return the smallest element in the array
     * @param arr
     *            the array that should be modified after the method is finished
     *            executing as needed
     * @param comparator
     *            the Comparator used to compare the data in arr
     * @param rand
     *            the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, 
        Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random cannot be null");
        }
        if (k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("index is not in bounds of a"
                    + "rray");
        }
        return kthSelect(k, arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Implements kth select.
     *
     * @param <T>
     *            data type to sort
     * @param k
     *            the index to retrieve data from + 1 (due to 0-indexing) if the
     *            array was sorted; the 'k' in "kth select"; e.g. if k == 1,
     *            return the smallest element in the array
     * @param arr
     *            the array that should be modified after the method is finished
     *            executing as needed
     * @param comparator
     *            the Comparator used to compare the data in arr
     * @param rand
     *            the Random object used to select pivots
     * @param start
     *            the start of the subarray where the kth element is
     * @param end
     *            the end of the subarray where the kth element is
     * @return the kth smallest element
     */
    private static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, 
        Random rand, int start, int end) {
        int i = start + 1;
        int j = end;
        int p = rand.nextInt(end - start + 1) + start;
        swap(arr, start, p);
        T pivot = arr[start];
        boolean hasSwapped = false;
        while (i <= j) {
            hasSwapped = false;
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (j >= i && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (j < i) {
                swap(arr, start, j);
                hasSwapped = true;
            } else {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        if (!hasSwapped) {
            swap(arr, start, j);
        }
        if (j == k - 1) {
            return arr[j];
        } else if (j > k - 1) {
            return kthSelect(k, arr, comparator, rand, start, j - 1);
        } else {
            return kthSelect(k, arr, comparator, rand, j + 1, end);
        }
    }
}