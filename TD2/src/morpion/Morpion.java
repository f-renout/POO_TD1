package morpion;

public class Morpion {


    private final Grille grille = new Grille();

    private final Joueur joueur1;
    private final Joueur joueur2;

    public Morpion(JOUEUR type1, JOUEUR type2, int difficulte) {
        joueur1 = createJoueur(type1, Type.X, difficulte);
        joueur2 = createJoueur(type2, Type.O, difficulte);
    }

    public static void main(String[] args) {
        Morpion m = new Morpion(JOUEUR.HUMAN, JOUEUR.IA,2);
        m.deroule();
    }

    private void deroule() {
        Joueur dernierJoueur = joueur2;
        while (!grille.finie()) {
            if (dernierJoueur == joueur1) {
                dernierJoueur = joueur2;
            } else {
                dernierJoueur = joueur1;
            }
            Coup c = dernierJoueur.jouer();
            System.out.println(c);
            System.out.println(grille);
        }
        final var gagne = grille.gagnant();
        if(gagne != null){
            System.out.printf("le joueur avec les %s a gagné", gagne);
        }else{
            System.out.println("match nul");
        }
    }

    private Joueur createJoueur(JOUEUR typeJoueur, Type x, int difficulte) {
        if (typeJoueur == JOUEUR.HUMAN) {
            return new Human(x, grille);
        }
        return new IA(x, grille, difficulte);
    }

    private enum JOUEUR {HUMAN, IA}
}
