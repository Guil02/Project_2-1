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

            if(i==0 || i%2!=0) {
                weights.add(Functions.randomNumber(0, max));
            }
            else{
                weights.add(Functions.randomNumber(min, 0));
            }


        }
        agent.setWeights(weights);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        if(fitness>=0.999){
            GA.setNoIndividualWithOneFitness(false);
        }
        this.fitness = fitness;
    }

    public void setFitness() {
        double fitness = ((double) gamesWon) / ((double) gamesPlayed);
        this.fitness = fitness;
    }

    public void incrementGamesWon() {
        gamesWon++;
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void resetStatistics(){
        gamesPlayed = 0;
        gamesWon = 0;
    }

    public void mutateGene(){
        ArrayList<Double> weights = agent.getWeights();
        for(int i=0; i<geneLength; i++){
            weights.set(i, mutateChromosome(weights.get(i)));
        }

    }

    private double mutateChromosome(double weight) {
        double r = Math.random();
        if(r<GA.getMutationRate()){
            if(Math.random()<0.5){
                return weight + weight*Functions.randomNumber(-0.1,0.1);
            }
            else return weight - weight*Functions.randomNumber(-0.1,0.1);
        }
        return weight;
    }

    public int getGeneLength(){
        return geneLength;
    }

    public ArrayList<Double> getGene() {
        return agent.getWeights();
    }

    public void setGene(ArrayList<Double> weights){
        agent.setWeights(weights);
    }

    public GeneticAlgorithmAgent getAgent() {
        return agent;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }
}
