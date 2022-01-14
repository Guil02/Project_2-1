package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import gui.DebugWindow.DebugWindowStage;
import javafx.application.Platform;
import model.algorithm.Expectiminimax;
import model.algorithm.TDTreeNode;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;
import utils.FenEvaluator;
import utils.Functions;

import java.util.ArrayList;
import java.util.Random;


public class TDLearningAgent extends Player{
    public static final boolean LEARN = true;
    public static final boolean SIGMOID_ACTIVE = false;
    private ArrayList<Double> weights;
    private TDTreeNode maxima;
    private static final int ply = 2;
    public static final String fileName = "build/classes/java/main/model/player/weights.txt";
    private Expectiminimax expectiminimax = new Expectiminimax();
    private static final double lambda = 0.70;
    private static double alpha = 0.70;
    private static int sleep = 100;
    private static int amountOfGame = 0;

    public TDLearningAgent() {
        weights = Functions.readInWeights(fileName);
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

    public static ArrayList<Double> getPieces(Board board){
        ArrayList<Double> factors = new ArrayList<>();
        factors.add(0,Factor.piece_value(board, true,'p'));
        factors.add(1,Factor.piece_value(board, true,'n'));
        factors.add(2,Factor.piece_value(board, true,'b'));
        factors.add(3,Factor.piece_value(board, true,'r'));
        factors.add(4,Factor.piece_value(board, true,'q'));
        return factors;
    }

    public static double evaluationPieces(Board board, ArrayList<Double> weights){
        ArrayList<Double> factors = getPieces(board);
        double val = 0;
        if(!board.containsKing(true)){
            return -1;
        }
        else if(!board.containsKing(false)){
            return 1;
        }

        for(int i = 0; i<factors.size(); i++){
            val += factors.get(i)*weights.get(i);
        }
        return  Functions.tanh(val);
    }

    public static double evaluation(Board board, boolean whiteIsMax, ArrayList<Double> weights){
        ArrayList<Double> factors = evaluateFactors(board, whiteIsMax);
        double eval = 0;
        for(int i = 0; i<factors.size(); i++){
            eval += factors.get(i)*weights.get(i);
        }
        return Functions.tanh(eval);
    }

    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        TDTreeNode root = new TDTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0);
        expectiminimax.expectiminimax(root, (ply*2)-1, (ply*2)-1);
        double maxValue;
        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else{
            maxValue = Double.POSITIVE_INFINITY;
        }
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

    public static void learn(Board board){
        ArrayList<String> states = board.getBoardStates();
        ArrayList<Double> weights = Functions.readInWeights(fileName);
        ArrayList<Double> evals = evaluateAllBoards(states, weights);

        System.out.println("Started Learning");

        ArrayList<Double> deltaW = new ArrayList<>();
        for(int i = 0; i<5; i++){
            deltaW.add(0.0);
        }

        for(int i = 0; i<states.size()-1; i++){
            for(int j = 0; j<deltaW.size(); j++){
                deltaW.set(j, alpha*(evals.get(i+1)-evals.get(i))*gradientSum(i,evals, j, board));
            }
            updateWeights(weights, deltaW);
        }
        Functions.writeWeights(weights, fileName);
        System.out.println(evals);
        System.out.println(weights);
        System.out.println("Finished learning");
        boolean foundNaN = false;
        for (Double weight : weights) {
            foundNaN = Double.isNaN(weight);
        }
        if(!foundNaN){
            board.getGameRunner().reset();
        }
        else{
            System.out.println("retry learning");
            learn(board);
        }
    }

    public static void updateWeights(ArrayList<Double> weights, ArrayList<Double> deltaW){
        for(int i = 0; i<deltaW.size(); i++){
            weights.set(i, weights.get(i)+deltaW.get(i));
        }
    }

    public static double gradientSum(int finalIndex, ArrayList<Double> evals, int weightIndex, Board board){
        double val = 0;
        ArrayList<Double> factors = evaluateFactors(board, true);
        for(int i = 0; i<finalIndex; i++){
            val+= Math.pow(lambda, finalIndex-i)*Functions.tanhDeriv(evals.get(i))*factors.get(weightIndex);
        }
        return val;
    }

    public static ArrayList<Double> evaluateAllBoards(ArrayList<String> states, ArrayList<Double> weights){
        ArrayList<Double> evals = new ArrayList<>();
        for(int i = 0; i<states.size(); i++){
            evals.add(evaluationPieces(FenEvaluator.read(states.get(i)), weights));
        }
        return evals;
    }


//    public void launch(Board board){
//        new Thread(() -> {
//            try{
//
//                // Stop if game is on pause
//                if (DebugWindowStage.isOnPause) {
//                    pauseThread();
//                }
//                runAgent(board);
//                TDTreeNode move = getMaxima();
//                if(move.isDoPromotion()){
//                    board.storeMove();
//                    BoardUpdater.runPromotion(board, move.getBoard(), move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
//                    if(Config.GUI_ON){
//                        Platform.runLater(
//                                new Thread(board::launchGuiUpdate)
//                        );
//                    }
//                }
//                else{
////                    if(!Board.GUI_ON) printBoard(board.getBoardModel(), board);
//                    BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
//                    if(Config.GUI_ON){
//                        Platform.runLater(
//                                new Thread(board::launchGuiUpdate)
//                        );
//                    }
//                }
//            }
//            catch(Exception e){
//                System.err.println("Piece might already have been moved due to glitch in the threading");
//                e.printStackTrace();
//            }
//        }).start();
//    }
/*
    public TDTreeNode getMaxima() {
        return maxima;
    }
*/
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

/*
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

*/
}
