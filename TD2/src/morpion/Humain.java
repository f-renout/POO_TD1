package morpion;

import java.util.Scanner;

public class Humain extends Joueur {
    public Humain(Type x, Grille grille) {super(x, grille);}

    @Override
    protected Coup prochainCoup() {
        boolean valide;
        int x;
        int y;
        Coup result;
        do {
            x = readCoord("entrer la ligne");
            y = readCoord("entrer la colonne");
            result = new Coup(x, y, getJeton());
            valide = grille.isValide(result);
        } while (!valide);
        return result;
    }

    private int readCoord(String text) {
        int res = -1;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.print(text + " (0, 1 ou 2): ");
                int num = sc.nextInt();
                if (num >= 0 && num <= 2) {
                    res = num;
                }
            } catch (Exception e) {
                System.out.println("Merci de saisir un nombre entre 0 et 2");
            }
        } while (res == -1);
        return res;
    }
}
