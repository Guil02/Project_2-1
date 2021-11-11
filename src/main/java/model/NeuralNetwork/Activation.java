package model.NeuralNetwork;

public class Activation {
    public static double[] activateReLU(double[] inputs){
        double[] outputs = new double[inputs.length];

        for(int i = 0; i < inputs.length; i++){
            outputs[i] = Math.max(0, inputs[i]);
        }

        return outputs;
    }

    // (e^x – e^-x) / (e^x + e^-x)
    public static double[] activateHyperbolicTangent(double[] inputs){
        double[] outputs = new double[inputs.length];
        for(int i = 0; i < inputs.length; i++){
            outputs[i] = (Math.exp(inputs[i]) - Math.exp(-inputs[i])) / (Math.exp(inputs[i]) + Math.exp(-inputs[i]));
        }

        return outputs;
    }
}
