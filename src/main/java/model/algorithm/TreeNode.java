package model.algorithm;

import java.util.ArrayList;

public class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children = new ArrayList<>();
    private double value;
    // 1 = max
    // 2 = min
    // 3 = chance
    private int nodeType;
    private double probability;
    private double lowerBound = Double.MIN_VALUE; // alpha default value
    private double upperBound = Double.MAX_VALUE; // beta default value

    public TreeNode(double value, TreeNode parent, int nodeType, double probability) {
        this.value = value;
        this.parent = parent;
        this.nodeType = nodeType;
        this.probability = probability;

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
}
