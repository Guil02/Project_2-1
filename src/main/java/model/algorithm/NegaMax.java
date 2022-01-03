package model.algorithm;

import controller.GameRunner;

public class NegaMax {
    private final double L;
    private final double U;
    public NegaMax(double L, double U) {
        this.L = L;
        this.U = U;
    }

    public double negaMax(TreeNode node, int depth, int color, int maxDepth){
        node.createChildren();
        if(depth==0||!node.hasChildren()){
            return node.getValue()*color;
        }
        double value = Double.NEGATIVE_INFINITY;
        for(int i =0; i<node.getChildren().size(); i++){
            value = Math.max(value, -negaMax(node.getChildren().get(i),depth-1,-color, maxDepth));

            if(depth<maxDepth&&!GameRunner.DEBUG){
                node.getChildren().set(i,null);
            }
        }
        node.setValue(value);
        return value;
    }

    public static int callsNega = 0;
    public double expectiNegaMax(TreeNode node, int depth, int color, int maxDepth){
        callsNega++;
        node.createChildren();
        if(depth==0||!node.hasChildren()){
            return node.getValue()*color;
        }
        double value;
        if(node.getNodeType()==3){
            value = 0;
        }
        else value = Double.NEGATIVE_INFINITY;
        for(int i =0; i<node.getChildren().size(); i++){
            if(node.getNodeType()==3){
                double probability = node.getChildren().get(i).getProbability();
                double childValue = expectiNegaMax(node.getChildren().get(i), depth - 1, color, maxDepth);
                value = value + (probability * childValue);
            }
            else{
                value = Math.max(value, -expectiNegaMax(node.getChildren().get(i),depth-1,-color, maxDepth));
            }

            if(depth<maxDepth&&!GameRunner.DEBUG){
                node.getChildren().set(i,null);
            }
        }
        node.setValue(value);
        return value;
    }


    public static int callsStar1 = 0;
    public double star1(TreeNode node, double alpha, double beta, int depth, int color, int maxDepth){
        callsStar1++;
        node.createChildren();
        if(depth==0||!node.hasChildren()){
            return node.getValue()*color;
        }
        double value;
        double N = 0, A = 0, B = 0;
        if(node.getNodeType()==3){
            N = node.getChildren().size();
            A = N*(alpha-U)+U;
            B = N*(beta-L)+L;
            value = 0;
        }
        else value = Double.NEGATIVE_INFINITY;
        for(int i =0; i<node.getChildren().size(); i++){
            if(node.getNodeType()==3){
                double AX = Math.max(A, L);
                double BX = Math.min(B, U);
                double v = star1(node.getChildren().get(i), AX, BX, depth-1, color, maxDepth);
                value = value + v;
                if(v <= A){
                    value += U*(N-i);
                    return value/N;
                }
                if(v >=B){
                    value += L*(N-1);
                    return value/N;
                }
                A += U-v;
                B += L-v;
            }
            else{
                value = Math.max(value, -star1(node.getChildren().get(i),alpha, beta,depth-1,-color, maxDepth));
                alpha = Math.max(alpha, value);
                if(alpha>=beta){
                    break;
                }
            }

            if(depth<maxDepth&&!GameRunner.DEBUG){
                node.getChildren().set(i,null);
            }
        }
        if(node.getNodeType()==3){
            node.setValue(value/N);
            return value/N;
        }

        node.setValue(value);
        return value;
    }

    public static int callsStar2 = 0;
    public double star2(TreeNode node, double alpha, double beta, int depth, int color, int maxDepth){
        callsStar2++;
        node.createChildren();
        if(depth==0||!node.hasChildren()){
            return node.getValue()*color;
        }
        double value;
        int N = 0;
        double A = 0, B = 0, AX = 0;
        double[] W = new double[0];
        if(node.getNodeType()==3){
            N = node.getChildren().size();
            A = N*(alpha-U);
            B = N*(beta-L);
            AX = Math.max(A, L);
            W = new double[N];
            value = 0;
        }
        else value = Double.NEGATIVE_INFINITY;

        double vSum = 0;
        for(int i = 0; i<N; i++){
            if(node.getNodeType()==3){
                B+=L;
                double BX  = Math.min(B,U);
                W[i]=probe(node.getChildren().get(i), AX, BX, depth-1, color, maxDepth);
                vSum += W[i];
                if(W[i] >= B){
                    vSum += L * (N-i);
                    return vSum/N;
                }
                B -= W[i];
            }
        }
        for(int i =0; i<node.getChildren().size(); i++){
            if(node.getNodeType()==3){
                A += U;
                B += W[i];
                AX = Math.max(A, L);
                double BX = Math.min(B, U);

                double v = star2(node.getChildren().get(i), AX, BX, depth-1, color, maxDepth);
                value = value + v;
                if(v <= A){
                    value += U*(N-i);
                    return value/N;
                }
                if(v >=B){
                    value += L*(N-1);
                    return value/N;
                }
                A -= v;
                B -= v;
            }
            else{
                value = Math.max(value, -star2(node.getChildren().get(i),alpha, beta,depth-1,-color, maxDepth));
                alpha = Math.max(alpha, value);
                if(alpha>=beta){
                    break;
                }
            }

            if(depth<maxDepth &&!GameRunner.DEBUG){
                node.getChildren().set(i,null);
            }
        }
        if(node.getNodeType()==3){
            node.setValue(value/N);
            return value/N;
        }

        node.setValue(value);
        return value;
    }

    public double probe(TreeNode node, double AX, double BX, int depth, int color, int maxDepth){
        node.createChildren();
        if(depth == 0 || !node.hasChildren()){
            return node.getValue();
        }

        int choice = pickSuccesor(node);

        return star2(node.getChildren().get(choice), AX, BX, depth-1, -color, maxDepth);
    }

    public int pickSuccesor(TreeNode node){
        int choice = 0;
        int N = node.getChildren().size();
        if(N<2){
            return choice;
        }
        else{
            for (int i = 0; i<N; i++){
                if(node.hasBestQuality()){
                    return i;
                }
                else if(node.hasGoodQuality()){
                    choice = i;
                }
            }
        }
        return choice;
    }

}
