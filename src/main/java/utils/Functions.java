package utils;

import controller.Board;
import model.pieces.ChessPiece;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Functions{

    private static final double c = 0.01;
    public static ArrayList<Double> readInWeights(String fileName){
        ArrayList<Double> weights = new ArrayList<>();
        String line;
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferreader.readLine()) != null) {
                weights.add(Double.parseDouble(line));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return weights;
    }

    public static void writeWeights(ArrayList<Double> weights, String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName, false)) {
            for (Double weight : weights) {
                fileWriter.write(weight + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendWeights(ArrayList<Double> weights, String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write("NEW ITERATION: \n");
            for (Double weight : weights) {
                fileWriter.write(weight + ", ");
            }
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendString(String x, String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write("NEW ITERATION: \n");
            fileWriter.write(x);
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double tanh(double x){
        if(x<-100){
            return -1;
        }
        else if(x>100){
            return 1;
        }
        return (Math.exp(x)-Math.exp(-x))/(Math.exp(x)+Math.exp(-x));
    }

    public static double tanhDeriv(double x){
        return (4*Math.exp(2*x))/Math.pow((Math.exp(2*x)+1), 2);
    }

    public static double sigmoid(double x){
        double res = 1.0/(1-Math.exp(-x));
        return res;
    }

    public static double relu(double value) {
        if(value<0){
            return 0;
        }
        else return value;
    }

    public static double leaky_relu(double value) {
        if(value<0){
            return c*value;
        }
        else{
            return value;
        }
    }

    public static double sigmoidDeriv(double value) {
        return value*(1-value);
    }

    public static double reluDeriv(double value) {
        if(value<0){
            return 0;
        }
        if(value>0){
            return 1;
        }
        throw new ArithmeticException("ReLuDeriv undefined for value: " + value);
    }

    public static double leaky_reluDeriv(double value) {
        if(value>0){
            return 1;
        }
        return c*value;
    }

    private boolean isPromotion(ChessPiece piece, int yTo){
        if(piece.getPieceType()==1){
            if(piece.isWhite()&&yTo==0){
                return true;
            }
            else if(!piece.isWhite()&&yTo==7){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    private double getPieceValue(int pieceType){
        return switch (pieceType) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 9;
            case 6 -> 0;
            default -> 0;
        };
    }

    public static double randomNumber(double min, double max){
        double interval = max-min;
        return Math.random()*interval+min;
    }

    public static void normalize(ArrayList<Double> values, double max){
        for(int i = 0; i<values.size(); i++){
            double normalized = values.get(i)/max;
            values.set(i,normalized);
        }
    }

    public static char convertChar(char c, boolean whiteIsMax){
        if(whiteIsMax)
            return Character.toUpperCase(c);
        return Character.toLowerCase(c);
    }

    private static ArrayList<Double> makeList(String s) {
        ArrayList<Double> weights = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(s);
        StringBuilder st = new StringBuilder();//TODO ADD TO LIST


        for(int i = 16; i<stringBuilder.length(); i++){
            char nextChar = stringBuilder.charAt(i);
            switch (nextChar){
                case '[',' ',':':
                    break;
                case ',',']':
                    weights.add(Double.parseDouble(st.toString()));
                    st = new StringBuilder();
                    System.out.println();
                    break;
                default:
                    st.append(nextChar);
                    System.out.print(nextChar);
            }
        }


        return weights;
    }

    public static ArrayList<ArrayList<Double>> readGAWeights(String fileName) {
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();
        String line;
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(fileName));
            while ((line = bufferreader.readLine()) != null) {
                if(line.charAt(0)=='W'){
                    weights.add(makeList(line));
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return weights;
    }



    public static void printBoard(ChessPiece[][] boardModel, Board board) {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + board.getCharOffField(j,i) + " ] ");
            }
            System.out.println();
        }
    }
}