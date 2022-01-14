package model.algorithm;

import controller.Board;
import model.player.Player;
import utils.BoardEncoding;
import utils.Functions;
import utils.GABoardEncoding;
import utils.NodeEnum;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithmAgent extends Player {
    private static final String fileName = "build/classes/java/main/model/player/GAWeights.txt";
    private static final int ply = 3;
    private static final int amountOfWeights = 25;
    private static final double percentageChange = 0.05;

    private GABoardEncoding encoder;
    private ArrayList<Double> weights;
    private GeneticAlgorithmTreeNode maxima;
    private ExpectiminimaxStar2 expectiminimaxStar2;


    public GeneticAlgorithmAgent() {
        this.encoder = new GABoardEncoding();
        this.expectiminimaxStar2 = new ExpectiminimaxStar2(true);
        initializeWeights();
    }

    @Override
    public void runAgent(Board board) {
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        GeneticAlgorithmTreeNode root;
        if(maxIsWhite){
            root = new GeneticAlgorithmTreeNode(copy, 0, null, NodeEnum.MAX_NODE.getId(), 1, 0, 0, 0, 0, this);
        }
        else{
            root = new GeneticAlgorithmTreeNode(copy, 0, null, NodeEnum.MIN_NODE.getId(), 1, 0, 0, 0, 0, this);
        }
        double res = expectiminimaxStar2.expectiminimaxWithStar2(root, (ply*2)-1, (ply*2)-1);
        double maxValue;
        if(maxIsWhite){
            maxValue = Double.NEGATIVE_INFINITY;
        }
        else{
            maxValue = Double.POSITIVE_INFINITY;
        }
        ArrayList<GeneticAlgorithmTreeNode> highestNodes = new ArrayList<>();
        GeneticAlgorithmTreeNode maxNode = (GeneticAlgorithmTreeNode) root.getChildren().get(0);
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            GeneticAlgorithmTreeNode subChild = (GeneticAlgorithmTreeNode) child;
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

        this.maxima = maxNode;
    }

    @Override
    public GeneticAlgorithmTreeNode getMaxima() {
        return maxima;
    }
    public double evaluation(Board board){
        int[] encoding = encoder.evaluate(board);
        double value = 0;
        for(int i=0; i<encoding.length; i++){
            value += encoding[i]*weights.get(i);
        }
        value = value * Functions.randomNumber(1 - percentageChange, 1 + percentageChange);
        return value;
    }

    public void initializeWeights(){
        int amountOfWeights = 25;
        weights = new ArrayList<>(amountOfWeights);
        for(int i=0; i<amountOfWeights; i++){
            weights.add(Functions.randomNumber(-1,1));
        }
    }

    public ArrayList<Double> getWeights(){return weights;}

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public static int getAmountOfWeights(){
        return amountOfWeights;
    }
}
