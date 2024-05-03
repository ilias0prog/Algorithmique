import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.lang.Math;
import java.lang.System;

public interface Algorithms {
    Random rng = new Random();

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
    State initialState = new State(heroPos, heroHealth, heroScore, monsters, treasures, nbHint, nbLevel);

    //int score_to_beat = GS.greedySolution(initialState);


    static void main(String[] args) {
        // Your code here
        System.out.println("Hello world!");
        int[] ss = {0,3};
        int[] ts = {2,0};
        int height = 7;
        int width = 11;
        System.out.println(isReachableInNMoves(ss,ts,5));
        System.out.println(getAllValidPaths(ss, 5, width, height));
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

    private static ArrayList<String> getAllValidPaths(int[] startSquare, int n, int width, int height) {
        ArrayList<String> allPaths = new ArrayList<>();

        // Méthode récursive pour construire les chemins
        generatePaths(startSquare[0], startSquare[1], n, width, height, "", allPaths);

        return allPaths;
    }

    private static void generatePaths(int x, int y, int remainingMoves, char lastMove, int width, int height, String path, ArrayList<String> allPaths) {
        // Si on a utilisé tous les mouvements, ajouter le chemin à la liste
        if (remainingMoves == 0) {
            allPaths.add(path);
            return;
        }

        // Tentative de déplacement vers la droite
        if (x + 1 < width && path.isEmpty() || path.charAt(path.length() - 1) == 'r') {
            generatePaths(x + 1, y, remainingMoves - 1, width, height, path + 'r', allPaths);
        }

        // Tentative de déplacement vers la gauche, mais seulement si on ne remonte pas
        if (x - 1 >= 0 && (path.isEmpty() || path.charAt(path.length() - 1) == 'l') && y > startSquare[1]) {
            generatePaths(x - 1, y, remainingMoves - 1, width, height, path + 'l', allPaths);
        }

        // Tentative de déplacement vers le bas
        if (y + 1 < height) {
            generatePaths(x, y + 1, remainingMoves - 1, width, height, path + 'd', allPaths);
        }
    }



    private static int getValueAtCoords(int[][] board, int x, int y) {
        return board[x][y];
    }

    private static int getPathScore(State state, String path) {
        int pathLength = path.length();
        int score = state.heroScore + state.heroHealth;
        int heroX = state.heroPos[0];
        int heroY = state.heroPos[1];
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        for (int i = 0; i < pathLength; i++) {
            char direction = path.charAt(i);
            switch (direction) {
                case 'd':
                    if (heroY < height) {
                        // Faire descendre le héros
                        heroY += 1;
                        break;
                    }
                case 'r':
                    if (heroX < width) {
                        // Faire avancer le héros
                        heroX += 1;
                        break;
                    }
                case 'l':
                    if (width < heroX) {
                        heroX -= 1;
                        break;
                    }
            }
            // Ramasser le trésor s'il y'en a un
            score += getValueAtCoords(state.treasures, heroX, heroY);
            // Combattre le monstre s'il y'en a un
            score -= getValueAtCoords(state.monsters, heroX, heroY);
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
        int startX = startSquare[0];
        int startY = startSquare[1];
        int targetX = targetSquare[0];
        int targetY = targetSquare[1];
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

    private static String getBestDirection(int[] startSquare, State state) {
        ArrayList<int[]> allSquares = new ArrayList<int[]>();
        allSquares = getAllValidPathsNSquaresFrom(startSquare, 5);
        int bestScore = -999999999;
        StringBuilder bestDir = new StringBuilder();
        for (int[] allSquare : allSquares) {
            StringBuilder path = new StringBuilder();
            path = getPathTo(startSquare, allSquare, state);
            int score = getPathScore(state, path.toString());
            if (score > bestScore) {
                bestDir = path;
                bestScore = score;
            }
        }
        return bestDir.substring(0, 1);
    }

//    private static void moveHero(State state, int[] startSquare, String path) {
//        int pathLength = path.length();
//        for (int i = 0; i < pathLength; i++) {
//            char direction = path.charAt(i);
//            int heroX = startSquare[0];
//            int heroY = startSquare[1];
//            int height = state.monsters.length;
//            int width = state.monsters[0].length;
//
//            switch (direction) {
//                case 'd':
//                    if (heroY < height) {
//                        // Faire descendre le héros
//                        state.heroPos[1] += 1;
//                    }
//                case 'r':
//                    if (heroX < width) {
//                        // Faire avancer le héros
//                        state.heroPos[0] += 1;
//                        //
//                    }
//                case 'l':
//                    if (width < heroX) {
//                        state.heroPos[0] -= 1;
//                    }
//            }
//            // Ramasser le trésor s'il y'en a un
//            state.heroScore += getValueAtCoords(state.treasures, heroX, state.heroPos[1]);
//            // Combattre le monstre s'il y'en a un
//            state.heroHealth -= getValueAtCoords(state.monsters, heroX, state.heroPos[1]);
//            if (state.heroHealth < 1) {
//                System.out.println("T'es mort");
//            }
//        }
//    }

    private static void moveHero(State state, String path) {
        int pathLength = path.length();
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        for (char direction : path.toCharArray()) {
            switch (direction) {
                case 'd':
                    if (state.heroPos[1] < height) {
                        // Faire descendre le héros
                        state.heroPos[1] += 1;
                    }
                    break;
                case 'r':
                    if (state.heroPos[0] < width) {
                        // Faire avancer le héros
                        state.heroPos[0] += 1;
                    }
                    break;
                case 'l':
                    if (width < state.heroPos[0]) {
                        state.heroPos[0] -= 1;
                    }
                    break;
            }
            // Ramasser le trésor s'il y'en a un
            state.heroScore += getValueAtCoords(state.treasures, state.heroPos[0], state.heroPos[1]);
            // Combattre le monstre s'il y'en a un
            state.heroHealth -= getValueAtCoords(state.monsters, state.heroPos[0], state.heroPos[1]);
            if (state.heroHealth < 1) {
                System.out.println("T'es mort");
            }
            System.out.println("Hero position : " + Arrays.toString(state.heroPos));
            System.out.println("Hero score : " + state.heroScore);
        }
    }

//    private static boolean heroCanMove(State state){
//        if(state.heroHealth > 1) return false;
//
//        // Check surrounding squares
//        int leftMonster = state.monsters[state.heroPos[0] - 1][state.heroPos[1]];
//        int rightMonster = state.monsters[state.heroPos[0] + 1][state.heroPos[1]];
//        int bottomMonster = state.monsters[state.heroPos[0]][state.heroPos[1] + 1];
//
//        int leftTreasure = state.treasures[state.heroPos[0] - 1][state.heroPos[1]];
//        int rightTreasure = state.treasures[state.heroPos[0] + 1][state.heroPos[1]];
//        int bottomTreasure = state.treasures[state.heroPos[0]][state.heroPos[1] + 1];
//
//        int minMonster = Math.min(leftMonster,rightMonster);
//        minMonster = Math.min(minMonster, bottomMonster);
//        int maxTreasure = Math.max(leftTreasure,rightTreasure);
//        maxTreasure = Math.max(maxTreasure, bottomTreasure);
//
//        return state.heroHealth >= (state.heroHealth + maxTreasure - minMonster);
//    }

    private static boolean heroCanMove(State state) {
        int heroX = state.heroPos[0];
        int heroY = state.heroPos[1];
        int height = state.monsters.length;
        int width = state.monsters[0].length;

        if (heroX < 0 || heroX >= width || heroY < 0 || heroY >= height) {
            // Le héros est en dehors des limites du niveau
            return false;
        }

        int monster = state.monsters[heroX][heroY];
        int treasure = state.treasures[heroX][heroY];

        // Condition de retour basée sur la logique du jeu
        return state.heroHealth >= (state.heroHealth + treasure - monster);
    }


    interface GS {
        static int greedySolution(State state) {
            int[] startSquare = state.heroPos;
            int height = state.monsters.length;
            int width = state.monsters[0].length;
            int score_to_beat = 0;

            while (state.heroPos[0] < width && state.heroPos[1] < height) {
                System.out.println("################################");
                String best_direction = getBestDirection(startSquare, state);
                System.out.println(best_direction);
                int path_score = getPathScore(state, best_direction);
                moveHero(state, best_direction);

                score_to_beat += path_score;
            }
            System.out.println("Score to beat : " + score_to_beat);
            return score_to_beat;
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
