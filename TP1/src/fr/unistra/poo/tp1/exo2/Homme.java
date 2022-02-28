package fr.unistra.poo.tp1.exo2;

public class Homme extends Mammifere{
    public Homme(String nom) {
        super(nom);
    }

    public Homme() {
    }

    @Override
    public String getType() {
        return super.getType()+" Je suis un homme.";
    }

}
