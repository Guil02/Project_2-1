package model.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegaMaxTest {

    /**
     * The tree used in this test was retrieved from the sampleExam3 from the course reasoning techniques.
     *
     */
    @Test
    public void testNormalTreeWithoutChance(){
        final double expected = 3;
        NegaMax negaMax = new NegaMax(0,0);
        TreeNode node = new TreeNode(0, null, 1, 1);
        int j = 0;
        while(j<2){
            node.addChild(new TreeNode(0,node,2,1.0/2));
            j++;
        }
        int index = 0;
        double[] values = {6,2,8,3,-3,-1,4,4,2,32,4,2,5,4,-3,-3};
        for(int i = 0; i<node.getChildren().size(); i++){
            TreeNode temp = node.getChildren().get(i);
            int t = 0;
            while(t<2){
                TreeNode node1 = new TreeNode(0, temp, 1, 1.0 / 2);
                temp.addChild(node1);
                int t2 = 0;
                while(t2<2){
                    TreeNode node2 = new TreeNode(0, node1, 2, 1.0 / 2);
                    node1.addChild(node2);
                    int t3 = 0;
                    while(t3<2){
                        node2.addChild(new TreeNode(values[index++], node2,1,1.0/2));
                        t3++;
                    }
                    t2++;
                }
                t++;
            }
        }
        double actual = negaMax.negaMax(node, 5, 1, 5);
        System.out.println("Value found: "+ actual);

        assertEquals(expected, actual);
    }

    /**
     * The tree used here was self invented.
     */
    @Test
    public void testTreeWithChance(){
        final double expected = 4;
        NegaMax negaMax = new NegaMax(0,0);
        TreeNode node = new TreeNode(0, null, 1, 1);
        int t = 0;
        int index = 0;
        double[] value = {2,3,0,8};
        while(t<2){
            TreeNode node1 = new TreeNode(0, node, 3, 1.0 / 2);
            node.addChild(node1);
            int t2 = 0;
            while(t2<2){
                node1.addChild(new TreeNode(value[index++],node1, 2,1.0/2));
                t2++;
            }
            t++;
        }

        double actual = negaMax.expectiNegaMax(node, 4, 1, 4);
        System.out.println("Value found: "+ actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testStar1Pruning(){
        final double expected = 4;
        NegaMax negaMax = new NegaMax(-100,100);
        TreeNode node = new TreeNode(0, null, 1, 1);
        int t = 0;
        int index = 0;
        double[] value = {2,3,0,8};
        while(t<2){
            TreeNode node1 = new TreeNode(0, node, 3, 1.0 / 2);
            node.addChild(node1);
            int t2 = 0;
            while(t2<2){
                node1.addChild(new TreeNode(value[index++],node1, 2,1.0/2));
                t2++;
            }
            t++;
        }

        double actual = negaMax.star1(node, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 4, 1, 4);
        System.out.println("Value found: "+ actual);
    }


    @Test
    public void testStar2Pruning(){
        final double expected = 4;
        NegaMax negaMax = new NegaMax(-100,100);
        TreeNode node = new TreeNode(0, null, 1, 1);
        int t = 0;
        int index = 0;
        double[] value = {2,3,0,8};
        while(t<2){
            TreeNode node1 = new TreeNode(0, node, 3, 1.0 / 2);
            node.addChild(node1);
            int t2 = 0;
            while(t2<2){
                node1.addChild(new TreeNode(value[index++],node1, 2,1.0/2));
                t2++;
            }
            t++;
        }

        double actual = negaMax.star2(node, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 4, 1, 4);
        System.out.println("Value found: "+ actual);
        System.out.println("Calls to negamax: "+NegaMax.callsNega);
        System.out.println("Calls to star1: "+NegaMax.callsStar1);
        System.out.println("Calls to star2: "+NegaMax.callsStar2);
    }

    @Test
    public void star2Pruning2(){
        NegaMax negaMax = new NegaMax(-10,10);
        TreeNode node = new TreeNode(0, null, 1, 1);
        TreeNode chance = new TreeNode(0, node, 3, 1);
        node.addChild(chance);
        int T = 0;
        int index = 0;
        double[] values = {-5,5,-10,-9,0,5,1,3,-10,-1,2,5,4,3};
        int[] childrenPerNode = {2,2,2,2,3,3};
        while(T<6){
            TreeNode node1 = new TreeNode(0, chance, 2, 1.0 / 6);
            chance.addChild(node1);
            double T2 = 0;
            while(T2<childrenPerNode[T]){
                node1.addChild(new TreeNode(values[index++], node1, 3, 1.0/childrenPerNode[T]));
                T2++;
            }
            T++;
        }
        double actual = negaMax.star2(node, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 4,1,4);
        System.out.println("Value found: "+actual);
    }
}