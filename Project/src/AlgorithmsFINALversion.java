import java.util.*;
import java.lang.*;

public interface AlgorithmsFINALversion {
    Random rng = new Random();

    static void main(String[] args) {
        // Launch timer
        long startMs = System.currentTimeMillis();
        long startNs = System.nanoTime();

        // create a game state//
        int[] heroPos = {0, 1};
        int heroHealth = 100;
        int heroScore = 0;
        int[][] monsters={{0,0,0,0},
                {10,40,0,0},
                {0,0,20,0}};
        int[][] treasures={{0,0,0,0},
                {0,0,0,0},
                {70,0,0,50}};;
        int nbHint = 0;
        int nbLevel = 1;
        int[][] monsters_array = new int[7][5];
        int[][] treasures_array = new int[7][5];

        GT.generateMonstersAndTreasures(monsters_array, treasures_array);

        State game = new State(heroPos, heroHealth, heroScore, monsters_array, treasures_array, nbHint, nbLevel);
        String way=DP.perfectSolution(game);
        System.out.println("Monsters:");
        printMatrix(monsters_array);
        System.out.println("Treasures:");
        printMatrix(treasures_array);
        int greedy_score = GS.greedySolution(game);
        System.out.println("Greedy score : " + greedy_score);
        System.out.println("Monsters:");
        printMatrix(monsters_array);
        System.out.println("Treasures:");
        printMatrix(treasures_array);
        System.out.println("way:");
        System.out.println(way);
        System.out.println(DP.computescore(game,way));

        // End of timer
        long endMs = System.currentTimeMillis();
        long endNs = System.nanoTime();

        // Display the timer
        System.out.println("---");
        System.out.println("Temps de calcul (ms): " + (endMs - startMs));
        System.out.println("Temps de calcul (ns): " + (endNs - startNs));
        System.out.println("---");
    }


    /* --- Generate & Test --- */

    interface GT {
        /*
         * @ [First, OpenJML doc]
         * @ requires monstersToFill != null && treasuresToFill != null;
         * @ requires monstersToFill.length == treasuresToFill.length;
         * @ requires monstersToFill.length != 0 && treasuresToFill.length != 0;
         * @ requires \forall(int i; 0 <= i && i < monsterToFill.lenght;(monsterToFill[i].lenght == treasuresToFill[i].length) && (monsterToFill[i].lenght != 0) && (treasuresToFill[i].length != 0));
         * @ ensures (\return == true)==>/forall(int i;0<=i &&  i<monsterToTest.lenght; /sum(int j;0<=j &&  j<monsterToTest[i].lenght;monsterToTest[i][j])>=/sum(int j;0<=j &&  j<treasuresToTest[i].lenght;<treasuresToTest[i][j])
         * && \forall (int i; 0<=i && i < treasuresToTest.length;\num_of(int j; 0<=j && j < treasuresToTest.length; TreasuresTotest[i][j]!=0)<=2)
         * &&  \forall (int i; 0<=i  && i < monsterToTest;\num_of(int j; 0<=j && j < monsterToTest.length; monsterToTest[i][j]!=0)>=2)
         * --- [Second, authors info]
         * Implementation: Julien Jamoulle | Last update : 09/05/24
         * Specification: Houria Ariouat, Julien Jamoulle | Last update : 07/05/24
         * Test/Debug:  Houria Ariouat, Julien Jamoulle| Last update :09/05/24
         */
        static void generateMonstersAndTreasures(int[][] monstersToFill, int[][] treasuresToFill) {
            boolean result = false;
            // Testing test function of GT
            while (!result) {
                generate(monstersToFill, treasuresToFill);
                result = test(monstersToFill, treasuresToFill);
            }
        }

        /* --- Utility functions for GT --- */

        /*
         * @ [First, OpenJML doc]
         * @ requires monstersToFill != null && treasuresToFill != null;
         * @ requires monstersToFill.length == treasuresToFill.length;
         * @ requires monstersToFill.length != 0 && treasuresToFill.length != 0;
         * @ requires \forall(int i; 0 <= i && i < monsterToFill.lenght;(monsterToFill[i].lenght == treasuresToFill[i].length) && (monsterToFill[i].lenght != 0) && (treasuresToFill[i].length != 0));
         * @ ensures \forall (int i; 0 <= i && i < monstersToFill.length;(\forall int j; 0 <= j && j < monstersToFill[i].length;(monsterToFill[i][j] != 0 && treasuresToFill[i][j] == 0  || monsterToFill[i][j] == 0  && treasuresToFill[i][j] != 0))
         * @ ensures \forall (int i; 0<=i && i < treasuresToFill.length;\num_of(int i; 0<=i && i < treasuresToFill.length;treasureCount[i] <= 2))
         * --- [Second, authors info]
         * Specification: Houria Ariouat | Last update : 09/05/24
         * Implementation: Houria Ariouat | Last update : 01/05/24
         * Test/Debug:  Houria Ariouat, Julien Jamoulle| Last update :01/05/24
         ***/

