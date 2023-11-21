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
                System.out.println(piecesOfThisColor.get(i)+ " still hac valid moves");
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
                        {System.out.println("queen or bishop strikes");return false;}
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
                        {System.out.println("rook or bishop strikes");return false;}
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
                                {System.out.println("knight strikes");return false;}
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
                        {System.out.println("pawn strikes");return false;}
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
                                {System.out.println("king strikes");return false;}
                    }
                    catch(IllegalArgumentException e)
                    {
                        //out f bounds
                    }
        }
        
        
        return true;
    }
}
