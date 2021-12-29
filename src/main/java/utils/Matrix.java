package utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Matrix {
    public static double dotProduct(double[] arr1, double[] arr2){
        if(arr1.length != arr2.length)
            throw new RuntimeException("Arrays must be same size");
        double result = 0;
        for(int i=0; i<arr1.length; i++){
            result += arr1[i] * arr2[i];
        }
        return result;
    }

    public static ArrayList<Double> additionVector(ArrayList<Double> arr1, ArrayList<Double> arr2){
        if(arr1.size() != arr2.size())
            throw new RuntimeException("Arrays must be same size");
        ArrayList<Double> res = new ArrayList<>();
        for(int i=0; i<arr1.size(); i++){
            res.add(arr1.get(i)+arr2.get(i));
        }
        return res;
    }

    public static ArrayList<Double> varMultiplication(ArrayList<Double> arr1, double variable){
        ArrayList<Double> res = new ArrayList<>();

        for(int i = 0; i<arr1.size(); i++){
            res.add(arr1.get(i)*variable);
        }

        return res;
    }

    public static ArrayList<Double> normalizeVector(ArrayList<Double> arr1, double max, double min){
        ArrayList<Double> res = new ArrayList<>();
        for(int i = 0; i<arr1.size(); i++){
            Double normalized = (arr1.get(i)-min)/(max-min);
            res.add(normalized);
        }

        return res;
    }

    public static ArrayList<Double> devideByMax(ArrayList<Double> arr1){
        double max = arr1.get(0);
        for(int i = 0; i<arr1.size(); i++){
            max = Math.max(max, arr1.get(i));
        }
        ArrayList<Double> res = new ArrayList<>();
        for(int i = 0; i<arr1.size(); i++){
            Double divided = arr1.get(i)/max;
            res.add(divided);
        }

        return res;
    }



}
