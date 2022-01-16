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
        ArrayList<Double> list = new ArrayList<>(Arrays.asList(0.9230043043412133, 0.9579294123388052, -0.8987613217288711, 0.4787569929049993, -0.4790921630275934, 0.15535815190012636, -0.4041549817045518, 0.29384638295086907, -0.9143841863508754, 0.4815323582427779, -0.3687284975933644, 0.9627888714096052, -0.6306082672037663, 0.03971704345680872, -0.8078936176416487, 0.030708023854975505, -0.28961485413418714, 1.0081540647618508, -0.3185625100333636, 0.03706889958347246, -0.16724065938440583, 0.18930925941731636, -0.1261355128325475, 0.6524905058467498, -0.7082077071566959));
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
        if(!board.containsKing(true)){
            return -100;
        }
        else if(!board.containsKing(false)){
            return 100;
        }
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
