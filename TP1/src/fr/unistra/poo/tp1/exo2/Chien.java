package fr.unistra.poo.tp1.exo2;

public class Chien extends Mammifere{
    public Chien(String nom) {
        super(nom);
    }

    public Chien() {
    }

    @Override
    public String getType() {
        return super.getType()+" Je suis un chien.";
    }
}
