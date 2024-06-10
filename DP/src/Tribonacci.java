import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Tribonacci {
    public static void main(String[] args) {
//        int[] memo = new int[9];
//        System.out.println(trib(7, memo));

        int[] numbers = {4, 6, 10};
        boolean[] memo = new boolean[numbers.length];
        HashMap<Integer, Boolean> Memo = new HashMap<>();
        System.out.println(sumPossible(15, numbers, Memo));
    }


    public static int trib(int n, int[] memo) {

        if (memo[n] != 0) return memo[n];

        if (n == 0 || n == 1) return 0;
        if (n == 2) return 1;

        int t = trib(n - 1, memo) + trib(n - 2, memo) + trib(n - 3, memo);

        memo[n] = t;
        return t;
    }

    public static boolean sumPossible(int amount, int[] numbers, HashMap<Integer, Boolean> memo) {

        if (memo.containsKey(amount)) return memo.get(amount);

        if (amount == 0) return true;

        boolean possible = false;

        for (int i = 0; i < numbers.length; i++) {
            if (amount >= numbers[i]) {
                possible = sumPossible(amount - numbers[i], numbers, memo);
            }
        }
        memo.put(amount, possible);

        return possible;
    }

//    public static int minChange(int amount, int[] coins, HashMap<Integer, Integer> memo){
//        if(amount == 0) return 0;
//
//        int min = -1;
//        for(int i : coins){
//            // Trouver quel coin choisir
//            if(amount > i){
//                int total = Math.min(minChange(amount - i, coins, memo), min);
//                memo.put(amount, total);
//            }
//        }
//    }

    public static int countPaths(int[][] grid, int x, int y, HashMap<int[], Integer> memo) {
        if (x == grid.length - 1 && y == grid[0].length - 1) return 1;

        int cp = 0;

        if(x < grid.length - 1 || y < grid[0].length) {
            cp = countPaths(grid, x + 1, y, memo) + countPaths(grid, x, y + 1, memo);
        }
        
        int[] pos = {x, y};
        memo.put(pos, cp);
        return cp;
    }
}