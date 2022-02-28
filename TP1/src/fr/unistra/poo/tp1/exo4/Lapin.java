package fr.unistra.poo.tp1.exo4;

public class Lapin {
    private static int nbTotal=0;

    private boolean vivant;

    public void passaALaCasserole(){
        if(vivant) {
            vivant = false;
            nbTotal--;
        }
    }

    public Lapin(boolean vivant) {
        this.vivant = vivant;
        if(vivant && nbTotal>=50){
            throw new IllegalArgumentException("Impossible de creer plus de 50 lapins");
        }
    }
    
}
