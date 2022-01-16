package model.neuralNetwork;

public enum ActivationEnum {
    TANH(0),
    SIGMOID(1),
    RELU(2),
    LEAKY_RELU(3);

    private final int id;
    ActivationEnum(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
