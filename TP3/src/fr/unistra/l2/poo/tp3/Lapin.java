package fr.unistra.l2.poo.tp3;

import java.util.Objects;

public class Lapin {
    int id;
    int age;
    float poids;
    float taille;

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public float getPoids() {
        return poids;
    }

    public float getTaille() {
        return taille;
    }

    public Lapin(int id, int age, float poids, float taille) {
        this.id = id;
        this.age = age;
        this.poids = poids;
        this.taille = taille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lapin lapin = (Lapin) o;
        return id == lapin.id && age == lapin.age && Float.compare(lapin.poids, poids) == 0 && Float.compare(lapin.taille, taille) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, poids, taille);
    }

    @Override
    public String toString() {
        return "Lapin{" +
                "id=" + id +
                ", age=" + age +
                ", poids=" + poids +
                ", taille=" + taille +
                '}';
    }
}
