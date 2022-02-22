package morpion;

import java.util.ArrayList;
import java.util.List;

public class Grille {
    Jeton[][] matrice;

    public Grille() {
        matrice = new Jeton[3][3];
    }

    public void remplir(Coup coup) {
        if (matrice[coup.getX()][coup.getY()] != null) {
            throw new RuntimeException("coup invalide");
        }
        matrice[coup.getX()][coup.getY()] = coup.getJeton();
    }

    public boolean valide(Coup coup) {
        return matrice[coup.getX()][coup.getY()] == null;
    }

    public void annuler(Coup coup){
        matrice[coup.getX()][coup.getY()] = null;
    }

    @Override
    public String toString() {
        String toFormat = """
                ----------
                |%s|%s|%s|
                |%s|%s|%s|
                |%s|%s|%s|
                ----------
                """;
        List<String> params = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Jeton current = matrice[x][y];
                final var jetonValue = current == null ? " " : current.type().toString();
                params.add(jetonValue);
            }
        }
        return String.format(toFormat, params);
    }

    private Type horizontalWinner() {
        for (int colonne = 0; colonne < 3; colonne++) {
            if (match(matrice[0][colonne], matrice[1][colonne], matrice[2][colonne])) {
                return matrice[0][colonne].type();
            }
        }
        return null;
    }

    private Type verticalWinner() {
        for (int ligne = 0; ligne < 3; ligne++) {
            if (match(matrice[ligne][0], matrice[ligne][1], matrice[ligne][2])) {
                return matrice[ligne][0].type();
            }
        }
        return null;
    }

    private Type diagonalWinner() {
        if(match(matrice[0][0], matrice[1][1], matrice[2][2]) || match(matrice[2][0], matrice[1][1], matrice[0][2])){
            return matrice[1][1].type();
        }
        return null;
    }

    private boolean match(Jeton jeton1, Jeton jeton2, Jeton jeton3) {
        if (jeton1 == null || jeton2 == null || jeton3 == null) {
            return false;
        }
        return jeton1.type() == jeton2.type() && jeton2.type() == jeton3.type();
    }

    public Type gagnant() {
        Type res = verticalWinner();
        if(res==null){
            res = horizontalWinner();
        }
        if(res==null){
            res = diagonalWinner();
        }
        return res;
    }

    public boolean plein() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Jeton current = matrice[x][y];
                if (current == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Coup> coupsPossibles(Type type) {
        List<Coup> result = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Jeton current = matrice[x][y];
                if (current == null) {
                    result.add(new Coup(x, y, new Jeton(type)));
                }
            }
        }
        return result;
    }

    public boolean finie() {
        return gagnant() != null || plein();
    }
}
