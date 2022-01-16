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

/**
 * Class representing a Genetic Algorithm.
 * Works mostly in a static context.
 */
public class GA {


    // Variables
    private static final int populationSize = 20;
    private static final int amountOfMatchUps = populationSize/2;
    private static final String fileName = "build/classes/java/main/model/GeneticAlgorithm/weights";
    private Population population;
    public static boolean training = false;
    public static boolean noIndividualWithOneFitness = true;
    private static final double mutationRate = 0.05;
    private static final int amountOfGenerations = 1000;
    public static final int matchesPerGeneration = 20;

    /**
     * Constructor
     */
    public static void setNoIndividualWithOneFitness(boolean noIndividualWithOneFitness) {
        GA.noIndividualWithOneFitness = noIndividualWithOneFitness;
    }

    public GA() {

        population = new Population(populationSize, false);



        train();
    }

    public static boolean getNoIndividualWithOneFitness() {
        return noIndividualWithOneFitness;
    }

    /**
     * Trains a population.
     */
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

    /**
     * Runs a set of matches for one generation.
     */
    private void runGames() {
        Board board = new Board();
        GameRunner gameRunner = new GameRunner(board);
        population.resetStatistics();
        int index = 0;
        while(index< matchesPerGeneration){
            System.out.println("Matchup: "+index);


            runMatchupVersusSearch(board, gameRunner);

            int[][] matchups = createRandomMatching();
            runMatchup(board, gameRunner, matchups);
            index++;
        }
    }

    /**
     * This runs ONE WHOLE SET of match-ups, there are matchesPerGeneration numbers of match-ups per generation.
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

    /**
     * Handles the current thread.
     */
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

    /**
     * Updates the number of games played and the wins according to the agents.
     * @param board
     * @param matchUps
     * @param i
     */
    private void updateGamesPlayedAndWins(Board board, int[][] matchUps, int i) {
        population.getIndividual(matchUps[i][0]).incrementGamesPlayed();
        population.getIndividual(matchUps[i][1]).incrementGamesPlayed();
        if(!board.containsKing(true)){
            population.getIndividual(matchUps[i][0]).incrementGamesWon();
        }
        else if(!board.containsKing(false)){
            population.getIndividual(matchUps[i][1]).incrementGamesWon();
        }
    }

    private void updateGamesPlayedAndWins(Board board, int i) {
        population.getIndividual(i).incrementGamesPlayed();


        if(!board.containsKing(false)){
            population.getIndividual(i).incrementGamesWon();
        }

    }

    /**
     * Creates a random matchup between the agents so that each agent plays another one.
     * @return an INTEGER array containing the indices of the agent in the individuals list - NOT the agents itself
     */
    private int[][] createRandomMatching(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0; i<populationSize; i++){
            arr.add(i);
        }
        int[][] matchUps = new int[amountOfMatchUps][2];
        for(int i=0; i<amountOfMatchUps; i++){
            for(int j=0; j<2; j++){
                int r = (int) Functions.randomNumber(0,arr.size()-1);
                matchUps[i][j] = arr.get(r);
                arr.remove(r);
            }
        }
        return matchUps;
    }

    /**
     * @return current mutation rate
     */
    public static double getMutationRate() {
        return mutationRate;
    }

    /**
     * @return file name as a string
     */
    public static String getFileName(){
        return fileName;
    }
}
