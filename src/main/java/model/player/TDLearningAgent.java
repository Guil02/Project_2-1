package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import controller.GameRunner;
import javafx.application.Platform;
import model.algorithm.Expectiminimax;
import model.algorithm.TDTreeNode;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;
import utils.FenEvaluator;
import utils.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class TDLearningAgent extends Player{
    public static final boolean LEARN = false;
    public static final boolean SIGMOID_ACTIVE = false;
    private ArrayList<Double> weights;
    private TDTreeNode maxima;
    private static final int ply = 2;
    private static final String fileName = "build/classes/java/main/model/player/weights.txt";
    private Expectiminimax expectiminimax = new Expectiminimax();
    private static final double lambda = 0.70;
    private static double alpha = 1;
    private static int amountOfGame = 0;

    public TDLearningAgent() {
        weights = readInWeights();
    }

    public static ArrayList<Double> evaluateFactors(Board board, boolean whiteIsMax){
        ArrayList<Double> factors = new ArrayList<>(33);
        factors.add(0,Factor.piece_value(board, whiteIsMax, 'p'));
        factors.add(1,Factor.piece_value(board, whiteIsMax, 'n'));
        factors.add(2,Factor.piece_value(board, whiteIsMax, 'b'));
        factors.add(3,Factor.piece_value(board, whiteIsMax, 'r'));
        factors.add(4,Factor.piece_value(board, whiteIsMax, 'q'));
        factors.add(5,Factor.pawn_doubled_penalty_value(board, whiteIsMax));
        factors.add(6,Factor.pawn_isolated_penalty_value(board, whiteIsMax));
        factors.add(7,Factor.pawn_central(board, whiteIsMax));
        factors.add(8,Factor.piece_mobility(board,whiteIsMax,'n'));
        factors.add(9,Factor.piece_mobility(board,whiteIsMax,'b'));
        factors.add(10,Factor.piece_mobility(board,whiteIsMax,'r'));
        factors.add(11,Factor.piece_mobility(board,whiteIsMax,'q'));
        factors.add(12,Factor.piece_mobility(board,whiteIsMax,'k'));
        factors.add(13,Factor.passedPawn(board, whiteIsMax));
        factors.add(14,Factor.rooks_on_seventh_rank(board, whiteIsMax));
        factors.add(15,Factor.knight_periphery_0(board, whiteIsMax));
        factors.add(16,Factor.knight_periphery_1(board, whiteIsMax));
        factors.add(17,Factor.knight_periphery_2(board, whiteIsMax));
        factors.add(18,Factor.knight_periphery_3(board, whiteIsMax));
        factors.add(19,Factor.attacking_king(board, whiteIsMax));



        return factors;
    }


    public static double evaluation(Board board, boolean whiteIsMax, ArrayList<Double> weights){
        if((whiteIsMax&&!board.containsKing(!whiteIsMax))||(!whiteIsMax&&!board.containsKing(whiteIsMax))){
            if(SIGMOID_ACTIVE){
                return(sigmoid(1000));
            }
            else return normalize(1000);
        }
        else if((whiteIsMax&&!board.containsKing(whiteIsMax))||(!whiteIsMax&&!board.containsKing(!whiteIsMax))){
            if(SIGMOID_ACTIVE){
                return(sigmoid(-1000));
            }
            else return normalize(-1000);
        }
        ArrayList<Double> factors = evaluateFactors(board, whiteIsMax);
        double eval = 0;
        for(int i = 0; i<factors.size(); i++){
            eval += factors.get(i)*weights.get(i);
        }
        if(SIGMOID_ACTIVE){
            return sigmoid(eval);
        }
        else return normalize(eval);
    }

    public static ArrayList<Double> readInWeights(){
        String line;
        ArrayList<Double> weights = new ArrayList<>();
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferreader.readLine()) != null) {
                weights.add(Double.parseDouble(line));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        System.out.println("------------------------------------------------------------------------------------");
//        System.out.println("Read in weights: "+weights);
        return weights;
    }

    public static void writeWeights(ArrayList<Double> weights){
        try(FileWriter fileWriter = new FileWriter(fileName, false)) {
            for (Double weight : weights) {
                fileWriter.write(weight + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChildren(TDTreeNode root, boolean doEvaluation, boolean maxIsWhite){
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

    private void createChanceChildren(TDTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 3, doEvaluation, maxIsWhite);
                }
            }
        }
    }

    private void createMaxChildren(TDTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 1, 1.0/movablePieces.size(), maxIsWhite);
        }

    }

    private void createMinChildren(TDTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 2, 1.0/movablePieces.size(), maxIsWhite);
        }
    }

    private void createChanceChild(TDTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability, boolean maxIsWhite){
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        if(doEvaluation){
            value = evaluation(copy, maxIsWhite, weights);
        }
        TDTreeNode child = new TDTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0, parent.isMaxIsWhite(), parent.getTdLearning());
        parent.addChild(child);
    }

    private void createChild(TDTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation, boolean maxIsWhite){

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
                        value = evaluation(copy, maxIsWhite, weights);
                    }

                    TDTreeNode child = new TDTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j, parent.isMaxIsWhite(), parent.getTdLearning());
                    parent.addChild(child);
                }
            }
        }
    }

    private void createPromotionChild(TDTreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, boolean maxIsWhite, int nodeType){
        Board copy = parent.getBoard().clone();
        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
        BoardUpdater.addPiece(copy, promoted);

        double value = 0;
        if(doEvaluation){
            value = evaluation(copy, maxIsWhite, weights);
        }

        TDTreeNode child = new TDTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo, parent.isMaxIsWhite(), parent.getTdLearning());
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

    private double getPieceValue(int pieceType){
        return switch (pieceType) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 9;
            case 6 -> 0;
            default -> 0;
        };
    }

    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        TDTreeNode root = new TDTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite, this);
        expectiminimax.expectiminimax(root, (ply*2)-1, (ply*2)-1);
        double maxValue = Double.MIN_VALUE;
        ArrayList<TDTreeNode> highestNodes = new ArrayList<>();
        TDTreeNode maxNode;
        try{
            maxNode = (TDTreeNode) root.getChildren().get(0);
        }
        catch(Exception e){
            maxNode = root;
        }
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            TDTreeNode subChild = (TDTreeNode) child;
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
            try{
                if(ply<3){
                    Thread.sleep(100);
                }
                runAgent(board);
                TDTreeNode move = getMaxima();
                if(move.isDoPromotion()){
                    board.storeMove();
                    BoardUpdater.runPromotion(board, move.getBoard(), move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if(Config.GUI_ON){
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                }
                else{
//                    if(!Board.GUI_ON) printBoard(board.getBoardModel(), board);
                    BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if(Config.GUI_ON){
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
        }).start();
    }

    public TDTreeNode getMaxima() {
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

    public static void learn(Board board){
        System.out.println(java.lang.Thread.activeCount());
        amountOfGame++;
        if(amountOfGame<50) {
            System.out.println("Started learning");
            ArrayList<String> states = board.getBoardStates();
            ArrayList<ArrayList<String>> subDividedStates = FenEvaluator.separateWhiteAndBlack(states);
        ArrayList<String> white = subDividedStates.get(0);
        states = white;
//        ArrayList<String> black = subDividedStates.get(1);
            ArrayList<Double> weights = readInWeights();
            System.out.println("initial weights: " + weights);

            ArrayList<Double> weightChange = new ArrayList<>();
            for (int i = 0; i < weights.size(); i++) {
                weightChange.add(0.0);
            }

        for(int i = 0; i< states.size()-1; i++){
            ArrayList<Double> gradient = gradient(states.get(i), true, weights);
            double mul = 0;
            for(int j = i; j<states.size()-1; j++){
                double pow = Math.pow(lambda, j - i);
                double temporalDifference = getTemporalDifference(states.get(j), states.get(j + 1), true, weights);
                double v = pow * temporalDifference;
                mul = mul + v;
            }
            ArrayList<Double> arr2 = Matrix.varMultiplication(gradient, mul);
            weightChange = Matrix.additionVector(weightChange, arr2);
        }

            weightChange = Matrix.varMultiplication(weightChange, alpha);

            weights = Matrix.additionVector(weights, weightChange);
            writeWeights(weights);
            System.out.println("weights change: " + weightChange);
            System.out.println("new weights: " + weights);
            board.getGameRunner().reset();
        }
    }

    public static ArrayList<Double> gradient(String fen, boolean whiteIsMax, ArrayList<Double> weights){
        Board board = FenEvaluator.read(fen);
        ArrayList<Double> val = evaluateFactors(board, whiteIsMax);
        ArrayList<Double> deriv = new ArrayList<>();
        if(SIGMOID_ACTIVE){
            double evaluation = evaluation(board, whiteIsMax, weights);
            for(int i = 0; i<val.size(); i++){
                double x = evaluation *(1- evaluation)*val.get(i);
                deriv.add(i, x);
            }
        }
        else{
            for(int i = 0; i<val.size(); i++){
                double x = 1/(max-min)* val.get(i);
                deriv.add(i, x);
            }
        }

        return deriv;
    }

    private static final double max = 1000;
    private static final double min = -1000;
    public static double normalize(double x){
        double res = (x-min)/(max-min);
        return res;
    }

    public static double sigmoid(double x){
        double res = 1.0/(1-Math.exp(-x));
        return res;
    }

    public static double getTemporalDifference(String fen1, String fen2, boolean maxIsWhite, ArrayList<Double> weights){
        Board xFirst = FenEvaluator.read(fen1);
        Board xLast = FenEvaluator.read(fen2);

        double eval1 = evaluation(xFirst, maxIsWhite, weights);
        double eval2 = evaluation(xLast, maxIsWhite, weights);

        return eval2-eval1;
    }

    public static void printEvaluations(ArrayList<String> fens, boolean maxIsWhite, ArrayList<Double> weights){
        for(String s : fens){
            Board board = FenEvaluator.read(s);
            System.out.println(evaluation(board, maxIsWhite, weights));
        }
    }
}
