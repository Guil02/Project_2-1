package model.algorithm;

import controller.Board;
import model.player.TDLearningAgent;

public class TDTreeNode extends TreeNode{
    private TDLearningAgent tdLearningAgent;
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;
    private Board board;
    private boolean doPromotion = false;
    private boolean maxIsWhite;

    public TDTreeNode(Board board, double value, TreeNode parent, int nodeType, double probability, int xFrom, int yFrom, int xTo, int yTo, boolean maxIsWhite, TDLearningAgent tdLearningAgent) {
        super(value, parent, nodeType, probability);
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
        this.board = board;
        this.maxIsWhite = maxIsWhite;
        this.tdLearningAgent = tdLearningAgent;
    }

    @Override
    public void createChildren() {
        tdLearningAgent.createChildren(this, true, this.getBoard().getWhiteMove());
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

    public TDLearningAgent getTdLearning() {
        return tdLearningAgent;
    }

    public boolean isMaxIsWhite() {
        return maxIsWhite;
    }
}
