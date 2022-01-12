package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import controller.GameRunner;
import gui.DebugWindow.DebugWindowStage;
import javafx.application.Platform;
import model.NeuralNetwork.NeuralNetwork;
import model.algorithm.Expectiminimax;
import model.algorithm.ExpectiminimaxStar2;
import model.algorithm.NNTreeNode;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;

import utils.BoardEncoding;
import utils.FenEvaluator;
import utils.Functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NNAgent extends Player {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 1;
    private static final int AMOUNT_OF_LAYERS = 3;
    private static final int[] NEURONS_PER_LAYER = {13,10,1};
    public static final int SLEEP = 100;
    private NeuralNetwork network;
    public static final boolean LEARN = true;
    private ExpectiminimaxStar2 expectiminimaxStar2;
    private BoardEncoding encoding;
    private final int ply = 2;
    private NNTreeNode maxima;
    private static final boolean DEBUG = GameRunner.DEBUG;
    private static final String fileName = "build/classes/java/main/model/player/NNWeights2.txt";
    private static final double lambda = 0.70;
    private static final double alpha = 0.70;

    public NNAgent() {
        expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        encoding = new BoardEncoding();
        network = new NeuralNetwork(AMOUNT_OF_LAYERS, NEURONS_PER_LAYER);
        network.setRELU(0,1);
        network.setTAHN(2);
        network.setWeights(Functions.readInWeights(fileName));
//        Functions.writeWeights(network.getWeights(), fileName);

    }

    public void learn(Board board){
        ArrayList<String> states = board.getBoardStates();
        ArrayList<Double> weights = network.getWeights();
        ArrayList<Double> evals = evaluateAllBoards(states);
        ArrayList<ArrayList<Double>> gradients = getAllGradients(states);
//        for(int i = 0; i<gradients.size(); i++){
//            for(int j = 0; j<gradients.get(i).size(); j++){
//                gradients.get(i).set(j, gradients.get(i).get(j)/100);
//            }
//        }
        System.out.println("Started Learning");
        ArrayList<Double> deltaW = new ArrayList<>();
        for(int i = 0; i<weights.size(); i++){
            deltaW.add(0.0);
        }
        System.out.println(deltaW.size());
        System.out.println(weights.size());
        System.out.println(gradients.get(0).size());

        for(int i = 0; i<states.size()-1; i++){
            for(int j = 0; j<deltaW.size(); j++){
                double element = alpha * (evals.get(i + 1) - evals.get(i)) * gradientSum(gradients, i, j);
                if(Double.isNaN(element)){
                    System.err.println("Found an NaN deltaW at i = "+i+" and j = "+j);
                }
                deltaW.set(j, element);
            }
            updateWeights(weights, deltaW);
        }
        network.setWeights(weights);
        Functions.writeWeights(weights, fileName);
        System.out.println(evals);
        System.out.println(weights);
        System.out.println("Finished learning");
        board.getGameRunner().reset();
    }

    public double gradientSum(ArrayList<ArrayList<Double>> gradients, int finalIndex, int weightIndex){
        double val = 0;
        for(int i = 0; i<finalIndex; i++){
            val+=gradients.get(i).get(weightIndex);
        }
        return val;
    }

    public ArrayList<ArrayList<Double>> getAllGradients(ArrayList<String> states){
        ArrayList<ArrayList<Double>> gradients = new ArrayList<>();
        for(int i = 0; i<states.size(); i++){
            network.computeTDGradient(encoding.boardToArray2(FenEvaluator.read(states.get(i))));
            gradients.add(network.getGradient());
        }
        return gradients;
    }

    public ArrayList<Double> evaluateAllBoards(ArrayList<String> states){
        ArrayList<Double> evals = new ArrayList<>();
        for(int i = 0; i<states.size(); i++){
            evals.add(evaluation(FenEvaluator.read(states.get(i))));
        }
        return evals;
    }

    private void updateWeights(ArrayList<Double> weights, ArrayList<Double> deltaW){
        for(int i = 0; i<deltaW.size(); i++){
            weights.set(i, weights.get(i)+deltaW.get(i));
        }
    }

    public double evaluation(Board board) {
        if(!board.containsKing(true)){
            return -1;
        }
        else if(!board.containsKing(false)){
            return 1;
        }
        double[] input = encoding.boardToArray2(board);
        double[] output = network.forwardPropagate(input);
        return output[0];
    }

    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        NNTreeNode root;
        if(maxIsWhite){
            root = new NNTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, this);
        }
        else{
            root = new NNTreeNode(copy, 0, null, 2, 1, 0, 0, 0, 0, this);
        }
        expectiminimaxStar2.expectiminimax(root, (ply*2)-1, (ply*2)-1);
        double maxValue;
        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else{
            maxValue = Double.POSITIVE_INFINITY;
        }
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
            if(maxIsWhite){
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
            else{
                if (subChild.getValue() <= maxValue) {
                    if (subChild.getValue() == maxValue) {
                        highestNodes.add(subChild);
                        continue;
                    }
                    highestNodes.clear();
                    highestNodes.add(subChild);
                    maxValue = subChild.getValue();
                }
            }
        }
        Random rand = new Random();
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));
        maxima = maxNode;
    }

    public NNTreeNode getMaxima() {
        return maxima;
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
//    public void createChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite){
//        if(root.getNodeType()== 1 || root.getNodeType() == 2){
//            createChanceChildren(root, doEvaluation, maxIsWhite);
//        }
//        else if(root.getNodeType() == 3){
//            if(root.getParent().getNodeType() == 1){
//                createMinChildren(root, doEvaluation, maxIsWhite);
//            }
//            else if(root.getParent().getNodeType() == 2){
//                createMaxChildren(root, doEvaluation, maxIsWhite);
//            }
//        }
//    }
//
//    private void createChanceChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite){
//        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
//            for(ChessPiece piece: pieces){
//                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
//                    boolean[][] validMoves = piece.validMoves(root.getBoard());
//                    createChild(root, validMoves, piece, 3, doEvaluation, maxIsWhite);
//                }
//            }
//        }
//    }
//
//    private void createMaxChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
//        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());
//
//        for (Character movablePiece : movablePieces) {
//            createChanceChild(root, doEvaluation, movablePiece, 1, 1.0/movablePieces.size(), maxIsWhite);
//        }
//
//    }
//
//    private void createMinChildren(NNTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
//        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());
//
//        for (Character movablePiece : movablePieces) {
//            createChanceChild(root, doEvaluation, movablePiece, 2, 1.0/movablePieces.size(), maxIsWhite);
//        }
//    }
//
//    private void createChanceChild(NNTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability, boolean maxIsWhite){
//        Board copy = parent.getBoard().clone();
//        copy.setMovablePiece(movablePiece);
//        double value = 0;
//        if(doEvaluation){
//            value = evaluation(copy);
//        }
//        NNTreeNode child = new NNTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0);
//        parent.addChild(child);
//    }
//
//    private void createChild(NNTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation, boolean maxIsWhite){
//
//        for(int i = 0; i< validMoves.length; i++){
//            for(int j = 0; j<validMoves[0].length; j++){
//                if(validMoves[i][j]){
//                    if(isPromotion(piece, j)){
//                        createPromotionChild(parent, piece, i, j, 2, doEvaluation, maxIsWhite, nodeType);
//                        createPromotionChild(parent, piece, i, j, 3, doEvaluation, maxIsWhite, nodeType);
//                        createPromotionChild(parent, piece, i, j, 4, doEvaluation, maxIsWhite, nodeType);
//                        createPromotionChild(parent, piece, i, j, 5, doEvaluation, maxIsWhite, nodeType);
//                        continue;
//                    }
//
//                    Board copy = parent.getBoard().clone();
//                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);
//
//                    double value = 0;
//                    if(doEvaluation){
//                        value = evaluation(copy);
//                    }
//
//                    NNTreeNode child = new NNTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j);
//                    parent.addChild(child);
//                }
//            }
//        }
//    }
//
//    private void createPromotionChild(NNTreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, boolean maxIsWhite, int nodeType){
//        Board copy = parent.getBoard().clone();
//        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
//        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
//        BoardUpdater.addPiece(copy, promoted);
//
//        double value = 0;
//        if(doEvaluation){
//            value = evaluation(copy);
//        }
//
//        NNTreeNode child = new NNTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo);
//        child.setDoPromotion(true);
//        parent.addChild(child);
//    }
//
//    private boolean isPromotion(ChessPiece piece, int yTo){
//        if(piece.getPieceType()==1){
//            if(piece.isWhite()&&yTo==0){
//                return true;
//            }
//            else if(!piece.isWhite()&&yTo==7){
//                return true;
//            }
//            else{
//                return false;
//            }
//        }
//        else{
//            return false;
//        }
//    }
}