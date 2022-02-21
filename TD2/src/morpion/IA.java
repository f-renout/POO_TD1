package morpion;

public class IA extends Joueur {
    private static final int MAX = 1000;
    private static final int MIN = -1000;
    Coup prochainCoup = null;
    private final int niveau;

    public IA(Type x, Grille grille, int niveau) {
        super(x, grille);
        this.niveau = niveau;
    }

    @Override
    public Coup prochainCoup() {
        max(niveau);
        return prochainCoup;
    }

    private int evaluer() {
        if (grille.finie()) {
            if (grille.gagne() != null) {
                return grille.gagne() == type ? MAX : MIN;
            }
        }
        return 0;
    }

    private int max(int profondeur) {
        if (profondeur == 0) {
            prochainCoup = null;
            return evaluer();
        }
        final var coups = grille.coupsPossibles(type);
        if (coups.isEmpty()) {
            prochainCoup = null;
            return evaluer();
        }
        int scoreMax = MIN - 1;
        Coup prochain = null;
        for (Coup coup : coups) {
            grille.remplir(coup);
            int score = min(profondeur - 1);
            if (score > scoreMax) {
                scoreMax = score;
                prochain = coup;
            }
            grille.annuler(coup);
        }
        prochainCoup = prochain;
        return scoreMax;
    }

    private int min(int profondeur) {
        if (profondeur == 0) {
            prochainCoup = null;
            return evaluer();
        }
        final var coups = grille.coupsPossibles(type.adversaire());
        if (coups.isEmpty()) {
            prochainCoup = null;
            return evaluer();
        }
        int scoreMin = MAX + 1;
        Coup prochain = null;
        for (Coup coup : coups) {
            grille.remplir(coup);
            int score = max(profondeur - 1);
            if (score < scoreMin) {
                scoreMin = score;
                prochain = coup;
            }
            grille.annuler(coup);
        }
        prochainCoup = prochain;
        return scoreMin;
    }
}
