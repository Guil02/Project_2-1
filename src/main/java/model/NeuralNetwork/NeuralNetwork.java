package model.NeuralNetwork;


import utils.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 2;


    public static final boolean DEBUG = true;
    private Layer[] layers;

    public NeuralNetwork(int amountOfLayers, int[] neuronsPerLayer, int min, int max) {
        Neuron.setWeightRange(min,max);
        layers = new Layer[amountOfLayers];
        for(int i = 1; i<amountOfLayers; i++){
            layers[i]= new Layer(neuronsPerLayer[i-1], neuronsPerLayer[i]);
        }
    }

    public NeuralNetwork(int amountOfLayers, int[] neuronsPerLayer) {
        Neuron.setWeightRange(-1,1);
        layers = new Layer[amountOfLayers];
        for(int i = 1; i<amountOfLayers; i++){
            layers[i]= new Layer(neuronsPerLayer[i-1], neuronsPerLayer[i]);
        }
    }

    public double[] forwardPropagate(double[] input){
        layers[0] = new Layer(input);
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                double val = 0;
                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
                    val += layers[i-1].getNeurons()[k].getValue()*layers[i].getNeurons()[j].getWeight()[k];
                }
                val += layers[i].getNeurons()[j].getBias();
                if(DEBUG){
                    System.out.println("val: "+(val-layers[i].getNeurons()[j].getBias()));
                    System.out.println("val+bias: "+val);
                    System.out.println("val+bias+tanh: "+ Functions.tanh(val));
                }
                layers[i].getNeurons()[j].setValue(Functions.tanh(val));
            }
        }
        double[] output = new double[layers[layers.length-1].getNeurons().length];
        for(int i = 0; i<output.length; i++){
            output[i] = layers[layers.length-1].getNeurons()[i].getValue();
        }
        return output;
    }

    public void computeTDGradient(double[] input){
        forwardPropagate(input);
        for(int i = layers.length-1; i>0; i--){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
                Neuron current = layers[i].getNeurons()[j];
                double val = 0;
                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
                    val += layers[i-1].getNeurons()[k].getValue()*layers[i].getNeurons()[j].getWeight()[k];
                }
                val += layers[i].getNeurons()[j].getBias();

                for(int k = 0; k<layers[i-1].getNeurons().length; k++){
                    current.setWeight_gradient(layers[i-1].getNeurons()[k].getValue()*Functions.tanhDeriv(val),k);
                }
            }
        }
    }

    public ArrayList<Double> getGradient(){
        ArrayList<Double> gradient = new ArrayList<>();
        for(int i = 1; i<layers.length; i++){
            for(int j = 0; j<layers[i].getNeurons().length; j++){
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
                for(int k = 0; k<layers[i].getNeurons()[j].getWeight().length; k++){
                    layers[i].getNeurons()[j].getWeight()[k]=weights.get(index++);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "layers=" + Arrays.toString(layers) +
                '}';
    }
}
