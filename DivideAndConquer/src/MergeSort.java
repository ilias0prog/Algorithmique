package DivideAndConquer.src;
import java.util.Arrays;

public class MergeSort {
    static int[] merge(int[] leftArray, int[] rightArray, int[] array) {
        // MEEEEEEEEEEEEERGE ^^
        int li = 0, ri = 0, si = 0;
        while(li < leftArray.length && ri < rightArray.length) {
            if(leftArray[li] < rightArray[ri]){
                array[si] = leftArray[li];
                si++;
                li++;
            }
            else {
                array[si] = rightArray[ri];
                si++;
                ri++;
            }

        }
        return array;
    }

    static void mergeSort(int[] array){
        int arrayLength = array.length;
        if (arrayLength < 2) {
            return;
        }
        int l = arrayLength / 2;
        int [] leftArray = Arrays.copyOfRange(array, 0, l);
        int [] rightArray = Arrays.copyOfRange(array, l, arrayLength);
        mergeSort(leftArray);
        mergeSort(rightArray);
        merge(leftArray, rightArray, array);
    }
    public static void main(String[] args) {
        int[] array = {3,7,8,9,0,2,0,1,9,1,14,77,4,6,10};
        mergeSort(array);
    }
}