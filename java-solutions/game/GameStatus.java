package game;

public enum GameStatus {
    WIN(true),
    LOSS(true),
    DRAW(true),
    IN_PROCESS(false),
    DRAW_SUGGESTED(false);

    boolean isFinished;

    GameStatus(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
