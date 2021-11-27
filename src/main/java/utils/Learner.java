package utils;

import controller.Board;
import controller.BoardUpdater;
import model.NeuralNetwork.NeuralNetwork;
import model.player.MCTSAgent;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Learner {
    private static String fileName = "build/classes/java/main/utils/games.txt";
    private static final int FEATURES_COUNT = 315;
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

        System.out.println("started learning");
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
        System.out.println("done learning");

        Board board = new Board();
        BoardUpdater.fillGameStart(board);
        System.out.println(Arrays.toString(mctsAgent.evaluation(board)));
    }
}
