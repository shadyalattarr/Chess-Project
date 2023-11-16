public class Knight extends Piece {
    
    public Knight(int color)
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
        if((colDifference == 2 && rowDifference == 1)||(colDifference==1 && rowDifference==2))
            return true;
        return false;
    }
    
    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "k"; 
        return "K";  
    }
    
}
