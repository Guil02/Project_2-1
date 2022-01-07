package model.NeuralNetwork;

import utils.Functions;

import java.util.Arrays;

public class Layer {
    private Neuron[] neurons;

    /**
     * constructor for hidden and output layer
     * @param n_in number of incoming neurons
     * @param n_out number of neurons in the layer, thus the output.
     */
    public Layer(int n_in, int n_out) {
        this.neurons = new Neuron[n_out];

        for(int i = 0; i<n_out; i++){
            double[] weights = new double[n_in];
            for(int j = 0; j<n_in; j++){
                weights[j] = Functions.randomNumber(Neuron.minWeight, Neuron.maxWeight);
            }
            neurons[i] = new Neuron(weights, Functions.randomNumber(0,1));
        }
    }

    /**
     * constructor for input layer
     * @param input input values.
     */
    public Layer(double[] input) {
        this.neurons = new Neuron[input.length];
        for(int i = 0; i<input.length; i++){
            this.neurons[i] = new Neuron(input[i]);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "neurons=" + Arrays.toString(neurons) +
                '}';
    }
}
