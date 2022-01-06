package model.algorithm;

public class ExpectiminimaxStar2 extends Expectiminimax {

    public ExpectiminimaxStar2(boolean withPruning){
    }

    /**
     * star2 pruning method for the expectiminimax AI
     * @param node the current node
     * @param depth the depth of the corresponding tree
     */
    public void star2(TreeNode node, int depth){

        /*
         * alpha-beta default values
         */
        double alpha = -100;
        double beta = 100;

        /*
         * if no chance node children layer --> create
         */
        if(depth > 2){
           // for (TreeNode nodes : node.getChildren()){
            //    nodes.createChildren();
            //}
        }
        else{
            int number_of_children = node.getChildren().size();
            int max_number_of_leaves = 0;

            for(int i = 0; i < number_of_children; i++){
                int number_of_leaves = node.getChildren().get(i).getChildren().size();
                if (number_of_leaves > max_number_of_leaves){
                    max_number_of_leaves = number_of_leaves;
                }
            }
            /*
             * invistigate chance nodes leaves values and update the bounds (backpropagate the bounds onto the chance node parent)
             */
            for (int j = 0; j < max_number_of_leaves; j++) {
                for (int i = 0; i < number_of_children; i++) {
                    double leaf_value = expectiminimaxWithStar2(node.getChildren().get(i).getChildren().get(j), depth-2, depth);

                    if (node.getChildren().get(i).getNodeType() == 1) { //max
                        node.getChildren().get(i).updateLowerAndUpperBounds(leaf_value, node.getChildren().get(i).getUpperBound());
                        double new_lowerBound = 0;

                        for (int h = 0; h < number_of_children; h++) {
                            new_lowerBound += node.getChildren().get(h).getLowerBound() * node.getChildren().get(h).getProbability();
                            node.updateLowerAndUpperBounds(new_lowerBound, node.getUpperBound());
                        }

                    } else if (node.getChildren().get(i).getNodeType() == 2) { //min
                        node.getChildren().get(i).updateLowerAndUpperBounds(node.getChildren().get(i).getLowerBound(), leaf_value);
                        double new_upperBound = 0;

                        for (int l = 0; l < number_of_children; l++) {
                            new_upperBound += node.getChildren().get(i).getUpperBound() * node.getChildren().get(i).getProbability();
                            node.updateLowerAndUpperBounds(node.getLowerBound(), new_upperBound);
                        }

                    } else {
                        /*
                         * prune the chance nodes leaves when the first child bounds of the same layer are already out of the search window
                         */
                        if (!(node.isWithinBounds(alpha, beta))) {
                            node.delete();
                            break;
                        }
                    }
                }
            }
        }
    }

    public double expectiminimaxWithStar2(TreeNode node, int depth, int maxDepth){

        double a;
        if(!node.hasChildren()&&depth!=0){
            node.createChildren();
        }

        if(depth == 0 || !node.hasChildren()){
            return node.getValue();

        }
        else if(node.getNodeType() == 2){
            a = Double.POSITIVE_INFINITY;

            for(TreeNode children : node.getChildren()){
                a = Math.min(a, expectiminimaxWithStar2(children, depth - 1, maxDepth));
            }
        }
        else if(node.getNodeType() == 1){
            a = Double.NEGATIVE_INFINITY;

            for(TreeNode children : node.getChildren()){
                a = Math.max(a, expectiminimaxWithStar2(children, depth - 1, maxDepth));
            }
        }
        else{
            star2(node, depth);
            a = 0;

            for(TreeNode children : node.getChildren()){
                a = a + (children.getProbability() * expectiminimaxWithStar2(children, depth - 1, maxDepth));
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