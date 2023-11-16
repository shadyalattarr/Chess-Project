public class Rook extends Piece {
    
    public Rook(int color)
    {
        setColor(color);
    }

    //need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition,int nextPostion)
    {
        //to be done
        return false;
    }

    
    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        int myRow = myPosition/8;
        int myCol = myPosition%8;
        int nextRow = nextPostion/8;
        int nextCol = nextPostion%8;
        int rowDifference = (int)Math.abs(myRow-nextRow);
        int colDifference = (int)Math.abs(myCol-nextCol);
        if((rowDifference == 0 && colDifference>0)||(colDifference==0 && rowDifference>0))
            return true;
        return false;
    }


    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "r"; 
        return "R";  
    }
    
}
