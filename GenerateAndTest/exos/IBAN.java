package GenerateAndTest.exos;/*
 * Vous avez trouvé une faille dans l'interface d'une banque qui pourrait permettre une fuite d'une information : le
 * fait qu'un certain compte, identifié par son IBAN, existe bien au sein de cette banque. Mais vous savez que tester
 * tous les IBANs existants serait la meilleure façon d'être détecté par le système de sécurité de la banque. Vous
 * choisissez donc une approche plus subtile : ne tester que les IBANs syntaxiquement corrects.
 * Le but de IBAN.java est de générer une chaîne de caractères ressemblant à un identifiant de compte IBAN, comme
 * "BE34 7135 3223 5424". Ces identifiants doivent respecter une série de contraintes (purement fictives) :
 *   - ils doivent commencer par BE (nationalité de la banque)
 *   - les deux premiers chiffres doivent former un multiple de 17 (code de sécurité)
 *   - chaque premier chiffre des blocs de 4 chiffres doit être impair
 *   - la somme de tous les chiffres ne peut pas excéder 100
 *
 * Bonus : ajoutez la contrainte "l'IBAN doit contenir autant de nombres pairs et impairs"
 */

class IBAN {
    static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        // Lancement du timer
        long startMs = System.currentTimeMillis();
        long startNs = System.nanoTime();

        // Recuperation des resultats
        String solution = generateAndTest();

        // Fin du timer
        long endMs = System.currentTimeMillis();
        long endNs = System.nanoTime();

        // Affichage des resultats
        print("---");
        print("IBAN généré: "+solution);
        print("---");
        print("Temps de calcul (ms): "+ (endMs-startMs));
        print("Temps de calcul (ns): "+ (endNs-startNs));
        print("---");
    }

    static String generateAndTest() {
        /* Votre code ici */
        return null;
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