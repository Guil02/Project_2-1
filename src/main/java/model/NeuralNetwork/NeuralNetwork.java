package model.NeuralNetwork;

import java.util.Arrays;

public class NeuralNetwork {
    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.network();
    }
    public NeuralNetwork() {
    }

    public void network(){
        int n_inputs = 2;
        int n_neurons = 3;
        Layer layer1 = new Layer(n_inputs, n_neurons);
        double[] inputs = {-2,2};
        double[] forward = layer1.forward(inputs);
        double[] res = Activation.activateHyperbolicTangent(forward);
        System.out.println(Arrays.toString(forward));
        System.out.println(Arrays.toString(res));
    }
}
