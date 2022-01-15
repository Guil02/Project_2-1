package model.NeuralNetwork;


import utils.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 2;


    public static final boolean DEBUG = false;
    private Layer[] layers;

    public NeuralNetwork(int amountOfLayers, int[] neuronsPerLayer, double min, double max) {
        Neuron.setWeightRange(min,max);
        layers = new Layer[amountOfLayers];
        for(int i = 1; i<amountOfLayers; i++){
            layers[i]= new Layer(neuronsPerLayer[i-1], neuronsPerLayer[i]);
        }
    }

    public NeuralNetwork(int amountOfLayers, int[] neuronsPerLayer) {
        Neuron.setWeightRange(-1,1);
        layers = new Layer[amountOfLayers];
        layers[0] = new Layer(new double[neuronsPerLayer[0]]);
        for(int i = 1; i<amountOfLayers; i++){
            layers[i]= new Layer(neuronsPerLayer[i-1], neuronsPerLayer[i]);
        }
    }

    public double[] forwardPropagate(double[] input){
        layers[0] = new Layer(input);
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                double val = calculateNeuronValue(i, j);
                if(DEBUG){
                    System.out.println("val: "+(val-layers[i].getNeurons()[j].getBias()));
                    System.out.println("val+bias: "+val);
                    System.out.println("val+bias+tanh: "+ Functions.tanh(val));
                }

                layers[i].getNeurons()[j].setValue(doActivation(val, layers[i].getActivation()));
            }
        }
        double[] output = new double[layers[layers.length-1].getNeurons().length];
        for(int i = 0; i<output.length; i++){
            output[i] = layers[layers.length-1].getNeurons()[i].getValue();
        }
        return output;
    }

    private double calculateNeuronValue(int i, int j) {
        double val = 0;
        for(int k = 0; k<layers[i -1].getNeurons().length; k++){
            val += layers[i -1].getNeurons()[k].getValue()*layers[i].getNeurons()[j].getWeight()[k];
        }
        val += layers[i].getNeurons()[j].getBias();
        return val;
    }

