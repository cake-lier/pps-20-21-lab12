package it.unibo.u12lab.code;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToeViewImpl implements TicTacToeView {
    private static final int SIZE = 3;
    private static final String EXIT_TEXT = "Exit";
    private static final String APP_TITLE = "TTT";
    public static final int APP_WIDTH = 200;
    public static final int APP_HEIGHT = 230;
    public static final String PLAYER_SYMBOL = "X";
    public static final String COMPUTER_SYMBOL = "O";
    public static final String GAME_WON_TEXT = "Won!";
    public static final String GAME_EVEN_TEXT = "Even!";
    public static final String GAME_LOST_TEXT = "Lost!";

    private final TicTacToeController controller;
    private final List<List<JButton>> board;
    private final JButton exit;
    private final JFrame frame;

    public TicTacToeViewImpl(final TicTacToeController controller) {
        this.controller = Objects.requireNonNull(controller);
        this.board = new ArrayList<>();
        this.exit  = new JButton(EXIT_TEXT);
        this.frame = new JFrame(APP_TITLE);
        initPane();
    }

    public void displayPlayerMove(final int row, final int column) {
        this.displayMove(row, column, PLAYER_SYMBOL);
    }

    public void displayComputerMove(final int row, final int column) {
        this.displayMove(row, column, COMPUTER_SYMBOL);
    }

    private void displayMove(final int row, final int column, final String text) {
        final JButton button = this.board.get(row).get(column);
        button.setText(text);
        button.setEnabled(false);
    }

    private void initPane() {
        this.frame.setLayout(new BorderLayout());
        final JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        IntStream.range(0, SIZE)
                 .boxed()
                 .map(i -> IntStream.range(0, SIZE)
                                    .mapToObj(j -> {
                                        final JButton button = new JButton("");
                                        button.addActionListener(e -> {
                                            if (!this.controller.isFinished()) {
                                                this.controller.humanMove(i, j);
                                                if (this.controller.isGameWon()) {
                                                    this.exit.setText(GAME_WON_TEXT);
                                                } else if (this.controller.isGameEven()) {
                                                    this.exit.setText(GAME_EVEN_TEXT);
                                                } else if (this.controller.isGameLost()) {
                                                    this.exit.setText(GAME_LOST_TEXT);
                                                }
                                            }
                                        });
                                        return button;
                                    })
                                    .peek(boardPanel::add)
                                    .collect(Collectors.toList()))
                 .forEach(this.board::add);
        final JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(this.exit);
        this.exit.addActionListener(e -> System.exit(0));
        this.frame.add(BorderLayout.CENTER, boardPanel);
        this.frame.add(BorderLayout.SOUTH, bottomPanel);
        this.frame.setSize(APP_WIDTH, APP_HEIGHT);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
