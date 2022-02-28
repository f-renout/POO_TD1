package fr.unistra.poo.tp1.exo2;

public class Poisson extends Animal{
    public Poisson(String nom) {
        super(nom);
    }

    public Poisson() {
    }

    @Override
    public String getType() {
        return super.getType()+" Je suis un poisson.";
    }

}
