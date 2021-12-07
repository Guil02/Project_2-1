package model.NeuralNetwork;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.PawnPiece;
import model.player.MCTSAgent;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    @Test
    void boardToArray() {
        Board board = new Board();
        BoardUpdater.fillGameStart(board);
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        double[] val = neuralNetwork.boardToArray(board);
        BoardUpdater.movePiece(board,4,6, 4,4);
        double[] val2 = neuralNetwork.boardToArray(board);
        double[][] twoPos = new double[2][val.length];
        twoPos[0]=val;
        twoPos[1]=val2;
        double[][] label = new double[2][2];
        label[0][0]=1;
        label[0][1]=0;
        label[1][0]=0;
        label[1][1]=1;
        INDArray tens = Nd4j.create(twoPos);
        INDArray labels = Nd4j.create(label);
        DataSet data = new DataSet(tens, labels);
        double[][] temp = new double[1][val.length];
        temp[0]=val;
        neuralNetwork.network(data, Nd4j.create(temp));
//        System.out.println(tens);
    }

    @Test
    public void testMCTS(){
        MCTSAgent mctsAgent = new MCTSAgent();
        Board board = new Board();
        BoardUpdater.fillGameStart(board);
        mctsAgent.evaluation(board);
        for(int i = 0; i<1000; i++){
            System.out.println(Arrays.toString(mctsAgent.evaluation(board)));
        }
    }
}