package fr.unistra.poo.td3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class RLE {
    public static final int NOIR = 0x000000;
    public static final int BLANC = 0xFFFFFF;
    public static final int CTRL_POIDS_FORT = 0x8000;

    public static void afficheImage(int[] image) {
        for (int n : image) {
            if (n == NOIR) {
                System.out.print("N");
            } else if (n == BLANC) {
                System.out.print("B");
            }
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
            // nos infos de controles sont sur 4 lettres (2 octets = 0000->ffff)
            //et on les converti en nombre
            String controleString = compression.substring(0, 4);
            int controle = Integer.parseInt(controleString, 16);
            //on retire les bits de controles de la chaine de compression
            //ils ont été déjà lus et interpretes
            compression = compression.substring(4);

            //si le cntrole a un bit de poids fort à 1
            if ((controle & CTRL_POIDS_FORT) != 0) {
                //on calcule le nombre d'occurence en remplacant le bit de poids fort pas un 0
                int nb = controle - CTRL_POIDS_FORT;
                //le pixel qui a été compressée est sur 6 lettres (3 couleur chaque etant sur 1 octer donc 00-ff)
                String couleurString = compression.substring(0, 6);
                //comme à chaque fois, on retire de la chaine de compression l'information qu'on vient de lire
                compression = compression.substring(6);
                //on converti le pixel en chiffre et on la rajoute le bon nombre de fois (si on a lu 10 blancs, on rajoute 10 fois blancs dans la liste)
                int couleur = Integer.parseInt(couleurString, 16);
                for (int i = 0; i < nb; i++) {
                    list.add(couleur);
                }
            } else {
                //bit de poids fort à 0 => controle = le nombre de pixels qui sont non compressées
                //sinon on a X caracteres qui sont present directements
                for (int i = 0; i < controle; i++) {
                    //donc on va repeter X fois l'opération suivante:

                    //lecture du pixel
                    String couleurString = compression.substring(0, 6);
                    //on retire ce q'on a lu de la chaine de compression
                    compression = compression.substring(6);
                    //on transforme ce qu'on a lu en nombre
                    int couleur = Integer.parseInt(couleurString, 16);
                    //on le rajoute dans la liste
                    list.add(couleur);
                }
            }
        }

        //on va ensuite convertir la liste en tableau
        return list.stream().mapToInt(Integer::intValue).toArray();

    }

    public static String compresser(int[] image) {
        //la chaine qui contiendra le resultat de la compression
        StringBuilder compression = new StringBuilder();
        //le buffer qui ca tenir la liste des couleurs en cours d'anlyse
        Queue<Integer> buffer = new LinkedList<>();
        //quelle est la derniere couleur lue
        int couleurRepetee = 0;
        //compteur sur combien de fois la derniere couleur est repetée
        int nbRepetitions = 0;
        //pour chaque pixel de notre image
        for (int i = 0; i < image.length; i++) {
            //on lit le pixel et on le rajoute au buffer
            int pixel = image[i];
            buffer.add(pixel);

            // Si début d'une série
            if (nbRepetitions == 0) {
                couleurRepetee = pixel;
                nbRepetitions++;
            } else if (couleurRepetee == pixel) {
                // Si on est dans la répétition
                nbRepetitions++;
                //on "démarre" la compression
                if (nbRepetitions == 3) {
                    //nb represente le nombre de pixels qui auraient pu etre bufferises avant qu'on compresse
                    int nb = buffer.size() - 3;
                    if (nb != 0) {
                        //par exemple si on a  BNBNBBBB -> dans le buffer on aura [B,N,B,N,B,B,B] => on doit "sauver"  [B,N,B,N] qui sont des pixels non compressé
                        //avant de continuer à "stocker" la suite de notre buffer

                        //on stocke les octests de controles = le nb de pixels non compressés qui vont etre inserés
                        stockePixelsNonCompresses(nb, compression, buffer);
                    }
                }
                //on etait dans une repetition induisant une compression et on change par ex BBBBN
            } else if (nbRepetitions > 2) {
                stockePixelCompresses(nbRepetitions, couleurRepetee, compression);
                //on clear notre buffer (utilisation de stream, c'est la même chose que faire
                //            for(int idx = 0 ; idx < nbRepetitions ; idx++){
                //                    buffer.poll();
                //                }
                IntStream.rangeClosed(1, nbRepetitions).forEach(idx -> buffer.poll());
                //et on traite le pixel courant => la couleur repetée est la nouvelle couleur lue et le nb de repet est 1
                couleurRepetee = pixel;
                nbRepetitions = 1;
            } else {
                //sinon on est dans un cas ou la repet c'est 1 ou 2
                //on met juste la couleur repetée
                couleurRepetee = pixel;
            }
        }
        // On gère la fin (ce qui reste dans notre buffer une fois qu'on a fini de lire notre image)
        traiterBufferEnFinDeLecture(compression, buffer, couleurRepetee, nbRepetitions);
        return compression.toString();
    }

    private static void traiterBufferEnFinDeLecture(StringBuilder compression, Queue<Integer> buffer, int couleurRepetee, int nbRepetitions) {
        //soit on a moins de 3 repet et on va stocket ce qu'on a dans le buffer sans compression
        if (nbRepetitions < 3) {
            int nb = buffer.size();
            if (nb != 0) {
                stockePixelsNonCompresses(nb, compression, buffer);
            }
            //soit on a 3 repets ou plus et la on a un pixel "compressé" dans notre buffer donc on le stocke
        } else {
            stockePixelCompresses(nbRepetitions, couleurRepetee, compression);
        }
    }

    private static void stockePixelCompresses(int nbRepetitions, int couleurRepetee, StringBuilder compression) {
        //on définit nos octets de controle
        int controle = nbRepetitions | CTRL_POIDS_FORT;
        String controleString = format("%04x", controle);
        //et le pixel qui est repetée
        String couleurString = format("%06x", couleurRepetee);
        //on rajoute à notre chaine les octeets de controle et le pixel repeté
        compression.append(controleString).append(couleurString);
    }

    private static void stockePixelsNonCompresses(int nb, StringBuilder compression, Queue<Integer> buffer) {
        String controleString = format("%04x", nb);
        compression.append(controleString);
        for (int j = 0; j < nb; j++) {
            Integer colj = buffer.poll();
            String couleurString = format("%06x", colj);
            compression.append(couleurString);
        }
    }

    public static void main(String[] args) {
        int[] tab = new int[]{BLANC, BLANC, BLANC, NOIR, NOIR, NOIR, NOIR, BLANC, NOIR, BLANC, NOIR, BLANC, BLANC, BLANC, BLANC, BLANC};

        afficheImage(tab);
        afficheImageHexa(tab);
        String compr = compresser(tab);
        System.out.println(compr);
        int[] image = decompresser(compr);
        afficheImageHexa(image);
        afficheImage(image);

    }
}
