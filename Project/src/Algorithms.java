import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.lang.Math;
import java.lang.System;

public interface Algorithms {
    Random rng = new Random();

    static void main(String[] args) {
        // Your code here
        System.out.println("Hello world!");
        int[] ss = {0,3};
        int[] ts = {2,0};
        int height = 11;
        int width = 7;
        //System.out.println(isReachableInNMoves(ss,ts,5));



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

        // Méthode récursive pour construire les chemins
        generatePaths(state,startSquare[1], startSquare[0], n, width, height, "", allPaths);

        return allPaths;
    }

    private static void generatePaths(State state, int x, int y, int remainingMoves, int width, int height, String path, ArrayList<String> allPaths) {
        // Si on a utilisé tous les mouvements, ajouter le chemin à la liste
        if (remainingMoves == 0 || (y+1 == height && x+1 == width && path.charAt(path.length() - 1) == 'r') || (y+1 == height && x == 0 && path.charAt(path.length() - 1) == 'l')) {
            allPaths.add(path);
            return;
        }

        // Tentative de déplacement vers le bas
        //if (y + 1 < height || ( y+1 == height && (x >= width - 1 && path.charAt(path.length() - 1) == 'r' || 0 >= x && path.charAt(path.length() - 1) == 'l'))) {
        if (y + 1 < height) {

            generatePaths(state, x, y + 1, remainingMoves - 1, width, height, path + 'd', allPaths);
            }

        // Tentative de déplacement vers la droite

            if (x + 1 < width && (path.isEmpty() || path.charAt(path.length() - 1) == 'r' || path.charAt(path.length() - 1) == 'd')) {
                generatePaths(state, x + 1, y, remainingMoves - 1, width, height, path + 'r', allPaths);
            }


        // Tentative de déplacement vers la gauche, mais seulement si on ne remonte pas
            if (x > 0 && (path.isEmpty() || path.charAt(path.length() - 1) == 'l' || path.charAt(path.length() - 1) == 'd')) {
                generatePaths(state, x - 1, y, remainingMoves - 1, width, height, path + 'l', allPaths);
            }
    }



    private static int getValueAtCoords(int[][] board, int y, int x) {
        return board[y][x];
    }

