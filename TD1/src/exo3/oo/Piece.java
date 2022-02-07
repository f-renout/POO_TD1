package exo3.oo;

public class Piece {
    private final int size;

    public Piece(int size) {
        this.size = size;
    }

    public int getSize(){
        return size;
    }

    public String print(){
        String motif = "*";
        return motif.repeat(2*size-1);
    }

    @Override
    public String toString() {
        return "Piece [taille=" + size+"]";
    }
}
