package com.company.chesscore;

import java.util.ArrayList;

public class Board {
    //wanna make squaes private and make initialise board method
    public BoardSquare squares[][] ;
    int[] kingPosition;
    ArrayList<ArrayList<Piece>> piecesArray = new ArrayList<ArrayList<Piece>>(); 
      //left public for testing?
    public Board()
    {   
        
    }
    public void capture(Piece capturedPiece)
    {
        if(capturedPiece.getColorNum() != -1)
            piecesArray.get(capturedPiece.getColorNum()).remove(capturedPiece);
    }
    public boolean canImakeAnyMoves(int myColor)
    {
        //loop thru the arraylist
        ArrayList<Piece> piecesOfThisColor = piecesArray.get(myColor); 
        Piece currentPiece;
        for(int i =0;i<piecesOfThisColor.size();i++)
        {
            currentPiece = piecesOfThisColor.get(i);
            if(piecesOfThisColor.get(i).getAllValidMovesFromPiece().size() != 0)
            {
                //System.out.println(piecesOfThisColor.get(i)+ " still hac valid moves");
                return true;
            }
        }
        return false;
    
    }
    public void initialiseBoard()
    {
        //creating board
        kingPosition = new int[2];//0 black 1 white
        kingPosition[0] = 4;
        kingPosition[1] = 60;
        piecesArray.add(new ArrayList<Piece>());//blCK 
        piecesArray.add(new ArrayList<Piece>());//WHITE
        //pieces array.get(0) black and 1 white
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
                    squares[r][c] = new BoardSquare(new Rook(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
                    break;
                case(56):
                case(63):
                    //white rook el heya castle
                    squares[r][c] = new BoardSquare(new Rook(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
                    break;
                case(1):
                case(6):
                    //black knight
                    squares[r][c] = new BoardSquare(new Knight(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
                    break;
                case(57):
                case(62):
                    //white knight
                    squares[r][c] = new BoardSquare(new Knight(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
                    break;
                case(2):
                case(5):
                    //black bishop
                    squares[r][c] = new BoardSquare(new Bishop(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
                    break;
                case(58):
                case(61):
                    //white bishop
                    squares[r][c] = new BoardSquare(new Bishop(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
                    break;
                case(3):
                    //black queen
                    squares[r][c] = new BoardSquare(new Queen(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
                    break;
                case(4):
                    //black king
                    squares[r][c] = new BoardSquare(new King(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
                    break;
                case(60):
                    //white king
                    squares[r][c] = new BoardSquare(new King(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
                    break;
                case(59):
                    //white queen
                    squares[r][c] = new BoardSquare(new Queen(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
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
                    squares[r][c] = new BoardSquare(new Pawn(0,this), i);
                    piecesArray.get(0).add(squares[r][c].getPiece());
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
                    squares[r][c] = new BoardSquare(new Pawn(1,this), i);
                    piecesArray.get(1).add(squares[r][c].getPiece());
                    break;
                default:
                    //null squares
                    //squares[r][c] = new BoardSquare(null, i);
                    //badal null empty square to print?
                    squares[r][c] = new BoardSquare(new EmptySquare(), i);
                    squares[r][c].getPiece().setPosition(i);
            }
        }
    }

    public String createMoveString(int myRow,int myCol,int nextRow,int nextCol)
    {
        return columnConvert(myCol) +""+ rowConvert(myRow) + "," 
        + columnConvert(nextCol) + rowConvert(nextRow);
    } 
    public boolean isIllegal(int row,int col) 
    {
        if(row <0 || row>7 || col<0 || col>7)
            return true;
        return false;
    }   
    public BoardSquare getBoardSquare(int nextPostion) {
       return squares[nextPostion/8][nextPostion%8];
    }

    public int getIntPosition(int row,int col)
    {
        return 8*row + col;
    }
    public char columnConvert(int myColumn) 
    {
        return (char)(97+myColumn);
    }
    
    public char rowConvert(int myRow)
    {
        return (char)(56-myRow);//56 ascii of 8
    }
    public void printBoard()
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8 * i + j + ":" + getBoardSquare(getIntPosition(i,j)).getPiece() + "\t");
            }
            System.out.println();
        }
    }
    public int getColumnNumber(char column)
    {
        return (int)column-97;
    }

    public int getRowNumber(char row)
    {
        return (8-(row-48));
    }
    public Piece movePiece(int fromPosition,int toPosition)
    {//returns captured piece , needs to be captured by ft
        Piece pieceToMve = getBoardSquare(fromPosition).getPiece();
        if(pieceToMve instanceof King)
            kingPosition[pieceToMve.getColorNum()] = toPosition;
        
        Piece capturedPiece = getBoardSquare(toPosition).getPiece();
        getBoardSquare(toPosition).setPiece(this.getBoardSquare(fromPosition).getPiece());
        getBoardSquare(fromPosition).setPiece(new EmptySquare());
        return capturedPiece;//2akeed hatkoon mn oppo color or mt
    }
    public boolean isMyKingSafe(int kingPosition,int myColor)
    {
        int r = kingPosition/8;
        int c = kingPosition%8;
        int newR,newC;
        int temp;
        //checking diagonals: bishop and queen
        for(int i = -1;i<=1;i+=2)
        {
            for(int j= -1;j<=1;j+=2)//i and j for "direction"
            {
                try{
                    temp=1;
                    newR = r+temp*i;
                    newC = c+ temp*j;
                    if(isIllegal(newR, newC))//reach end of board
                        throw new IllegalArgumentException(); 
                    //next while loop keep going util you find apiece or hit end
                    while(getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() == -1)//while empy sqr
                    {
                        temp++;
                        newR = r+ temp*i;
                        newC =c+ temp*j;
                        if(isIllegal(newR, newC))//reach end of board
                            throw new IllegalArgumentException();
                    }
                    //reaching this means hit a piece
                    //if bishop or queen and their color is not mine----they enemy
                    if((getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Bishop
                     || getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Queen) &&
                     getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
                        {//System.out.println("queen or bishop strikes");
                        return false;}
                }
                catch(IllegalArgumentException e)
                {
                    //end board
                }
            }
        }
        //checking str8s:rook ad queen
        for(int i = -1;i<=1;i++)
        {
            for(int j= -1;j<=1;j++)//i and j for "direction"
            {
                if(!((i==0 && j!=0) || (i!=0 &&j==0)))//ixor//lazem one of them 0-- 01 10 // if none is 0 so diag. 
                    continue;//no movement
                try{
                    temp=1;
                    newR = r+temp*i;
                    newC = c+ temp*j;
                    if(isIllegal(newR, newC))//reach end of board
                        throw new IllegalArgumentException(); 
                    while(getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() == -1)//while empy sqr
                    {
                        temp++;
                        newR = r+ temp*i;
                        newC =c+ temp*j;
                        if(isIllegal(newR, newC))//reach end of board
                            throw new IllegalArgumentException();     
                    }
                    //reaching this means we hit a piece @newRnewC
                    //if rook or queen and their color is not mine----they enemy
                    if((getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Rook
                     || getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Queen) &&
                     getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
                        {//System.out.println("rook or bishop strikes");
                        return false;}
                    // end of possible path here, change path
                }
                catch(IllegalArgumentException e)
                {
                    //reached end of board
                }
            }
        }
        //checking Ls: knight
        int rowFactor,colFactor;
        for(int rc = 0;rc<=1;rc++)
        {   
            if(rc==0)
            {   
                rowFactor = 1; colFactor = 2;
            }
            else
            {
                rowFactor = 2; colFactor =1;
            }
            for(int i =-1;i<=1;i+=2)
            {
                for(int j=-1;j<=1;j+=2)
                {  
                    newR = r+i*rowFactor;
                    newC = c+j*colFactor;
                    try{//try catch lazem in loop cuz if out of lop it ends
                        if(isIllegal(newR, newC))
                            throw new IllegalArgumentException();
                        //not invalid square
                        //check if its opposite knight
                        //if knight found there and his color is not ,ine we in danger
                        if(getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Knight &&
                            getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
                                {//System.out.println("knight strikes");
                                return false;}
                    }
                    catch(IllegalArgumentException e)
                    {
                        //exited board
                    }
                }
            }
        }        
        //checking pawn attacks
        newR = (myColor == 0)? r+1:r-1;//color is 0 black queen.. pawns gonna be below her.. increment row
        //2 col configs
        for(int i=-1;i<=1;i+=2)
        {
            try{
                newC = c+i;
                if(isIllegal(newR, newC))
                    throw new IllegalArgumentException();
                //it is a right sqr
                //if pawn found and opposite color
                if(getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof Pawn &&
                    getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
                        {//System.out.println("pawn strikes");
                        return false;}
            }
            catch(IllegalArgumentException e)
            {
                //beyond
            }
        }
        //checking arond by 1 from king enemy
        for(int i=-1;i<=1;i++)
        {
            for(int j=-1;j<=1;j++)
                if(i!=0 || j!=0)//false only when both 0
                    try{
                        newR = r+i;
                        newC = c+j;
                        if(isIllegal(newR, newC))
                            throw new IllegalArgumentException();
                        if(getBoardSquare(getIntPosition(newR,newC)).getPiece() instanceof King &&
                            getBoardSquare(getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
                                {//System.out.println("king strikes");
                                return false;}
                    }
                    catch(IllegalArgumentException e)
                    {
                        //out f bounds
                    }
        }
        
        
        return true;
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

    public boolean isValidCastle(int myPosition, int nextPosition) {
        nextPosition = getRookPostion(myPosition, nextPosition);
        // System.out.println("helllo");
        // System.out.println(nextPosition + " " + nextPosition);
        if (nextPosition == -1)
            return false;
        // System.out.println("helllo");
        BoardSquare kingSquare = getBoardSquare(myPosition);
        BoardSquare rookSquare = getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        // System.out.println(king instanceof King);
        if (king instanceof King && rook instanceof Rook && isMyKingSafe(myPosition, king.getColorNum())) {
            // System.out.println("king and rook");
            if (king.getColor().equals(rook.getColor())) {
                // System.out.println("same color");
                if (king.getColor().equals("White")) {
                    // System.out.println("white");

                    if (myPosition == 60 && nextPosition == 63) {
                        // System.out.println("right place");
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            // System.out.println("right move");
                            if (getBoardSquare(61).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(62).getPiece().getColor().equals("Empty Square")&& whiteRookRightCastle(myPosition)) {
                                // System.out.println("nice");
                                return true;
                            }
                        }
                    } else if (myPosition == 60 && nextPosition == 56) {
                        // System.out.println("right place");
                        // System.out.println(kingSquare.getPiece().firstMove);
                        // System.out.println(rookSquare.getPiece().firstMove);
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (getBoardSquare(59).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(58).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(57).getPiece().getColor().equals("Empty Square")&& whiteRookLeftCastle(myPosition))
                                return true;
                        }
                    }
                } else {
                    if (myPosition == 4 && nextPosition == 7) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (getBoardSquare(5).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(6).getPiece().getColor().equals("Empty Square")&& blackRookRightCastle(myPosition)) {
                                return true;
                            }
                        }
                    } else if (myPosition == 4 && nextPosition == 0) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (getBoardSquare(3).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(2).getPiece().getColor().equals("Empty Square")
                                    && getBoardSquare(1).getPiece().getColor().equals("Empty Square")&& blackRookLeftCastle(myPosition))
                                return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean whiteRookRightCastle(int myPosition) {
        Piece king = getBoardSquare(myPosition).getPiece();
        boolean kingAt61 = king.isKingSafeFromMyMove(myPosition, 61);
        boolean kingAt62 = king.isKingSafeFromMyMove(myPosition, 62);
        if (kingAt61 && kingAt62)
            return true;
        return false;
    }

    public boolean whiteRookLeftCastle(int myPosition) {
        Piece king = getBoardSquare(myPosition).getPiece();
        boolean kingAt59 = king.isKingSafeFromMyMove(myPosition, 59);
        boolean kingAt58 = king.isKingSafeFromMyMove(myPosition, 58);
        boolean kingAt57 = king.isKingSafeFromMyMove(myPosition, 57);
        if (kingAt59 && kingAt58 && kingAt57)
            return true;
        return false;
    }

    public boolean blackRookRightCastle(int myPosition) {
        Piece king = getBoardSquare(myPosition).getPiece();
        boolean kingAt5 = king.isKingSafeFromMyMove(myPosition, 5);
        boolean kingAt6 = king.isKingSafeFromMyMove(myPosition, 6);
        if (kingAt5 && kingAt6)
            return true;
        return false;
    }

    public boolean blackRookLeftCastle(int myPosition) {
        Piece king = getBoardSquare(myPosition).getPiece();
        boolean kingAt3 = king.isKingSafeFromMyMove(myPosition, 3);
        boolean kingAt2 = king.isKingSafeFromMyMove(myPosition, 2);
        boolean kingAt1 = king.isKingSafeFromMyMove(myPosition, 1);
        if (kingAt3 && kingAt2 && kingAt1)
            return true;
        return false;
    }
   
}
