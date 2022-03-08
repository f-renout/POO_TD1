/**
 * Définition de la classe Test
 */

package fr.unistra.l2.td4.test;

import fr.unistra.l2.td4.controleur.PointControleur;
import fr.unistra.l2.td4.modele.PointModele;

/**
 *
 */
public class Test
{

    public static void main(String[] args)
    {
        PointModele modele = new PointModele(0, 0);
        PointControleur controleur = new PointControleur(modele);
        controleur.AfficherVues();
    }

}
