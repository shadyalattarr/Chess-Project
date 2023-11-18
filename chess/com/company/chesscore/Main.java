package com.company.chesscore;

public class Main {
    public static void main(String[] args) {

        System.out.println("----Chess Board----");
        Board board = new Board();// makes no sense how we dealing wih it
        // kolo 2awel 7arf but king Aa and WHITE black
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }

    }
}