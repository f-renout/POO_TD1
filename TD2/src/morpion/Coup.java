package morpion;

public class Coup {
    int x;
    int y;
    private final Jeton jeton;

    public Coup(int x, int y, Jeton jeton) {
        this.x = x;
        this.y = y;
        this.jeton = jeton;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Jeton getJeton() {
        return jeton;
    }

    @Override
    public String toString() {
        return "Coup{" + "x=" + x + ", y=" + y + ", jeton=" + jeton + '}';
    }
}
