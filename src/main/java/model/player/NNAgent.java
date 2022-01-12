package model.player;

import config.Config;
import controller.Board;
import controller.GameRunner;
import model.NeuralNetwork.NeuralNetwork;
import model.algorithm.ExpectiminimaxStar2;
import model.algorithm.NNTreeNode;
import model.algorithm.TreeNode;

import utils.BoardEncoding;
import utils.FenEvaluator;
import utils.Functions;
import utils.NodeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NNAgent extends Player {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 1;
    private static final int AMOUNT_OF_LAYERS = 3;
    private static final int[] NEURONS_PER_LAYER = {13,10,1};
    public static final int SLEEP = 100;
    private static NeuralNetwork network;
    public static final boolean LEARN = true;
    private ExpectiminimaxStar2 expectiminimaxStar2;
    private BoardEncoding encoding;
    private final int ply = 2;
    private NNTreeNode maxima;
    private static final boolean DEBUG = Config.DEBUG;
    private static final String fileName = "build/classes/java/main/model/player/NNWeights2.txt";
    private static final double lambda = 0.70;
    private static final double alpha = 0.10;
    private static final double gamma = 0.70;
    private static boolean initialized = false;

    public NNAgent() {
        expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        encoding = new BoardEncoding();
        if(!initialized){
            network = new NeuralNetwork(AMOUNT_OF_LAYERS, NEURONS_PER_LAYER);
            network.setRELU(0,1);
            network.setTANH(2);
            network.setWeights(Functions.readInWeights(fileName));
            System.out.println(Functions.readInWeights(fileName));
            initialized = true;
        }
//        Functions.writeWeights(network.getWeights(), fileName);

    }



    public void learn(Board board){
        if(DEBUG) {
            System.out.println("Started learning");
        }
        ArrayList<String> states = board.getBoardStates();
        printEvaluations(states);
        ArrayList<Double> weights = network.getWeights();
        ArrayList<Double> z = initializeZ(weights);

        int amountOfTurns = states.size();
        int finalIteration = amountOfTurns-1;
        Board S = FenEvaluator.read(states.get(0));

        for(int i = 1; i<amountOfTurns; i++){
            double R = giveReward(board, i, finalIteration);
            Board newS = FenEvaluator.read(states.get(i));

            evaluateZ(z, S);

            double delta = evaluateDelta(R, S, newS);

            updateWeights(weights, z, delta);

            S = newS;
        }
        network.setWeights(weights);
        Functions.writeWeights(weights, fileName);
        if(DEBUG) {
            System.out.println("new weights: " + weights);
        }
//        board.getGameRunner().reset();
    }

    private void printEvaluations(ArrayList<String> states) {
        for(int i = 0; i<states.size(); i++){
            System.out.println(evaluation(FenEvaluator.read(states.get(i))));
        }
    }

    private void updateWeights(ArrayList<Double> weights, ArrayList<Double> z, double delta){
        int amountOfWeights = weights.size();
        for(int i = 0; i<amountOfWeights; i++){
            double newValue = weights.get(i) + alpha*delta*z.get(i);
            weights.set(i, newValue);
        }
    }

    private double evaluateDelta(double r, Board s, Board newS) {
        double outputState = evaluation(s);
        double outputNewState = evaluation(newS);
        double delta = r + gamma*outputNewState - outputState;
        return delta;
    }

    private void evaluateZ(ArrayList<Double> z, Board state){
        int amountOfWeights = z.size();

        double[] input = encoding.boardToArray2(state);
        network.computeTDGradient(input);
        ArrayList<Double> gradient = network.getGradient();
//        System.out.println("Gradient: "+gradient);
        for(int i = 0; i<amountOfWeights; i++){
            double newValue = gamma*lambda*z.get(i) + gradient.get(i);
            z.set(i, newValue);
        }

    }



    private int giveReward(Board board, int index, int finalIteration) {
        if(index==finalIteration){
            if(whiteWinGame(board)) {
                return 1;
            }
            else return -1;
        }
        else return 0;
    }


    public boolean whiteWinGame(Board board){
        return board.containsKing(true);
    }



    public void learnt(Board board){
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
//        System.out.println(deltaW.size());
//        System.out.println(weights.size());
//        System.out.println(gradients.get(0).size());

        for(int i = 0; i<states.size()-1; i++){
            for(int j = 0; j<deltaW.size(); j++){
                double element = alpha * (evals.get(i + 1) - evals.get(i)) * gradientSum(gradients, i, j);
                if(Double.isNaN(element)){
                    throw new RuntimeException("Found an NaN deltaW at i = "+i+" and j = "+j);
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
        for(int i = 0; i<finalIndex+1; i++){
            val+=Math.pow(lambda, finalIndex-i)*gradients.get(i).get(weightIndex);
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
            root = new NNTreeNode(copy, 0, null, NodeEnum.MAX_NODE.getId(), 1, 0, 0, 0, 0, this);
        }
        else{
            root = new NNTreeNode(copy, 0, null, NodeEnum.MIN_NODE.getId(), 1, 0, 0, 0, 0, this);
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

    private ArrayList<Double> initializeZ(ArrayList<Double> weights) {
        ArrayList<Double> z = new ArrayList<>();
        for(int i = 0; i< weights.size(); i++){
            z.add(0.0);
        }
        return z;
    }
}