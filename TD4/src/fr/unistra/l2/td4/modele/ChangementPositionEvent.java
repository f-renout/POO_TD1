package fr.unistra.l2.td4.modele;

import java.util.EventObject;

public class ChangementPositionEvent extends EventObject {
    private final int nouveauX;
    private final int nouveauY;

    public ChangementPositionEvent(Object source, int nouvX, int nouvY) {
        super(source);
        nouveauX = nouvX;
        nouveauY = nouvY;
    }

    public int getNouveauX() {
        return nouveauX;
    }

    public int getNouveauY() {
        return nouveauY;
    }

}