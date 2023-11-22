package com.company.chesscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ChessGame {
    Board board;
    int player;
    boolean gameOn;

    public Board getBoard() {
        return board;
    }

    ChessGame() {
        this.board = new Board();
        board.initialiseBoard();
        this.player = 1;// white starts
        this.gameOn = true;

    }

    public Piece promotionPiece(int toPosition, char stringPiece) {
        // lazem setpositio b setpiece
        Piece newPiece;
        switch (stringPiece) {
            case 'K':
                newPiece = new Knight(player, this.board);
                System.out.println("promoted to knight");
                break;
            case 'B':
                newPiece = new Bishop(player, this.board);
                System.out.println("promoted to bishop");
                break;
            case 'R':
                newPiece = new Rook(player, this.board);
                System.out.println("promoted to rook");
                break;
            case 'Q':
                newPiece = new Queen(player, this.board);
                System.out.println("promoted to queen");
                break;
            default:
                System.out.println("no promotion lmfao");
                newPiece = new EmptySquare();// if we sent an empty square then no promo
                break;
        }

        return newPiece;
    }

    public ArrayList<String> getMovesFromFile(String file) {
        ArrayList<String> inputMoves = new ArrayList<String>();
        File fileName = new File(file);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                inputMoves.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("filenotfound");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputMoves;
    }

    public boolean isPieceMyColor(int fromPosition) {
        return board.getBoardSquare(fromPosition).getPiece().getColorNum() == player;// player 0 black and 1 white..
        // we check before this that we are not accessing an empty square
    }

    public boolean isLoneKing(int color) {
        if (board.piecesArray.get(color).size() == 1) {
            return true;
        }
        return false;
    }

    public boolean isKingAndBishop(int color) {
        if (board.piecesArray.get(color).size() == 2) {
            if (board.piecesArray.get(color).get(0) instanceof Bishop
                    || board.piecesArray.get(color).get(1) instanceof Bishop)
                return true;
        }
        return false;
    }

    public boolean isKingAndKnight(int color) {
        if (board.piecesArray.get(color).size() == 2) {
            if (board.piecesArray.get(color).get(0) instanceof Knight
                    || board.piecesArray.get(color).get(1) instanceof Knight)
                return true;
        }
        return false;
    }

    public boolean isThisColorInsufficient(int color) {
        return isKingAndBishop(color) || isLoneKing(color) || isKingAndKnight(color);
    }

    public boolean insufficientMaterial() {
        return isThisColorInsufficient(0) && isThisColorInsufficient(1);
    }

    public void startGame() {
        boolean kingInCheck[] = { false, false };
        int otherPlayer;
        ArrayList<String> inputMoves = getMovesFromFile("ChessGame.txt");
        // printArrayList(inputMoves);
        int fromPosition, toPosition;
        char promoteTo;
        int fromCol, fromRow, toCol, toRow;
        String fromTo[] = new String[3];
        Piece capturedPiece;
        for (String move : inputMoves) {
            // need to split each move to from and to
            // System.out.println(player);
            // board.printBoard();
            // System.out.println(move);
            try {
                if (gameOn) {
                    fromTo = move.split(",");
                    fromRow = board.getRowNumber(fromTo[0].charAt(1));
                    fromCol = board.getColumnNumber(fromTo[0].charAt(0));
                    toRow = board.getRowNumber(fromTo[1].charAt(1));
                    toCol = board.getColumnNumber(fromTo[1].charAt(0));
                    try {
                        promoteTo = fromTo[2].charAt(0);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        promoteTo = 'n';// no promo
                    }
                    if (board.isIllegal(toRow, toCol) || board.isIllegal(fromRow, fromCol))
                        throw new IllegalArgumentException();
                    fromPosition = board.getIntPosition(fromRow, fromCol);
                    toPosition = board.getIntPosition(toRow, toCol);
                    if (promoteTo != 'n' && !(board.getBoardSquare(fromPosition).getPiece() instanceof Pawn))// promo
                    {
                        throw new InvalidMove();
                    }
                    // we have from and to and they are not illegal
                    // are we trying to access an empty square?
                    if (!isPieceMyColor(fromPosition)
                            || board.getBoardSquare(fromPosition).getPiece().getColorNum() == -1)// if empty or not my
                                                                                                 // color
                    {
                        System.out.println(player);
                        System.out.println(board.getBoardSquare(fromPosition).getPiece().getColorNum());
                        throw new NotAccessiblePiece();
                    } else {// ur color

                        // check castling
                        otherPlayer = player == 1 ? 0 : 1;
                        Piece fromPiece, toPiece;
                        // System.out.println();

                        fromPiece = board.getBoardSquare(fromPosition).getPiece();
                        toPiece = board.getBoardSquare(toPosition).getPiece();

                        // System.out.println(fromPosition + " " + toPosition);
                        // System.out.println(kingInCheck[player]);
                        // System.out.println("king");
                        // board.getBoardSquare(11).getPiece()
                        if (castle(fromPosition, toPosition)) {
                            System.out.println("Castle");
                            // movement done

                            // continue;

                            // need to check if ath is clear
                            // need to check if king is safe after this move
                        } // else if enpassant
                          // is it enpassant
                        else if (fromPiece instanceof Pawn) {
                            //System.out.println(fromPosition + " " + toPosition);
                            if (!fromPiece.isKingSafeFromMyMove(fromPosition, toPosition)) {
                                System.out.println("move not vald frmo ours");
                                throw new InvalidMove();
                            } else {
                                Pawn pawn = (Pawn) fromPiece;
                               // System.out.println(fromPosition + " " + toPosition);
                                if (pawn.captureEnPassant(fromPosition, toPosition)) {
                                    System.out.println("En Passant");
                                    System.out.println("Captured Pawn");
                                } else if (this.isValidMove(fromPosition, toPosition)) {
                                    boolean h = pawn.isEnPassantCandidate(fromPosition, toPosition);
                                   // System.out.println(h);
                                    capturedPiece = board.movePiece(fromPosition, toPosition);
                                    board.capture(capturedPiece);// for array list
                                    // if(capturedPiece instanceof Pawn)
                                    // {
                                    // System.out.println("Pawn");
                                    // }
                                    // movement done

                                    if (capturedPiece.getColorNum() == otherPlayer)// either equals other color or -1
                                        System.out.println("Captured " + getPieceString(capturedPiece));
                                    // now need to check if king in check
                                } else {
                                    System.out.println("move not vald frmo ours");
                                    throw new InvalidMove();
                                }
                            }
                        }

                        else {

                            // System.out.println(board.getBoardSquare(toPosition).getPiece().toString());
                            // System.out.println("not castle");
                            if (this.isValidMove(fromPosition, toPosition))// checks if it is a correct move and king is
                                                                           // safe
                            {// checks if it is a normal move no shenanigans (maybe pawn promo)
                             // if a valid normal move then
                             // System.out.println(board.getBoardSquare(toPosition).getPiece().toString());
                             // System.out.println("not castle");

                                capturedPiece = board.movePiece(fromPosition, toPosition);
                                board.capture(capturedPiece);// for array list
                                // if(capturedPiece instanceof Pawn)
                                // {
                                // System.out.println("Pawn");
                                // }
                                // movement done

                                if (capturedPiece.getColorNum() == otherPlayer)// either equals other color or -1
                                    System.out.println("Captured " + getPieceString(capturedPiece));
                                // now need to check if king in check
                            } else {
                                System.out.println("move not vald frmo ours");
                                throw new InvalidMove();
                            }
                        }
                        // did a king become in check
                        // if you white your move makes the other dude in check
                        kingInCheck[otherPlayer] = !board.isMyKingSafe(board.kingPosition[otherPlayer], otherPlayer);
                        if (kingInCheck[otherPlayer]) {
                            if (!board.canImakeAnyMoves(otherPlayer)) {
                                if (otherPlayer == 0) {
                                    System.out.println("White Won");
                                    gameOn = false;
                                } else {
                                    System.out.println("Black Won");
                                    gameOn = false;
                                }
                            } else {
                                if (otherPlayer == 0)
                                    System.out.println("Black in check");
                                else
                                    System.out.println("White in check");
                            }
                        } else {// king not in check

                            if (!board.canImakeAnyMoves(otherPlayer)) {// stalemate
                                gameOn = false;
                                System.out.println("Stalemate");
                            }
                            // i can move and am not in check ez
                            // insfficient material to continue?
                            if (insufficientMaterial()) {
                                System.out.println("Insufficient material");
                                gameOn = false;
                            }

                        }
                        if (player == 0)
                            player = 1;
                        else
                            player = 0;
                    }
                } else {// game offline
                    System.out.println("Game already ended");
                }
            } catch (InvalidMove e) {
                System.out.println("Invalid moveeeeee");
            } catch (NotAccessiblePiece e) {
                // invalid move
                System.out.println("Invalid move?");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move");
                // illegal inout
            }

        }
    }

    public String getPieceString(Piece piece) {
        // return piece name
        if (piece instanceof Pawn)
            return "Pawn";
        else if (piece instanceof Rook)
            return "Rook";
        else if (piece instanceof Knight)
            return "Knight";
        else if (piece instanceof Bishop)
            return "Bishop";
        else if (piece instanceof Queen)
            return "Queen";
        else if (piece instanceof King)
            return "King";
        else
            return "Empty Square";
    }

    public boolean isValidMove(int fromPosition, int toPosition) {

        Piece fromPiece = board.getBoardSquare(fromPosition).getPiece();
        // System.out.println(fromPiece.toString());
        // System.out.println(fromPosition + " "+ toPosition);
        // System.out.println(board.getBoardSquare(fromPosition).getPiece().toString());
        // System.out.println(fromPiece.isKingSafeFromMyMove(fromPosition, toPosition));
        // System.out.println(fromPiece.isValidMove(fromPosition, toPosition));
        

        return fromPiece.isValidMove(fromPosition, toPosition) && fromPiece.checkPath(fromPosition, toPosition)
                && fromPiece.isKingSafeFromMyMove(fromPosition, toPosition);

    }

    // before i call this method isValied and checkPath should be called
    public boolean promotePawn(int myPosition, int nextPosition, Piece newPiece) {
        BoardSquare promotionSquare = board.getBoardSquare(nextPosition);
        Piece oldPiece = board.getBoardSquare(myPosition).getPiece();

        if (oldPiece instanceof Pawn) {
            int row = nextPosition / 8;

            // Check if the pawn reached the end of the board
            if (oldPiece.getColor().equals("White") && row == 0) {
                promotionSquare.setPiece(newPiece);
                return true;
            } else if (oldPiece.getColor().equals("Black") && row == 7) {
                promotionSquare.setPiece(newPiece);
                return true;
            }
        }
        return false;
    }

    public int getRookPostion(int kingPosition, int nextPosition) {
        if (kingPosition == 60 && nextPosition == 62)
            return 63;
        else if (kingPosition == 60 && nextPosition == 58)
            return 56;
        else if (kingPosition == 4 && nextPosition == 6)
            return 7;
        else if (kingPosition == 4 && nextPosition == 2)
            return 0;
        else
            return -1;
    }

    // just needs to check isKingSafe
    // first move dose not work
    public boolean isValidCastle(int myPosition, int nextPosition) {
        nextPosition = getRookPostion(myPosition, nextPosition);
        // System.out.println("helllo");
        // System.out.println(nextPosition + " " + nextPosition);
        if (nextPosition == -1)
            return false;
        // System.out.println("helllo");
        BoardSquare kingSquare = board.getBoardSquare(myPosition);
        BoardSquare rookSquare = board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        // System.out.println(king instanceof King);
        if (king instanceof King && rook instanceof Rook && board.isMyKingSafe(myPosition, king.getColorNum())) {
            // System.out.println("king and rook");
            if (king.getColor().equals(rook.getColor())) {
                // System.out.println("same color");
                if (king.getColor().equals("White")) {
                    // System.out.println("white");

                    if (myPosition == 60 && nextPosition == 63) {
                        // System.out.println("right place");
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            // System.out.println("right move");
                            if (board.getBoardSquare(61).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(62).getPiece().getColor().equals("Empty Square")) {
                                // System.out.println("nice");
                                return true;
                            }
                        }
                    } else if (myPosition == 60 && nextPosition == 56) {
                        // System.out.println("right place");
                        // System.out.println(kingSquare.getPiece().firstMove);
                        // System.out.println(rookSquare.getPiece().firstMove);
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (board.getBoardSquare(59).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(58).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(57).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                } else {
                    if (myPosition == 4 && nextPosition == 7) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (board.getBoardSquare(5).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(6).getPiece().getColor().equals("Empty Square")) {
                                return true;
                            }
                        }
                    } else if (myPosition == 4 && nextPosition == 0) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (board.getBoardSquare(3).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(2).getPiece().getColor().equals("Empty Square")
                                    && board.getBoardSquare(1).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean whiteRookRightCastle(int myPosition) {
        Piece king = board.getBoardSquare(myPosition).getPiece();
        boolean kingAt61 = king.isKingSafeFromMyMove(myPosition, 61);
        boolean kingAt62 = king.isKingSafeFromMyMove(myPosition, 62);
        if (kingAt61 && kingAt62)
            return true;
        return false;
    }

    public boolean whiteRookLeftCastle(int myPosition) {
        Piece king = board.getBoardSquare(myPosition).getPiece();
        boolean kingAt59 = king.isKingSafeFromMyMove(myPosition, 59);
        boolean kingAt58 = king.isKingSafeFromMyMove(myPosition, 58);
        boolean kingAt57 = king.isKingSafeFromMyMove(myPosition, 57);
        if (kingAt59 && kingAt58 && kingAt57)
            return true;
        return false;
    }

    public boolean blackRookRightCastle(int myPosition) {
        Piece king = board.getBoardSquare(myPosition).getPiece();
        boolean kingAt5 = king.isKingSafeFromMyMove(myPosition, 5);
        boolean kingAt6 = king.isKingSafeFromMyMove(myPosition, 6);
        if (kingAt5 && kingAt6)
            return true;
        return false;
    }

    public boolean blackRookLeftCastle(int myPosition) {
        Piece king = board.getBoardSquare(myPosition).getPiece();
        boolean kingAt3 = king.isKingSafeFromMyMove(myPosition, 3);
        boolean kingAt2 = king.isKingSafeFromMyMove(myPosition, 2);
        boolean kingAt1 = king.isKingSafeFromMyMove(myPosition, 1);
        if (kingAt3 && kingAt2 && kingAt1)
            return true;
        return false;
    }

    // i should to do it in getallvalidmoves king
    public boolean castle(int myPosition, int nextPosition) {
        if (!isValidCastle(myPosition, nextPosition))
            return false;
        nextPosition = getRookPostion(myPosition, nextPosition);
        BoardSquare kingSquare = board.getBoardSquare(myPosition);
        BoardSquare rookSquare = board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        if (myPosition == 60 && nextPosition == 63 && whiteRookRightCastle(myPosition)) {
            board.getBoardSquare(61).setPiece(rook);
            board.getBoardSquare(62).setPiece(king);
            board.getBoardSquare(60).setPiece(new EmptySquare());
            board.getBoardSquare(63).setPiece(new EmptySquare());
            king.firstMove = false;
            rook.firstMove = false;
            board.kingPosition[king.getColorNum()] = 62;

            return true;
        } else if (myPosition == 60 && nextPosition == 56 && whiteRookLeftCastle(myPosition)) {
            board.getBoardSquare(59).setPiece(rook);
            board.getBoardSquare(58).setPiece(king);
            board.getBoardSquare(60).setPiece(new EmptySquare());
            board.getBoardSquare(56).setPiece(new EmptySquare());
            board.kingPosition[king.getColorNum()] = 58;
            king.firstMove = false;
            rook.firstMove = false;
            return true;
        } else if (myPosition == 4 && nextPosition == 7 && blackRookRightCastle(myPosition)) {
            board.getBoardSquare(5).setPiece(rook);
            board.getBoardSquare(6).setPiece(king);
            board.getBoardSquare(4).setPiece(new EmptySquare());
            board.getBoardSquare(7).setPiece(new EmptySquare());
            board.kingPosition[king.getColorNum()] = 6;
            king.firstMove = false;
            rook.firstMove = false;
            return true;
        } else if (myPosition == 4 && nextPosition == 0 && blackRookLeftCastle(myPosition)) {
            board.getBoardSquare(3).setPiece(rook);
            board.getBoardSquare(2).setPiece(king);
            board.getBoardSquare(4).setPiece(new EmptySquare());
            board.getBoardSquare(0).setPiece(new EmptySquare());
            board.kingPosition[king.getColorNum()] = 2;
            king.firstMove = false;
            rook.firstMove = false;
            return true;
        } else
            return false;

    }

    /*
     * public static void main(String[] args) {
     * 
     * System.out.println("----Chess Board----");
     * Board testBoard = new Board();
     * ArrayList<String> validMoves = new ArrayList<String>();
     * // test castle
     * // Piece whiteKing = testboard.squares[7][4].getPiece();
     * // System.out.println(whiteKing.firstMove + "he");
     * // Piece whiteRook = testboard.squares[7][0].getPiece();
     * // promotion test
     * // board.getBoardSquare(8).setPiece(testboard.getBoardSquare(50).getPiece());
     * // board.getBoardSquare(0).setPiece(new EmptySquare());
     * 
     * // System.out.println(promotePawn(8, 0, new Queen(1)));
     * System.out.println(testboard.getBoardSquare(8).getPiece().firstMove);
     * 
     * // testboard.getBoardSquare(57).setPiece(new EmptySquare());
     * // testboard.getBoardSquare(58).setPiece(new EmptySquare());
     * // testboard.getBoardSquare(59).setPiece(new EmptySquare());
     * testboard.getBoardSquare(57).setPiece(new EmptySquare());
     * testboard.getBoardSquare(58).setPiece(new EmptySquare());
     * testboard.getBoardSquare(59).setPiece(new EmptySquare());
     * for (int i = 0; i < 8; i++) {
     * for (int j = 0; j < 8; j++) {
     * System.out.print(8 * i + j + ":" + testboard.squares[i][j].getPiece() +
     * "\t");
     * }
     * System.out.println();
     * }
     * 
     * castle(60, 56);
     * 
     * for (int i = 0; i < 8; i++) {
     * for (int j = 0; j < 8; j++) {
     * System.out.print(8 * i + j + ":" + testboard.squares[i][j].getPiece() +
     * "\t");
     * }
     * System.out.println();
     * }
     * 
     * }
     */
    static void printArrayList(ArrayList<String> validMoves) {
        if (validMoves.size() == 0)
            System.out.println("array list is empty ");
        System.out.println(validMoves.size());
        for (String move : validMoves) {
            System.out.println(move);
        }
    }
}
