package fr.unistra.l2.td4.vue;
import fr.unistra.l2.td4.controleur.PointControleur;
import fr.unistra.l2.td4.modele.ChangementPositionEvent;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VueSlider extends PointVue implements ChangeListener
{
    private final JScrollBar scrollX;
    private final JScrollBar scrollY;
    
    public VueSlider(PointControleur controleur, int x, int y)
    {
        super(controleur);
        scrollX = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -5, 5);
        scrollY = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -5, 5);
        scrollX.getModel().addChangeListener(this);
        scrollY.getModel().addChangeListener(this);
        JPanel content = new JPanel();
        GridLayout grid = new GridLayout(2,1);
        content.setLayout(grid);
        content.add(scrollX);
        content.add(scrollY);
        setContentPane(content);
        pack();
        setSize(200, 200);
    }


    @Override
    public void positionChanged(ChangementPositionEvent event)
    {
        scrollX.setValue(event.getNouveauX());
        scrollY.setValue(event.getNouveauY());
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        getControleur().notifyPositionUpdate(scrollX.getValue(), scrollY.getValue());
    }


}
