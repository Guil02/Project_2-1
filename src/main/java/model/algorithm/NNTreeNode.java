package model.algorithm;

import controller.Board;
import model.player.NNAgent;

public class NNTreeNode extends TreeNode{
    private NNAgent NNAgent;
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    private Board board;
    private boolean doPromotion = false;
    private boolean maxIsWhite;

    public NNTreeNode(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo, boolean maxIsWhite, NNAgent NNAgent) {
        super(value, parent, nodeType, probability);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.board = board;
        this.maxIsWhite = maxIsWhite;
        this.NNAgent = NNAgent;
    }

    @Override
    public void createChildren() {
        NNAgent.createChildren(this, true, this.getBoard().getWhiteMove());
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

    public NNAgent getMctsAgent() {
        return NNAgent;
    }

    public boolean isMaxIsWhite() {
        return maxIsWhite;
    }
}