//    public void computeTDGradient(double[] input){
//        forwardPropagate(input);
//        for(int i = layers.length-1; i>0; i--){
//            for(int j = 0; j<layers[i].getNeurons().length; j++){
//                Neuron current = layers[i].getNeurons()[j];
//                double val = calculation(i, j);
//
//                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
//                    double gradient = layers[i - 1].getNeurons()[k].getValue() * doDerivativeActivation(val, layers[i].getActivation());
//                    current.setWeight_gradient(gradient,k);
//                }
//            }
//        }
//    }

    public void computeTDGradient(double[] input){
        forwardPropagate(input);
        for(int i = layers.length-1; i>0; i--){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                Neuron current = layers[i].getNeurons()[j];
                double val = calculateNeuronValue(i,j);

                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
                    double derivativeActivation = doDerivativeActivation(val, layers[i].getActivation());
                    double temp_grad = current.getWeight()[k]*derivativeActivation*current.getTemp_grad();
                    temp_grad = temp_grad + layers[i-1].getNeurons()[k].getTemp_grad();
                    layers[i-1].getNeurons()[k].setTemp_grad(temp_grad);
                    double gradient;
                    if( i == layers.length-1){
                        gradient = layers[i-1].getNeurons()[k].getValue()*derivativeActivation;
                    }
                    else{
                        gradient = layers[i-1].getNeurons()[k].getValue()*derivativeActivation*current.getTemp_grad();
                    }

                    current.setWeight_gradient(gradient, k);
                }
            }
        }
    }


    public void computeGradient(double[] input){
        forwardPropagate(input);

        resetTempGrad();

        for(int i = layers.length-1; i>0; i--){

            int amountOfNeurons = layers[i].getNeurons().length;
            for(int j = 0; j<amountOfNeurons; j++){

                Neuron current = layers[i].getNeurons()[j];
                double val = calculateNeuronValue(i,j);

                double bias_gradient;
                if(i==layers.length-1){
                    bias_gradient = doDerivativeActivation(val, layers[i].getActivation());
                }
                else {
                    bias_gradient = doDerivativeActivation(val, layers[i].getActivation())*current.getTemp_grad();
                }
                current.setBias_gradient(bias_gradient);


                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
                    Neuron previousNeuron = layers[i - 1].getNeurons()[k];

                    double previousValue = previousNeuron.getValue();
                    double derivative = doDerivativeActivation(val, layers[i].getActivation());

                    double temp_grad = previousNeuron.getTemp_grad() + current.getWeight()[k]*derivative;

                    double gradient;
                    if(i==layers.length-1){
                        gradient = previousValue * derivative;
                    }
                    else {
                        gradient = previousValue * derivative * current.getTemp_grad();
                    }


                    previousNeuron.setTemp_grad(temp_grad);
                    current.setWeight_gradient(gradient, k);

                }
            }
        }
    }

    private void resetTempGrad() {
        for(int i = 0; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                layers[i].getNeurons()[j].setTemp_grad(0);
            }
        }
    }


    public ArrayList<Double> getGradient(){
        ArrayList<Double> gradient = new ArrayList<>();
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                gradient.add(layers[i].getNeurons()[j].getBias_gradient());
                for(int k = 0; k<layers[i].getNeurons()[j].getGradient().length; k++){
                    gradient.add(layers[i].getNeurons()[j].getGradient()[k]);
                }
            }
        }
        return gradient;
    }

    public ArrayList<Double> getWeights(){
        ArrayList<Double> weights = new ArrayList<>();
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                weights.add(layers[i].getNeurons()[j].getBias());
                for(int k = 0; k<layers[i].getNeurons()[j].getWeight().length; k++){
                    weights.add(layers[i].getNeurons()[j].getWeight()[k]);
                }
            }
        }
        return weights;
    }

    public void setWeights(ArrayList<Double> weights){
        int index = 0;
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                layers[i].getNeurons()[j].setBias(weights.get(index++));
                for(int k = 0; k<layers[i].getNeurons()[j].getWeight().length; k++){
                    layers[i].getNeurons()[j].getWeight()[k]=weights.get(index++);
                }
            }
        }
    }

    public double doActivation(double value, int activationNumber){
        switch (activationNumber){
            case 0:
                return Functions.tanh(value);
            case 1:
                return Functions.sigmoid(value);
            case 2:
                return Functions.relu(value);
            case 3:
                return Functions.leaky_relu(value);
            default:
                return value;
        }
    }

    public double doDerivativeActivation(double value, int activationNumber){
        switch (activationNumber){
            case 0:
                return Functions.tanhDeriv(value);
            case 1:
                return Functions.sigmoidDeriv(value);
            case 2:
                return Functions.reluDeriv(value);
            case 3:
                return Functions.leaky_reluDeriv(value);
            default:
                return value;
        }
    }

    public void setTANH(int... layerNumber){
        for(int i = 0; i< layerNumber.length; i++){
            try{
                layers[layerNumber[i]].setActivation(ActivationEnum.TANH.getId());
            }
            catch (NullPointerException e){
                System.err.println("The layer you have tried to access wasn't initialize yet");
            }
        }
    }

    public void setSIGMOID(int... layerNumber){
        for(int i = 0; i< layerNumber.length; i++){
            try{
                layers[layerNumber[i]].setActivation(ActivationEnum.SIGMOID.getId());
            }
            catch (NullPointerException e){
                System.err.println("The layer you have tried to access wasn't initialize yet");
            }
        }
    }

    public void setRELU(int... layerNumber){
        for(int i = 0; i< layerNumber.length; i++){
            try{
                layers[layerNumber[i]].setActivation(ActivationEnum.RELU.getId());
            }
            catch (NullPointerException e){
                System.err.println("The layer you have tried to access wasn't initialize yet");
            }
        }
    }

    public void setLeakyRELU(int... layerNumber){
        for(int i = 0; i< layerNumber.length; i++){
            try{
                layers[layerNumber[i]].setActivation(ActivationEnum.LEAKY_RELU.getId());
            }
            catch (NullPointerException e){
                System.err.println("The layer you have tried to access wasn't initialize yet");
            }
        }
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "layers=" + Arrays.toString(layers) +
                '}';
    }

    public void setBias(int a) {
        for(int i = 0; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                layers[i].getNeurons()[j].setBias(a);
            }
        }
    }
}
