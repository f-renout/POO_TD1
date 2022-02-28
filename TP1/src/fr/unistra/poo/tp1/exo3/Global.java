package fr.unistra.poo.tp1.exo3;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static void main(String[] args) {
        List<Vehicule> vehicules = new ArrayList<>();
        Peage p = new Peage();
        vehicules.add(new Voiture());
        vehicules.add(new Voiture());
        vehicules.add(new Voiture());
        vehicules.add(new Voiture());
        vehicules.add(new Voiture());
        vehicules.add(new Camion(4,15));
        vehicules.add(new Camion(2,6));
        vehicules.add(new Camion(2,9));
        vehicules.add(new Camion(6,25));

        for (Vehicule vehicule : vehicules) {
            p.facture(vehicule);
        }

        System.out.printf("Nombre de camions: %d%n",p.nbCamion);
        System.out.printf("Nombre de voitures: %d%n",p.nbVoiture);
        System.out.printf("Euros en caisse: %f%n",p.totalCaisse);
    }
}