package model.algorithm;

import controller.Board;
import model.player.TDLearning;

public class TDTreeNode extends TreeNode{
    private TDLearning tdLearning;
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    private Board board;
    private boolean doPromotion = false;
    private boolean maxIsWhite;

    public TDTreeNode(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo, boolean maxIsWhite, TDLearning tdLearning) {
        super(value, parent, nodeType, probability);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.board = board;
        this.maxIsWhite = maxIsWhite;
        this.tdLearning = tdLearning;
    }

    @Override
    public void createChildren() {
        tdLearning.createChildren(this, true, this.getBoard().getWhiteMove());
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

    public boolean isDoPromotion() {
        return doPromotion;
    }

    public Board getBoard() {
        return board;
    }

    public TDLearning getTdLearning() {
        return tdLearning;
    }

    public boolean isMaxIsWhite() {
        return maxIsWhite;
    }
}
