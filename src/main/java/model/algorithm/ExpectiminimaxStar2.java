package model.algorithm;

public class ExpectiminimaxStar2 extends Expectiminimax {

    public ExpectiminimaxStar2(boolean withPruning){
        super(withPruning);
    }
    int count = 0;


    /**
     * star2 pruning method for the expectimax AI
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
            for (TreeNode nodes : node.getChildren()){
                nodes.createChildren();
            }
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
                    double leaf_value = expectiminimax(node.getChildren().get(i).getChildren().get(j), depth-2);

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

    public double expectiminimax(TreeNode node, int depth){
        System.out.println("Test " + count);
        count++;
        //System.out.println("Cont nodes: " + countNodes(node, 1));
        double a;
        if(!node.hasChildren()){
            node.createChildren();
        }

        if(depth == 0 || !node.hasChildren()){
            return node.getValue();

        }
        else if(node.getNodeType() == 2){
            a = Double.MAX_VALUE;

            for(TreeNode children : node.getChildren()){
                a = Math.min(a, expectiminimax(children, depth - 1));
            }
        }
        else if(node.getNodeType() == 1){
            a = Double.MIN_VALUE;

            for(TreeNode children : node.getChildren()){
                a = Math.max(a, expectiminimax(children, depth - 1));
            }
        }
        else{
            star2(node, depth);
            a = 0;

            for(TreeNode children : node.getChildren()){
                a = a + (children.getProbability() * expectiminimax(children, depth - 1));
            }
        }
        node.setValue(a);
        return a;
    }

    /**
     * Recursively iterate over all child nodes to count all the nodes in the tree
     * @param node      root node
     * @return          number of nodes in the tree
     */
    private int countNodes(TreeNode node, int count) {
        if (node.hasChildren()) {
            System.out.println("Found a child");
            for (TreeNode child : node.getChildren()) {
                return count + countNodes(child, count);
            }
        }
        else {
            return 1; // Base case if no more children are available
        }
        return -1;
    }
}
