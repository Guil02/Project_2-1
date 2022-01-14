package utils;

import model.GeneticAlgorithm.Individual;

import java.util.ArrayList;

public class GAFunctions {
    public void quickSort(ArrayList<Individual> agents, int low, int high){
        if(low<high){
            int part = partition(agents, low, high);
            quickSort(agents, low, part-1);
            quickSort(agents, part+1, high);
        }
    }

    private int partition(ArrayList<Individual> agents, int low, int high){
        double pivot = agents.get(high).getFitness();
        int i = low-1;
        for(int j=low;j<high;j++){
            if(agents.get(j).getFitness()<pivot){
                i++;
                Individual tempAgent = agents.get(i);
                agents.set(i, agents.get(j));
                agents.set(j, tempAgent);
            }
        }
        Individual tempAgent = agents.get(i+1);
        agents.set(i+1, agents.get(high));
        agents.set(high, tempAgent);
        return i+1;
    }
}
