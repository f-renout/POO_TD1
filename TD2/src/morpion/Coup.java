package morpion;

public class Coup {
    int ligne;
    int colonne;
    private final Jeton jeton;

    public Coup(int ligne, int colonne, Jeton jeton) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.jeton = jeton;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public Jeton getJeton() {
        return jeton;
    }

    @Override
    public String toString() {
        return "Coup{" + "ligne=" + ligne + ", colonne=" + colonne + ", jeton=" + jeton + '}';
    }
}
