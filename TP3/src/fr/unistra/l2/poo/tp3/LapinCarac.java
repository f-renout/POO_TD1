package fr.unistra.l2.poo.tp3;

import java.util.Comparator;

public enum LapinCarac {
    AGE(Comparator.comparing(Lapin::getAge)),
    TAILLE(Comparator.comparing(Lapin::getTaille)),
    POIDS(Comparator.comparing(Lapin::getPoids));

    LapinCarac(Comparator<Lapin> comparator) {
        this.comparator = comparator;
    }

    Comparator<Lapin> comparator;

    public Comparator<Lapin> getComparator() {
        return comparator;
    }
}
