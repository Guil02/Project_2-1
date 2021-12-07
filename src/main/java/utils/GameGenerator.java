package utils;

import controller.Board;

import java.io.FileWriter;
import java.io.IOException;

public class GameGenerator {
    private static String fileName = "build/classes/java/main/utils/games.txt";
    public static void writeGame(Board board){

        try(FileWriter fileWriter = new FileWriter(fileName, true)) {
            for (String fen : board.getBoardStates()) {
                fileWriter.write(fen + "\n");
            }
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
