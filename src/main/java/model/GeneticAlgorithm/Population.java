package model.GeneticAlgorithm;

import utils.Functions;
import utils.GAFunctions;

import java.util.ArrayList;

public class Population {
    private int populationsize;
    private ArrayList<Individual> individuals;
    private GAFunctions gaFunctions;
    //TODO: SET TO FALSE AFTER EVAL
    private boolean isSorted = false;
    private int numberOfStrongest;
    private int numberOfSurvivors;
    private int geneLength;


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

    //TODO: FIX TO SORT FROM HIGH TO LOW
    public void sortIndividuals() {
        if(!isSorted){
            gaFunctions.quickSort(individuals, 0, individuals.size() - 1);
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

    //TODO: FIX SO TWO CHILDREN ARE PASSED
    public void birth(){
        for(int i=numberOfSurvivors; i<individuals.size(); i++){
            int motherIndex = getMotherIndex();
            Individual mother = individuals.get(motherIndex);
            int fatherIndex = getFatherIndex();
            Individual father = individuals.get(fatherIndex);

            crossOver(mother, father, individuals.get(i));

        }
    }



    private void crossOver(Individual mother, Individual father, Individual child) {
        int[] crossoverPoints = generateCrossOverPoints();
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


}
