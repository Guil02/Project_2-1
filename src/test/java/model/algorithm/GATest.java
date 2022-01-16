package model.algorithm;

import model.GeneticAlgorithm.GA;
import model.GeneticAlgorithm.Individual;
import org.junit.jupiter.api.Test;
import utils.GAFunctions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GATest {


    @Test
    public void testQuickSort() {
        double[] val = {10, 67, 345, 2, 234, 8};
        double[] expected = {2, 8, 10, 67, 234, 345};
        double[] actual = doQuickSort(val);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQuickSort2() {
        double[] val = {9, -8, 9, 0, 23444, -345};
        double[] actual = doQuickSort(val);
        double[] expected = {-345, -8, 0, 9, 9, 23444};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQuickSort3() {
        double[] val = {0.1, 4.20, 6.9, 666, 0.12, 1, 4.55};
        double[] actual = doQuickSort(val);
        double[] expected = {0.1, 0.12, 1, 4.20, 4.55, 6.9, 666};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQuickSort4() {
        double[] val = {5.3, 3456.6, 34.2, 64.32, 25.4};
        double[] actual = doQuickSort(val);
        double[] expected = {5.3, 25.4, 34.2, 64.32, 3456.6};
        assertArrayEquals(expected, actual);
    }

    private double[] doQuickSort(double[] val) {
        ArrayList<Individual> agents = new ArrayList<>();
        for (int i = 0; i < val.length; i++) {
            agents.add(new Individual());
            agents.get(i).setFitness(val[i]);
        }

        GAFunctions ga = new GAFunctions();
        ga.quickSort(agents, 0, val.length - 1);
        double[] actual = new double[agents.size()];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = agents.get(i).getFitness();
        }
        return actual;
    }

}