package fr.unistra.poo.tp1.exo4;

public class LapinFact {
    public static final int MAX_LAPINS = 2;
    private static int nbTotal=0;

    private boolean vivant;

    public void passaALaCasserole(){
        if(vivant) {
            vivant = false;
            nbTotal--;
        }
    }

    private LapinFact(boolean vivant) {
        this.vivant = vivant;
        if(vivant){
            nbTotal++;
        }
    }

    public static LapinFact creationLapin(boolean vivant){
        if(vivant && nbTotal >= MAX_LAPINS){
            return null;
        }
        return new LapinFact(vivant);
    }

    @Override
    public String toString() {
        return "Je suis un lapin "+(vivant?"vivant ":"mort ")+"sur une population totale de "+nbTotal+" lapins vivants";
    }

    public static void main(String[] args) {
        LapinFact l1 = LapinFact.creationLapin(true);
        System.out.println(l1);
        l1.passaALaCasserole();
        System.out.println(l1);
        LapinFact l2 = LapinFact.creationLapin(true);
        System.out.println(l2);
        LapinFact l3 = LapinFact.creationLapin(false);
        System.out.println(l3);
        LapinFact l4 = LapinFact.creationLapin(true);
        System.out.println(l4);
        LapinFact l5 = LapinFact.creationLapin(true);
        System.out.println(l5);

    }
}