        static void generate(int[][] monstersToFill, int[][] treasuresToFill) {
              /*@
              @ loop_invariant 0 <= i <= treasureToFill.length -1
              @ loop_invariant 0 <= treasurecount.length < treasuresToFill.length
              @ decrease treasureToFill.length-1
              @*/

            //for each row
            for (int i = 0; i <= treasuresToFill.length - 1; i++) {

                int[] treasureCount = new int[treasuresToFill.length];
                Random randomobject = new Random();
                int valuetreasure;
                int valuemonster;


                /*
                 * @ loop_invariant 0 <= j <= treasureToFill.length -1
                 * @ loop_invariant 0 <= randomnumber < 6
                 * @ loop_invariant 1 <= valuetreasure <= 99
                 * @ loop_invariant 1 <= valuemonster <= 50
                 * @ decrease treasureToFill.length-1
                 ***/
                //for each column at a specific row
                for (int j = 0; j <= treasuresToFill[i].length - 1; j++) {

                    int randomnumber = randomobject.nextInt(6);

                    //test the probabilities
                    //if the randomnumber is equal to 5, we have a 1/6 probability of having a treasure
                    if (randomnumber == 5 && treasureCount[i] < 2 && monstersToFill[i][j] == 0) {//condition to have a valid board
                        valuetreasure = randomobject.nextInt(1, 99);
                        treasuresToFill[i][j] = valuetreasure;
                        treasureCount[i] += 1;
                    }

                    //if the randomnumber is equal to 0 or 1, we have a 2/6 probability of having a monster
                    if (randomnumber <= 1 && treasuresToFill[i][j] == 0) {
                        valuemonster = randomobject.nextInt(1, 50);
                        monstersToFill[i][j] = valuemonster;
                    }
                    //if the randomnumber is equal to 2 or 3 or 4, we have a 3/6 probability of having a nothing
                    if (randomnumber > 1 && randomnumber <= 4) {
                        treasuresToFill[i][j] = 0;
                        monstersToFill[i][j] = 0;
                    }


                }

            }

        }

        /*
         * @ [First, OpenJML doc]
         *
         * @requires monsterToTest!=null
         * @requires treasuresToTest!=null
         * @requires monsterToTest.lenght!=0 && treasuresToTest.lenght!=0
         * @requires  monsterToTest.lenght==treasuresToTest.length
         * @requires  \forall(int i;0<=i &&  i<monsterToTest.lenght;(monsterToTest[i].lenght==treasuresToTest[i].length) && (monsterToTest[i].lenght!=0) && (treasuresToTest[i].length!=0));
         * @ ensures (\return == true)==>/forall(int i;0<=i &&  i<monsterToTest.lenght; /sum(int j;0<=j &&  j<monsterToTest[i].lenght;monsterToTest[i][j])>=/sum(int j;0<=j &&  j<treasuresToTest[i].lenght;<treasuresToTest[i][j])
         * && \forall(int i; 0<=i && i < treasuresToTest.length;\num_of(int j; 0<=j && j < treasuresToTest.length; TreasuresTotest[i][j]!=0)<=2)
         * && \forall (int i; 0<=i && i < monsterToTest; \num_of(int j; 0<=j && j < monsterToTest.length; monsterToTest[i][j]!=0)>=2)
         * --- [Second, authors info]
         * Specification: Julien Jamoulle | Last update : 08/05/24
         * Implementation: Julien Jamoulle | Last update : 03/05/24
         * Test/Debug: Julien Jamoulle| Last update :27/04/24
         ***/
        static boolean test(int[][] monsterToTest, int[][] treasuresToTest) {
            // For each row calculate the treasures and monster

            /*
             * @ loop_invariant 0 <= i <= monsterToTest.length-1
             * @ decrease  monsterToTest.length-i
             ***/
            for (int i = 0; i < monsterToTest.length; i++) {
                int monsterCount = 0;
                int monsterValue = 0;
                int treasureCount = 0;
                int treasureValue = 0;
                /*
                 * @ loop_invariant 0 <= j <= monsterToTest[i].length-1
                 * @ decrease  monsterToTest[i].length-j
                 ***/
                for (int j = 0; j < monsterToTest[i].length; j++) {
                    // **Variant= monsterToTest[i].length-j, Inv: 0<=j<monsterToTest[i].length,...
                    // **//
                    // If there is a monster on the case update the value link to it
                    if (monsterToTest[i][j] != 0) {
                        monsterCount++;
                        monsterValue += monsterToTest[i][j];
                    }
                    if (treasuresToTest[i][j] != 0) {
                        treasureCount++;
                        treasureValue += treasuresToTest[i][j];
                    }

                    // Test if there is a monster and a treasure on the same case
                    if(treasuresToTest[i][j]!=0 && monsterToTest[i][j]!=0){
                        return false;
                    }
                }

                // Checking the conditions requiered by the game
                if ((monsterCount < 2) || (treasureCount > 2)) {
                    return false;
                }

                if (treasureValue > monsterValue) {
                    return false;
                }
            }
            return true;
        }
    }

