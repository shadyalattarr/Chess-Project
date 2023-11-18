package com.company.chesscore;

public class Board {
    public static BoardSquare squares[][] ;
    //left public for testing?
    public Board()
    {
        //creating board
        squares = new BoardSquare[8][8];
        int r,c;
        for(int i = 0;i<64;i++)
        {
            r = i/8; c = i%8;
            switch(i)
            {
                case(0):
                case(7):
                    //black rook
                    squares[r][c] = new BoardSquare(new Rook(0), i);
                    break;
                case(56):
                case(63):
                    //white rook el heya castle
                    squares[r][c] = new BoardSquare(new Rook(1), i);
                    break;
                case(1):
                case(6):
                    //black knight
                    squares[r][c] = new BoardSquare(new Knight(0), i);
                    break;
                case(57):
                case(62):
                    //white knight
                    squares[r][c] = new BoardSquare(new Knight(1), i);
                    break;
                case(2):
                case(5):
                    //black bishop
                    squares[r][c] = new BoardSquare(new Bishop(0), i);
                    break;
                case(58):
                case(61):
                    //white bishop
                    squares[r][c] = new BoardSquare(new Bishop(1), i);
                    break;
                case(3):
                    //black queen
                    squares[r][c] = new BoardSquare(new Queen(0), i);
                    break;
                case(4):
                    //black king
                    squares[r][c] = new BoardSquare(new King(0), i);
                    break;
                case(59):
                    //white king
                    squares[r][c] = new BoardSquare(new King(1), i);
                    break;
                case(60):
                    //white queen
                    squares[r][c] = new BoardSquare(new Queen(1), i);
                    break;
                case(8):
                case(9):
                case(10):
                case(11):
                case(12):
                case(13):
                case(14):
                case(15):
                    //black pawns
                    squares[r][c] = new BoardSquare(new Pawn(0), i);
                    break;
                case(48):
                case(49):
                case(50):
                case(51):
                case(52):
                case(53):
                case(54):
                case(55):
                    //white pawns
                    squares[r][c] = new BoardSquare(new Pawn(1), i);
                    break;
                default:
                    //null squares
                    //squares[r][c] = new BoardSquare(null, i);
                    //badal null empty square to print?
                    squares[r][c] = new BoardSquare(new EmptySquare(), i);
            }
        }
    }
    public static  BoardSquare getBoardSquare(int nextPostion) {
        // TODO Auto-generated method stub

       return squares[nextPostion/8][nextPostion%8];
    }


}
