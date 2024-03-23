package DivideAndConquer.src;

public class BinarySearch {
    static int[] search(int[] array, int n){
        //Bon code :
        int arrayLength = array.length;
        int i = arrayLength/2;
        while(array[i] != n) {
            System.out.println(array[i]);
            if (array[i] < n) {
                i = i + arrayLength;
            }
            else {
                i = i - arrayLength;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        int[] array = {3,7,8,9,0,2,0,1,9,1,14,77,4,6,10};
        int n = 77;
        search(array, n);
    }
}