package fr.unistra.poo.tp1.exo1;

public class BinomeSol0 extends Binome {
    protected BinomeSol0(double pa, double pb, double pc, double delta) {
        super(pa,pb,pc,delta);
    }

    @Override
    public void calculer_racine() {
        System.out.printf("Il n'y a pas de solutions à l'équation %s %n",getEquation());
    }

    @Override
    public int nb_racine() {
        return 0;
    }

}
