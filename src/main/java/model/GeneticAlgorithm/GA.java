package model.GeneticAlgorithm;

import model.algorithm.GeneticAlgorithmAgent;

import java.util.ArrayList;

public class GA {
    private static final int populationSize = 64;
    private Population population;

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;


    public GA() {
        population = new Population(populationSize);
    }


}
