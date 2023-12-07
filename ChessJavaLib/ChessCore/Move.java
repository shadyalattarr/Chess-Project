package ChessCore;

import ChessCore.Pieces.Piece;

public final class Move {
    private final Square fromSquare;
    private final Square toSquare;
    private final PawnPromotion pawnPromotion;
    private final Piece capturedPiece;//added this

    public Move(Square fromSquare, Square toSquare,Piece capturedPiece) {
        this(fromSquare, toSquare, PawnPromotion.None,capturedPiece);
        
    }

    public Move(Square fromSquare, Square toSquare, PawnPromotion pawnPromotion, Piece capturedPiece) {
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.pawnPromotion = pawnPromotion;
        
        this.capturedPiece = capturedPiece;
    }
    
    public Piece getCapturedPiece() {
        return this.capturedPiece;
    }
    public Square getFromSquare() {
        return fromSquare;
    }

    

    public Square getToSquare() {
        return toSquare;
    }

    public PawnPromotion getPawnPromotion() {
        return pawnPromotion;
    }

    public boolean isHorizontalMove() {
        return getDeltaX() != 0 && getDeltaY() == 0;
    }

    public boolean isVerticalMove() {
        return getDeltaX() == 0 && getDeltaY() != 0;
    }

    public boolean isDiagonalMove() {
        int deltaX = getAbsDeltaX();
        int deltaY = getAbsDeltaY();

        return deltaX == deltaY && deltaX != 0;
    }

    public int getDeltaX() {
        return getToSquare().getFile().getValue() - getFromSquare().getFile().getValue();
    }

    public int getDeltaY() {
        return getToSquare().getRank().getValue() - getFromSquare().getRank().getValue();
    }

    public int getAbsDeltaX() {
        return Math.abs(getDeltaX());
    }

    public int getAbsDeltaY() {
        return Math.abs(getDeltaY());
    }
}
