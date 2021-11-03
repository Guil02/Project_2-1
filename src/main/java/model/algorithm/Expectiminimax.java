package model.algorithm;

public class Expectiminimax {
    public Expectiminimax() {
    }

    public double expectiminimax(TreeNode node, int depth, int n){
        double a;
        node.createChildren();
        if(depth==0 || !node.hasChildren()){
            return node.getValue();
        }
        else if(node.getNodeType()==2){
            a = Double.MAX_VALUE;
            for(TreeNode children: node.getChildren()){
                a = Math.min(a, expectiminimax(children, depth-1,n));
                if(depth == n-1){
                    System.gc();
                }
            }
        }
        else if(node.getNodeType()==1){
            a = Double.MIN_VALUE;
            for(TreeNode children: node.getChildren()){
                a = Math.max(a, expectiminimax(children, depth-1,n));
                if(depth == n-1){
                    System.gc();
                }
            }
        }
        else{
            a = 0;
            for(TreeNode children: node.getChildren()){
                a = a+(children.getProbability()*expectiminimax(children, depth-1,n));
                if(depth == n-1){
                    System.gc();
                }
            }
        }
        node.setValue(a);
        return a;
    }
}
