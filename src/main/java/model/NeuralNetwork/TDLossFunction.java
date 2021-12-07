package model.NeuralNetwork;

import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.lossfunctions.impl.LossL1;

public class TDLossFunction extends LossL1 {
    @Override
    public double computeScore(INDArray labels, INDArray preOutput, IActivation activationFn, INDArray mask, boolean average) {
        System.out.println("labels: "+labels);
        System.out.println("preOutput: "+preOutput);
        return super.computeScore(labels, preOutput, activationFn, mask, average);
    }
}
