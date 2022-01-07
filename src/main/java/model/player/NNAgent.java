package model.player;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import controller.GameRunner;
import javafx.application.Platform;
import model.NeuralNetwork.NeuralNetwork;
import model.algorithm.Expectiminimax;
import model.algorithm.NNTreeNode;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;

import utils.FenEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NNAgent extends Player {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 1;
//    private MultiLayerConfiguration configuration;
//    private MultiLayerNetwork model;
    private NeuralNetwork neuralNetwork;
    public static final boolean LEARN = true;
    private Expectiminimax expectiminimax;
    private final int ply = 1;
    private NNTreeNode maxima;
    private BaselineAgent baselineAgent;
    private static final boolean DEBUG = GameRunner.DEBUG;
    private static final boolean DO_RANDOM = false;

    public NNAgent() {
//        configuration = new NeuralNetConfiguration.Builder()
//                .activation(Activation.RELU)
//                .weightInit(WeightInit.XAVIER)
//                .updater(new AdaDelta())
//                .l2(0.0001).list().layer(0, new DenseLayer.Builder()
//                        .nIn(FEATURES_COUNT).nOut(20).build()).
//                layer(1, new DenseLayer.Builder()
//                        .nIn(20).nOut(20).build())
//                .layer(2, new OutputLayer.Builder(
//                        LossFunctions.LossFunction.L1)
//                        .activation(Activation.TANH)
//                        .nIn(20).nOut(CLASSES_COUNT).build())
//                .build();
//
//        model = new MultiLayerNetwork(configuration);
//        model.init();
//        neuralNetwork = new NeuralNetwork();
        expectiminimax = new Expectiminimax();
        baselineAgent = new BaselineAgent();
    }



    public double[] evaluation(Board board) {
        return new double[]{0};
    }


    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        NNTreeNode root = new NNTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite, this);
        expectiminimax.expectiminimax(root, (ply*2)-1, (ply*2)-1);
        double maxValue = Double.MIN_VALUE;
        ArrayList<NNTreeNode> highestNodes = new ArrayList<>();
        NNTreeNode maxNode;
        try{
            maxNode = (NNTreeNode) root.getChildren().get(0);
        }
        catch(Exception e){
            maxNode = root;
        }
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            NNTreeNode subChild = (NNTreeNode) child;
            if (subChild.getValue() >= maxValue) {
                if (subChild.getValue() == maxValue) {
                    highestNodes.add(subChild);
                    continue;
                }
                highestNodes.clear();
                highestNodes.add(subChild);
                maxValue = subChild.getValue();
            }
        }
        Random rand = new Random();
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));
        maxima = maxNode;
    }

    public void launch(Board board){
        System.gc();
        new Thread(() -> {
            if(!DO_RANDOM) {
            try{
                if(ply<3){
//                    Thread.sleep(100);
                }
                runAgent(board);
                NNTreeNode move = getMaxima();
                if(move.isDoPromotion()){
                    board.storeMove();
                    BoardUpdater.runPromotion(board, move.getBoard(), move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if(Board.GUI_ON){
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                }
                else{
//                    if(!Board.GUI_ON) printBoard(board.getBoardModel(), board);
                    BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if(Board.GUI_ON){
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                }
            }
            catch(Exception e){
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
            }
            else{
                baselineAgent.launch(board);
            }
        }).start();
    }

    public NNTreeNode getMaxima() {
        return maxima;
    }

    public double[] computeEndEval(Board board){
        double[] endEval = new double[2];
        endEval[0]=0.5;
        endEval[1]=0.5;
        if(BoardUpdater.containsKing(board, true)) {
            endEval[0] = 1;
            endEval[1] = 0;
            if(DEBUG){
                System.out.println("White has won");
            }
        }
        else{
            endEval[0] = 0;
            endEval[1] = 1;
            if(DEBUG){
                System.out.println("Black has won");
            }
        }
        return endEval;
    }

    public int getPieceType(char pieceType){
        switch(pieceType){
            case 'n','N':
                return 2;
            case 'b','B':
                return 3;
            case 'r','R':
                return 4;
            case 'q','Q':
                return 5;
        }
        return 0;
    }
    public void createChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        if(root.getNodeType()== 1 || root.getNodeType() == 2){
            createChanceChildren(root, doEvaluation, maxIsWhite);
        }
        else if(root.getNodeType() == 3){
            if(root.getParent().getNodeType() == 1){
                createMinChildren(root, doEvaluation, maxIsWhite);
            }
            else if(root.getParent().getNodeType() == 2){
                createMaxChildren(root, doEvaluation, maxIsWhite);
            }
        }
    }

    private void createChanceChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 3, doEvaluation, maxIsWhite);
                }
            }
        }
    }

    private void createMaxChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 1, 1.0/movablePieces.size(), maxIsWhite);
        }

    }

    private void createMinChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 2, 1.0/movablePieces.size(), maxIsWhite);
        }
    }

    private void createChanceChild(NNTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability, boolean maxIsWhite){
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        if(doEvaluation){
            double[] temp = evaluation(copy);
            if(maxIsWhite){
                value = temp[0];
            }
            else{
                value = temp[0];
            }
        }
        NNTreeNode child = new NNTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0, parent.isMaxIsWhite(), parent.getMctsAgent());
        parent.addChild(child);
    }

    private void createChild(NNTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation, boolean maxIsWhite){

        for(int i = 0; i< validMoves.length; i++){
            for(int j = 0; j<validMoves[0].length; j++){
                if(validMoves[i][j]){
                    if(isPromotion(piece, j)){
                        createPromotionChild(parent, piece, i, j, 2, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 3, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 4, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 5, doEvaluation, maxIsWhite, nodeType);
                        continue;
                    }

                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);

                    double value = 0;
                    if(doEvaluation){
                        double[] temp = evaluation(copy);
                        if(maxIsWhite){
                            value = temp[0];
                        }
                        else{
                            value = temp[0];
                        }
                    }

                    NNTreeNode child = new NNTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j, parent.isMaxIsWhite(), parent.getMctsAgent());
                    parent.addChild(child);
                }
            }
        }
    }

    private void createPromotionChild(NNTreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, boolean maxIsWhite, int nodeType){
        Board copy = parent.getBoard().clone();
        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
        BoardUpdater.addPiece(copy, promoted);

        double value = 0;
        if(doEvaluation){
            double[] temp = evaluation(copy);
            if(maxIsWhite){
                value = temp[0];
            }
            else{
                value = temp[0];
            }
        }

        NNTreeNode child = new NNTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo, parent.isMaxIsWhite(), parent.getMctsAgent());
        child.setDoPromotion(true);
        parent.addChild(child);
    }

    private boolean isPromotion(ChessPiece piece, int yTo){
        if(piece.getPieceType()==1){
            if(piece.isWhite()&&yTo==0){
                return true;
            }
            else if(!piece.isWhite()&&yTo==7){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public Board doMove(Board board){
        runAgent(board);
        NNTreeNode move = maxima;
        Board clone = board.clone();
        if(move.isDoPromotion()){
            boolean isWhite = clone.getPieceOffField(move.getxFrom(), move.getyFrom()).isWhite();
            int pieceType = getPieceType(move.getBoard().getCharOffField(move.getxTo(), move.getyTo()));
            ChessPiece promoted = BoardUpdater.createPiece(isWhite, move.getxTo(), move.getyTo(), pieceType);
            BoardUpdater.removePiece(clone, move.getxFrom(), move.getyFrom());
            BoardUpdater.addPiece(clone, promoted);
            if(!BoardUpdater.containsKing(clone, !isWhite)){
                board.setGameOver(true);
            }
        }
        else BoardUpdater.movePiece(clone, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
        return clone;
    }


}