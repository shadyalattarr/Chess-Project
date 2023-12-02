package com.company.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import com.company.chesscore.Board;
import com.company.chesscore.Piece;

public class Main extends JFrame {
    JFrame frame = new JFrame("Chess");
    private Board board = new Board();

    private final static int Tile_Size = 100;
    //it wont work on small Path
    private String fileDestantion = "D:\\Study\\Prog\\Chess-Project\\";
    // JLabel label = new JLabel(new ImageIcon(fileDestantion +
    // "Chess-Project\\src\\com\\company\\gui\\ChessPieces.png"));

    public Main() {
        board.initialiseBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(8 * Tile_Size, 8 * Tile_Size);
        frame.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            JPanel panel = new JPanel(new BorderLayout(1,1));
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
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        new Main();
        Board board = new Board();
        // board.initialiseBoard();
        // System.out.println(board.getBoardSquare(0).getPiece().pieceName());

    }
}
