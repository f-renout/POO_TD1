package fr.unistra.l2.poo.tp3;

import java.util.*;
import java.util.stream.Collectors;

public class Ferme {
    List<Lapin> lapins;
    List<Lapin> lapinsParTaille;
    List<Lapin> lapinsParAge;
    List<Lapin> lapinsParPoids;

    public Ferme() {
        lapins = new ArrayList<>();
        lapinsParPoids = new ArrayList<>();
        lapinsParAge = new ArrayList<>();
        lapinsParTaille = new ArrayList<>();
    }

    public void addLapin(Lapin toAdd) {
        lapins.add(toAdd);
    }

    public void deleteLapin(Lapin toRemove) {
        lapins.remove(toRemove);
    }

    public void deleteLapin(int lapinId) {
        lapins = lapins.stream().filter(l -> l.getId() != lapinId).collect(Collectors.toList());
    }

    protected void generateParTaille() {
        lapinsParTaille = lapins.stream().sorted(LapinCarac.TAILLE.comparator).collect(Collectors.toList());
    }
    protected void generateParAge() {
        lapinsParAge = lapins.stream().sorted(LapinCarac.AGE.comparator).collect(Collectors.toList());
    }
    protected void generateParPoids() {
        lapinsParPoids = lapins.stream().sorted(LapinCarac.POIDS.comparator).collect(Collectors.toList());
    }

    public Iterator<Lapin> getLapinList(LapinCarac type, boolean descending) {
        List<Lapin> toDisplay;
        switch (type) {
            case AGE:
                toDisplay = lapinsParAge;
                break;
            case TAILLE:
                toDisplay = lapinsParTaille;
                break;
            case POIDS:
                toDisplay = lapinsParPoids;
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (descending) {
            List<Lapin> tmp = new ArrayList<>(toDisplay);
            Collections.reverse(tmp);
            toDisplay = tmp;
        }
        return toDisplay.iterator();
    }

}
