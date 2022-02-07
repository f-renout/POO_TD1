package exo3.basique;

import java.util.*;

public enum Position {
    GAUCHE, CENTRE, DROITE;

    public static Position restant(Position p1, Position p2){
        return Arrays.stream(Position.values())
                .filter(p->p!=p1)
                .filter(p->p!=p2)
                .findFirst()
                .get();
    }
}
