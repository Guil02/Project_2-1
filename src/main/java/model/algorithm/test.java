package model.algorithm;

import controller.Board;
import controller.BoardUpdater;
import controller.GameRunner;
import controller.GraphicsConnector;
import model.pieces.ChessPiece;

public class test {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        Board board = new Board();
        GraphicsConnector graphicsConnector = new GraphicsConnector(gameRunner);
        board.setGraphicsConnector(graphicsConnector);
        BoardUpdater.fillGameStart(board);
        board.firstMoveDiceRoll();
        Board copy = board.clone();
        board.setMovablePiece('N');
        copy.setMovablePiece('K');
        System.out.println(board.getMovablePiece());
        System.out.println(copy.getMovablePiece());

        ChessTreeNode root = new ChessTreeNode(board, 0, null, 1, 1, 0,0,0,0);
        GetAIMoves getAIMoves = new GetAIMoves();
        getAIMoves.createChildren(root, false);
        for(int i = 0; i<root.getChildren().size(); i++){
            ChessTreeNode child = (ChessTreeNode) root.getChildren().get(i);
            printBoard(child.getBoard().getBoardModel(), child.getBoard());
            System.out.println("board value: "+child.getValue());
        }

//        for(TreeNode node: root.getChildren()){
//            ChessTreeNode subNode = (ChessTreeNode) node;
//            getAIMoves.createChildren(root, true);
//        }

        Expectiminimax expectiminimax = new Expectiminimax();
        System.out.println(expectiminimax.expectiminimax(root, 10));
    }

    public static void printBoard(ChessPiece[][] boardModel, Board board) {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + board.getCharOffField(j,i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }
    }
}
