package model.NeuralNetwork;

import org.junit.jupiter.api.Test;
import utils.Functions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    @Test
    public void forwardPassTest1(){
        NeuralNetwork network = new NeuralNetwork(2, new int[]{2,1});
        ArrayList<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        network.setWeights(weights);
        network.setBias(0);
        network.setTANH(0,1);

        double[] input = {2, 1};
        double[] res = network.forwardPropagate(input);
        double expected = Functions.tanh(4);
        double actual = res[0];
        assertEquals(expected, actual);
    }

    @Test
    public void forwardPassTest2(){
        NeuralNetwork network = new NeuralNetwork(3, new int[]{2,2,1});
        ArrayList<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        weights.add(3.0);
        weights.add(4.0);
        weights.add(5.0);
        weights.add(6.0);
        network.setWeights(weights);
        network.setBias(0);
        network.setRELU(0,1,2);

        double[] input = {2, 1};
        double[] res = network.forwardPropagate(input);
        double expected = 80;
        double actual = res[0];
        assertEquals(expected, actual);
    }

    @Test
    public void backPassTest1(){
        NeuralNetwork network = new NeuralNetwork(2, new int[]{2,1});
        ArrayList<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        network.setWeights(weights);
        network.setBias(0);
        network.setTANH(0,1);

        double[] input = {2, 1};
        network.computeGradient(input);

        ArrayList<Double> expected = new ArrayList<>();
        double val1 = 2*Functions.tanhDeriv(4);
        double val2 = 1*Functions.tanhDeriv(4);
        expected.add(val1);
        expected.add(val2);

        ArrayList<Double> actual = network.getGradient();
        assertEquals(expected, actual);
    }

    @Test
    public void backPassTest2(){
        NeuralNetwork network = new NeuralNetwork(3, new int[]{2,2,1});
        ArrayList<Double> weights = new ArrayList<>();
        weights.add(1.0);
        weights.add(2.0);
        weights.add(3.0);
        weights.add(4.0);
        weights.add(5.0);
        weights.add(6.0);
        network.setWeights(weights);
        network.setBias(0);
        network.setTANH(0,1,2);

        double[] input = {2, 1};
        network.computeGradient(input);

        ArrayList<Double> expected = new ArrayList<>();
        double v3 = Functions.tanhDeriv(Functions.tanh(4) * 5 + Functions.tanh(10) * 6);
        double val5=v3*Functions.tanh(4);
        double val6= v3*Functions.tanh(10);

        double v1 = Functions.tanhDeriv(4);
        double val1=2*v1*(v3*5);
        double val2=1*v1*(v3*5);

        double v2 = Functions.tanhDeriv(10);
        double val3=2*v2*(v3*6);
        double val4=1*v2*(v3*6);

        expected.add(val1);
        expected.add(val2);
        expected.add(val3);
        expected.add(val4);
        expected.add(val5);
        expected.add(val6);

        ArrayList<Double> actual = network.getGradient();

        assertEquals(expected, actual);
    }

}