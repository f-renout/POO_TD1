package fr.unistra.l2.td4.modele;
import java.util.EventListener;

public interface ChangementPositionListener extends EventListener
{
	void positionChanged(ChangementPositionEvent event);
}
