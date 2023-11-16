public class EmptySquare extends Piece{
    @Override
    public String toString()
    {
        return "e";
    }

    @Override
    boolean isValidMove(int myPosition,int nextPostion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chackPath'");
    }
}
