package model.algorithm;

public class Expectiminimax {
    final int UPPER_BOUND=100;
    final int LOWER_BOUND=-100;
    public Expectiminimax() {
    }
    public double star1(TreeNode node, int depth){
       int score = 0;
       double alpha=0;// we need to get these somehow
       double beta=0;
       // i guess bounds = 100 and -100 cus thats our win/loss atm
        if(depth==0 || !node.hasChildren()){
            return node.getValue();
        }
        int predecessor_scores=0;
        double succ_prob=1;
        for(int i=0;i<node.getChildren().size();i++){
            double probability =node.getChildren().get(i).getProbability();
            succ_prob -=probability;
            double current_alpha =(( alpha - UPPER_BOUND*succ_prob-predecessor_scores)/probability);
            double current_beta = ((beta - LOWER_BOUND*succ_prob-predecessor_scores)/probability);
            double n_alpha = Math.max(LOWER_BOUND,current_alpha);
            double n_beta = Math.min(UPPER_BOUND,current_beta);
            //random bullshit with doing the moves idk ?????
            // score = expectiminimax i guess? but with alpha and beta dont we need to rewrite the entire minimax to not use Node???
            /*
            if(score>=current_beta) return beta;
            if(score<=current_alpha) return alpha;

             */
        }
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
