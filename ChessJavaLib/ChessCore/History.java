package ChessCore;

import java.util.Stack;

import ChessCore.ChessGame.Memento;

public class History {
    //caretaker
    private ChessGame game;
    private Stack<Memento> movesHistory = new Stack<Memento>();

    public History(ChessGame game)
    {
        this.game = game;
    }
    public void pushToStack(Memento m)
    {
        movesHistory.push(m);
    }
    public void saveState()
    {
        movesHistory.push(game.returnState());
    }
    public boolean undo()//pop restore
    {
        if(!movesHistory.empty())
        {
            game.restore(movesHistory.pop());
            return true;
        }

        return false;
    }
}
