package com.company.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import com.company.chesscore.*;

public class Main extends JFrame {
    JFrame frame = new JFrame("Chess");
    private Board board = new Board();
    private BoardSquare fromSquare;
    private BoardSquare toSquare;
    private Piece pressedPiece;

    private final static int Tile_Size = 100;
    // it wont work on small Path
    private String fileDestantion = "D:\\Study\\Prog\\Chess-Project\\";
    // JLabel label = new JLabel(new ImageIcon(fileDestantion +
    // "Chess-Project\\src\\com\\company\\gui\\ChessPieces.png"));

    public Main() {
        board.initialiseBoard();
        fromSquare = null;
        toSquare = null;
        pressedPiece = null;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * Tile_Size, 8 * Tile_Size);
        frame.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            JPanel panel = new JPanel(new BorderLayout(1, 1));
            frame.add(panel);
            int row = (i / 8) % 2;
            if (row == 0) {
                panel.setBackground(i % 2 == 0 ? new Color(255, 253, 228) : new Color(111, 78, 55));
            } else {
                panel.setBackground(i % 2 == 0 ? new Color(111, 78, 55) : new Color(255, 253, 228));
            }
            if (board.getBoardSquare(i).getPiece().getColorNum() != -1) {
                // JLabel piece = new JLabel(
                // new ImageIcon(fileDestantion +board.getBoardSquare(i).getPiece().pieceName()
                // + ".png"));
                // piece.setPreferredSize(new Dimension(20, 20));
                // piece.setText(board.getBoardSquare(i).getPiece().getColor()
                // + board.getBoardSquare(i).getPiece().getColorNum());
                // panel.add(piece);
                try {
                    BufferedImage image = ImageIO
                            .read(new File(fileDestantion + board.getBoardSquare(i).getPiece().pieceName() + ".png"));
                    if (image != null) {
                        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        JLabel piece = new JLabel(new ImageIcon(scaledImage));
                        panel.add(piece);
                    } else {
                        System.out.println("Image is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image: " + e.getMessage());
                }
            }
        }
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX() / Tile_Size;
                int y = e.getY() / Tile_Size;
                int index = y * 8 + x;
                if (fromSquare == null) {
                    fromSquare = board.getBoardSquare(index);
                    pressedPiece = fromSquare.getPiece();
                    // highlight the valied moves form the piece
                    ArrayList<String> validMoves = pressedPiece.getAllValidMovesFromPiece();
                    if (validMoves.size() == 0) {
                        fromSquare = null;
                        pressedPiece = null;
                        JOptionPane.showMessageDialog(null, "No valid moves for this piece");
                    } else {
                        highlightValidMoves(validMoves);

                        System.out.println("From: " + fromSquare.getPiece().pieceName());
                    }
                } else {
                    toSquare = board.getBoardSquare(index);
                    System.out.println("To: " + toSquare.getPiece().pieceName());
                    if (fromSquare.getPiece().getPiecePosition() != toSquare.getPiece().getPiecePosition()) {
                        board.movePiece(fromSquare.getPiece().getPiecePosition(),
                                toSquare.getPiece().getPiecePosition());
                        System.out.println("Moved");
                        pressedPiece = null;
                        fromSquare = null;
                        toSquare = null;
                        removeHighlights();
                    } else {
                        System.out.println("Not moved");
                        pressedPiece = null;
                        fromSquare = null;
                        toSquare = null;
                    }
                }
                updateChessboardGUI();
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
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void highlightValidMoves(ArrayList<String> validMoves) {
        for (int i = 0; i < validMoves.size(); i++) {
            int index = Integer.parseInt(validMoves.get(i));
            JPanel panel = (JPanel) frame.getContentPane().getComponent(index);
            System.out.println("index: " + index);
            panel.setBackground(new Color(162, 255, 134));
        }
    }

    private void removeHighlights() {
        for (int i = 0; i < 64; i++) {
            JPanel panel = (JPanel) frame.getContentPane().getComponent(i);
            int row = (i / 8) % 2;
            if (row == 0) {
                panel.setBackground(i % 2 == 0 ? new Color(255, 253, 228) : new Color(111, 78, 55));
            } else {
                panel.setBackground(i % 2 == 0 ? new Color(111, 78, 55) : new Color(255, 253, 228));
            }
        }
    }

    private void updateChessboardGUI() {

        for (int i = 0; i < 64; i++) {
            JPanel panel = (JPanel) frame.getContentPane().getComponent(i);

            panel.removeAll();

            if (board.getBoardSquare(i).getPiece().getColorNum() != -1) {
                try {
                    BufferedImage image = ImageIO
                            .read(new File(fileDestantion + board.getBoardSquare(i).getPiece().pieceName() + ".png"));
                    Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    JLabel piece = new JLabel(new ImageIcon(scaledImage));
                    panel.add(piece);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image: " + e.getMessage());
                }
            }
        }

        frame.revalidate();
    frame.repaint();
    }

    public static void main(String[] args) {
        new Main();
        Board board = new Board();
        // board.initialiseBoard();
        // System.out.println(board.getBoardSquare(0).getPiece().pieceName());

    }
}
