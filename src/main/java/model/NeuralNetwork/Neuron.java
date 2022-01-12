package model.NeuralNetwork;

import java.util.Arrays;

public class Neuron {
    protected static double minWeight = -1;
    protected static double maxWeight = 1;

    private double weight[];
    private double bias;
    private double[] weight_gradient;
    private double temp_grad = 1;
    private double value  = 0;

    /**
     * constructor for hidden/output neurons
     * @param weight
     * @param bias
     */
    public Neuron(double weight[], double bias) {
        this.weight = weight;
        this.bias = bias;
        this.weight_gradient = new double[weight.length];
    }

    /**
     * constructor for input neuron
     * @param value
     */
    public Neuron(double value) {
        this.weight = null;
        this.bias = -1;
        this.weight_gradient = null;
        this.value = value;
    }

    public static void setWeightRange(double min, double max){
        minWeight = min;
        maxWeight = max;
    }

    public double[] getWeight() {
        return weight;
    }

    public double getBias() {
        return bias;
    }

    public double[] getGradient() {
        return weight_gradient;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setWeight_gradient(double value, int index){
        this.weight_gradient[index] = value;
    }

    public void setWeight_gradient(double[] weight_gradient) {
        this.weight_gradient = weight_gradient;
    }

    public double getTemp_grad() {
        return temp_grad;
    }

    public void setTemp_grad(double temp_grad) {
        this.temp_grad = temp_grad;
    }



    @Override
    public String toString() {
        return "\nNeuron{" +
                "\nweight=" + Arrays.toString(weight) +
                ",\n bias=" + bias +
                ",\n weight_gradient=" + Arrays.toString(weight_gradient) +
                ",\n value=" + value + "\n" +
                '}';
    }

    public void setBias(int a) {
        this.bias = a;
    }
}
