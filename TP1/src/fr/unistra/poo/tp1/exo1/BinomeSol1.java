package fr.unistra.poo.tp1.exo1;

public class BinomeSol1 extends Binome {
    protected BinomeSol1(double pa, double pb, double pc, double delta) {
        super(pa,pb,pc,delta);
    }

    @Override
    public void calculer_racine() {
        System.out.printf("Il y a 1 solution à l'équation %s : %e%n",getEquation(), valeur_racine(1));
    }

    @Override
    public int nb_racine() {
        return 1;
    }

    @Override
    public double valeur_racine(int i) {
        return -b/(2*a);
    }
}
