package fr.unistra.poo.tp1.exo3;

public class Camion extends Vehicule{
    int nbEssieu;
    float poidsTotal;

    public Camion(int nbEssieu, float poidsTotal) {
        this.nbEssieu = nbEssieu;
        this.poidsTotal = poidsTotal;
    }

    public int getNbEssieu() {
        return nbEssieu;
    }

    public float getPoidsTotal() {
        return poidsTotal;
    }
}
