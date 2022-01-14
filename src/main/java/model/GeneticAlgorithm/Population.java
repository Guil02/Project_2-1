package model.GeneticAlgorithm;

import utils.Functions;
import utils.GAFunctions;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
    private int populationsize;
    private ArrayList<Individual> individuals;
    private GAFunctions gaFunctions = new GAFunctions();
    private boolean isSorted = false;
    private final int numberOfStrongest = 8;
    private final int numberOfSurvivors = 40;
    private int geneLength;
    private int AmountOfWeightsStored = 3;


    public Population(int populationSize) {
        individuals = new ArrayList<>();
        this.populationsize = populationSize;
        for (int i = 0; i < populationSize; i++) {
            individuals.add(new Individual());
        }
        geneLength = individuals.get(0).getGeneLength();
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public void sortIndividuals() {
        if(!isSorted){
            gaFunctions.quickSort(individuals, 0, individuals.size() - 1);
            Collections.reverse(individuals);
            isSorted = true;
        }

    }

    //idea: choose x with best fitness
    //remove: y with lowest fitness and replace with random new chromosomes
    //all others are replaced with crossover from x

    public void steadyStateSelection(){
        sortIndividuals();
        birth();

    }


    public void birth(){
        for(int i=numberOfSurvivors; i<individuals.size(); i = i + 2){
            int motherIndex = getMotherIndex();
            Individual mother = individuals.get(motherIndex);
            int fatherIndex = getFatherIndex();
            Individual father = individuals.get(fatherIndex);

            crossOver(mother, father, individuals.get(i), individuals.get(i+1));

        }
    }



    private void crossOver(Individual mother, Individual father, Individual child1, Individual child2) {
        int[] crossoverPoints = generateCrossOverPoints();

        ArrayList<Double> geneMother = mother.getGene();
        ArrayList<Double> geneFather = father.getGene();
        ArrayList<Double> geneChild1 = createCrossOver(geneMother, geneFather, crossoverPoints, true);
        ArrayList<Double> geneChild2 = createCrossOver(geneMother, geneFather, crossoverPoints, false);
        child1.setGene(geneChild1);
        child2.setGene(geneChild2);
    }

    private ArrayList<Double> createCrossOver(ArrayList<Double> geneMother, ArrayList<Double> geneFather, int[] crossoverPoints, boolean motherFirst) {
        ArrayList<Double> gene = new ArrayList<>();
        if (motherFirst) {
            addToList(geneMother, gene, 0, crossoverPoints[0]);
            addToList(geneFather, gene, crossoverPoints[0], crossoverPoints[1]);
            addToList(geneMother, gene, crossoverPoints[1], geneLength);
        }
        else {
            addToList(geneFather, gene, 0, crossoverPoints[0]);
            addToList(geneMother, gene, crossoverPoints[0], crossoverPoints[1]);
            addToList(geneFather, gene, crossoverPoints[1], geneLength);
        }

        return gene;
    }

    private void addToList(ArrayList<Double> list, ArrayList<Double> target, int startIndex, int endIndex){
        for(int i = startIndex; i<endIndex; i++){
            target.add(list.get(i));
        }
    }

    private int[] generateCrossOverPoints() {
        int firstCrossOverPoint = (int)Functions.randomNumber(0,geneLength);
        int secondCrossOverPoint = (int)Functions.randomNumber(0,geneLength);
        if(secondCrossOverPoint<firstCrossOverPoint){
            int temp = firstCrossOverPoint;
            firstCrossOverPoint = secondCrossOverPoint;
            secondCrossOverPoint = temp;
        }

        return new int[]{firstCrossOverPoint, secondCrossOverPoint};
    }

    private int getFatherIndex() {
        return (int)Functions.randomNumber(0,numberOfStrongest-1);
    }

    private int getMotherIndex() {
        return (int)Functions.randomNumber(0,numberOfStrongest-1);
    }

    private void mutatePopulation(){
        for(int i=0; i<populationsize; i++){
            individuals.get(i).mutateGene();
        }
    }

    private static final String log = "build/classes/java/main/model/GeneticAlgorithm/log";
    public void print() {
        for(int i = 0; i<individuals.size(); i++){
            String x = "Weights no. " + i + " : " + individuals.get(i).getGene();
            System.out.println(x);
            String x1 = "Fitness no. " + i + " : " + individuals.get(i).getFitness();
            System.out.println(x1);
            String x2 = "games won no. " + i + " : " + individuals.get(i).getGamesWon();
            System.out.println(x2);
            String x3 = "games played no. " + i + " : " + individuals.get(i).getGamesPlayed();
            System.out.println(x3);
            Functions.appendString(x, log);
            Functions.appendString(x1, log);
            Functions.appendString(x2, log);
            Functions.appendString(x3, log);
        }

    }

    public void storeWeights(){
        for(int i = 0; i<AmountOfWeightsStored; i++){
            ArrayList<Double> weights = individuals.get(i).getGene();
            Functions.appendWeights(weights,GA.getFileName());
        }

    }


    public void resetStatistics() {
        for (Individual individual : individuals) {
            individual.resetStatistics();
        }
    }
}
