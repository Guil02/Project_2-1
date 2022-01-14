package model.algorithm;

import controller.Board;
import model.player.SearchAgent;

public class ChessTreeNode extends TreeNode{
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    private Board board;
    private boolean doPromotion = false;
    private TreeBuilder treeBuilder;

    public ChessTreeNode(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo) {
        super(value, parent, nodeType, probability);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.board = board;
        treeBuilder = new TreeBuilder();
        setObjectType(0);
    }

    @Override
    public void createChildren() {
        treeBuilder.createChildren(this, true);
    }

    public int getxFrom() {
        return xFrom;
    }

    public int getyFrom() {
        return yFrom;
    }

    public int getxTo() {
        return xTo;
    }

    public int getyTo() {
        return yTo;
    }

    public void setDoPromotion(boolean doPromotion) {
        this.doPromotion = doPromotion;
    }

    @Override
    public void evaluate() {
        SearchAgent.evaluation(this.board);
    }

    public boolean isDoPromotion() {
        return doPromotion;
    }

    public Board getBoard() {
        return board;
    }


    @Override
    public boolean hasBestQuality() {
        if(!board.containsKing(!board.getWhiteMove())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean hasGoodQuality() {
        return super.hasGoodQuality();
    }
}
