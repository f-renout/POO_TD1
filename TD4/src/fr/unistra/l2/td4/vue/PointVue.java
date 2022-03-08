package fr.unistra.l2.td4.vue;
import fr.unistra.l2.td4.controleur.PointControleur;
import fr.unistra.l2.td4.modele.ChangementPositionListener;

import javax.swing.JFrame;

public abstract class PointVue extends JFrame implements ChangementPositionListener
{
    private final PointControleur controleur;
    
    public PointVue(PointControleur controleur)
    {
        super();
        this.controleur = controleur;
    }
    
    public final PointControleur getControleur() { return controleur; }
    

}