    private static int getPathScore(State state, String path) {
        int pathLength = path.length();
        int score = state.heroScore + state.heroHealth;
        int heroX = state.heroPos[1];
        int heroY = state.heroPos[0];
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


    /**
     *
     * Spécification : Ilias Essarhiri
     * Implémentation: Ilias Essarhiri + ajustement de la dernière ligne par ChatGPT
     * pour ajuster la dernière ligne.
     *
     * Representation : startsquare and targetSquare represent coordinates of this
     * form : {1,2} if x coordinate is 1 and y coordinate is 2;
     *
     * @requires : startSquare and targetSquare contain 2 positive integers;
     * @return : true if targetSquare can be reached in n moves from startSquare,
     *         false otherwise;
     */

    // @requires startSquare != null && targetSquare != null;
    // @return = ( (startY == targetY && deltaX == n) || (deltaX + deltaY) <= n &&
    // (deltaX + deltaY) % 2 == 1) )
    // @ensures \result = ?
    // @ Pure
    private static boolean isReachableInNMoves(int[] startSquare, int[] targetSquare, int n) {
        int startX = startSquare[0];
        int startY = startSquare[1];
        int targetX = targetSquare[0];
        int targetY = targetSquare[1];

        // Check if target square is above or equal to start square
        if (targetY < startY)
            return false;

        int deltaX = Math.abs(targetX - startX);
        int deltaY = Math.abs(targetY - startY);

        // Check if the target square can be reached in exactly n moves
        if (startY == targetY && deltaX == n)
            return true;

        // Check if the sum of deltaX and deltaY is less than or equal to n,
        // and if the difference between deltaX and deltaY is odd
        if ((deltaX + deltaY) <= n && (Math.abs(deltaX - deltaY) % 2 == 1)) {
            // Check if the hero moves horizontally only once
            if (deltaX == 1 && deltaY == n - 1)
                return true;
            // Check if the hero moves vertically only once
            if (deltaY == 1 && deltaX == n - 1)
                return true;
        }

        return false;
    }


    private static ArrayList<int[]> getAllValidPathsNSquaresFrom(int[] startSquare, int n) {
        ArrayList<int[]> allSquares = new ArrayList<int[]>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[] newSquare = { i, j };
                if (isReachableInNMoves(startSquare, newSquare, n))
                    allSquares.add(newSquare);
            }
        }
        return allSquares;
    }

    private static StringBuilder getPathTo(int[] startSquare, int[] targetSquare, State state) {
        StringBuilder path = new StringBuilder();
        int startX = startSquare[1];
        int startY = startSquare[0];
        int targetX = targetSquare[1];
        int targetY = targetSquare[0];
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        // Déplacer horizontalement vers la droite ou la gauche jusqu'à atteindre la
        // même colonne que la cible
        while (startX != targetX) {
            if (startX < targetX && startX < width) {
                path.append('r');
                startX++;
            }
            if (0 < startX) {
                path.append('l');
                startX--;
            }
        }

        while (startY < targetY && startY < height) {
            path.append('d');
            startY++;
        }
        return path;
    }


    private static void moveHero(State state, String path) {
        int pathLength = path.length();
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        for (char direction : path.toCharArray()) {
            switch (direction) {
                case 'd':
                    if (state.heroPos[0] + 1 < height) {
                        // Hero groes down
                        state.heroPos[0] += 1;
                    }
                    break;
                case 'r':
                    if (state.heroPos[1] + 1 < width) {
                        // Hero goes right
                        state.heroPos[1] += 1;
                    }
                    break;
                case 'l':
                    if (state.heroPos[1] - 1 >= 0) {
                        // Hero goes left
                        state.heroPos[1] -= 1;
                    }
                    break;
            }
            // Check if hero is still within the board
            if (state.heroPos[0] >= 0 && state.heroPos[0] < height &&
                    state.heroPos[1] >= 0 && state.heroPos[1] < width) {
                // If treasure on that square, collect it
                state.heroScore += getValueAtCoords(state.treasures, state.heroPos[0], state.heroPos[1]);
                state.treasures[state.heroPos[0]][state.heroPos[1]] = 0;
                // If monster on that square, fight it
                state.heroHealth -= getValueAtCoords(state.monsters, state.heroPos[0], state.heroPos[1]);
                state.monsters[state.heroPos[0]][state.heroPos[1]] = 0;

                if (state.heroHealth < 1) {
                    System.out.println("T'es mort");
                }
                System.out.println("Hero position : " + Arrays.toString(state.heroPos));
                System.out.println("Hero score : " + state.heroScore);
                System.out.println("Hero health : " + state.heroHealth);
            } else {
                System.out.println("Hero out of board");
            }
        }
    }


    private static boolean heroCanMove(State state) {
        int heroX = state.heroPos[1];
        int heroY = state.heroPos[0];
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        if (heroX < 0 || heroX >= width || heroY < 0 || heroY >= height) {
            // Le héros est en dehors des limites du niveau
            return false;
        }

        int monster = state.monsters[heroY][heroX];
        int treasure = state.treasures[heroY][heroX];

        // Condition de retour basée sur la logique du jeu
        return state.heroHealth >= (state.heroHealth + treasure - monster);
    }

    private static String getBestPathIn(State state, ArrayList<String> all_paths){
        String best_path = all_paths.getFirst();
        int best_score = 0;
        for(String path : all_paths){
            int path_score = getPathScore(state,path);
            if(best_score < path_score){
                best_path = path;
                best_score = path_score;
            }
        }
        return best_path;
    }


    interface GS {
        static int greedySolution(State state) {
            int height = state.monsters.length;
            int width = state.monsters[0].length;
            int score_to_beat = 0;
            int p = 0;

            while ( p < 100 && state.heroPos[1] < width && state.heroPos[0] < height && 0 < state.heroHealth && heroCanMove(state)) {
//                int right_score;
//                int left_score;
//                int down_score;
//                int best_score = 0;
//                ArrayList<String> right_paths;
//                ArrayList<String> left_paths;
//                ArrayList<String> down_paths;
//                String best_direction;
//
//                // Find the value of the square on the right
//                int[] r_square = {state.heroPos[0], state.heroPos[1]+1};
//                right_paths = getAllValidPaths(r_square,5,width,height);
//                String r_path = getBestPathIn(state, right_paths);
//                right_score = getPathScore(state,r_path);
//                if(best_score < right_score){
//                    best_direction = r_path;
//                }
//
//                int[] l_square = {state.heroPos[0], state.heroPos[1]-1};
//                left_paths = getAllValidPaths(l_square,5,width,height);
//                String l_path = getBestPathIn(state, left_paths);
//                left_score = getPathScore(state,l_path);
//                if(best_score < left_score){
//                    best_direction = l_path;
//                }
//
//                int[] d_square = {state.heroPos[0]+1, state.heroPos[1]};
//                down_paths = getAllValidPaths(d_square,5,width,height);
//                String d_path = getBestPathIn(state, down_paths);
//                down_score = getPathScore(state,d_path);
//                if(best_score < down_score){
//                    best_direction = d_path;
//                }
                int best_score = 0;
                char best_direction = 'd';
                ArrayList<String> all_paths;
                all_paths = getAllValidPaths(state,state.heroPos, 5, width, height);
                for(String path : all_paths){
                    int path_score = getPathScore(state,path);
                    if(best_score < path_score){
                        best_direction = path.charAt(0);
                        best_score = path_score;
                    }
                }
                moveHero(state, String.valueOf(best_direction));
//                String best_direction = getBestDirection(state.heroPos, state);
//                System.out.println(best_direction);
//                int path_score = getPathScore(state, best_direction);
//                moveHero(state, best_direction);

//                score_to_beat += path_score;
//                p += 1;
                if(state.heroPos[0] >= height-1) break;
            }

            return state.heroScore;
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
