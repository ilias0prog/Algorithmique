
import java.util.*;
import java.lang.System;

public interface Algorithms {
    Random rng = new Random();

    static void main(String[] args) {
        // Your code here
//        System.out.println("Hello world!");
//        int[] ss = {0,3};
//        int[] ts = {2,0};
//        int height = 11;
//        int width = 7;
//        //System.out.println(isReachableInNMoves(ss,ts,5));



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

        // Nombre initial de conseils disponibles pour le joueur
        int nbHint = 1;

        // Numéro du niveau actuel
        int nbLevel = 1;

        // Initialisation de l'objet State
        State new_state = new State(heroPos, heroHealth, heroScore, monsters, treasures, nbHint, nbLevel);

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

    /*** --- Generate & Test --- ***/
    interface GT {
        static void generateMonstersAndTreasures(int[][] monstersToFill, int[][] treasuresToFill) {
            // TODO
        }

        /*** --- Utility functions for GT --- ***/
        // TODO (if you have any)
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

    private static ArrayList<String> getAllValidPaths(State state, int[] startSquare, int n, int width, int height) {
        ArrayList<String> allPaths = new ArrayList<>();

        // Get all paths
        generatePaths(state,startSquare[1], startSquare[0], n, width, height, "", allPaths);

        return allPaths;
    }

    private static void generatePaths(State state, int x, int y, int remainingMoves, int width, int height, String path, ArrayList<String> allPaths) {
        // After n moves, add path to allPaths
        if (remainingMoves == 0 || (y+1 == height && x+1 == width && path.charAt(path.length() - 1) == 'r') || (y+1 == height && x == 0 && path.charAt(path.length() - 1) == 'l')) {
            allPaths.add(path);
            return;
        }

        // Going down if possible
        if (y + 1 < height) {
            generatePaths(state, x, y + 1, remainingMoves - 1, width, height, path + 'd', allPaths);
            }

        // Going right if possible
            if (x + 1 < width && (path.isEmpty() || path.charAt(path.length() - 1) == 'r' || path.charAt(path.length() - 1) == 'd')) {
                generatePaths(state, x + 1, y, remainingMoves - 1, width, height, path + 'r', allPaths);
            }

        // Going left if possible
            if (x > 0 && (path.isEmpty() || path.charAt(path.length() - 1) == 'l' || path.charAt(path.length() - 1) == 'd')) {
                generatePaths(state, x - 1, y, remainingMoves - 1, width, height, path + 'l', allPaths);
            }
    }



    private static int getValueAtCoords(int[][] board, int y, int x) {
        return board[y][x];
    }

    private static int getPathScore(State state, String path, int[] start_square) {
        int pathLength = path.length();
        int score = state.heroScore + state.heroHealth;
        int heroX = start_square[1];
        int heroY = start_square[0];
        int height = state.monsters.length;
        int width = state.monsters[0].length;

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
            // Ramasser le trésor s'il y en a un
            score += getValueAtCoords(state.monsters, heroY, heroX);
            // Combattre le monstre s'il y en a un
            score -= getValueAtCoords(state.monsters, heroY, heroX);
        }
        return score;
    }

    // startSquare[0] = y and startSquare[1] = x
    private static int[] moveHero(State state, String path, int[] startSquare, int hero_health, int hero_score) {
        int height = state.monsters.length;
        int width = state.monsters[0].length;

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
                // If treasure on that square, collect it
                hero_score += getValueAtCoords(state.treasures, startSquare[0], startSquare[1]);
                state.treasures[startSquare[0]][startSquare[1]] = 0;
                // If monster on that square, fight it
                hero_health -= getValueAtCoords(state.monsters, startSquare[0], startSquare[1]);
                state.monsters[startSquare[0]][startSquare[1]] = 0;

                if (hero_health < 1) {
                    System.out.println("T'es mort");
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



    interface GS {
        static int greedySolution(State state) {
            int height = state.monsters.length;
            int width = state.monsters[0].length;
            int[] startSquare = {state.heroPos[0],state.heroPos[1]};
            int heroHealth = state.heroHealth;
            int heroScore = state.heroScore;

            while (startSquare[1] < width && startSquare[0] < height-1 && 0 < heroHealth) {
                int best_score = 0;
                char best_direction = 'd';
                ArrayList<String> all_paths;
                all_paths = getAllValidPaths(state,startSquare, 5, width, height);
                for(String path : all_paths){
                    int path_score = getPathScore(state,path, startSquare);
                    if(best_score < path_score){
                        best_direction = path.charAt(0);
                        best_score = path_score;
                    }
                }
                int[] health_score = moveHero(state, String.valueOf(best_direction), startSquare, heroHealth, heroScore);
                heroHealth = health_score[0];
                heroScore = health_score[1];
            }
            return heroScore;
        }
        /*** --- Utility functions for GS --- ***/
        // TODO (if you have any)
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
