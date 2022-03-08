package fr.unistra.l2.td4.modele;

import javax.swing.event.EventListenerList;

public class PointModele {
    private int x;
    private int y;

    private final EventListenerList listeners;

    public PointModele(int x, int y) {
        this.x = x;
        this.y = y;
        listeners = new EventListenerList();
    }

    public PointModele() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        positionChangee();
    }

    public void addChangementPositionListener(ChangementPositionListener listener) {
        listeners.add(ChangementPositionListener.class, listener);
    }

    public void removeChangePositionListener(ChangementPositionListener listener) {
        listeners.remove(ChangementPositionListener.class, listener);
    }

    private void positionChangee() {
        ChangementPositionListener[] listeners = this.listeners.getListeners(ChangementPositionListener.class);
        for (ChangementPositionListener listener : listeners) {
            listener.positionChanged(new ChangementPositionEvent(this, x, y));
        }
    }

}
