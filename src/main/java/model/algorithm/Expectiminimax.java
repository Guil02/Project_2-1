package model.algorithm;

public class Expectiminimax {

    public Expectiminimax() {
    }

    public double expectiminimax(TreeNode node, int depth, int maxDepth){
        double a;
        node.createChildren();
        if(depth==0 || !node.hasChildren()){
            return node.getValue();
        }
        else if(node.getNodeType()==2){
            a = Double.POSITIVE_INFINITY;
            for(TreeNode children: node.getChildren()){
                a = Math.min(a, expectiminimax(children, depth-1, maxDepth));
            }
        }
        else if(node.getNodeType()==1){
            a = Double.NEGATIVE_INFINITY;
            for(TreeNode children: node.getChildren()){
                a = Math.max(a, expectiminimax(children, depth-1, maxDepth));
            }
        }
        else{
            a = 0;
            for(TreeNode children: node.getChildren()){
                a = a+(children.getProbability()*expectiminimax(children, depth-1, maxDepth));
            }
        }
        node.setValue(a);
        if(depth<maxDepth){
            for(int i = 0; i<node.getChildren().size(); i++){
                node.getChildren().set(i,null);
            }
        }
        return a;
    }
}