package exo3.oo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Pique {
    private final Position position;

    public Position getPosition() {
        return position;
    }

    public Pique(Position position) {
        this.position = position;
        pieces = new Stack<>();
    }

    private final Stack<Piece> pieces;

    public void add(Piece piece){
        if(pieces.isEmpty()){
            pieces.push(piece);
        }else{
            Piece peek = pieces.peek();
            if(peek.getSize()>piece.getSize()){
                pieces.push(piece);
            }else{
                throw new InvalidMoveException(piece,peek,position);
            }
        }
    }

    public Piece getPiece(){
        return pieces.pop();
    }

    public List<String> print(int nbPiece){
        String empty = "|";
        String base = "=".repeat(2 * nbPiece - 1);
        List<String> display = new ArrayList<>();
        display.add(createLine(base, nbPiece, nbPiece));
        for(int i = 0 ; i < pieces.size(); i++){
            Piece piece = pieces.elementAt(i);
            display.add(createLine(piece.print(), piece.getSize(), nbPiece));
        }
        for(int i = pieces.size(); i<nbPiece; i++){
            display.add(createLine(empty,1,nbPiece));
        }
        Collections.reverse(display);
        return display;
    }

    private String createLine(String motif, int largeur, int max) {
        int additionnal = max-largeur+3; //3 = margin
        String external = " ".repeat(additionnal);
        return external+motif+external;
    }

    public static void main(String[] args) {
        Pique p= new Pique(Position.GAUCHE);
        p.add(new Piece(3));
        p.add(new Piece(2));
        p.add(new Piece(1));
        List<String> print = p.print(4);
        for (String s : print) {
            System.out.println(s);
        }
    }
}
