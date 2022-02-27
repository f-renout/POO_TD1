package fr.unistra.poo.td3;

import java.util.ArrayList;
import java.util.List;

public class RLE {
    public static final int NOIR = 0x000000;
    public static final int BLANC = 0xFFFFFF;
    public static final int CTRL_POIDS_FORT = 0x8000;

    public static void afficheImage(int[] image) {
        for (int n : image) {
            if (n == NOIR)
                System.out.print("N");
            else if (n == BLANC)
                System.out.print("B");
        }
        System.out.println("");
    }

    public static void afficheImageHexa(int[] image) {
        for (int n : image) {
            System.out.printf("%06x", n);
        }
        System.out.println("");
    }

    public static int[] decompresser(String compression) {
        List<Integer> list = new ArrayList<>();
        while (compression.length() != 0) {
            // Controle.
            String controleString = compression.substring(0, 4);
            compression = compression.substring(4);
            int controle = Integer.parseInt(controleString, 16);
            //si le bit de poid fort est un 1
            if ((controle & CTRL_POIDS_FORT) != 0) {
                //nb = ctrl-bit poids fort (= on passe le bit de poids fort à 0)
                int nb = controle - CTRL_POIDS_FORT;
                String couleurString = compression.substring(0, 6);
                compression = compression.substring(6);
                int couleur = Integer.parseInt(couleurString, 16);
                for (int i = 0; i < nb; i++)
                    list.add(couleur);
            } else {
                //sinon on a X caracteres qui sont present directements
                for (int i = 0; i < controle; i++) {
                    String couleurString = compression.substring(0, 6);
                    compression = compression.substring(6);
                    int couleur = Integer.parseInt(couleurString, 16);
                    list.add(couleur);
                }
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();

    }

    public static String compresser(int[] image) {
        String compression = "";
        List<Integer> buffer = new ArrayList<>();
        int couleurRepetee = 0;
        int nbRepetitions = 0;
        for (int i = 0; i < image.length; i++) {
            int couleur = image[i];
            buffer.add(couleur);
            if (nbRepetitions == 0) {
                // Si début d'une série
                couleurRepetee = couleur;
                nbRepetitions++;
            } else if (couleurRepetee == couleur) {
                // Si on est dans la répétition
                nbRepetitions++;
                if (nbRepetitions == 3) {
                    int nb = buffer.size() - 3;
                    if (nb != 0) {
                        //on a dans notre buffer les alternance de caracteres  plus les reps à la fin
                        //on va stocker le nb de repet char dans le buffer qui ne sont pas des repetitions
                        String controleString = String.format("%04x", nb);
                        compression += controleString;
                        for (int j = 0; j < nb; j++) {
                            Integer colj = buffer.remove(0);
                            String couleurString = String.format("%06x", colj);
                            compression += couleurString;
                        }
                    }
                }
            } else if (nbRepetitions > 2) { // Si on change, et qu'on a eu une série avant.
                int controle = nbRepetitions | CTRL_POIDS_FORT;
                String controleString = String.format("%04x", controle);
                String couleurString = String.format("%06x", couleurRepetee);
                compression += controleString + couleurString;
                buffer.subList(0, nbRepetitions).clear();
                couleurRepetee = couleur;
                nbRepetitions = 1;
            } else {
                couleurRepetee = couleur;
            }
        }
        // On gère la fin :
        if (nbRepetitions < 3) {
            int nb = buffer.size();
            if (nb != 0) {
                String controleString = String.format("%04x", nb);
                compression += controleString;
                for (int j = 0; j < nb; j++) {
                    Integer colj = buffer.remove(0);
                    String couleurString = String.format("%06x", colj);
                    compression += couleurString;
                }
            }
        } else {
            int controle = nbRepetitions | CTRL_POIDS_FORT;
            String controleString = String.format("%04x", controle);
            String couleurString = String.format("%06x", couleurRepetee);
            compression += controleString + couleurString;
        }
        return compression;
    }

    public static void main(String[] args) {
        int[] tab = new int[]{
                BLANC, BLANC, BLANC, NOIR,
                NOIR, NOIR, NOIR, BLANC,
                NOIR, BLANC, NOIR, BLANC,
                BLANC, BLANC, BLANC, BLANC
        };
//        int[] tab = new int[]{
//                BLANC, BLANC, BLANC, BLANC, BLANC, BLANC
//        };

        afficheImage(tab);
        afficheImageHexa(tab);
        String compr = compresser(tab);
        System.out.println(compr);
        int[] image = decompresser(compr);
        afficheImageHexa(image);
        afficheImage(image);

    }
}
