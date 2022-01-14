package model.GeneticAlgorithm;

import model.algorithm.GeneticAlgorithmAgent;
import utils.Functions;

import java.util.ArrayList;

public class Individual {
    private static final double min = -1;
    private static final double max = 1;
    private double fitness;
    private GeneticAlgorithmAgent agent;
    private int geneLength = GeneticAlgorithmAgent.getAmountOfWeights();
    private int gamesWon = 0;
    private int gamesPlayed = 0;

    public Individual() {
        this.agent = new GeneticAlgorithmAgent();
        initializeWeights();
    }

    private void initializeWeights() {
        ArrayList<Double> weights = new ArrayList<>();
        for (int i = 0; i < geneLength; i++) {
            weights.add(Functions.randomNumber(min, max));
        }
        agent.setWeights(weights);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double calculateFitness() {
        double fitness = ((double) gamesWon) / ((double) gamesPlayed);
        return fitness;
    }

    public void incrementGamesWon() {
        gamesWon++;
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void mutateGene(){
        ArrayList<Double> weights = agent.getWeights();
        for(int i=0; i<geneLength; i++){
            weights.set(i, mutateChromosome(weights.get(i)));
        }

    }

    private double mutateChromosome(double weight) {
        double r = Math.random();
        if(r<Population.getMutationRate()){
            return weight * Functions.randomNumber(0.95,1.05);
        }
        return weight;
    }

    public int getGeneLength(){
        return geneLength;
    }
}
