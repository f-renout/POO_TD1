package fr.unistra.l2.td4.controleur;

import fr.unistra.l2.td4.modele.PointModele;
import fr.unistra.l2.td4.vue.VueGraphe;
import fr.unistra.l2.td4.vue.VueSlider;

public class PointControleur {
    private final VueGraphe vueGraphe;
    private final VueSlider vueSlider;

    private final PointModele pointModele;

    public PointControleur(PointModele pointMod) {
        pointModele = pointMod;
        int x = pointMod.getX();
        int y = pointMod.getY();
        vueGraphe = new VueGraphe(this, x, y);
        vueSlider = new VueSlider(this, x, y);
        pointModele.addChangementPositionListener(vueSlider);
        pointModele.addChangementPositionListener(vueGraphe);
    }

    public void AfficherVues() {
        vueGraphe.setVisible(true);
        vueSlider.setVisible(true);
    }

    public void notifyPositionUpdate(int x, int y) {
        pointModele.setPosition(x, y);
    }

}
