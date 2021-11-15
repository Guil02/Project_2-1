package model.algorithm;

import java.util.ArrayList;

public class Expectiminimax {
    final int UPPER_BOUND=100;
    final int LOWER_BOUND=-100;
    public Expectiminimax() {
    }
    public double star1(TreeNode node, int depth){
        /*
        STEPS IMO
        1. GET ALPHA BETA BOUNDS
        2. GO THRU EACH POINT AND ALL THE VALUES
        3. IF THE VALUES EXCEED THE BOUNDS, END GOING THRU POINT
        4. REMOVE THE OTHER CHILDREN(CUT-OFF)


         */
        boolean doneWithLooking=false;
        ArrayList<TreeNode> children = node.getChildren();
        int stuck_at=0;
        // FIRST STEP
       double score = 0;
       double alpha=0;
       double beta=0;
       // i guess bounds = 100 and -100 cus thats our win/loss atm
        if(depth==0 || !node.hasChildren()){
            return node.getValue();
        }
        //STEP 2
        int predecessor_scores=0;
        double succ_prob=1;
        for(int i=0;i<children.size();i++){
            if(!doneWithLooking) {
                stuck_at=i+1;
                double probability = children.get(i).getProbability();
                succ_prob -= probability;
                double current_alpha = ((alpha - UPPER_BOUND * succ_prob - predecessor_scores) / probability);
                double current_beta = ((beta - LOWER_BOUND * succ_prob - predecessor_scores) / probability);
                double n_alpha = Math.max(LOWER_BOUND, current_alpha);
                double n_beta = Math.min(UPPER_BOUND, current_beta);
                score = expectiminimax(children.get(i), depth-1);
               /*

               if ( we decide to stop then){

                 doneWithLooking=true
                 int stuck_at=i
                 }
                */


            }
            else{
                // REMOVING
              children.remove(stuck_at);
            }


        }
        return 0;
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
           a=star1(node,depth);
        }
        node.setValue(a);
        return a;
    }
}
