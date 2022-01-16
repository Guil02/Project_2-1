package model.GeneticAlgorithm;

import config.Config;
import controller.Board;
import controller.GameRunner;
import model.algorithm.GeneticAlgorithmAgent;
import model.player.SearchAgent;
import utils.FenEvaluator;
import utils.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class GA {
    private static final int populationSize = 50;
    private static final int amountOfMatchUps = populationSize/2;
    private static final String fileName = "build/classes/java/main/model/GeneticAlgorithm/weights";
    private Population population;
    public static boolean training = false;
    public static boolean noIndividualWithOneFitness = true;
    private static final double mutationRate = 0.05;
    private static final int amountOfGenerations = 1000;
    public static final int matchesPerGeneration = 20;

    public static void setNoIndividualWithOneFitness(boolean noIndividualWithOneFitness) {
        GA.noIndividualWithOneFitness = noIndividualWithOneFitness;
    }

    public GA() {
        //in log copy the last 20 weights to these here in the specific order that they are printed in
        //it was going pretty well then i accidentaly quit my pc like 3 hours in and then it would multithread like 1 generation in every single time
        population = new Population(populationSize, false);



        train();
        }

    public static boolean getNoIndividualWithOneFitness() {
        return noIndividualWithOneFitness;
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


            runMatchupVersusSearch(board, gameRunner);

            index++;
        }
    }

    /**
     * this runs ONE WHOLE SET of mathchups, there are matchesPerGeneration numbers of matchups per generation
     * @param board
     * @param gameRunner
     * @param matchups
     * @return
     */
    private void runMatchup(Board board, GameRunner gameRunner, int[][] matchups) {
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

    private void runMatchupVersusSearch(Board board, GameRunner gameRunner) {
        for(int i = 0; i< populationSize; i++){
            System.out.println("game: "+i);
            GeneticAlgorithmAgent agent1 = population.getIndividual(i).getAgent();
            SearchAgent agent2 = new SearchAgent(board);

            gameRunner.GATraining(agent1, agent2);

            handleThread();
            updateGamesPlayedAndWins(board,  i);
            gameRunner.GAReset();
        }
    }

    private void handleThread() {
        training = true;
        int index = 0;
        while(training){
            index++;
            if(index>200){
                training = false;
            }
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

    private void updateGamesPlayedAndWins(Board board, int i) {
        population.getIndividual(i).incrementGamesPlayed();


        if(!board.containsKing(false)){
            population.getIndividual(i).incrementGamesWon();
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
