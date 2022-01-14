package model.GeneticAlgorithm;

import config.Config;
import controller.Board;
import controller.GameRunner;
import model.algorithm.GeneticAlgorithmAgent;
import utils.FenEvaluator;
import utils.Functions;

import java.util.ArrayList;

public class GA {
    private static final int populationSize = 50;
    private static final int amountOfMatchUps = populationSize/2;
    private static final String fileName = "build/classes/java/main/model/GeneticAlgorithm/weights";
    private Population population;
    public static boolean training = false;

    private static final double mutationRate = 0.015;
    private static final int amountOfGenerations = 100;
    public static final int matchesPerGeneration = 10;



    public GA() {
        population = new Population(populationSize);
        train();
        }

    private void train() {
        int index = 0;
        while(index<amountOfGenerations){
            System.out.println("Generation: "+index);
            runGames();
            population.updateGeneration();
            if(Config.DEBUG){
                population.print();
            }
            population.storeWeights();
            index++;
        }
    }

    private void runGames() {
        Board board = new Board();
        GameRunner gameRunner = new GameRunner(board);
        population.resetStatistics();
        int index = 0;
        while(index< matchesPerGeneration){
            System.out.println("Matchup: "+index);
            int[][] matchups = createRandomMatching();

            runMatchup(board, gameRunner, index, matchups);
            index++;
        }
    }

    /**
     * this runs ONE WHOLE SET of mathchups, there are matchesPerGeneration numbers of matchups per generation
     * @param board
     * @param gameRunner
     * @param index
     * @param matchups
     * @return
     */
    private void runMatchup(Board board, GameRunner gameRunner, int index, int[][] matchups) {
        for(int i = 0; i< matchups.length; i++){
            System.out.println("game: "+i);
            GeneticAlgorithmAgent agent1 = population.getIndividual(matchups[i][0]).getAgent();
            GeneticAlgorithmAgent agent2 = population.getIndividual(matchups[i][1]).getAgent();

            gameRunner.GATraining(agent1, agent2);

            handleThread();
            updateGamesPlayedAndWins(board, matchups, i);
            gameRunner.GAReset();
        }
    }

    private void handleThread() {
        training = true;

        while(training){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGamesPlayedAndWins(Board board, int[][] matchups, int i) {
        population.getIndividual(matchups[i][0]).incrementGamesPlayed();
        population.getIndividual(matchups[i][1]).incrementGamesPlayed();

        if(!board.containsKing(true)){
            population.getIndividual(matchups[i][0]).incrementGamesWon();
        }
        else if(!board.containsKing(false)){
            population.getIndividual(matchups[i][1]).incrementGamesWon();
        }
    }

    /**
     * Creates a random matchup between the agents so that each agent plays another one
     * @return an INTEGER array containing the indices of the agent in the individuals list - NOT the agents itself
     */
    private int[][] createRandomMatching(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0; i<populationSize; i++){
            arr.add(i);
        }
        
        int[][] matchups = new int[amountOfMatchUps][2];
        
        for(int i=0; i<amountOfMatchUps; i++){
            for(int j=0; j<2; j++){
                int r = (int) Functions.randomNumber(0,arr.size()-1);
                matchups[i][j] = arr.get(r);
                arr.remove(r);
            }
            
        }

        return matchups;
    }

    public static double getMutationRate() {
        return mutationRate;
    }

    public static String getFileName(){
        return fileName;
    }
}
