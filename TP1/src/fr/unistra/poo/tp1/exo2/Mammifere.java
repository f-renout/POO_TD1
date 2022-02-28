package fr.unistra.poo.tp1.exo2;

public class Mammifere extends Animal{
    public Mammifere(String nom) {
        super(nom);
    }

    public Mammifere() {
    }

    @Override
    public String getType() {
        return super.getType()+" Je suis un mammifere.";
    }
}
