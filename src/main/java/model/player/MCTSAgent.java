package model.player;

import controller.Board;
import controller.BoardUpdater;
import controller.Dice;
import controller.GameRunner;
import javafx.application.Platform;
import model.NeuralNetwork.NeuralNetwork;
import model.algorithm.Expectiminimax;
import model.algorithm.MCTSTreeNode;
import model.algorithm.TDTreeNode;
import model.algorithm.TreeNode;
import model.pieces.ChessPiece;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import utils.FenEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static controller.GameRunner.DEBUG;

public class MCTSAgent extends Player {
    private static final int FEATURES_COUNT = 315;
    private static final int CLASSES_COUNT = 2;
    private MultiLayerConfiguration configuration;
    private MultiLayerNetwork model;
    private NeuralNetwork neuralNetwork;
    public static final boolean LEARN = true;
    private Expectiminimax expectiminimax;
    private final int ply = 2;
    private MCTSTreeNode maxima;
    private BaselineAgent baselineAgent;
    private static final boolean DEBUG = GameRunner.DEBUG;
    private static final boolean DO_RANDOM = false;

    public MCTSAgent() {
        configuration = new NeuralNetConfiguration.Builder()
                .activation(Activation.RELU)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(0.1, 0.9))
                .l2(0.0001).list().layer(0, new DenseLayer.Builder()
                        .nIn(FEATURES_COUNT).nOut(3).build()).
                layer(1, new DenseLayer.Builder()
                        .nIn(3).nOut(3).build())
                .layer(2, new OutputLayer.Builder(
                        LossFunctions.LossFunction.L1)
                        .activation(Activation.TANH)
                        .nIn(3).nOut(CLASSES_COUNT).build())
                .build();

