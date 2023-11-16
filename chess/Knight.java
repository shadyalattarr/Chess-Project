public class Knight extends Piece {
    
    public Knight(int color)
    {
        setColor(color);
    }

    //need to create a get all valid moves method for each piece
    public boolean isValidMove()
    {
        //to be done
        return true;
    }
    
    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "k"; 
        return "K";  
    }
}
