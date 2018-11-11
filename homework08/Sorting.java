import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class Sorting {


    /**
     * Swap elements at given two indices
     *
     * @param arr    the array in which to do the swap
     * @param index1 index of first element
     * @param index2 index of second element
     * @param <T>    data type of elements
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        int sortedStart = 0;
        int sortedEnd = arr.length - 1;

        while (sortedStart < sortedEnd) {
            int lastSorted = sortedStart;
            for (int i = sortedStart; i < sortedEnd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    lastSorted = i;
                }
            }
            sortedEnd = lastSorted;
            for (int i = sortedEnd; i > sortedStart; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i, i - 1);
                    lastSorted = i;
                }
            }
            sortedStart = lastSorted;
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     * <p>
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Note that there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int largestValueIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[largestValueIndex]) > 0) {
                    largestValueIndex = j;
                }
            }
            if (largestValueIndex != i) {
                swap(arr, i, largestValueIndex);
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        if (arr.length == 1) {
            return;
        }

        T[] arr1 = (T[]) new Object[arr.length / 2];
        for (int i = 0; i < arr.length / 2; i++) {
            arr1[i] = arr[i];
        }
        T[] arr2 = (T[]) new Object[arr.length % 2 == 0
                ? arr.length / 2 : arr.length / 2 + 1];
        for (int i = arr.length / 2; i < arr.length; i++) {
            arr2[i - (arr.length / 2)] = arr[i];
        }

        mergeSort(arr1, comparator);
        mergeSort(arr2, comparator);

        int pointerForArr1 = 0;
        int pointerForArr2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (pointerForArr1 == arr1.length) {
                arr[i] = arr2[pointerForArr2++];
            } else if (pointerForArr2 == arr2.length) {
                arr[i] = arr1[pointerForArr1++];
            } else {
                arr[i] = comparator.compare(arr1[pointerForArr1],
                        arr2[pointerForArr2]) > 0
                        ? arr2[pointerForArr2++] : arr1[pointerForArr1++];
            }

        }

    }


    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     * <p>
     * Do NOT use anything from the Math class except Math.abs
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array.");
        }

        LinkedList<Integer>[] counter = new LinkedList[19];

        for (int i = 0; i < counter.length; i++) {
            counter[i] = new LinkedList<>();
        }
        int divisor = 1;
        boolean cont = true;

        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int placeValue = arr[i] / divisor;
                if (placeValue != 0) {
                    cont = true;
                }


                int bucket = placeValue % 10 + 9;
                counter[bucket].add(arr[i]);
            }
            if (!cont) {
                break;
            }

            int idx = 0;
            for (int i = 0; i < counter.length; i++) {
                while (!counter[i].isEmpty()) {
                    arr[idx++] = counter[i].poll();
                }
            }
            if (divisor == 1000000000) {
                break;
            }
            divisor *= 10;


        }
    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     * <p>
     * int pivotIndex = r.nextInt(b - a) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from
     *                   + 1 (due to 0-indexing) if
     *                   the array was sorted; the 'k'
     *                   in "kth select"; e.g. if k ==
     *                   1, return the smallest element in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws IllegalArgumentException if the array or comparator or rand is
     *                                  null or k is not in the range of 1 to
     *                                  arr.length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random cannot be null.");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException(
                    "K is not in the range 1 - " + arr.length);
        }

        int start = 0;
        int end = arr.length - 1;

        return arr[quickSort(k, arr, comparator, start, end, rand)];


    }

    /**
     * Recursive helper method for kth select
     *
     * @param k          element to find
     * @param arr        array searching in
     * @param comparator comparator for T
     * @param start      starting index for loop
     * @param end        terminating index for loop
     * @param rand       user given random number generator
     * @param <T>        type of values in array
     * @return j when position j is found
     */
    private static <T> int quickSort(int k, T[] arr,
                                     Comparator<T> comparator,
                                     int start, int end, Random rand) {
        int pivot = start + rand.nextInt(end + 1 - start);
        swap(arr, start, pivot);
        int i = start + 1;
        int j = end;
        while (i <= j) {
            if (comparator.compare(arr[i], arr[start]) <= 0) {
                i++;
            } else if (comparator.compare(arr[j], arr[start]) >= 0) {
                j--;
            } else {
                swap(arr, i, j);
                i++;
                j--;
            }

        }

        swap(arr, j, start);
        if (j == k - 1) {
            return j;
        }
        if (j > k - 1) {
            return quickSort(k, arr, comparator, start, j - 1, rand);
        } else if (j < k - 1) {
            return quickSort(k, arr, comparator, j + 1, end, rand);
        }
        return k - 1;


    }


}
