package model.algorithm;

import controller.Board;

public class ChessCheatAiTreeNode extends TreeNode{
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    private Board board;
    private boolean doPromotion = false;
    private CheatAiTree cheatAiTree = new CheatAiTree();
    private boolean maxIsWhite;

    public ChessCheatAiTreeNode(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo, boolean maxIsWhite) {
        super(value, parent, nodeType, probability);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.board = board;
        this.maxIsWhite = maxIsWhite;
    }

    @Override
    public void createChildren() {
        cheatAiTree.createChildren(this, true, true, maxIsWhite);
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
    }

    public boolean isDoPromotion() {
        return doPromotion;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isMaxIsWhite() {
        return maxIsWhite;
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
