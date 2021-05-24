package it.unibo.u12lab.code;

public class TicTacToeControllerImpl implements TicTacToeController {
    private static final String THEORY_FILE = "ttt.pl";

    private final TicTacToe model;
    private final TicTacToeView view;
    private GameState state;

    public TicTacToeControllerImpl() {
        this.model = new TicTacToeImpl(THEORY_FILE);
        this.view = new TicTacToeViewImpl(this);
        this.state = GameState.ONGOING;
    }

    public void humanMove(final int i, final int j) {
        if (this.model.isAFreeCell(i, j)) {
            this.view.displayPlayerMove(i, j);
            this.model.setHumanCell(i, j);
        }
        if (this.model.checkVictory()) {
            this.state = GameState.WON;
        } else if (this.model.checkCompleted()) {
            this.state = GameState.EVEN;
        } else {
            computerMove();
        }
    }

    public boolean isFinished() {
        return this.state == GameState.WON || this.state == GameState.LOST || this.state == GameState.EVEN;
    }

    public boolean isGameWon() {
        return this.state == GameState.WON;
    }

    public boolean isGameEven() {
        return this.state == GameState.EVEN;
    }

    public boolean isGameLost() {
        return this.state == GameState.LOST;
    }

    private void computerMove() {
        final int[] i = this.model.setComputerCell();
        this.view.displayComputerMove(i[0], i[1]);
        if (this.model.checkVictory()){
            this.state = GameState.LOST;
        }
    }
}
