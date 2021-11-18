package model.algorithm;

import com.google.inject.OutOfScopeException;

import java.util.ArrayList;

public class ExpectiminimaxStar2 extends Expectiminimax {

    public ExpectiminimaxStar2(boolean withPruning) {
        super(withPruning);
    }

    public void star2(TreeNode node, int depth) {

        double alpha = -100;
        double beta = 100;

        if (depth > 2) {

            for (TreeNode nodes : node.getChildren()){
                nodes.createChildren();
            }

        } else {
            int number_of_branches = node.getChildren().size();
            int max_number_of_leaves = 0;

            for (int i = 0; i < number_of_branches; i++) {
                int number_of_leaves = node.getChildren().get(i).getChildren().size();
                if (number_of_leaves > max_number_of_leaves) {
                    max_number_of_leaves = number_of_leaves;
                }
            }
            for (int j = 0; j < max_number_of_leaves; j++) {
                for (int i = 0; i < number_of_branches; i++) {
                    double leaf_value = expectiminimax(node.getChildren().get(i).getChildren().get(j), depth-2);

                    if (node.getChildren().get(i).getNodeType() == 1) { //max
                        node.getChildren().get(i).updateLowerAndUpperBounds(leaf_value, node.getChildren().get(i).getUpperBound());
                        double new_lowerBound = 0;

                        for (int h = 0; h < number_of_branches; h++) {
                            new_lowerBound += node.getChildren().get(h).getLowerBound() * node.getChildren().get(h).getProbability();
                            node.updateLowerAndUpperBounds(new_lowerBound, node.getUpperBound());
                        }

                    } else if (node.getChildren().get(i).getNodeType() == 2) { //min
                        node.getChildren().get(i).updateLowerAndUpperBounds(node.getChildren().get(i).getLowerBound(), leaf_value);
                        double new_upperBound = 0;

                        for (int l = 0; l < number_of_branches; l++) {
                            new_upperBound += node.getChildren().get(i).getUpperBound() * node.getChildren().get(i).getProbability();
                            node.updateLowerAndUpperBounds(node.getLowerBound(), new_upperBound);
                        }

                    } else {
                        if (!(node.isWithinBounds(alpha, beta))) {
                            node.delete();
                            break;
                        }
                    }
                }
            }
        }
    }

    public double expectiminimax(TreeNode node, int depth) {
        double a;
        if (!node.hasChildren()) {
            node.createChildren();
        }

        if (depth == 0 || !node.hasChildren()) {
            return node.getValue();
        } else if (node.getNodeType() == 2) {
            a = Double.MAX_VALUE;
            for (TreeNode children : node.getChildren()) {
                a = Math.min(a, expectiminimax(children, depth - 1));
            }
        } else if (node.getNodeType() == 1) {
            a = Double.MIN_VALUE;
            for (TreeNode children : node.getChildren()) {
                a = Math.max(a, expectiminimax(children, depth - 1));
            }
        } else {
            star2(node, depth);
            a = 0;
            for (TreeNode children : node.getChildren()) {
                a = a + (children.getProbability() * expectiminimax(children, depth - 1));
            }
        }
        node.setValue(a);
        return a;
    }
}
