package model.NeuralNetwork;

import utils.Matrix;

import java.util.Arrays;

public class Neuron {
    double[] weights;
    double bias;
    public Neuron(int n_inputs) {
        weights = new double[n_inputs];
        for(int i = 0; i<n_inputs; i++){
            weights[i]=Math.random();
        }
        bias = 0;
    }

    public double forward(double[] inputs){
        return Matrix.dotProduct(inputs, weights);
    }
}
