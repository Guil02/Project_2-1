package controller;

public class Dice {

    public int rollTheDice() {
        double rand = (Math.random() * 6) + 1;
        return (int) rand;
    }
}