    /* --- Divide & Conquer --- */
    interface DC {
        /*
         * @[First,OpenJML doc]
         * @requires monstersToSort != null
         * @requires treasuresToSort != null
         * @requires monstersToSort.length == treasuresToSort.length
         * @requires (\forall int i; 0 <= i < monstersToSort.length; monstersToSort[i] != null && treasuresToSort[i] != null && monstersToSort[i].length == treasuresToSort[i].length)
         * @ensure (\forall int i; 0 <= i < monstersToSort.length;
         *              (\forall int j; 0 <= j < monstersToSort[i].length; monstersToSort[i][j] == monsterSorted[i][j]))
         * @ensure (\forall int i; 0 <= i < treasuresToSort.length;
         *              (\forall int j; 0 <= j < treasuresToSort[i].length; treasuresToSort[i][j] == treasureSorted[i][j]))
         * --- [Second, authors info]
         * Specification: Brian Lacroix  Last update : 05/05/24
         * Implementation: Brian Lacroix | Last update : 01/05/24
         * Test/Debug: Brian Lacroix| Last update :02/05/24
         ***/
        static void sortLevel(int[][] monstersToSort, int[][] treasuresToSort) {
            int nbrRow = monstersToSort.length;
            int nbrCaseByRow = monstersToSort[0].length;
            int[][] monsterSorted = new int[nbrRow][nbrCaseByRow];
            int[][] treasureSorted = new int[nbrRow][nbrCaseByRow];
            int[][] tabValueByRow = tabToSortByRow(nbrRow, nbrCaseByRow, monstersToSort,treasuresToSort);
            mergeSort(tabValueByRow);
            //for each column at a specific row
            /*
             * @ loop_invariant 0 <= row <= nbrRow-1
             * @ decrease  row= nbrRow-1
             ***/
            for (int row = 0; row <= nbrRow - 1; row++) {
                monsterSorted[row] = monstersToSort[tabValueByRow[row][0]];
                treasureSorted[row] = treasuresToSort[tabValueByRow[row][0]];
            }
            for (int row = 0; row <= nbrRow - 1; row++) {
                monstersToSort[row]=monsterSorted[row];
                treasuresToSort[row]=treasureSorted[row];
            }
        }

        /* --- Utility functions for DC --- */

        /*
         * @[First,OpenJML doc]
         *
         * @requires nbrRow > 0
         * @requires nbrCaseByRow > 0
         * @requires monstersToSort != null
         * @requires monstersToSort.length == nbrRow
         * @requires  (\forall int i; 0 <= i < nbrRow; monstersToSort[i].length == nbrCaseByRow)
         * @requires treasuresToSort != null
         * @requires treasuresToSort.length == nbrRow
         * @requires (\forall int i; 0 <= i < nbrRow; treasuresToSort[i].length == nbrCaseByRow)
         *
         * @ensure \result != null && \result.length == nbrRow &&
         * @ensure   (\forall int row; 0 <= row < nbrRow;  \result[row][0] == row && \result[row][1] == (\sum int position; 0 <= position < nbrCaseByRow; treasureToSort[row][position] - monstersToSort[row][position]));
         *
         * --- [Second, authors info]
         * Specification: Brian Lacroix  Last update : 05/05/24
         * Implementation: Brian Lacroix | Last update : 01/05/24
         * Test/Debug: Brian Lacroix| Last update :02/05/24
         ***/
        static int[][] tabToSortByRow(int nbrRow, int nbrCaseByRow, int[][] monstersToSort,int[][] treasureToSort) {
            //tableau de tableau avec en 0 l'index et en 1 la valeur
            int[][] ValueByRow = new int[nbrRow][2];
            //pour chaque rangée
            /*@
              @ loop_invariant 0 <= row <= nbrRow-1
              @ decrease  nbrRow-1 - row
              @*/
            for (int row = 0; row <= nbrRow-1; row++) {
                ValueByRow[row][0] = row;
                ValueByRow[row][1] = 0;
                //pour chaque case dans une rangée
                /*@
                  @ loop_invariant 0 <= position <= nbrCaseByRow-1
                  @ decrease  nbrCaseByRow-1 - position
                  @*/
                for (int position = 0; position <= nbrCaseByRow-1; position++) {
                    ValueByRow[row][1] += treasureToSort[row][position];
                    ValueByRow[row][1] -= monstersToSort[row][position];
                }
            }
            return ValueByRow;
        }



