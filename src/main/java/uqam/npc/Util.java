package uqam.npc;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String concat(String a, String b) {
        StringBuilder sb = new StringBuilder();
        return sb.append(a).append(b).toString();
    }

    // Generate random array
    public static int[] randomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int) ((Math.random() - 0.5) * 1000) % 100;
        return arr;
    }

    // Print
    public static void print(int[] array) {
        System.out.print("[ ");
        for (int a : array)
            System.out.print(a + ", ");
        System.out.println("]");
    }

    // Swapping two elements
    static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    //-------------------------- Sorting algorithms

    // Insertion sort
    public static int[] insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] < array[j]) {
                    for (int k = i; k > j; k--) {
                        swap(array, k, k - 1);
                    }
                    break;
                }
            }
        }
        return array;
    }

    // Selection sort
    public static int[] selectionSort(int[] array) {
        int min;
        for (int i = 0; i < array.length; i++) {
            min = i;
            for (int j = i; j < array.length; j++) {
                if (array[min] > array[j])
                    min = j;
            }
            swap(array, i, min);
        }
        return array;
    }

    public static int[] shellSort(int[] array) {
        for (int offset = array.length / 2; offset > 0; offset /= 2) {
            for (int i = offset; i < array.length; i++) {
                int index = i;
                for (int j = i - offset; j >= 0; j -= offset) {
                    if (array[j] > array[index]) {
                        swap(array, j, index);
                        index = j;
                    }
                }
            }
        }
        return array;
    }

    // Merge sort
    public static int[] mergeSort(int[] array) {
        int[][] s = split(array);
        array = merge(s[0], s[1]);
        return array;
    }

    private static int[][] split(int[] a) {
        int size = a.length / 2;
        int[][] s = new int[2][];
        s[0] = new int[size];
        s[1] = new int[a.length - size];
        System.arraycopy(a, 0, s[0], 0, size);
        System.arraycopy(a, size, s[1], 0, a.length - size);
        return s;
    }

    private static int[] merge(int[] a, int[] b) {
        if (a.length > 1) {
            int[][] s = split(a);
            a = merge(s[0], s[1]);
        }
        if (b.length > 1) {
            int[][] s = split(b);
            b = merge(s[0], s[1]);
        }

        int ai = 0, bi = 0;
        int[] m = new int[a.length + b.length];
        for (int i = 0; i < m.length; i++) {
            if (ai < a.length && bi < b.length) {
                if (a[ai] > b[bi])
                    m[i] = b[bi++];
                else
                    m[i] = a[ai++];
            } else if (ai < a.length) {
                m[i] = a[ai++];
            } else if (bi < b.length) {
                m[i] = b[bi++];
            }
        }
        return m;
    }

    // Quick sort
    public static int[] quickSort(int[] array) {
        return quickSort(array, 0, array.length - 1);
    }

    private static int[] quickSort(int[] array, int start, int end) {
        if (end - start <= 1) {
            if ((end - start == 1) && array[start] > array[end]) {
                swap(array, start, end);
            }
            return array;
        }

        int pivot = pivot(array, start);
        int med = partition(array, start, end, pivot);
        quickSort(array, start, med - 1);
        quickSort(array, med + 1, end);
        return array;
    }

    private static int pivot(int[] array, int start) {
        return start;
    }

    private static int partition(int[] array, int start, int end, int pivot) {
        swap(array, end, pivot);
        int place = end;

        for (int i = start; i < end; i++) {
            if (array[i] > array[end]) {
                boolean ordered = true;
                for (int j = i + 1; j < end; j++) {
                    if (array[j] <= array[end]) {
                        ordered = false;
                        swap(array, j, i);
                        break;
                    }
                }
                if (ordered) {
                    place = i;
                    break;
                }
            }
        }
        swap(array, place, end);
        return place;
    }

    // Heap sort
    public static int[] heapSort(int[] array) {
        // make heap
        int hsize;
        for (hsize = 1; hsize < array.length; hsize++) {
            heapfyUp(array, hsize, hsize);
        }
        // remove heap
        while (--hsize != 0) {
            swap(array, 0, hsize);
            heapfyDown(array, hsize - 1, 0);
        }
        return array;
    }

    private static void heapfyDown(int[] array, int hsize, int index) {
        if (index * 2 + 1 <= hsize) {
            int child = index * 2 + 1;
            if (index * 2 + 2 <= hsize && array[child] < array[index * 2 + 2])
                child = index * 2 + 2;
            if (array[child] > array[index]) {
                swap(array, index, child);
                heapfyDown(array, hsize, child);
            }
        }
    }

    private static void heapfyUp(int[] array, int hsize, int index) {
        int parent = (index % 2 == 0) ? ((index - 2) / 2) : ((index - 1) / 2);
        if (parent >= 0) {
            if (array[index] > array[parent]) {
                swap(array, index, parent);
                heapfyUp(array, hsize, parent);
            }
        }
    }

    // Radix sort
    public static int[] radixSort(int[] array) {
        // positive and negative buckets
        var buckets = new ArrayList<List<Integer>>();
        for (int i = 0; i < 10 * 2; i++)
            buckets.add(new ArrayList<>());
        int index = 0;
        do {
            // clear
            for (List<Integer> bucket : buckets)
                bucket.clear();
            // bucket-in
            for (int value : array) {
                int bucketIndex = getDigit(value, index) + 10;
                buckets.get(bucketIndex).add(value);
            }
            // check
            if (buckets.get(10).size() == array.length)
                break;
            // bucket-out
            var bucket = buckets.get(0);
            int bucketIndex = 0;
            for (int i = 0; i < array.length; i++) {
                while (bucket.isEmpty())
                    bucket = buckets.get(++bucketIndex);
                array[i] = bucket.remove(0);
            }
            index++;
        } while (true);
        return array;
    }

    private static int getDigit(int value, int index) {
        long exp = 1;
        for (int i = 0; i < index; i++)
            exp *= 10;
        return ((int) ((double) value / exp)) % 10;
    }
}
