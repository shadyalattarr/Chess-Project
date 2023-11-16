public class King extends Piece {
    
    public King(int color)
    {
        setColor(color);
    }

    //need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition,int nextPostion)
    {
        if(checkPath(myPosition, nextPostion))
            return true;
        return false;
    }

    @Override//can we have the board here????????????????????????????????????????????????????/
    boolean checkPath(int myPosition, int nextPostion) {
     int myRow = myPosition/8;
        int myCol = myPosition%8;
        int nextRow = nextPostion/8;
        int nextCol = nextPostion%8;
        int rowDifference = (int)Math.abs(myRow-nextRow);
        int colDifference = (int)Math.abs(myCol-nextCol);
        if(rowDifference<1 && colDifference<1)
        {
            return true;//7araka sa7
        }
        return false;   
    }

    
    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "a"; 
        return "A";  
    }
}
