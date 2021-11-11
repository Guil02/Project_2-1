package utils;

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

    public static double[] additionVector(ArrayList<Double> arr1, ArrayList<Double> arr2){
        if(arr1.size() != arr2.size())
            throw new RuntimeException("Arrays must be same size");
        double[] res = new double[arr1.size()];
        for(int i=0; i<arr1.size(); i++){
            res[i] = arr1.get(i)+arr2.get(i);
        }
        return res;
    }



}
