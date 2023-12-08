package ChessCore;

import java.util.ArrayList;
import java.util.List;

public class GameStatePublisher {
    private List<GameStateObserver> observers = new ArrayList<>();
    private GameStatus status;

    public void setStatus(GameStatus status) {
        this.status = status;
        notifyObservers();
    }

    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.update(status);
        }
    }
}
