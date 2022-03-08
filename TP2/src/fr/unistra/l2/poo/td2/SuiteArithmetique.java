package fr.unistra.l2.poo.td2;

public class SuiteArithmetique extends Suite {
    public SuiteArithmetique(int premier, int rang) {
        super(premier, rang);
    }

    @Override
    public int valeurAuRangN(int n) {
        return premier + ((n - 1) * rang);
    }

    @Override
    public int sommeAuRangN(int n) {
        return n * (premier + valeurAuRangN(n)) / 2;
    }
}