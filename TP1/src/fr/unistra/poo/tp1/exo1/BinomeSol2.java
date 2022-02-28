package fr.unistra.poo.tp1.exo1;

public class BinomeSol2 extends Binome {
    protected BinomeSol2(double pa, double pb, double pc, double delta) {
        super(pa, pb, pc, delta);
    }

    @Override
    public void calculer_racine() {
        System.out.printf("Il y a 2 solutions à l'équation %s : %e et %e%n", getEquation(), valeur_racine(1), valeur_racine(2));
    }

    @Override
    public int nb_racine() {
        return 1;
    }

    @Override
    public double valeur_racine(int i) {
        if (i == 1) {
            return (-b + Math.sqrt(dis)) / (2 * a);
        } else {
            return (-b - Math.sqrt(dis)) / (2 * a);
        }
    }
}
