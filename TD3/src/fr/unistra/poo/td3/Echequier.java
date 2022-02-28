package fr.unistra.poo.td3;

public class Echequier {
    private int positions[] = {0, 0, 0, 0, 0, 0, 0, 0};
    private int nbSolutions = 0;

    private Echequier() {
    }

    private void afficher() {
        //on affiche la position des reignes sur chaque ligne
        System.out.print("pos : ");
        for (int k = 0; k < 8; k++) {
            System.out.print(positions[k]);
        }
        System.out.println("");
        // Affiche sous forme de plateau d'echec avec des R pour les reines
        // et _ pour des cases vides
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(positions[i] == j ? "R " : "_ ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean conflit(int ligneReine1, int colonneReine1, int ligneReine2, int colonneReine2) {
        //un coup est invalide si même ligne, même colonne ou même diagonale
        return (ligneReine1 == ligneReine2 || colonneReine1 == colonneReine2 || (Math.abs(ligneReine1 - ligneReine2) == Math.abs(colonneReine1 - colonneReine2)));

    }

    // renvoie vrai si un coup est possible avec les reine déjà placées sur les lignes précédentes
    private boolean coupPossible(int ligne, int colonne) {
        if (ligne > 0) {
            for (int ligneExistante = 0; ligneExistante < ligne; ligneExistante++) {
                //un coup est possible si pas de conflit avec les positions des reines déjà placées
                if (conflit(ligneExistante, positions[ligneExistante], ligne, colonne))
                    return false;
            }
        }
        return true;
    }

    // Tentative de placer la ieme reine
    private void placerReine(int nieme) {
        for (int colNieme = 0; colNieme < 8; colNieme++) {
            //	Si le coup est possible, on place la reine et on cherche a placer la suivante
            if (coupPossible(nieme, colNieme)) {
                positions[nieme] = colNieme;
                if (nieme == 7) {
                    nbSolutions++;
                    // Si les huit reines sont placees, on affiche la solution
                    afficher();
                } else {
                    placerReine(nieme + 1);
                }
            }
        }
    }

    public static void main(String... args) {
        Echequier first = new Echequier();
        first.placerReine(0);
        System.out.println("Nb solutions totales : " + first.nbSolutions);
    }
}