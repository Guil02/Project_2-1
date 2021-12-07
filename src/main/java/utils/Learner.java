package utils;

import controller.Board;
import controller.BoardUpdater;
import controller.GameRunner;
import model.NeuralNetwork.NeuralNetwork;
import model.player.MCTSAgent;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Learner {
    private static final boolean temp = false;
    public static final int AMOUNT_OF_MOVES = 13;
    private static String fileName = "build/classes/java/main/utils/games.txt";
    private static final int FEATURES_COUNT = 315;
    private static final int BATCH_SIZE = 1;
    private static final double LAMBDA = 0.7;
    private static final boolean DEBUG = GameRunner.DEBUG;
    public Learner() {
        MCTSAgent mctsAgent = new MCTSAgent();
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        String line;
        ArrayList<ArrayList<String>> games = new ArrayList<>();
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferreader.readLine()) != null) {
                ArrayList<String> fens = new ArrayList<>();
                while(!line.isEmpty()){
                    fens.add(line);
                    line = bufferreader.readLine();
                }

                games.add(fens);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int length = 0;
        for(int i = 0; i<games.size(); i++){
            length+=games.get(i).size();
        }
        double[][] labels = new double[length][2];
        double[][] features = new double[length][FEATURES_COUNT];
        int index = 0;
        for(int i = 0; i< games.size(); i++){
            Board endBoard = FenEvaluator.read(games.get(i).get(games.get(i).size()-1));
            double[] label = {0,0};
            if(!BoardUpdater.containsKing(endBoard, true)){
                label = new double[]{1, -1};
            }
            else if(!BoardUpdater.containsKing(endBoard, false)){
                label = new double[]{-1, 1};
            }
            for(int j = 0; j<games.get(i).size(); j++){
                features[index] = neuralNetwork.boardToArray(FenEvaluator.read(games.get(i).get(j)));
                labels[index] = label;
                index++;
            }
        }
        if(temp){
            if(DEBUG) System.out.println("started learning");
            for(int i = 0; i<features.length; i++){
                double[][] temp = new double[1][features[i].length];
                temp[0]=features[i];

                double[][] temp2 = new double[1][labels[i].length];
                temp2[0] = labels[i];
                INDArray featuresArray = Nd4j.create(temp);
                INDArray labelsArray = Nd4j.create(temp2);
                DataSet data = new DataSet(featuresArray, labelsArray);
                mctsAgent.learn(data);
            }
            if(DEBUG) System.out.println("done learning");
        }
        else{
//            double[][] batch = new double[BATCH_SIZE][FEATURES_COUNT];
            ArrayList<String> games1d = to1D(games);
            Board[] batch = new Board[BATCH_SIZE];
            double[][] batchLabels = new double[BATCH_SIZE][2];
            ArrayList<Integer> alreadySeen = new ArrayList<>();
            for(int i = 0; i<BATCH_SIZE; i++){
                int pos = ((int)(Math.random()*features.length))-1;
                if(alreadySeen.contains(pos)){
                    i--;
                    continue;
                }
                alreadySeen.add(pos);
                int ind = 0;
                batch[i]=FenEvaluator.read(games1d.get(pos));
                batchLabels[i]=labels[pos];
            }
            ArrayList<Board> errorBoards = new ArrayList<>();
            ArrayList<Double> error = new ArrayList<>();
            if(DEBUG) System.out.println("start batch");
            for(int i = 0; i<BATCH_SIZE; i++){
                if(DEBUG) System.out.println("i: "+i);
                ArrayList<Board> moves = new ArrayList<>();
                moves.add(batch[i]);
                double[][] evaluation = new double[AMOUNT_OF_MOVES][1];
                for(int j = 1; j< AMOUNT_OF_MOVES; j++){
                    moves.add(mctsAgent.doMove(moves.get(j-1)));
                    evaluation[j] = mctsAgent.evaluation(moves.get(j));
                    if(moves.get(j).getGameOver()){
                        break;
                    }
                }
                if(DEBUG) System.out.println("start error calc");
                for(int j = 1; j< moves.size(); j++){
                    double totalError = 0;
                    for(int k = j; k<moves.size(); k++){
                        totalError += (evaluation[k][0]-evaluation[k-1][0]) *Math.pow(LAMBDA, (k-j));
                    }
                    errorBoards.add(moves.get(j));
                    error.add(totalError);
                }
            }
            if(DEBUG) System.out.println("done calculating error");
            mctsAgent.learn(errorBoards, error);
        }

        Board board = new Board();
        BoardUpdater.fillGameStart(board);
        System.out.println(Arrays.toString(mctsAgent.evaluation(board)));
    }

    public ArrayList<String> to1D(ArrayList<ArrayList<String>> list){
        ArrayList<String> res = new ArrayList<>();
        for (ArrayList<String> objects : list) {
            res.addAll(objects);
        }
        return res;
    }
}
