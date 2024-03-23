package GenerateAndTest.exos;/*
 * Vous disposez d'un échiquier de taille 4x4. Le but de EightQueens.java est de placer 4 reines sur cet échiquier
 * sans qu'aucune ne menace directement une autre reine.
 *
 * Bonus: généralisez le problème pour pouvoir placer n dames sur un échiquier de n x n.
 */

class EightQueens {
    static void print(Object o) {
        System.out.println(o);
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 1) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Lancement du timer
        long startMs = System.currentTimeMillis();
        long startNs = System.nanoTime();

        // Les variables propres au probleme
        int n = 4;
        int[][] emptyBoard = new int[n][n];  // 0 = empty, 1 = queen

        // Recuperation des resultats
        int[][] boardWithQueens = generateAndTest(emptyBoard);

        // Fin du timer
        long endMs = System.currentTimeMillis();
        long endNs = System.nanoTime();

        // Affichage des resultats
        print("---");
        print("Solution: ");
        printBoard(boardWithQueens);
        print("---");
        print("Temps de calcul (ms): "+ (endMs-startMs));
        print("Temps de calcul (ns): "+ (endNs-startNs));
        print("---");
    }

    static int[][] generateAndTest(int[][] boardToFill) {
        /* Votre code ici */
        return boardToFill;
    }

    static String generate() {
        /* Votre code ici */
        return null;
    }

    static boolean test() {
        /* Votre code ici */
        return false;
    }
}