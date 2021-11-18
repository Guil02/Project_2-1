package model.algorithm;
import java.util.ArrayList;

public class Expectiminimax {

    boolean withPruning;
    public Expectiminimax(boolean withPruning) {
        this.withPruning = withPruning;
    }

    public double expectiminimax(TreeNode node, int depth){
        double a;
        node.createChildren();
        if(depth==0 || !node.hasChildren()){
            return node.getValue();
        }
        else if(node.getNodeType()==2){
            a = Double.MAX_VALUE;
            for(TreeNode children: node.getChildren()){
                a = Math.min(a, expectiminimax(children, depth-1));
            }
        }
        else if(node.getNodeType()==1){
            a = Double.MIN_VALUE;
            for(TreeNode children: node.getChildren()){
                a = Math.max(a, expectiminimax(children, depth-1));
            }
        }
        else{
            a = 0;
            for(TreeNode children: node.getChildren()){
                a = a+(children.getProbability()*expectiminimax(children, depth-1));
            }
        }
        node.setValue(a);
        return a;
    }
}
