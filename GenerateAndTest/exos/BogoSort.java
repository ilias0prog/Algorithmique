package GenerateAndTest.exos;/*
 * Le but de BogoSort.java est d'implémenter l'algorithme de BogoSort. Cet algorithme naïf consiste à générer une
 * permutation aléatoire d'une liste donnée et de vérifier si cette permutation est triée. Tant qu'on ne trouve pas de
 * permutation triée, on en crée de nouvelles.
 *
 * Indice: si la génération d'une permutation aléatoire pose souci, consultez l'implémentation Java disponible à
 * https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
 *
 * Bonus: implémentez une version déterministe de BogoSort qui parcourt toutes les permutations dans un certain ordre
 * plutôt que de les générer au hasard.
 */
import java.util.Random;
import java.util.Arrays;

class BogoSort {
    static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        // Lancement du timer
        long startMs = System.currentTimeMillis();
        long startNs = System.nanoTime();

        // Les variables propres au probleme
        int n = 9;
        int[] toSort = new int[n];
        for (int i = 0; i < n; i++)
            toSort[i] = n - i;  // Descending order

        // Recuperation des resultats
        int[] sorted = generateAndTest(toSort);

        // Fin du timer
        long endMs = System.currentTimeMillis();
        long endNs = System.nanoTime();

        // Affichage des resultats
        print("---");
        print("Starting list: " + Arrays.toString(toSort));
        print("Sorted list: " + Arrays.toString(sorted));
        print("---");
        print("Temps de calcul (ms): " + (endMs - startMs));
        print("Temps de calcul (ns): " + (endNs - startNs));
        print("---");
    }

    static int[] generateAndTest(int[] toSort) {
        if (toSort == null) {
            return null;
        }
        while (!test(toSort)) {
            int l = toSort.length;
            // Créer un tableau contenant les indices
            int[] indexes = new int[l];
            // Le remplir
            for (int i = 1; i < l; i++) {
                indexes[i] = i;
            }
            // Mélanger le tableau d'indices:
            shuffle(indexes);
            // Modifier toSort en conséquence :
            for (int i = 0; i < l; i++) {
                toSort[i] = toSort[indexes[i]];
            }

        }
        return toSort;
    }

    static int[] shuffle(int[] toShuffle) {
        // On parcourt le tableau en faisant des échanges aléatoires
        Random random = new Random();
        for (int i = 0; i < toShuffle.length; i++) {
            int j = random.nextInt(toShuffle.length);
                int alt = toShuffle[i];
                toShuffle[i] = toShuffle[j];
                toShuffle[j] = alt;
        }
        return toShuffle;
    }

    static String generate() {

        return null;
    }

    static boolean test(int[] toTest) {
        // Vérifier si le tableau est trié dans l'ordre croissant
        for (int i = 0; i < toTest.length - 1; i++) {
            if (toTest[i] > toTest[i + 1]) {
                return false; // Le tableau n'est pas trié, donc retourner false
            }
        }
        return true; // Le tableau est trié, donc retourner true
    }


    /*static boolean test(int[] toTest) {
        int l = toTest.length;
        if (l == 2 && toTest[0] <= toTest[1]) {
            return true;
        } else {
            // Créer un nouveau tableau
            if (l >= 4) {
                int[] newToTest = new int[l / 2];
                System.arraycopy(toTest, 0, newToTest, 0, l / 2);
                return test(newToTest);
            } else {
                return false;
            }
        }
    }
    */
}
