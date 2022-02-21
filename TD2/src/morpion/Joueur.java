package morpion;

public abstract class Joueur {
    protected Type type;
    protected final Grille grille;

    protected Joueur(Type type, Grille grille) {
        this.type = type;
        this.grille = grille;
    }

    public Type getType() {
        return type;
    }

    public Jeton getJeton() {
        return new Jeton(type);
    }

    public Coup jouer(){
        Coup c = prochainCoup();
        grille.remplir(c);
        return c;
    }

    protected abstract Coup prochainCoup();
}
