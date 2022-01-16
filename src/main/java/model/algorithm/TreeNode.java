package model.algorithm;

import controller.Board;
import utils.NodeEnum;

import java.util.ArrayList;


public abstract class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children = new ArrayList<>();
    private double value;
    // 1 = max
    // 2 = min
    // 3 = chance
    private int nodeType;
    private double probability;
    private NodeEnum objectType;
    private double lowerBound = Double.NEGATIVE_INFINITY; // default value
    private double upperBound = Double.POSITIVE_INFINITY; // default value

    public TreeNode(double value, TreeNode parent, int nodeType, double probability) {
        this.value = value;
        this.parent = parent;
        this.probability = probability;
        this.nodeType = nodeType;
    }
    public double getLowerBound() {
        return this.lowerBound;
    }

    public double getUpperBound() {
        return this.upperBound;
    }
    public void updateLowerAndUpperBounds(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean isWithinBounds(double alpha, double beta) {
        if(lowerBound<beta && upperBound>alpha){
            return true;
        }
        else{
            return false;
        }
    }

    public void delete() {
        for(TreeNode nodes : children){
            int size = nodes.getChildren().size();
            for(int i=0; i<(size-1); i++){
                nodes.getChildren().remove(1);
            }
        }
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public double getValue() {
        return value;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public void addChild(TreeNode node) {
        children.add(node);
    }

    public TreeNode getParent() {
        return parent;
    }

    public void createChildren(){

    }

    public boolean hasBestQuality(){
        return false;
    }

    public boolean hasGoodQuality(){
        return true;
    }

    public int getNodeType() {
        return nodeType;
    }

    public double getProbability() {
        return probability;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static String nodeTypeString(int x){
        if(x==1){
            return "max";
        }
        else if(x==2){
            return "min";
        }
        else if(x==3){
            return "chance";
        }
        else{
            return "non existing node type";
        }
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                ", nodeType=" + nodeTypeString(nodeType) +
                ", probability=" + probability +
                '}';
    }

    public abstract boolean isDoPromotion();

    public abstract Board getBoard();

    public abstract int getxFrom();

    public abstract int getyFrom();

    public abstract int getxTo();

    public abstract int getyTo();

    public abstract void setDoPromotion(boolean b);

    public abstract void evaluate();

    public void setObjectType(int x){
        switch(x){
            case 0:
                this.objectType = NodeEnum.SEARCH_NODE;
                break;
            case 1:
                this.objectType = NodeEnum.NN_NODE;
                break;
            case 2:
                this.objectType = NodeEnum.TD_NODE;
                break;
            case 3:
                this.objectType = NodeEnum.CHEAT_NODE;
                break;
            case 4:
                this.objectType = NodeEnum.GA_NODE;
                break;
            default:
                throw new IllegalStateException("Unexpected value for Node Type: " + x);
        }
        }

    public NodeEnum getObjectType() {
        return objectType;
    }
}
