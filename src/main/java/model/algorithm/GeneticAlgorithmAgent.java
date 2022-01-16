package model.algorithm;

import controller.Board;
import model.player.Player;
import utils.BoardEncoding;
import utils.Functions;
import utils.GABoardEncoding;
import utils.NodeEnum;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithmAgent extends Player {
    private static final String fileName = "build/classes/java/main/model/player/GAWeights.txt";
    private static final int ply = 2;
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
        ArrayList<Double> list = new ArrayList<>(Arrays.asList(0.5816964543869012, 0.29985506418720764, -0.7754863280313109, 0.7652046482261382, -0.08174013270198195, 0.9686098058092883, -0.39048348336643657, 0.8757113996491488, -0.6354598370631677, 0.34340780347023636, -0.7369067962848918, 0.5590110331645366, -0.6555757559499904, 0.046757850067840875, -0.7189454104638863, 0.03713018141564955, -0.3217208396190523, 0.9575906668344256, -0.3021225764672464, 0.03511293148418518, -0.16525911470042975, 0.18930925941731636, -0.10961655557042516, 0.5101322564782641, -0.4278780417634781));
        setWeights(list);
        System.out.println("GA: " + weights);
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

    public void setWeight(int i, double weight) {
        this.weights.set(i, weight);
    }

    public static int getAmountOfWeights(){
        return amountOfWeights;
    }
}
