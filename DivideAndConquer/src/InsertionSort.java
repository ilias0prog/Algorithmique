import java.util.Arrays;
import java.lang.Math;

public class InsertionSort{
    public static void main(String[] args) {
        int[] arr = {5, 2, 4, 6, 1, 3, 3, 10, 1, 23, 12, 1, 7, 0, 20, 28, 27, 4, 6, 7, 19, 24};
        recursiveInsertionSort(arr, arr.length);
//        int[] new_arr = mergeSort(arr);
        for(int a : arr) System.out.println(a);
    }

    //@ requires arr != null
    void insetionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++){
            int j = i;
            while(0 < j && arr[j] < arr[j-1]){
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j -= 1;
            }
        }
    }

    public static void recursiveInsertionSort(int[] arr, int n){
        if(n <= 1) return;
        recursiveInsertionSort(arr, n-1);
        insertProc(arr, n);
    }

    public static void insertProc(int[] arr, int n){
        int i = n - 2;
        int val = arr[n - 1];

        while(0 <= i && arr[i] > val){
            arr[i + 1] = arr[i];
            i--;
        }
        arr[i + 1] = val;
    }

    public static int[] mergeSort(int[] arr) {
        if(arr.length == 1) return arr;

        int mid = arr.length / 2;
        int[] leftArr = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int[] rightArr = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

        return merge(leftArr, rightArr);

    }

//    public static int[] merge(int[] a, int[] b){
//        int min = Math.min(a[0], b[0]);
//        int max = Math.max(a[0], b[0]);
//        return new int[] {min, max};
//    }

    public static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length) {
            result[k++] = a[i++];
        }

        while (j < b.length) {
            result[k++] = b[j++];
        }

        return result;
    }
}