
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.lang.System;

public interface Algorithms3 {
    Random rng = new Random();

    static void main(String[] args) {
        // Your code here
//        System.out.println("Hello world!");
//        int[] ss = {0,3};
//        int[] ts = {2,0};
//        int height = 11;
//        int width = 7;
//        //System.out.println(isReachableInNMoves(ss,ts,5));

        int[][] monsters_array = new int[11][7];
        int[][] treasures_array = new int[11][7];

        GT.generateMonstersAndTreasures(monsters_array, treasures_array);
        printMatrix(monsters_array);
        System.out.println("***************************");
        printMatrix(treasures_array);



        // Position initiale du héros
        int[] heroPos = {0,3};

        // Santé initiale du héros
        int heroHealth = 100;

        // Score initial du héros
        int heroScore = 0;

        // Tableau représentant les monstres dans le niveau
        int[][] monsters = new int[][] {
                {0, 20, 0, 0, 0, 15, 0},
                {0, 0, 10, 0, 5, 10, 0},
                {0, 30, 0, 0, 0, 0, 10},
                {0, 0, 5, 0, 10, 0, 20},
                {0, 10, 0, 0, 0, 10, 0},
                {0, 0, 0 , 15, 0, 0, 0},
                {5 , 0, 0, 0, 30, 0, 0},
                {0, 0, 5, 0, 10, 0, 20},
                {0, 10, 0, 0, 0, 10, 0},
                {0, 0, 0 , 15, 0, 0, 0},
                {5 , 0, 0, 0, 30, 0, 0}
        };

        // Tableau représentant les trésors dans le niveau
        int[][] treasures = new int[][] {
                {0, 10, 0, 0, 0, 0, 0},
                {0, 0, 0, 20, 0, 0, 0},
                {0, 0, 0, 15, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 20},
                {0, 10, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5 , 0},
                {0, 30, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 20},
                {0, 10, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5 , 0},
                {0, 30, 0, 0, 0, 0, 0}
        };

        int[][] monsters_arr ={{0,0,0,0},
                {10,40,0,0},
                {0,0,20,0}};
        int[][] treasures_arr ={{0,0,0,0},
                {0,0,0,0},
                {70,0,0,50}};;

        // Nombre initial de conseils disponibles pour le joueur
        int nbHint = 1;

        // Numéro du niveau actuel
        int nbLevel = 1;

        // Initialisation de l'objet State
        printMatrix(monsters_arr);
        System.out.println("#####################");
        printMatrix(treasures_arr);
        State new_state = new State(heroPos, heroHealth, heroScore, monsters_array, treasures_array, nbHint, nbLevel);

        //System.out.println(getAllValidPaths(new_state, new_state.heroPos, 5, width, height));



        long startTime = System.currentTimeMillis();
        int score_to_beat = GS.greedySolution(new_state);
        System.out.println("Score to beat : " + score_to_beat);

        long endTime = System.currentTimeMillis();
        long elapsedTimeInMillis = endTime - startTime;
        long elapsedTimeInSeconds = elapsedTimeInMillis / 1000;
        long remainingMilliseconds = elapsedTimeInMillis % 1000;
        System.out.println("Temps d'exécution : " + elapsedTimeInSeconds + " secondes et " + remainingMilliseconds + " millisecondes.");
    }

