package gui;

import ChessCore.*;
import ChessCore.Pieces.Piece;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

public class ChessGUI extends JFrame implements GameStateObserver    {
    JFrame frame;
    JPanel boardPanel;
    private ClassicChessGame game;
    // private ChessBoard board;
    private Square fromSquare;
    private Square toSquare;
    private final static int Square_Size = 100;
    // private String fileDestantion;
    private int fileNum, fileFix, rankNum, rankFix;
    int color = 1;// statrt
    History history;
    private GameStatus status;

    public void updateColor() {
        if (this.color == 0)
            this.color = 1;
        else
            this.color = 0;
    }

    public void updateThem() {
        if (game.getWhoseTurn() == Player.BLACK) {
            fileNum = 7;
            fileFix = 1;
            rankNum = 0;
            rankFix = -1;
        } else {
            fileNum = 0;
            fileFix = -1;
            rankNum = 7;
            rankFix = 1;
        }
    }

    public void highlightKingSquareIfInDanger() {
        if (Utilities.isInCheck(game.getWhoseTurn(), game.getBoard()))// if my king in check
        {
            // highlight my square
            Square checkdedKingSq = Utilities.getKingSquare(game.getWhoseTurn(), game.getBoard());
            int index = 8 * (rankNum - rankFix * checkdedKingSq.getRank().getValue())
                    + (fileNum - checkdedKingSq.getFile().getValue() * fileFix);// ith this need change?
            JPanel panel = (JPanel) boardPanel.getComponent(index);
            panel.setBackground(new Color(255, 255, 134));
        }
    }

    public ChessGUI() {

        this.game = new ClassicChessGame();
        this.history = new History(game);
        // this.board = game.getBoard();
        this.fromSquare = null;
        this.toSquare = null;
        // this.fileDestantion =
        // "C:\\Users\\reda\\Desktop\\Programming\\Chess-Project\\ChessJavaLib\\";
        this.frame = new JFrame("Chess");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout());
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        JButton undoButton = new JButton("Undo");
        
        // undoButton.setSize(80, 50);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(undoButton, BorderLayout.SOUTH);
        frame.setSize(8 * Square_Size, 8 * Square_Size + undoButton.getHeight());
        this.boardPanel.setSize(new Dimension(8 * Square_Size, 8 * Square_Size));
        game.addObserver(this);
        status = game.getGameStatus();

        this.setTiles();// sets tiles board and with its pieces

        undoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent a) {
                // TODO Auto-generated method stub
                if (a.getSource() == undoButton) {
                    history.undo();
                    updateThem();

                    boardPanel.removeAll();// fadi el frame // delted getContentPane().
                    setTiles();// resetting it
                    boardPanel.revalidate();
                    boardPanel.repaint();
                }
            }

        });
        boardPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX() / Square_Size;
                int y = (e.getY() + 2 * undoButton.getHeight()) / (Square_Size);
                // x and y are click posiiotn

                if (fromSquare == null) {// if this first click

                    fromSquare = new Square(BoardFile.values()[fileNum - x * fileFix],
                            BoardRank.values()[rankNum - y * rankFix]);// need change // 7- swapped
                    List<Square> moves = game.getAllValidMovesFromSquare(fromSquare);
                    if (moves.size() == 0) {// no mves available
                        fromSquare = null;
                        if (!game.isGameEnded())
                            JOptionPane.showMessageDialog(null, "No valid moves for this piece");// ith we need to
                                                                                                 // remove
                        // this?
                    } else {
                        highlightSquares(moves);

                    }

                } else {// fromsquare decided and to square required
                    // this click now is the to square
                    Move move;
                    toSquare = new Square(BoardFile.values()[fileNum - x * fileFix],
                            BoardRank.values()[rankNum - y * rankFix]);// need change
                    // need to check if enpassant el captured pieece gheir

                    move = new Move(fromSquare, toSquare);

                    if (game.isPawnPromotion(move)) {
                        move = new Move(fromSquare, toSquare, PawnPromotion.Queen);// as a test
                        if (game.isValidMove(move))
                            move = promtiMove(fromSquare, toSquare);
                    }
                    if (game.isValidMove(move)) {
                        // if(game.hasPieceIn(toSquare))
                        // JOptionPane.showMessageDialog(null, game.getPieceName(toSquare) + " IS
                        // CAPTURED");
                        history.saveState();

                        game.makeMove(move);// made move in board
                        // turn is black now and move is the whites move
                        fromSquare = null;
                    } else {
                        List<Square> moves = game.getAllValidMovesFromSquare(toSquare);
                        if (moves.size() == 0) {// no mves available
                            {
                                fromSquare = null;
                                JOptionPane.showMessageDialog(null, "Invalid move");
                            }
                        } else {

                            fromSquare = toSquare;
                        }

                    }
                    boardPanel.removeAll();// fadi el frame // delted getContentPane().
                    setTiles();// resetting it
                    boardPanel.revalidate();
                    boardPanel.repaint();
                    highlightKingSquareIfInDanger();
                    List<Square> moves = game.getAllValidMovesFromSquare(toSquare);
                    if (!game.isValidMove(move) && moves.size() != 0) {
                        highlightSquares(moves);
                    }
                    toSquare = null;
                }

                if (game.isGameEnded()) {
                    update(status);
                }

            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);

    }

    private Move promtiMove(Square fSquare, Square tSquare) {
        String[] options = { "Queen", "Rook", "Bishop", "Knight" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a Piece to Promote TO",
                "Promotion",
                0,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (choice == 0)
            return new Move(fSquare, tSquare, PawnPromotion.Queen);
        else if (choice == 1)
            return new Move(fSquare, tSquare, PawnPromotion.Rook);
        else if (choice == 2)
            return new Move(fSquare, tSquare, PawnPromotion.Bishop);
        else if (choice == 3)
            return new Move(fSquare, tSquare, PawnPromotion.Knight);
        else {
            return new Move(fSquare, tSquare);
        }
    }

    // private void getEnding() {
    //     if (game.getGameStatus() == GameStatus.BLACK_WON)
    //         JOptionPane.showMessageDialog(null, "Black Won");
    //     else if (game.getGameStatus() == GameStatus.WHITE_WON)
    //         JOptionPane.showMessageDialog(null, "White Won");
    //     else if (game.getGameStatus() == GameStatus.STALEMATE)
    //         JOptionPane.showMessageDialog(null, "Stalemate");
    //     else if (game.getGameStatus() == GameStatus.INSUFFICIENT_MATERIAL)
    //         JOptionPane.showMessageDialog(null, "Insufficient Material");

    // }

    private void highlightSquares(List<Square> squares) {
        for (Square square : squares) {
            int index = 8 * (rankNum - rankFix * square.getRank().getValue())
                    + (fileNum - square.getFile().getValue() * fileFix);// ith this need change?
            JPanel panel = (JPanel) boardPanel.getComponent(index); // deleted (JPanel) frame.getContentPane()
            panel.setBackground(new Color(162, 255, 134));// ask gumbile why it ust be type casted
        }
    }

    public void setI(int i) {
        JPanel panel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createEtchedBorder(new Color(0, 0, 0), new Color(0, 0, 0));
        panel.setBorder(border);
        boardPanel.add(panel);
        // change the method
        // if(i+1%8 !=0)
        // code= change color

        Color Color1 = new Color(255, 253, 228);// light
        Color Color0 = new Color(111, 78, 55);

        if (this.color == 1)
            panel.setBackground(Color1);
        else
            panel.setBackground(Color0);

        if (((i) % 8 != 7 && game.getWhoseTurn() == Player.WHITE)
                || (i % 8 != 0 && game.getWhoseTurn() == Player.BLACK)) {// change
            updateColor();
        }
        BoardFile file = BoardFile.values()[i % 8];// cols
        BoardRank rank = BoardRank.values()[7 - i / 8];// rows
        Square square = new Square(file, rank);
        if (game.getBoard().getPieceAtSquare(square) != null) {// if not empty
            try {// add an image.. piece in gui
                BufferedImage image = ImageIO.read(new File(game.getPieceName(square) + ".png"));// depending on the
                                                                                                 // peice
                if (image != null) {
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);// to fit the panel
                    JLabel piece = new JLabel(new ImageIcon(scaledImage));// add el piece img
                    panel.add(piece);
                } else {
                    System.out.println("Image is not found");// msh haye7sal isA
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());// same
            }
        }
    }

    private void setTiles() {
        updateThem();
        if (game.getWhoseTurn() == Player.BLACK) {
            for (int i = 63; i >= 0; i--) {//// changed
                setI(i);
            }
        } else {
            for (int i = 0; i < 64; i++) {
                setI(i);
            }
        }

    }

    public static void main(String[] args) {
        new ChessGUI();
        // ClassicChessGame game = new ClassicChessGame();
        // ChessBoard board = game.getBoard();
        // Square fromSquare = new Square(BoardFile.A, BoardRank.FIRST);
        // System.out.println(game.getPieceName(fromSquare));

        // BoardFile file = BoardFile.values()[0];
        // BoardRank rank = BoardRank.values()[0];
        // System.out.println(rank.getValue());

    }

    @Override
    public void update(GameStatus status) {
        // TODO Auto-generated method stub
        if (status == GameStatus.BLACK_WON)
            JOptionPane.showMessageDialog(null, "Black Won");
        else if (status == GameStatus.WHITE_WON)
            JOptionPane.showMessageDialog(null, "White Won");
        else if (status == GameStatus.STALEMATE)
            JOptionPane.showMessageDialog(null, "Stalemate");
        else if (status == GameStatus.INSUFFICIENT_MATERIAL)
            JOptionPane.showMessageDialog(null, "Insufficient Material");
    }
}