        /*
         * @[First,OpenJML doc]
         * @requires tableau != null && tableau.length > 1
         * @ensure (\forall int i; 0 <= i < tableau.length - 1; tableau[i][1] <= tableau[i + 1][1])
         *
         * --- [Second, authors info]
         * Specification: Brian Lacroix  Last update : 05/05/24
         * Implementation: Brian Lacroix | Last update : 01/05/24
         * Test/Debug: Brian Lacroix| Last update :02/05/24
         ***/
        static void mergeSort(int[][] tableau) {
            if (tableau == null || tableau.length <= 1) {
                return;
            }

            int milieu = tableau.length / 2;
            int[][] tableauGauche = Arrays.copyOfRange(tableau, 0, milieu);
            int[][] tableauDroit = Arrays.copyOfRange(tableau, milieu, tableau.length);

            mergeSort(tableauGauche);
            mergeSort(tableauDroit);

            merge(tableauGauche, tableauDroit, tableau);
        }


        /*
         * @[First,OpenJML doc]
         *
         * @requires tableauGauche != null
         * @requires tableauDroit != null
         * @requires tableau != null
         * @requires tableau.length == tableauGauche.length + tableauDroit.length
         * @requires (\forall int i; 0 <= i < tableauGauche.length - 1; tableauGauche[i][1] <= tableauGauche[i + 1][1])
         * @requires (\forall int j; 0 <= j < tableauDroit.length - 1; tableauDroit[j][1] <= tableauDroit[j + 1][1])
         *
         * @ensure (\forall int i; 0 <= i < tableau.length - 1; tableau[i][1] <= tableau[i + 1][1])
         * --- [Second, authors info]
         * Specification: Brian Lacroix  Last update : 05/05/24
         * Implementation: Brian Lacroix | Last update : 01/05/24
         * Test/Debug: Brian Lacroix| Last update :02/05/24
         */
        static void merge(int[][] tableauGauche, int[][] tableauDroit, int[][] tableau) {
            int i = 0, j = 0, k = 0;
            /*@
              @loop invariant 0 <= i <= tableauGauche.length;
              @loop invariant 0 <= j <= tableauDroit.length;
              @loop invariant k == i + j;
              @loop invariant (\forall int m; 0 <= m < k - 1; tableau[m][1] <= tableau[m + 1][1]);
              @decreases tableauGauche.length + tableauDroit.length - k ;
              @*/
            while (i < tableauGauche.length && j < tableauDroit.length) {
                // Comparer le deuxième élément des sous-tableaux
                if (tableauGauche[i][1] <= tableauDroit[j][1]) {
                    tableau[k] = tableauGauche[i];
                    i++;
                } else {
                    tableau[k] = tableauDroit[j];
                    j++;
                }
                k++;
            }
            /*@
              @ loop invariant i <= tableauGauche.length;
              @ loop invariant k >= i + j;
              @ loop invariant (\forall int m; 0 <= m < k - 1; tableau[m][1] <= tableau[m + 1][1]);
              @ decreases tableauGauche.length - i;
              @*/
            while (i < tableauGauche.length) {
                tableau[k] = tableauGauche[i];
                i++;
                k++;
            }
            /*@
              @ loop invariant j <= tableauDroit.length;
              @ loop invariant k >= i + j;
              @ loop invariant (\forall int m; 0 <= m < k - 1; tableau[m][1] <= tableau[m + 1][1]);
              @ decreases tableauDroit.length - j;
              @*/
            while (j < tableauDroit.length) {
                tableau[k] = tableauDroit[j];
                j++;
                k++;
            }
        }
    }

    /* --- Greedy Search --- */
    interface GS {

        // The LastMove object allows to track the last move made by the algorithm more easily
        class LastMove {
            String last_move;
            private LastMove(){
                last_move = "";
            }
        }

