package fr.unistra.poo.tp1.exo1;

public class Binome {
    // Donnees:
    protected double a, b, c, dis;

    // Methodes
    protected Binome(double pa, double pb, double pc, double pdis) {
        a = pa;
        b = pb;
        c = pc;
        dis = pdis;
    }

    public static Binome creation(double pa, double pb, double pc) {
        double delta;
        delta = pb * pb - 4.0 * pa * pc;
        if (delta < 0.0) {
            return new BinomeSol0(pa, pb, pc, delta);
        } else if (delta == 0.0) {
            return new BinomeSol1(pa, pb, pc, delta);
        } else {
            return new BinomeSol2(pa, pb, pc, delta);
        }
    }

    public void calculer_racine() {
        System.out.println("Erreur si ici !");
    }

    protected String getEquation() {
        return String.format("%e x^2 + %e x + %e", a,b,c);
    }

    public int nb_racine() {
        System.out.println("Erreur si ici !");
        return 0;
    }

    public double valeur_racine(int i) {
        System.out.println("Erreur si ici !");
        return 0.0;
    }

    public static void main(String[] args) {
        creation(1,1,1).calculer_racine();
    }
}