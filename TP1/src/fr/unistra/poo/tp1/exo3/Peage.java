package fr.unistra.poo.tp1.exo3;

public class Peage {
    int nbVoiture;
    int nbCamion;

    float totalCaisse;

    public void facture(Vehicule v) {
        if (v instanceof Camion) {
            factureCamion((Camion) v);
        } else {
            factureVoiture((Voiture) v);
        }
    }

    private void factureVoiture(Voiture v) {
        nbVoiture++;
        totalCaisse += 4;
    }

    private void factureCamion(Camion c) {
        nbCamion++;
        float cout = 7 * c.nbEssieu + 15 * c.poidsTotal;
        totalCaisse += cout;
    }

    public int nbVehicule() {
        return nbCamion + nbVoiture;
    }
}
