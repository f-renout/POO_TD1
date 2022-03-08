package fr.unistra.l2.poo.td2;

public abstract class Suite {
    int premier;
    int rang;

    public Suite(int premier, int rang) {
        this.premier = premier;
        this.rang = rang;
    }

    public int getPremier() {
        return premier;
    }

    public int getRang() {
        return rang;
    }

    public abstract int valeurAuRangN(int n);

    public abstract int sommeAuRangN(int n);
}
