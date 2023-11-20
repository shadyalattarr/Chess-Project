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
    int whiteKingPos;
    int blackKingPos;

    ChessGame() {
        this.board = new Board();
        this.player = 1;// white starts
        this.gameOn = true;
        whiteKingPos = 60;
        blackKingPos = 4;
    }
    public Piece promotionPiece(char stringPiece)
    {
        Piece newPiece;
        switch (stringPiece) {
            case 'K':
                newPiece = new Knight(player);
                System.out.println("promoted to knight");
                break;
            case 'B':
                newPiece = new Bishop(player);
                System.out.println("promoted to bishop");
                break;
            case 'R':
                newPiece = new Rook(player);
                System.out.println("promoted to rook");
                break;
            case 'Q':
                newPiece = new Queen(player);
                System.out.println("promoted to queen");
                break;
            default:
                System.out.println("no promotion lmfao");
                newPiece = new EmptySquare();//if we sent an empty square then no promo
                break;
        }
        
        return newPiece;
    }
    public ArrayList<String> getMovesFromFile(String file)
    {
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
        } catch(IOException e)
        {
            e.printStackTrace();
        } 
        return inputMoves;
    }
    public void writeToFile(String event){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Output.txt"),true));
            bw.write(event);
            bw.write("\n");
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean isPieceMyColor(int fromPosition)
    {
        return Board.getBoardSquare(fromPosition).getPiece().getColorNum() == player;//player 0 black and 1 white..
        //we check before this that we are not accessing an empty square
    }
    public void startGame()
    {
        int otherPlayer;
        ArrayList<String> inputMoves = getMovesFromFile("ChessGame.txt");
        //printArrayList(inputMoves);
        int fromPosition,toPosition;
        char promoteTo;
        int fromCol,fromRow,toCol,toRow;
        String fromTo[] = new String[3];
        String sendToFile;
        Piece captureedPiece;
        for (String move : inputMoves) {
            //need to split each move to from and to
            try{ 
                if(gameOn)
                {
                    fromTo = move.split(",");
                    fromRow = Board.getRowNumber(fromTo[0].charAt(1));
                    fromCol = Board.getColumnNumber(fromTo[0].charAt(0));
                    toRow = Board.getRowNumber(fromTo[1].charAt(1));
                    toCol = Board.getColumnNumber(fromTo[1].charAt(0));
                    try{
                        promoteTo = fromTo[2].charAt(0);
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                        promoteTo = 'n';
                    }
                    if(Board.isIllegal(toRow, toCol) || Board.isIllegal(fromCol, fromCol))
                        throw new IllegalArgumentException();
                    fromPosition = Board.getIntPosition(fromRow,fromCol);
                    toPosition = Board.getIntPosition(toRow,toCol);
                    System.out.println(fromPosition + " " + toPosition);
                    if(promoteTo != 'n' && !(Board.getBoardSquare(fromPosition).getPiece() instanceof Pawn))//promo
                        throw new InvalidMove();
                    //we have from and to and they are not illegal
                    //are we trying to access an empty square?
                    if(!isPieceMyColor(fromPosition) 
                        ||Board.getBoardSquare(fromPosition).getPiece().getColorNum() == -1)//if empty or not my color
                        throw new NotAccessiblePiece();
                    else{//ur color
                        //check castling
                        Piece fromPiece, toPiece;
                        fromPiece = Board.getBoardSquare(fromPosition).getPiece();
                        toPiece = Board.getBoardSquare(toPosition).getPiece();
                        if((fromPiece instanceof King) && (toPiece instanceof Rook) &&
                            (fromPiece.getColorNum() == toPiece.getColorNum()))
                        {
                            //need to check if ath is clear
                            //need to check if king is safe after this move
                        }//else if enpassant
                        //is it enpassant

                        //else
                        if(this.isValidMove(fromPosition,toPosition,promotionPiece(promoteTo)))//checks if it is a correct move and king is safe
                        {//checks if it is a normal move no shenanigans (maybe pawn promo)
                            //if a valid normal move then
                            captureedPiece = board.getBoardSquare(toPosition).getPiece();
                            board.movePiece(fromPosition, toPosition);//movement done 
                            otherPlayer = player == 1 ? 0 : 1;
                            if(captureedPiece.getColorNum() == otherPlayer)//either equals other color or -1
                                writeToFile("Captured "/*kaza */);
                            //now need to check if king in check
                        }
                        else
                            throw new InvalidMove();

                    }
                }
                else{//game offline
                    writeToFile("Game already ended");
                }
            }
            catch(InvalidMove e)
            {
                writeToFile("Invalid move");
            }
            catch(NotAccessiblePiece e){
                //invalid move
                writeToFile("Invalid move");
            }
            catch(IllegalArgumentException e)
            {
                writeToFile("Invalid move");
                //illegal inout
            }

        }
    }

    public boolean isValidMove(int fromPosition,int toPosition,Piece promoPiece) throws InvalidMove{
        
        //did it result in pawn prmotion? if so apply it
        //romopiece should have something if its promo
/*        if(promoPiece.getColorNum() != -1)//actually promo
            {
                if(!promotePawn(fromPosition, toPosition, promoPiece))//we sent something fa mafrood works
                    {
                        //if it didnt means a promootion piece was given yet it was invalid hence
                        throw new InvalidMove();
                    }
            }
*/ ///put this after aclling is valied and so
            //s the move valid?
        //if valid is there a capture?

        //check is black or white in check?// is kingsafe // need to bool iskingincheck

        //did someone win

        //check is valid move
        //checks isvalidmove piece checkpath ismykingsafe





        return true;

    }

    // before i call this method isValied and checkPath should be called
    public boolean promotePawn(int myPosition, int nextPosition, Piece newPiece) {
        BoardSquare promotionSquare = Board.getBoardSquare(nextPosition);
        Piece oldPiece = Board.getBoardSquare(myPosition).getPiece();

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

    // just needs to check isKingSafe
    // first move dose not work
    public boolean isValidCastle(int myPosition, int nextPosition) {

        BoardSquare kingSquare = Board.getBoardSquare(myPosition);
        BoardSquare rookSquare = Board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        System.out.println(king instanceof King);
        if (king instanceof King && rook instanceof Rook) {
            // System.out.println("king and rook");
            if (king.getColor().equals(rook.getColor())) {
                // System.out.println("same color");
                if (king.getColor().equals("White")) {
                    // System.out.println("white");
                    if (myPosition == 60 && nextPosition == 63) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(61).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(62).getPiece().getColor().equals("Empty Square")) {
                                return true;
                            }
                        }
                    } else if (myPosition == 60 && nextPosition == 56) {
                        // System.out.println("right place");
                        // System.out.println(kingSquare.getPiece().firstMove);
                        // System.out.println(rookSquare.getPiece().firstMove);
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(59).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(58).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(57).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                } else {
                    if (myPosition == 4 && nextPosition == 7) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(5).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(6).getPiece().getColor().equals("Empty Square")) {
                                return true;
                            }
                        }
                    } else if (myPosition == 4 && nextPosition == 0) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(3).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(2).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(1).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean castle(int myPosition, int nextPosition) {
        if (!isValidCastle(myPosition, nextPosition))
            return false;
        BoardSquare kingSquare = Board.getBoardSquare(myPosition);
        BoardSquare rookSquare = Board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        if (myPosition == 60 && nextPosition == 63 && Board.isMyKingSafe(62, king.getColorNum())) {
            Board.getBoardSquare(61).setPiece(rook);
            Board.getBoardSquare(62).setPiece(king);
            Board.getBoardSquare(60).setPiece(new EmptySquare());
            Board.getBoardSquare(63).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 60 && nextPosition == 56 && Board.isMyKingSafe(58, king.getColorNum())) {
            Board.getBoardSquare(59).setPiece(rook);
            Board.getBoardSquare(58).setPiece(king);
            Board.getBoardSquare(60).setPiece(new EmptySquare());
            Board.getBoardSquare(56).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 4 && nextPosition == 7 && Board.isMyKingSafe(6, king.getColorNum())) {
            Board.getBoardSquare(5).setPiece(rook);
            Board.getBoardSquare(6).setPiece(king);
            Board.getBoardSquare(4).setPiece(new EmptySquare());
            Board.getBoardSquare(7).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 4 && nextPosition == 0 && Board.isMyKingSafe(2, king.getColorNum())) {
            Board.getBoardSquare(3).setPiece(rook);
            Board.getBoardSquare(2).setPiece(king);
            Board.getBoardSquare(4).setPiece(new EmptySquare());
            Board.getBoardSquare(0).setPiece(new EmptySquare());
            return true;
        } else
            return false;

    }

/*     public static void main(String[] args) {
        
        System.out.println("----Chess Board----");
        Board testBoard = new Board();
        ArrayList<String> validMoves = new ArrayList<String>();
        // test castle
        // Piece whiteKing = testBoard.squares[7][4].getPiece();
        // System.out.println(whiteKing.firstMove + "he");
        // Piece whiteRook = testBoard.squares[7][0].getPiece();
        // promotion test
        // Board.getBoardSquare(8).setPiece(testBoard.getBoardSquare(50).getPiece());
        // Board.getBoardSquare(0).setPiece(new EmptySquare());

        // System.out.println(promotePawn(8, 0, new Queen(1)));
        System.out.println(testBoard.getBoardSquare(8).getPiece().firstMove);

        // testBoard.getBoardSquare(57).setPiece(new EmptySquare());
        // testBoard.getBoardSquare(58).setPiece(new EmptySquare());
        // testBoard.getBoardSquare(59).setPiece(new EmptySquare());
        testBoard.getBoardSquare(57).setPiece(new EmptySquare());
        testBoard.getBoardSquare(58).setPiece(new EmptySquare());
        testBoard.getBoardSquare(59).setPiece(new EmptySquare());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8 * i + j + ":" + testBoard.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }
        
        castle(60, 56);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8 * i + j + ":" + testBoard.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }

    }
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
