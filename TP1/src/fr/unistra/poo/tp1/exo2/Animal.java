package fr.unistra.poo.tp1.exo2;

public class Animal {
    private String nom;

    public Animal(String nom) {
        this.nom = nom;
    }

    public Animal() {
        this(null);
    }

    public String getType() {
        String result = "je suis un animal";
        if (nom != null) {
            return result + " de nom " + nom + ".";
        }
        return result + ".";
    }
}
