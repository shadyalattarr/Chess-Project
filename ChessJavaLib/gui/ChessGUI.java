package gui;

import ChessCore.*;
import ChessCore.Pieces.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessGUI extends JFrame {
    JFrame frame;
    private ClassicChessGame game;
    // private ChessBoard board;
    private Square fromSquare;
    private Square toSquare;
    private final static int Tile_Size = 100;
    private String fileDestantion;

    public ChessGUI() {
        this.game = new ClassicChessGame();
        // this.board = game.getBoard();
        this.fromSquare = null;
        this.toSquare = null;
        this.fileDestantion = "D:\\Study\\Prog\\ChessJavaLib\\";
        this.frame = new JFrame("Chess");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(8 * Tile_Size, 8 * Tile_Size);
        this.frame.setLayout(new GridLayout(8, 8));
        this.setTiles();
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX() / Tile_Size;
                int y = e.getY() / Tile_Size;
                if (fromSquare == null) {

                    fromSquare = new Square(BoardFile.values()[x], BoardRank.values()[7 - y]);
                    List<Square> moves = game.getAllValidMovesFromSquare(fromSquare);
                    if (moves.size() == 0) {
                        fromSquare = null;
                        JOptionPane.showMessageDialog(null, "No valid moves for this piece");
                    } else {
                        highlightSquares(moves);

                    }

                } else {
                    toSquare = new Square(BoardFile.values()[x], BoardRank.values()[7 - y]);

                    Move move = new Move(fromSquare, toSquare);
                    if (game.isValidMove(move)) {
                        // if(game.hasPieceIn(toSquare))
                        // JOptionPane.showMessageDialog(null, game.getPieceName(toSquare) + " IS
                        // CAPTURED");
                        game.makeMove(move);
                        frame.getContentPane().removeAll();
                        setTiles();
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid move");
                    }
                    fromSquare = null;
                    toSquare = null;
                }
                if (game.isGameEnded()) {
                    getEnding();
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

    private void getEnding() {
        if (game.getGameStatus() == GameStatus.BLACK_WON)
            JOptionPane.showMessageDialog(null, "Black Won");
        else if (game.getGameStatus() == GameStatus.WHITE_WON)
            JOptionPane.showMessageDialog(null, "White Won");
        else if (game.getGameStatus() == GameStatus.STALEMATE)
            JOptionPane.showMessageDialog(null, "Stalemate");
        else if (game.getGameStatus() == GameStatus.INSUFFICIENT_MATERIAL)
            JOptionPane.showMessageDialog(null, "Insufficient Material");

    }

    private void highlightSquares(List<Square> squares) {
        for (Square square : squares) {
            int index = 8 * (7 - square.getRank().getValue()) + square.getFile().getValue();
            JPanel panel = (JPanel) frame.getContentPane().getComponent(index);
            panel.setBackground(new Color(162, 255, 134));
        }
    }

    private void setTiles() {
        for (int i = 0; i < 64; i++) {
            JPanel panel = new JPanel(new BorderLayout(1, 1));
            frame.add(panel);
            int row = (i / 8) % 2;
            if (row == 0) {
                if (i % 2 == 0) {
                    panel.setBackground(new Color(255, 253, 228));
                } else {
                    panel.setBackground(new Color(111, 78, 55));
                }
            } else {
                if (i % 2 == 0) {
                    panel.setBackground(new Color(111, 78, 55));
                } else {
                    panel.setBackground(new Color(255, 253, 228));
                }
            }
            BoardFile file = BoardFile.values()[i % 8];
            BoardRank rank = BoardRank.values()[7 - i / 8];
            Square square = new Square(file, rank);
            if (game.getBoard().getPieceAtSquare(square) != null) {
                try {
                    BufferedImage image = ImageIO
                            .read(new File(fileDestantion + game.getPieceName(square) + ".png"));
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                        JLabel piece = new JLabel(new ImageIcon(scaledImage));
                        panel.add(piece);
                    } else {
                        System.out.println("Image is not found");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image: " + e.getMessage());
                }
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
}