        /*
         * [First, OpenJML doc]
         * Returns the score that the player has to beat in order to get a hint.
         *
         * @requires state != null
         * @ensures (\return == state.heroScore);
         * // ensures (\return == moveHero(monsters, treasures, path, startSquare, heroHealth, heroScore)[1] && (\forall int i; 0 < i && i < path.length(); path == getBestpathgetBestPath(state,startSquare,5, width, height, lm))
         * // ensures \return == heroScore where the hero followed the best greedy path (according to the instructions)
         * @pure
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 10/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 10/05/24
         * */
        static int greedySolution(State state) {
            LastMove lm = new LastMove();
            int height = state.monsters.length;
            int width = state.monsters[0].length;
            int[] startSquare = {state.heroPos[0], state.heroPos[1]};
            int heroHealth = state.heroHealth;
            int heroScore = state.heroScore;
            int[][] monsters = getArrayCopy(state.monsters);
            int[][] treasures = getArrayCopy(state.treasures);

            while (startSquare[0] < height - 1 && heroHealth > 0) {
                String best_direction = "";
                String best_path = getBestPath(monsters, treasures, startSquare, heroHealth, heroScore, 5, width, height, lm);
                if (best_path.isEmpty()) return heroScore;
                best_direction += best_path.charAt(0);
                lm.last_move = best_direction;
                System.out.println(best_path);
                int[] health_score = moveHero(monsters, treasures, best_direction, startSquare, heroHealth, heroScore);
                heroHealth = health_score[0];
                heroScore = health_score[1];
            }
            return heroScore;
        }



        /*** --- Utility functions for GS --- ***/
        // TODO (if you have any)

        /*
         * [First, OpenJML doc]
         * Creates a copy of the given array.
         *
         * @requires array != null;
         * @ensures \return != null;
         * @ensures (\forall int i; 0 <= i && i < \result.length();
         *          (\forall int j; 0 <= j && j < \result[i].length(); \result[i][j] == array[i][j]));
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 10/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 10/05/24
         */
        private static int[][] getArrayCopy(int[][] array) {
            int[][] newArray = new int[array.length][];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = Arrays.copyOf(array[i], array[i].length);
            }
            return newArray;
        }

        /*
         * [First, OpenJML doc]
         * Returns the n moves long path that gives the greatest value (health + score).
         *
         * @requires monsters != null && treasures != null && startSquare != null && last_move != null;
         * @requires startSquare.length() == 2;
         * @requires 0 < width;
         * @requires 0 < height;
         * @requires 0 < n <= Math.min(width, height);
         * @ensures \forall (char c : \result.toCharArray().contains(c); c == 'd' || c == 'r' || c == 'l');
         * @ensures \forall (int i; 0 < i && i < \result.length() - 1;
         *       !(\result.charAt(i) == 'r' and \result.charAt(i+1) == 'l')
         *    && !(\result.charAt(i) == 'l' and \result.charAt(i+1) == 'r'))
         * @ensures moveHero(monsters, treasures, \result, startSquare, heroHealth, heroScore)[1] == solution of the problem; //(moveHero[0] = health and moveHero[1] = score)
         *
         * @pure
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 09/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 10/05/24
         * */
        private static String getBestPath(int[][] monsters, int[][] treasures, int[] startSquare, int heroHealth, int heroScore, int n, int width, int height, GS.LastMove last_move) {
            String bestPath = "";
            int bestScore = Integer.MIN_VALUE;

            ArrayList<String> allPaths = new ArrayList<>();
            generatePaths(startSquare[1], startSquare[0], n, width, height, "", allPaths, last_move); // Generate all 5 moves long possible paths
            int pathScore;
            // Loop on all generated paths
            for (String path : allPaths) {
                pathScore = getPathScore(monsters, treasures, path, startSquare,heroHealth, heroScore); // Compute path score

                // If a new best score is found, save it into bestScore
                if (pathScore > bestScore && !path.isEmpty()) {
                    bestScore = pathScore; // Update best score
                    bestPath = path; // Update best path
                }
            }
            System.out.println("Best path : " + bestPath);
            System.out.println("Path score : " + bestScore);
            return bestPath; // Return the best path found
        }

        /*
         * [First, OpenJML doc]
         * Fills allPaths with all the remainingMoves moves long paths that are valid, starting from y,x.
         *
         * @requires 0 <= x && x < width;
         * @requires 0 <= y && y < height;
         * @requires remainingMoves >= 0;
         * @requires path != null;
         * @requires allPaths != null;
         * @requires lm != null;
         *
         * @ensures (\forall String s : allPaths;
         *          (\forall char c : s.toCharArray(); c == 'd' || c == 'r' || c == 'l')
         *
         *    &&    (\forall (int i; 0 < i && i < \result.length() - 1;
         *               !(\result.charAt(i) == 'r' and \result.charAt(i+1) == 'l')
         *           &&  !(\result.charAt(i) == 'l' and \result.charAt(i+1) == 'r'))
         *
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 07/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 09/05/24
         *
         * */
        private static void generatePaths(int x, int y, int remainingMoves, int width, int height, String path, ArrayList<String> allPaths, GS.LastMove lm) {
            // After n moves, add path to allPaths and return
            if (remainingMoves == 0 || (y+1 == height && x+1 == width && path.charAt(path.length() - 1) == 'r') || (y+1 == height && x == 0 && path.charAt(path.length() - 1) == 'l')) {
                allPaths.add(path);
                return;
            }

            // Going down if possible
            if (y + 1 < height) {
                generatePaths(x, y + 1, remainingMoves - 1, width, height, path + 'd', allPaths, lm);
            }

            // Going right if possible
            if (x + 1 < width && !lm.last_move.equals("l")) {
                generatePaths(x + 1, y, remainingMoves - 1, width, height, path + 'r', allPaths, lm);
            }

            // Going left if possible
            if (x > 0 && !lm.last_move.equals("r")) {
                generatePaths(x - 1, y, remainingMoves - 1, width, height, path + 'l', allPaths, lm);
            }
        }


