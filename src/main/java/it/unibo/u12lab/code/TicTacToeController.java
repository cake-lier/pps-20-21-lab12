package it.unibo.u12lab.code;

public interface TicTacToeController {
    void humanMove(int i, int j);

    boolean isFinished();

    boolean isGameWon();

    boolean isGameEven();

    boolean isGameLost();
}
