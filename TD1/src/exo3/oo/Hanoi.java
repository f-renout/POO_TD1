package exo3.oo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hanoi {

    private final int nbPiece;

    private final Pique gauche;
    private final Pique centre;
    private final Pique droite;

    private final Map<Position, Pique> map;

    public Hanoi(int nbPiece) {
        this.nbPiece = nbPiece;
        gauche = new Pique(Position.GAUCHE);
        centre = new Pique(Position.CENTRE);
        droite = new Pique(Position.DROITE);

        map = Stream.of(gauche,centre,droite).collect(Collectors.toMap(Pique::getPosition, Function.identity()));
        for(int i= nbPiece ; i >0 ; i--){
            gauche.add(new Piece(i));
        }
    }

    public void deplace(int nombre, Position plotDepart, Position plotArrive) {
        if(nombre==1){
            System.out.printf("on déplace 1 rondelle de %s à %s%n", plotDepart, plotArrive);
            Piece piece = map.get(plotDepart).getPiece();
            map.get(plotArrive).add(piece);
            print();
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

    private void print() {
        List<String> printGauche = gauche.print(nbPiece);
        List<String> printCentre = centre.print(nbPiece);
        List<String> printDroite = droite.print(nbPiece);
        for(int i = 0 ; i < printGauche.size() ; i++){
            System.out.println(printGauche.get(i)+printCentre.get(i)+printDroite.get(i));
        }
    }

    public static void main(String[] args) {
        Hanoi h = new Hanoi(3);
        h.print();
        h.deplace(3, Position.GAUCHE, Position.DROITE);
    }
}