        /*
         * [First, OpenJML doc]
         * Returns the value at board[y][x] (used for treasures and monsters).
         *
         * @requires board != null && 0 <= y && y < board.length() && 0 <= x && x < board[y].length()
         * @ensures \result == board[y][x]
         *
         * @pure
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 01/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 01/05/24
         * */
        private static int getValueAtCoords(int[][] board, int y, int x) {
            return board[y][x];
        }

        /*
         * [First, OpenJML doc]
         * Simulates a path and returns the value of the metric (health + score) after the last move. Used for comparing the different paths.
         *
         * @requires monsters != null && treasures != null && path != null && start_square != null;
         * @requires start_square.length() == 2;
         * @requires 0 <= start_square[0] && start_square[0] < monsters.length && 0 <= start_square[1] && start_square[1] < monsters[0].length;
         * @ensures \result == moveHero(monsters, treasures, path, startSquare, heroHealth, heroScore)[0] + moveHero(monsters, treasures, path, startSquare, heroHealth, heroScore)[1];
         * // @ensures \result == heroHealth + heroScore after following path;
         * @ensures monsters == \old(monsters);
         * @ensures treasures == \old(treasures);
         *
         * @pure
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 07/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 09/05/24
         * */
        private static int getPathScore(int[][] monsters, int[][] treasures, String path, int[] start_square, int hero_health, int hero_score) {
            int pathLength = path.length();
            int heroX = start_square[1];
            int heroY = start_square[0];
            int height = monsters.length;
            int width = monsters[0].length;

            for (int i = 0; i < pathLength; i++) {
                char direction = path.charAt(i);
                switch (direction) {
                    case 'd':
                        if (heroY + 1 < height) {
                            // Faire descendre le héros
                            heroY += 1;
                        }
                        break;
                    case 'r':
                        if (heroX + 1 < width) {
                            // Faire avancer le héros
                            heroX += 1;
                        }
                        break;
                    case 'l':
                        if (heroX - 1 >= 0) {
                            // Faire reculer le héros
                            heroX -= 1;
                        }
                        break;
                }
                if(heroX < 0 || width < heroX+1 || height < heroY+1) return hero_health + hero_score;
                // If treasure on this square, collect it
                hero_score += getValueAtCoords(treasures, heroY, heroX);
                // If monster on this square, fight it
                hero_health -= getValueAtCoords(monsters, heroY, heroX);
            }
            if(hero_health < 0) hero_health = Integer.MIN_VALUE;
            return hero_health + hero_score;
        }


        /*
         * [First, OpenJML doc]
         * @requires monsters != null && treasures != null && path != null && startSquare != null;
         * @requires startSquare.length == 2;
         * @ensures \result != null && \result.length() == 2;
         * @ensures path == \old(path);
         *
         * --- [Second, authors info]
         * Specification  : Ilias Essarhiri          / Last Update : 10/05/24
         * Implementation : Ilias Essarhiri          / Last Update : 07/05/24
         * Test/ Debug    : Ilias Essarhiri          / Last Update : 09/05/24
         * */