    public static void printMatrix(int[][] matrix) {//Chatgpt for this function (je demanderais pour savoir si je peux)
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    /*** --- Generate & Test --- ***/
    /* --- Generate & Test --- */
    interface GT {
        static void generateMonstersAndTreasures(int[][] monstersToFill, int[][] treasuresToFill) {
            boolean result = false;
            // Testing test function of GT
            while (!result) {
                generate(monstersToFill, treasuresToFill);
                result = test(monstersToFill, treasuresToFill);
            }
        }

        /*
         * @ [First, OpenJML doc]
         * @ requires monstersToFill != null && treasuresToFill != null
         * && monstersToFill.length == treasuresToFill.length
         * && monstersToFill.length != 0 && treasuresToFill.length != 0;
         * @ ensures \result == (\forall int i; 0<=i; i < monstersToFill.length; monstersToFill[i] >= 2 && treasuresToFill[i] < 2)
         * @ ensures \result == (\sum int i; 0<=i && i < monstersToFill.length; monstersToFill[i] >= treasuresToFill[i])
         * --- [Second, authors info]
         * Specification: Houria Ariouat | Last update : 04/05/24
         * Implementation: Houria Ariouat | Last update : 01/05/24
         * Test/Debug:  Houria Ariouat, Julien Jamoulle| Last update :01/05/24
         ***/

        static void generate(int[][] monstersToFill, int[][] treasuresToFill) {
            //for each row
            /*
             * @ loop_invariant 0 <= i <= treasureToFill.length -1
             * @ variant  i = treasureToFill.length-1
             ***/
            for (int i = 0; i <= treasuresToFill.length - 1; i++) {
                int[] monsterCount = new int[monstersToFill.length];
                int[] treasureCount = new int[treasuresToFill.length];
                int[] totalmonsterValue = new int[monstersToFill.length];
                int[] totaltreasureValue = new int[treasuresToFill.length];
                Random randomobject = new Random();
                int valuetreasure;
                int valuemonster;
                //for each column at a specific row
                /*
                 * @ loop_invariant 0 <= j <= treasureToFill.length -1
                 * @ variant  j = treasureToFill .length-1
                 ***/
                for (int j = 0; j <= treasuresToFill[i].length - 1; j++) {


                    int randomnumber = randomobject.nextInt(6);


                    //test the probabilities
                    //if 1/6
                    if (randomnumber == 5 && treasureCount[i] < 2 && monstersToFill[i][j] == 0) {//condition to have a valid board
                        valuetreasure = randomobject.nextInt(1, 99);
                        treasuresToFill[i][j] = valuetreasure;
                        treasureCount[i] += 1;
                        totaltreasureValue[i] += treasuresToFill[i][j];
                    }

                    //if 2/6
                    if (randomnumber <= 1 && treasuresToFill[i][j] == 0) {
                        valuemonster = randomobject.nextInt(1, 50);
                        monstersToFill[i][j] = valuemonster;
                        monsterCount[i] += 1;
                        totalmonsterValue[i] += monstersToFill[i][j];//add total of value to my variable
                    }
                    //if 3/6
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
         * @ requires monsterToTest!=null && treasuresToTest!=null
         * && monsterToTest.lenght!=0 && treasuresToTest.lenght!=0
         * && monsterToTest.lenght==treasuresToTest.length
         * && \forall(int i;0<=i &&  i<monsterToTest.lenght;(monsterToTest[i].lenght==treasuresToTest[i].length) && (monsterToTest[i].lenght!=0) && (treasuresToTest[i].length!=0));
         * @ ensures (\return == true)==>/forall(int i;0<=i &&  i<monsterToTest.lenght; /sum(int j;0<=j &&  j<monsterToTest[i].lenght;<monsterToTest[i][j])>=/sum(int j;0<=j &&  j<treasuresToTest[i].lenght;<treasuresToTest[i][j])
         * && /forall(int i;0<=i &&  i<monsterToTest.lenght; /sum(int j;0<=j &&  j<monsterToTest[i].lenght;
         * --- [Second, authors info]
         * Specification: Julien Jamoulle | Last update : 05/04/24
         * Implementation: Julien Jamoulle | Last update : 03/04/24
         * Test/Debug: Julien Jamoulle| Last update :27/04/24
         ***/
        static boolean test(int[][] monsterToTest, int[][] treasuresToTest) {
            // ** Time Complexity= n^2, Mem Complexity= ? **//
            // For each row calculate the treasures and monster
            for (int i = 0; i < monsterToTest.length; i++) {
                // **Variant= monsterToTest.length-i, Inv: 0<=i<monsterToTest.length,... **//
                int monsterCount = 0;
                int monsterValue = 0;
                int treasureCount = 0;
                int treasureValue = 0;

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


    /*** --- Divide & Conquer --- ***/
    interface DC {
        static void sortLevel(int[][] monstersToSort, int[][] treasuresToSort) {
            // TODO
        }

        /*** --- Utility functions for DC --- ***/
        // TODO (if you have any)
    }

    /*** --- Greedy Search --- ***/

    interface GS {
        static int greedySolution(State state) {
            // Idée : au lieu d'avancer apres chaque nouveau best path,
            // Créer genre une String a laquelle j'ajoute a chaque fois le nouveau mouvement a faire
            // Et touuuut a la fin, faire moveHero avec cette String la
            int[][] monsters = state.monsters;
            int[][] treasures = state.treasures;
            int height = state.monsters.length;
            int width = state.monsters[0].length;
            int[] startSquare = {state.heroPos[0],state.heroPos[1]};
            int heroHealth = state.heroHealth;
            int heroScore = state.heroScore;
            String last_move = "";
            String best_path = "";
            String final_path = "";

//            while(startSquare[1] < width && startSquare[0] < height-1) {
//                ArrayList<String> all_paths = new ArrayList<String>();
//                int best_score = Integer.MIN_VALUE;
//                all_paths = getAllValidPaths(state, startSquare, 5, width, height);
//                String direction = "";
//                for(String path : all_paths){
//                    int path_score = getPathScore(state, path, startSquare, heroHealth, heroScore);
//                    if(best_score < path_score){
//                        best_score = path_score;
//                        direction = path.substring(0,1);
//                    }
//                }
//                int[] health_score = moveHero(monsters, treasures, direction, startSquare, heroHealth, heroScore);
//                heroHealth = health_score[0];
//                heroScore = health_score[1];
//                if(heroHealth <= 0){
//                    System.out.println("you're dead");
//                    break;
//                    }
//                if(startSquare[0] == height-1) System.out.println("Bottom of the board reached");
//            }
//            return heroScore;
            ArrayList<String> greedy_path = new ArrayList<>();
            while(startSquare[0] < height){
                ArrayList<String> all_paths = getAllValidPaths(startSquare,5, width, height);
                System.out.println(all_paths);
                int best_score = Integer.MIN_VALUE;
                String direction = "";
                String new_path = "";
                for(String path : all_paths){
                    int path_score = getPathScore(monsters, treasures, path, startSquare, heroHealth, heroScore);
                    if(best_score < path_score && !(path.substring(0, 1).equals(getOppositeDirection(path)))){
                        best_score = path_score;
                        new_path = path;
                    }
                }
                best_path = best_path + new_path;
                int[] health_score = moveHero(monsters, treasures, direction, startSquare, heroHealth, heroScore);
                heroHealth = health_score[0];
                heroScore = health_score[1];
                last_move = direction;
                if(heroHealth <= 0){
                    System.out.println("you're dead");
                    break;
                }
                if(startSquare[0] == height-1) {
                    System.out.println("Bottom of the board reached");
                    break;
                }
            }
            return heroScore;
        }
        /*** --- Utility functions for GS --- ***/
        // TODO (if you have any)

        private static String getOppositeDirection(String dir){
            String opp_dir = "";
            if(Objects.equals(dir, "r")) opp_dir = "l";
            else if (Objects.equals(dir, "l")) opp_dir = "r";
            return opp_dir;
        }

        private static ArrayList<String> getAllValidPaths(int[] startSquare, int n, int width, int height) {
            ArrayList<String> allPaths = new ArrayList<>();

            // Get all paths
            generatePaths(startSquare[1], startSquare[0], n, width, height, "", allPaths);
            return allPaths;
        }

        private static void generatePaths(int x, int y, int remainingMoves, int width, int height, String path, ArrayList<String> allPaths) {
            // After n moves, add path to allPaths
            if(remainingMoves == 0){
                allPaths.add(path);
                return;
            }

            // Move down
            y = y+1;
            if(0 <= y && y < height){
                generatePaths(x, y, remainingMoves-1, width, height, path + "d", allPaths);
            }

            x = x+1;
            if (x < width && (path.isEmpty() || path.charAt(path.length() - 1) == 'r' || path.charAt(path.length() - 1) == 'd')){
                generatePaths(x, y, remainingMoves-1, width, height, path + "r", allPaths);
            }

            x = x-1;
            if(x >= 0 && (path.isEmpty() || path.charAt(path.length() - 1) == 'l' || path.charAt(path.length() - 1) == 'd')){
                generatePaths(x, y, remainingMoves-1, width, height, path + "l", allPaths);
            }
        }

        private static int[] moveHero(int[][] monsters, int[][] treasures, String path, int[] startSquare, int hero_health, int hero_score) {
            int height = monsters.length;
            int width = monsters[0].length;

            for (char direction : path.toCharArray()) {
                switch (direction) {
                    case 'd':
                        if (startSquare[0] + 1 < height) {
                            // Hero goes down
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
                    // If treasure on that square, collect it
                    hero_score += getValueAtCoords(treasures, startSquare[0], startSquare[1]);
                    treasures[startSquare[0]][startSquare[1]] = 0;
                    // If monster on that square, fight it
                    hero_health -= getValueAtCoords(monsters, startSquare[0], startSquare[1]);
                    monsters[startSquare[0]][startSquare[1]] = 0;

                    if (hero_health < 1) {
                        break;
                    }
                    System.out.println("Hero position : " + Arrays.toString(startSquare));
                    System.out.println("Hero score : " + hero_score);
                    System.out.println("Hero health : " + hero_health);
                } else {
                    System.out.println("Hero out of board");
                }
            }
            int[] health_score = {hero_health, hero_score};
            return health_score;
        }

        private static int getValueAtCoords(int[][] board, int y, int x) {
            return board[y][x];
        }

        private static int getPathScore(int[][] monsters, int[][] treasures, String path, int[] start_square, int hero_health, int hero_score) {
            int pathLength = path.length();
            int score = hero_score + hero_health;
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
                    case 'r':
                        if (heroX + 1 < width) {
                            // Faire avancer le héros
                            heroX += 1;
                        }
                    case 'l':
                        if (heroX - 1 >= 0) {
                            // Faire reculer le héros
                            heroX -= 1;
                        }
                }
                // Ramasser le trésor s'il y en a un
                score += getValueAtCoords(treasures, heroY, heroX);
                // Combattre le monstre s'il y en a un
                score -= getValueAtCoords(monsters, heroY, heroX);
            }
            return score;
        }

    }

    /*** --- Dynamic Programming --- ***/
    interface DP {
        static String perfectSolution(State state) {
            // TODO
            return "";
        }

        /*** --- Utility functions for DP --- ***/
        // TODO (if you have any)
    }

    /* --- Common utility functions --- */
    // TODO (if you have any)

}
