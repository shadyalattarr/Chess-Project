package ChessCore;

import ChessCore.Pieces.*;

public class PieceFactory {
    public static Piece createPiece(PieceType type, Player player) {
        switch (type) {
            case PAWN:
                return new Pawn(player);
            case ROOK:
                return new Rook(player);
            case KNIGHT:
                return new Knight(player);
            case BISHOP:
                return new Bishop(player);
            case QUEEN:
                return new Queen(player);
            case KING:
                return new King(player);
            default:
                return null;
        }
    }
}