        model = new MultiLayerNetwork(configuration);
        model.init();
        model.setParam("0_W", null);
        neuralNetwork = new NeuralNetwork();
        expectiminimax = new Expectiminimax();
        baselineAgent = new BaselineAgent();
    }

    public double[] evaluation(Board board) {
        double[] val = neuralNetwork.boardToArray(board);
        double[][] temp = {val};
        INDArray input = Nd4j.create(temp);
        INDArray res = model.output(input);
        double[][] output = res.toDoubleMatrix();
        return output[0];
    }

    public void learn(Board endBoard,ArrayList<String> fens, double[] endEval){
        System.out.println("learning");
        ArrayList<Board> boards = new ArrayList<>();
        double[][] evals = new double[fens.size()][2];
        for(int i = 0; i<fens.size(); i++){
            Board temp = FenEvaluator.read(fens.get(i));
            boards.add(temp);
            evals[i] = endEval;
        }
        learn(boards, evals);
        System.out.println("done");
        Board defaultBoard = new Board();
        BoardUpdater.fillGameStart(defaultBoard);
        double[][] data = {neuralNetwork.boardToArray(defaultBoard)};
        System.out.println(model.output(Nd4j.create(data)));
        endBoard.getGameRunner().reset();
    }

    public void learn(ArrayList<Board> boards, double[][] endEval){
        double[][] trainSet = new double[boards.size()][FEATURES_COUNT];
        for(int i = 0; i<boards.size(); i++){
            trainSet[i] = neuralNetwork.boardToArray(boards.get(i));
        }
        INDArray input = Nd4j.create(trainSet);
        INDArray labels = Nd4j.create(endEval);
        DataSet data = new DataSet(input, labels);
        model.fit(data);
        try {
            model.save(new File("model"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void learn(DataSet data){
        model.fit(data);
        try {
            model.save(new File("model"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runAgent(Board board){
        Board copy = board.clone();
        boolean maxIsWhite = board.getWhiteMove();
        MCTSTreeNode root = new MCTSTreeNode(copy, 0, null, 1, 1, 0, 0, 0, 0, maxIsWhite, this);
        expectiminimax.expectiminimax(root, (ply*2)-1);
        double maxValue = Double.MIN_VALUE;
        ArrayList<MCTSTreeNode> highestNodes = new ArrayList<>();
        MCTSTreeNode maxNode;
        try{
            maxNode = (MCTSTreeNode) root.getChildren().get(0);
        }
        catch(Exception e){
            maxNode = root;
        }
        highestNodes.add(maxNode);
        for (TreeNode child : root.getChildren()) {
            MCTSTreeNode subChild = (MCTSTreeNode) child;
            if (subChild.getValue() >= maxValue) {
                if (subChild.getValue() == maxValue) {
                    highestNodes.add(subChild);
                    continue;
                }
                highestNodes.clear();
                highestNodes.add(subChild);
                maxValue = subChild.getValue();
            }
        }
        Random rand = new Random();
        maxNode = highestNodes.get(rand.nextInt(highestNodes.size()));
        maxima = maxNode;
    }

    public void launch(Board board){
        System.gc();
        new Thread(() -> {
            if(!DO_RANDOM) {
            try{
                if(ply<3){
//                    Thread.sleep(100);
                }
                runAgent(board);
                MCTSTreeNode move = getMaxima();
                if(move.isDoPromotion()){
                    boolean hasLearned = false;
                    board.storeMove();
//                        System.err.println("---------------------------------------------------------------------");
                    boolean isWhite = board.getPieceOffField(move.getxFrom(), move.getyFrom()).isWhite();
                    int pieceType = getPieceType(move.getBoard().getCharOffField(move.getxTo(), move.getyTo()));
//                        System.out.println(move.getxTo()+" "+ move.getyTo());
//                        printBoard(board.getBoardModel(), board);
                    ChessPiece promoted = BoardUpdater.createPiece(isWhite, move.getxTo(), move.getyTo(), pieceType);
                    BoardUpdater.removePiece(board, move.getxFrom(), move.getyFrom());
                    BoardUpdater.addPiece(board, promoted);
                    if(!BoardUpdater.containsKing(board, !isWhite)){
                        if(isWhite){
                            if(Board.GUI_ON && board.isOriginal()){
                                Platform.runLater(
                                        new Thread(()->{
                                            board.getGraphicsConnector().setWin(true);
                                        })
                                );
                            }
                            board.setGameOver(true);
                            board.storeMove();
//                            ArrayList<String> boardStates = board.getBoardStates();
//                            for(int i = 0; i<board.getBoardStates().size(); i++){
//                                System.out.println(boardStates.get(i));
//                            }
                            if(LEARN&&board.isOriginal()){
                                double[] endEval = computeEndEval(board);

                                learn(board, board.getBoardStates(),endEval);
                                hasLearned = true;
                            }
                        }
                        else{
                            if(Board.GUI_ON && board.isOriginal()){
                                Platform.runLater(
                                        new Thread(()->{
                                            board.getGraphicsConnector().setWin(false);
                                        })
                                );
                            }
                            board.setGameOver(true);
                            board.storeMove();
//                            ArrayList<String> boardStates = board.getBoardStates();
//                            for(int i = 0; i<board.getBoardStates().size(); i++){
//                                System.out.println(boardStates.get(i));
//                            }
                            if(LEARN&&board.isOriginal()){
                                double[] endEval = computeEndEval(board);
                                learn(board, board.getBoardStates(), endEval);
                                hasLearned = true;
                            }
                        }
                    }
                    if(Board.GUI_ON){
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                    if(!hasLearned){
                        board.changeTurn();
                    }
                }
                else{
//                    if(!Board.GUI_ON) printBoard(board.getBoardModel(), board);
                    BoardUpdater.movePiece(board, move.getxFrom(), move.getyFrom(), move.getxTo(), move.getyTo());
                    if(Board.GUI_ON){
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                }
            }
            catch(Exception e){
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
            }
            else{
                baselineAgent.launch(board);
            }
        }).start();
    }

    public MCTSTreeNode getMaxima() {
        return maxima;
    }

    public double[] computeEndEval(Board board){
        double[] endEval = new double[2];
        endEval[0]=0.5;
        endEval[1]=0.5;
        if(BoardUpdater.containsKing(board, true)) {
            endEval[0] = 1;
            endEval[1] = 0;
            if(DEBUG){
                System.out.println("White has won");
            }
        }
        else{
            endEval[0] = 0;
            endEval[1] = 1;
            if(DEBUG){
                System.out.println("Black has won");
            }
        }
        return endEval;
    }

    public int getPieceType(char pieceType){
        switch(pieceType){
            case 'n','N':
                return 2;
            case 'b','B':
                return 3;
            case 'r','R':
                return 4;
            case 'q','Q':
                return 5;
        }
        return 0;
    }
    public void createChildren(MCTSTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        if(root.getNodeType()== 1 || root.getNodeType() == 2){
            createChanceChildren(root, doEvaluation, maxIsWhite);
        }
        else if(root.getNodeType() == 3){
            if(root.getParent().getNodeType() == 1){
                createMinChildren(root, doEvaluation, maxIsWhite);
            }
            else if(root.getParent().getNodeType() == 2){
                createMaxChildren(root, doEvaluation, maxIsWhite);
            }
        }
    }

    private void createChanceChildren(MCTSTreeNode root, boolean doEvaluation, boolean maxIsWhite){
        for (ChessPiece[] pieces: root.getBoard().getBoardModel()) {
            for(ChessPiece piece: pieces){
                if(piece != null && piece.isTurn(root.getBoard()) && root.getBoard().getMovablePiece()==piece.getPieceChar()){
                    boolean[][] validMoves = piece.validMoves(root.getBoard());
                    createChild(root, validMoves, piece, 3, doEvaluation, maxIsWhite);
                }
            }
        }
    }

    private void createMaxChildren(MCTSTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 1, 1.0/movablePieces.size(), maxIsWhite);
        }

    }

    private void createMinChildren(MCTSTreeNode root, boolean doEvaluation, boolean maxIsWhite) {
        ArrayList<Character> movablePieces = Dice.getMovablePieces(root.getBoard());

        for (Character movablePiece : movablePieces) {
            createChanceChild(root, doEvaluation, movablePiece, 2, 1.0/movablePieces.size(), maxIsWhite);
        }
    }

    private void createChanceChild(MCTSTreeNode parent, boolean doEvaluation, char movablePiece, int nodeType, double probability, boolean maxIsWhite){
        Board copy = parent.getBoard().clone();
        copy.setMovablePiece(movablePiece);
        double value = 0;
        if(doEvaluation){
            double[] temp = evaluation(copy);
            if(maxIsWhite){
                value = temp[0];
            }
            else{
                value = temp[1];
            }
        }
        MCTSTreeNode child = new MCTSTreeNode(copy,value, parent, nodeType, probability, 0,0,0,0, parent.isMaxIsWhite(), parent.getMctsAgent());
        parent.addChild(child);
    }

    private void createChild(MCTSTreeNode parent, boolean[][] validMoves, ChessPiece piece, int nodeType, boolean doEvaluation, boolean maxIsWhite){

        for(int i = 0; i< validMoves.length; i++){
            for(int j = 0; j<validMoves[0].length; j++){
                if(validMoves[i][j]){
                    if(isPromotion(piece, j)){
                        createPromotionChild(parent, piece, i, j, 2, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 3, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 4, doEvaluation, maxIsWhite, nodeType);
                        createPromotionChild(parent, piece, i, j, 5, doEvaluation, maxIsWhite, nodeType);
                        continue;
                    }

                    Board copy = parent.getBoard().clone();
                    BoardUpdater.movePiece(copy, piece.getX(), piece.getY(), i,j);

                    double value = 0;
                    if(doEvaluation){
                        double[] temp = evaluation(copy);
                        if(maxIsWhite){
                            value = temp[0];
                        }
                        else{
                            value = temp[1];
                        }
                    }

                    MCTSTreeNode child = new MCTSTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),i,j, parent.isMaxIsWhite(), parent.getMctsAgent());
                    parent.addChild(child);
                }
            }
        }
    }

    private void createPromotionChild(MCTSTreeNode parent, ChessPiece piece, int xTo, int yTo, int pieceType, boolean doEvaluation, boolean maxIsWhite, int nodeType){
        Board copy = parent.getBoard().clone();
        BoardUpdater.removePiece(copy, piece.getX(), piece.getY());
        ChessPiece promoted = BoardUpdater.createPiece(piece.isWhite(), xTo, yTo, pieceType);
        BoardUpdater.addPiece(copy, promoted);

        double value = 0;
        if(doEvaluation){
            double[] temp = evaluation(copy);
            if(maxIsWhite){
                value = temp[0];
            }
            else{
                value = temp[1];
            }
        }

        MCTSTreeNode child = new MCTSTreeNode(copy, value,parent,nodeType,1,piece.getX(),piece.getY(),xTo,yTo, parent.isMaxIsWhite(), parent.getMctsAgent());
        child.setDoPromotion(true);
        parent.addChild(child);
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


}