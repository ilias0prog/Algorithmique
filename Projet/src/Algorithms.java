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
            String possibleDirections;
            return 'a';
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
