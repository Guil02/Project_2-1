package model.GeneticAlgorithm;

import model.player.GeneticAlgorithmAgent;
import utils.Functions;
import java.util.ArrayList;

/**
 * Class representing a single individual of a population in a GA.
 */
public class Individual {
    private static final double min = -1;
    private static final double max = 1;
    private double fitness;
    private GeneticAlgorithmAgent agent;
    private int geneLength = GeneticAlgorithmAgent.getAmountOfWeights();
    private int gamesWon = 0;
    private int gamesPlayed = 0;

    /**
     * Constructor
     */
    public Individual() {
        this.agent = new GeneticAlgorithmAgent();
        initializeWeights();
    }

    /**
     * Initializes weights for factors of an individual.
     */
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

    /**
     * @return current fitness value
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the current fitness value.
     * @param fitness new fitness
     */
    public void setFitness(double fitness) {
        if(fitness>=0.999){
            GA.setNoIndividualWithOneFitness(false);
        }
        this.fitness = fitness;
    }

    /**
     * Sets the current fitness value automatically.
     */
    public void setFitness() {
        double fitness = ((double) gamesWon) / ((double) gamesPlayed);
        this.fitness = fitness;
    }

    /**
     * Increments the counter of how many games this individual has won.
     */
    public void incrementGamesWon() {
        gamesWon++;
    }

    /**
     * Increments the counter of how many games this individual has played.
     */
    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    /**
     * Reset counter of played games and won games.
     */
    public void resetStatistics(){
        gamesPlayed = 0;
        gamesWon = 0;
    }

    /**
     * Mutates some weights of the agent randomly.
     */
    public void mutateGene(){
        ArrayList<Double> weights = agent.getWeights();
        for(int i=0; i<geneLength; i++){
            weights.set(i, mutateChromosome(weights.get(i)));
        }
    }

    /**
     * Mutates a single chromosome.
     * @param weight old weight
     * @return new weight (after mutation)
     */
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

    /**
     * @return length of a gene
     */
    public int getGeneLength(){
        return geneLength;
    }

    /**
     * @return the whole gene of this individual
     */
    public ArrayList<Double> getGene() {
        return agent.getWeights();
    }

    /**
     * Sets the gene to a certain value
     * @param weights input
     */
    public void setGene(ArrayList<Double> weights){
        agent.setWeights(weights);
    }

    /**
     * @return current agent
     */
    public GeneticAlgorithmAgent getAgent() {
        return agent;
    }

    /**
     * @return number of played games
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @return number of won games
     */
    public int getGamesWon() {
        return gamesWon;
    }
}
