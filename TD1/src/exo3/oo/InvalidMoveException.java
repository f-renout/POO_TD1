package exo3.oo;

public class InvalidMoveException extends RuntimeException {
    private final Piece piece;
    private final Piece peek;
    private final Position position;

    public InvalidMoveException(Piece piece, Piece peek, Position position) {
        this.piece = piece;
        this.peek = peek;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Impossible de deplacer la piece " + piece +
                "sur la piece " + peek +
                " en position " + position;
    }
}
