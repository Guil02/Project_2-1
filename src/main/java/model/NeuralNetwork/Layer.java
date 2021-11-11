package model.NeuralNetwork;

public class Layer {
    private Neuron[] neurons;
    public Layer(int n_inputs, int n_neurons) {
        neurons = new Neuron[n_neurons];
        for(int i = 0; i< n_neurons; i++){
            neurons[i]= new Neuron(n_inputs);
        }
    }

    public double[] forward(double[] inputs){
        double[] output = new double[neurons.length];
        for(int i = 0; i<neurons.length; i++){
            output[i] = neurons[i].forward(inputs);
        }
        return output;
    }
}
