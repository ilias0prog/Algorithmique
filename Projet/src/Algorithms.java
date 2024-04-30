import java.util.*;


public interface Algorithms {
    Random rng = new Random();
    int height = 11;
    int width = 7;

    static void main(String[] args) {
        // Your code here
        System.out.println("Hello world!");
    }


    /*** --- Generate & Test --- ***/
    interface GT {
        static void generateMonstersAndTreasures(int[][] monstersToFill, int[][] treasuresToFill) {
            //TODO
        }

        /*** --- Utility functions for GT --- ***/
        //TODO (if you have any)
    }

    /*** --- Divide & Conquer --- ***/
    interface DC {
        static void sortLevel(int[][] monstersToSort, int[][] treasuresToSort) {
            //TODO
        }

        /*** --- Utility functions for DC --- ***/
        //TODO (if you have any)
    }

    /*** --- Greedy Search --- ***/

    private int getValueAtCoords(int[][] board, int x, int y){
        for (int[] square : board) {
            if (square[0] == x && square[1] == y) {
                return square[2];
            }
        }
        return 0;
    }
    private void moveHero(State state, String path){
        int pathLength = path.length();
        for(int i = 0; i < pathLength; i++){
            char direction = path.charAt(i);
            int heroX = state.heroPos[0];
            int heroY = state.heroPos[1];
            switch (direction) {
                case 'd':
                    if (heroY < height) {
                        // Faire descendre le héros
                        state.heroPos[1] += 1;
                    }
                case 'r':
                    if (heroX < width) {
                        // Faire avancer le héros
                        state.heroPos[0] += 1;
                        //
                    }
                case 'l':
                    if (width < heroX) {
                        state.heroPos[0] -= 1;
                    }
            }
            // Ramasser le trésor s'il y'en a un
            state.heroScore += getValueAtCoords(state.treasures, heroX, state.heroPos[1]);
            // Combattre le monstre s'il y'en a un
            state.heroHealth -= getValueAtCoords(state.monsters, heroX, state.heroPos[1]);
//                if(state.heroHealth < 1){
//                    // Dead
//                }
        }
    }

    private int getPathScore(State state, String path){
        int pathLength = path.length();
        int score = state.heroScore;
        int heroX = state.heroPos[0];
        int heroY = state.heroPos[1];
        for(int i = 0; i < pathLength; i++){
            char direction = path.charAt(i);
            switch (direction) {
                case 'd':
                    if (heroY < height) {
                        // Faire descendre le héros
                        heroY += 1;
                    }
                case 'r':
                    if (heroX < width) {
                        // Faire avancer le héros
                        heroX += 1;
                        //
                    }
                case 'l':
                    if (width < heroX) {
                        heroX -= 1;
                    }
            }
            // Ramasser le trésor s'il y'en a un
            score += getValueAtCoords(state.treasures, heroX, heroY);
            // Combattre le monstre s'il y'en a un
            score -= getValueAtCoords(state.monsters, heroX, heroY);
        }
        return score;
    }

    private char bestDirection(State state){
        char bestDir;
        int  bestScore;
        int heroX = state.heroPos[0];
        int heroY = state.heroPos[1];
        // try left :
        if(0 < state.heroPos[0]){
            int[] coords = {heroX-1, heroY};
            ArrayList<int[]> leftOptions = new ArrayList<>();
            leftOptions = getAllSquaresReachableWithNMoves(coords, 4);
            for(int[] square : leftOptions){

            }
        }
        return bestDir;
    }

    /**
     * Spécification : Ilias Essarhiri
     * Implémentation: Ilias Essarhiri + ajustement de la dernière ligne par ChatGPT pour s'assurer que la méthode est fiable dans tous les cas de figure
     *
     * Representation : startsquare and targetSquare represent coordinates of this form : {1,2} if x coordinate is 1 and y coordinate is 2;
     * @requires : startSquare and targetSquare contain 2 positive integers;
     * @return : true if targetSquare can be reached in 5 moves from startSquare, false otherwise;
     * **/

    //@requires startSquare != null && targetSquare != null;
    //@ensures /return = ...
    private boolean isReachableInFiveMoves(int[] startSquare, int[] targetSquare) {
        int startX = startSquare[0];
        int startY = startSquare[1];
        int targetX = targetSquare[0];
        int targetY = targetSquare[1];

        if (targetY < startY) return false;

        int deltaX = Math.abs(targetX - startX);
        int deltaY = Math.abs(targetY - startY);

        if (startY == targetY && deltaX == 5) return true;

        return (deltaX + deltaY) <= 5 && (deltaX + deltaY) % 2 == 1;
    }

    private boolean isReachableInNMoves(int[] startSquare, int[] targetSquare, int n) {
        int startX = startSquare[0];
        int startY = startSquare[1];
        int targetX = targetSquare[0];
        int targetY = targetSquare[1];

        // Vérifier si la case cible est au-dessus de la case de départ
        if (targetY < startY) return false;

        // Calculer la distance horizontale et verticale entre les deux cases
        int deltaX = Math.abs(targetX - startX);
        int deltaY = Math.abs(targetY - startY);

        // Vérifier si la case cible est sur le même étage que la case de départ et à une distance de dist déplacements horizontaux
        if (startY == targetY && deltaX == n) return true;

        // Vérifier si la case cible est accessible en utilisant au plus dist déplacements alternés entre horizontal et vertical
        return (deltaX + deltaY) <= n && (deltaX + deltaY) % 2 == 1;
    }


    private ArrayList<int[]> getAllSquaresReachableWithNMoves(int[] startSquare, int n){
        ArrayList<int[]> allSquares = new ArrayList<>();
        for(int i = 0; i<n ; i++){
            for(int j = 0; j<n; j++){
                int squareX = i + startSquare[0];
                int squareY = j + startSquare[1];
                int[] newSquare = {squareX,squareY};
                if(isReachableInNMoves(startSquare, newSquare, 5)) allSquares.add(newSquare);
            }
        }
        return allSquares;
    }



    private String getShortestPath(int[] startSquare, int[] targetSquare){
        String s = "aaa";
        return s;
    }

    private String bestPathToThisSquare(State state, int[] startSquare, int[] targetSquare){
        String s = "aaa";
        return s;
    }
    interface GS {
        static int greedySolution(State state) {
            //TODO
            return 0;
        }

        /*** --- Utility functions for GS --- ***/
        //TODO (if you have any)
    }

    /*** --- Dynamic Programming --- ***/
    interface DP {
        static String perfectSolution(State state) {
            //TODO
            return "";
        }

        /*** --- Utility functions for DP --- ***/
        //TODO (if you have any)
    }

    /* --- Common utility functions --- */
    //TODO (if you have any)

}
