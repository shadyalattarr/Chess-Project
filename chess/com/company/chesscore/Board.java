package com.company.chesscore;

public class Board {
    //wanna make squaes private and make initialise board method
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

    public static String createMoveString(int myRow,int myCol,int nextRow,int nextCol)
    {
        return columnConvert(myCol) +""+ rowConvert(myRow) + "," 
        + columnConvert(nextCol) + rowConvert(nextRow);
    } 
    public static boolean isIllegal(int row,int col) 
    {
        if(row <0 || row>7 || col<0 || col>7)
            return true;
        return false;
    }   
    public static BoardSquare getBoardSquare(int nextPostion) {
       return squares[nextPostion/8][nextPostion%8];
    }

    public static int getIntPosition(int row,int col)
    {
        return 8*row + col;
    }
    public static char columnConvert(int myColumn) 
    {
        return (char)(97+myColumn);
    }

    public static char rowConvert(int myRow)
    {
        return (char)(56-myRow);//56 ascii of 8
    }

    public static int getColumnNumber(char column)
    {
        return (int)column-97;
    }

    public static int getRowNumber(char row)
    {
        return 8-(int)row;
    }

    public static boolean isMyKingSafe(int kingPosition,int myColor)
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
                    if(Board.isIllegal(newR, newC))//reach end of board
                        throw new IllegalArgumentException(); 
                    //next while loop keep going util you find apiece or hit end
                    while(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() == -1)//while empy sqr
                    {
                        temp++;
                        newR = r+ temp*i;
                        newC =c+ temp*j;
                        if(Board.isIllegal(newR, newC))//reach end of board
                            throw new IllegalArgumentException();
                    }
                    //reaching this means hit a piece
                    //if bishop or queen and their color is not mine----they enemy
                    if((Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Bishop
                     || Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Queen) &&
                     Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
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
                    if(Board.isIllegal(newR, newC))//reach end of board
                        throw new IllegalArgumentException(); 
                    while(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() == -1)//while empy sqr
                    {
                        temp++;
                        newR = r+ temp*i;
                        newC =c+ temp*j;
                        if(Board.isIllegal(newR, newC))//reach end of board
                            throw new IllegalArgumentException();     
                    }
                    //reaching this means we hit a piece @newRnewC
                    //if rook or queen and their color is not mine----they enemy
                    if((Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Rook
                     || Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Queen) &&
                     Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
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
                        if(Board.isIllegal(newR, newC))
                            throw new IllegalArgumentException();
                        //not invalid square
                        //check if its opposite knight
                        //if knight found there and his color is not ,ine we in danger
                        if(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Knight &&
                            Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
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
                if(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof Pawn &&
                    Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
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
                        if(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece() instanceof King &&
                            Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() != myColor)
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