        // startSquare[0] = y and startSquare[1] = x
        private static int[] moveHero(int[][] monsters, int[][] treasures, String path, int[] startSquare, int hero_health, int hero_score) {
            int height = monsters.length;
            int width = monsters[0].length;

            for (char direction : path.toCharArray()) {
                switch (direction) {
                    case 'd':
                        if (startSquare[0] + 1 < height) {
                            // Hero groes down
                            startSquare[0] += 1;
                        }
                        break;
                    case 'r':
                        if (startSquare[1] + 1 < width) {
                            // Hero goes right
                            startSquare[1] += 1;
                        }
                        break;
                    case 'l':
                        if (startSquare[1] - 1 >= 0) {
                            // Hero goes left
                            startSquare[1] -= 1;
                        }
                        break;
                }
                // Check if hero is still within the board
                if (startSquare[0] >= 0 && startSquare[0] < height &&
                        startSquare[1] >= 0 && startSquare[1] < width) {
                    // If treasure on this square, collect it
                    hero_score += getValueAtCoords(treasures, startSquare[0], startSquare[1]);
                    treasures[startSquare[0]][startSquare[1]] = 0;

                    // If monster on this square, fight it
                    hero_health -= getValueAtCoords(monsters, startSquare[0], startSquare[1]);
                    monsters[startSquare[0]][startSquare[1]] = 0;

                    if (hero_health < 1) {
                        System.out.println("*Dead*");
                        break;
                    }
                    System.out.println("Hero position : " + Arrays.toString(startSquare));
                    System.out.println("Hero score : " + hero_score);
                    System.out.println("Hero health : " + hero_health);
                } else {
                    System.out.println("Hero out of board");
                }
            }
            ;
            return new int[] {hero_health, hero_score};
        }
    }

    /* --- Dynamic Programming --- */
    interface DP {

        /*
         * @ [First, OpenJML doc]
         * @requires GT.test(State.monsters,State.treasures)==true
         * @requires state!=null
         * @ensure \return!=null
         * @ensure \return.length>=state.monsters.Length
         * @ensure \forall(String s; s.lenght>=state.monsters.length;computescore(state,\return)>=computescore(state,s))
         * @ensures \return instanceof String
         * --- [Second, authors info]
         * Specification: julien jamoulle  Last update : 09/05/24
         * Implementation: Julien Jamoulle | Last update : 03/05/24
         * Test/Debug: Julien Jamoulle| Last update :03/05/24
         ***/
        static String /*@pure*/ perfectSolution(State state) {
            HashMap<String,String>memo=new HashMap<String,String>();
            return findthebestway(state,"",memo);
        }

        /* --- Utility functions for DP --- */

        /*
         * @ [First, OpenJML doc]
         * @requires GT.test(State.monsters,State.treasures)==true
         * @requires solution.lenght==O;
         * @requires state!=null
         * @requires solution!=null
         * @requires memo!=null
         * @requires memo.isEmpty()
         * @ensure \return!=null
         * @ensure \return.length>=state.monsters.Length
         * @ensure \forall(String s; s.lenght>=state.monsters.length;computescore(state,/return)>=computescore(state,s))
         * @ensures \return instanceof String
         * --- [Second, authors info]
         * Specification: julien jamoulle  Last update : 08/05/24
         * Implementation: Julien Jamoulle | Last update : 03/05/24
         * Test/Debug: Julien Jamoulle| Last update :03/05/24
         ***/
        static String /*@pure*/ findthebestway(State state, String solution, HashMap<String,String> memo) {
            //Counting the movements made in the solution
            int dcount = 0;
            int rcount = 0;
            int lcount = 0;

            /*
             * @ loop_invariant 0 <= i < solution.length()
             * @ loop_invariant dcount<=state.monster.lenght
             * @loop_invariant lcount<solution.length()
             * @loop_invariant rcount<solution.length()
             * @ variant solution.length()-i
             **/
            for (int i = 0; i < solution.length(); i++) {
                if (solution.charAt(i) == 'd') {
                    dcount++;
                }
                if (solution.charAt(i) == 'r') {
                    rcount++;
                }
                if (solution.charAt(i) == 'l') {
                    lcount++;
                }
            }

            //Creating the key
            String key = generatekey(state, solution, computescore(state, solution));
            //Checking if the key is in the memo hashmap
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            //Base case: If I am deeper enough
            if (dcount >= state.monsters.length) {
                return solution;
            }
            else {
                //Recursive step

                // Defining the optimal way and its score
                String optimalsolution = solution; // Empty string in a first place
                int optimalscore;
                //force the hero to moove until he reached the end
                if (dcount != state.monsters.length) {
                    optimalscore = Integer.MIN_VALUE;
                }
                else {
                    optimalscore = computescore(state, optimalsolution);
                }
                int sizeofsolution = solution.length();//size of the string


                //Explore right

                // If I am not on the edge of the board and my last move wasn't left
                if ((state.heroPos[1] + rcount - lcount != state.monsters[0].length - 1) && ((sizeofsolution == 0) || solution.charAt(sizeofsolution - 1) != 'l')) {
                    String rightpath = findthebestway(state, solution + 'r', memo);
                    int rightscore = computescore(state, rightpath);

                    //If the way explore is better than the actual
                    if (rightscore >= optimalscore) {
                        optimalsolution = rightpath;
                        optimalscore = rightscore;
                    }

                }

                //Explore  left

                //If I am not on the edge of the board and my last move wasn't right
                if ((state.heroPos[1] + rcount - lcount != 0) && ((sizeofsolution == 0) || solution.charAt(sizeofsolution - 1) != 'r')) {
                    String leftpath = findthebestway(state, solution + 'l', memo);
                    int leftscore = computescore(state, leftpath);

                    //If the way explore is better than the actual
                    if (leftscore >= optimalscore) {
                        optimalsolution = leftpath;
                        optimalscore = leftscore;
                    }
                }

                //Explore down
                String downpath = findthebestway(state, solution + 'd', memo);
                int downscore = computescore(state, downpath);
                //If the way explore is better than the actual
                if (downscore >= optimalscore) {
                    optimalsolution = downpath;
                }

                // Creating the key
                String key2 = generatekey(state, optimalsolution, computescore(state, optimalsolution));
                //If the solution is not in the memo add it
                if (!(memo.containsKey(key2))) {
                    memo.put(key, optimalsolution);
                }
                return optimalsolution;
            }
        }

        /*
         * @ [First, OpenJML doc]
         * Moves the hero according to path and modifies their score as well as their health.
         *
         * @ requires path != null;
         * @ ensures \result == currentstate.heroScore + (\sum int i; 0 <= i && i < path.length; currentstate.treasures[hero_y][hero_x]) + currentstate.heroHealth - (\sum int i; 0 <= i && i < path.length; currentstate.monsters[hero_y][hero_x])
         * --- [Second, authors info]
         * Specification: Houria Ariouat, julien jamoulle | Last update : 09/05/24
         * Implementation: Houria Ariouat | Last update : 03/05/24
         * Test/Debug: Houria Ariouat, julien jamoulle | Last update :06/05/24
         **/
        public static /*@pure*/ int computescore(State currentstate, String path){
            // Variables requiered
            int pathscore = currentstate.heroScore;
            int pathhealth = currentstate.heroHealth;
            int hero_y = currentstate.heroPos[0];
            int hero_x = currentstate.heroPos[1];

            for (int i = 0; i < path.length(); i++){
                //movement right, the coordinate x of the hero increase
                if (path.charAt(i) == 'r'){
                    hero_x += 1;}
                //movement left, the coordinate x of the hero decrease
                else if (path.charAt(i) == 'l'){
                    hero_x -= 1;}
                //movement down, the coordinate y of the hero increase
                else if (path.charAt(i) == 'd'  && i!=path.length()-1){
                    hero_y += 1;}

                if(i!=path.length()-1){
                    //Update the value of heroHealth and heroScore
                    pathscore += currentstate.treasures[hero_y][hero_x];
                    pathhealth -= currentstate.monsters[hero_y][hero_x];}
            }

            //Give a really bad score to the hero if he take a way that make him die
            if(pathhealth<0){
                return Integer.MIN_VALUE;
            }

            return pathhealth +pathscore;
        }
        /*
         * @ [First, OpenJML doc]
         * @requires state!=null
         * @requires  optimalsolution!=null
         * @requires score!=null
         * @requires computescore(state,optimalsolution)=score
         * @ensures \return != null
         * @ensures \return instanceof String
         * @ensures \return.contains(score.Tostring)
         * --- [Second, authors info]
         * Specification: julien jamoulle| Last update : 06/05/24
         * Implementation: julien jamoulle| Last update : 06/05/24
         * Test/Debug:  julien jamoulle | Last update :06/05/24
         **/
        public static /*@pure*/ String generatekey(State state, String optimalsolution, Integer score){
            //Counting the movements made in the solution
            int dcount = 0;
            int rcount=0;
            int lcount=0;

            /*
             * @ loop_invariant 0 <= i < optimalsolution.length()
             * @ loop_invariant dcount<=state.monster.lenght
             * @loop_invariant lcount<optimalsolution.length()
             * @loop_invariant rcount<optimalsolution.length()
             * @ variant  optimalsolution.length()-i
             **/
            for (int i = 0; i < optimalsolution.length(); i++) {
                if (optimalsolution.charAt(i) == 'd') {
                    dcount++;
                }
                if (optimalsolution.charAt(i) == 'r') {
                    rcount++;
                }
                if (optimalsolution.charAt(i) == 'l') {
                    lcount++;
                }
            }
            // Calculating the Hero position
            Integer Hero_x=state.heroPos[1]+rcount-lcount;
            Integer Hero_y= state.heroPos[0]+dcount;
            //If i can reference the last movement of the hero
            if(optimalsolution.length()>=2){
                return Hero_y.toString()+Hero_x.toString()+score.toString()+optimalsolution.charAt(optimalsolution.length()-2);}
            //else the last movement is nothing 'n'
            return Hero_y.toString()+Hero_x.toString()+score.toString()+'n';

        }
    }

    /* --- Common utility functions --- */

    /*
     *  --- [authors info]
     * Implementation: chat GPT
     **/
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}


