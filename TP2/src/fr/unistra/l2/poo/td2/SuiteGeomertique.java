package fr.unistra.l2.poo.td2;

public class SuiteGeomertique extends Suite {
    public SuiteGeomertique(int premier, int rang) {
        super(premier, rang);
    }

    @Override
    public int valeurAuRangN(int n) {
        return premier*((int)Math.pow(rang, n-1));
    }

    @Override
    public int sommeAuRangN(int n) {
        return premier*(1-(int)Math.pow(rang,n))/(1-rang);
    }
}