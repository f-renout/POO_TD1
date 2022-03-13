package fr.unistra.l2.poo.tp3;

import javax.swing.*;
import java.util.Random;
import java.util.function.Consumer;

public class LapinProgressBarGenerator extends SwingWorker<Void, Void> {
    private Ferme ferme;
    private int amount;
    private boolean indexingOnly;
    private OnGeneratorEndListener endListener;

    public LapinProgressBarGenerator(Ferme ferme, int amount, boolean indexingOnly,
                        OnGeneratorEndListener endListner) {
        this.ferme = ferme;
        this.amount = amount;
        this.indexingOnly = indexingOnly;
        this.endListener = endListner;
    }

    // Task Executed in background thread.
    @Override
    public Void doInBackground() {
        double progress = 0;
        //Initialize progress property.
        setProgress(0);

        Random rand = new Random();
        double progress_step;
        if (!indexingOnly) {
            progress_step = 50 / amount;

            for (int i = 1; i <= amount; i++) {
                int id = i;
                int age = (int) (1 + rand.nextDouble() * (15.0 - 1.0));
                float taille = (float) (0.2 + rand.nextDouble() * (20.0 - 0.2));
                float poids = (float) (5.0 + rand.nextDouble() * (45.0 - 5.0));
                Lapin l = new Lapin(id, age, taille, poids);
                ferme.addLapin(l);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress += progress_step;
                setProgress(Math.min((int) progress, 100));
            }
            progress_step = 17.0;
        } else {
            progress_step = 34.0;
        }

        // Launch the indexing
        buildIndex(Ferme::generateParAge);
        progress += progress_step;
        setProgress(Math.min((int)progress, 100));
        buildIndex(Ferme::generateParTaille);
        progress += progress_step;
        setProgress(Math.min((int)progress, 100));
        buildIndex(Ferme::generateParPoids);
        setProgress(100);
        return null;
    }

    private void buildIndex(Consumer<Ferme> generateParAge) {
        generateParAge.accept(ferme);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void done() {
        setProgress(0);
        if (endListener != null)
            endListener.onGeneratorEnd();
    }
}
