package exo3.basique;

public class Hanoi {
    public void deplace(int nombre, Position plotDepart, Position plotArrive) {
        if(nombre==1){
            System.out.printf("on déplace 1 rondelle de %s à %s%n", plotDepart, plotArrive);
        }
        if(nombre>1) {
            Position intermetiaire = Position.restant(plotDepart, plotArrive);
            //on bouge les n-1 sur le plot intermediaire
            deplace(nombre-1, plotDepart, intermetiaire);
            //on deplace la base vers la destination
            deplace(1, plotDepart, plotArrive);
            //on deplace les n-1 de l'intermediaire à la destination
            deplace(nombre-1, intermetiaire, plotArrive);
        }
    }

    public static void main(String[] args) {
        Hanoi h = new Hanoi();
        h.deplace(3, Position.GAUCHE, Position.DROITE);
    }
}
