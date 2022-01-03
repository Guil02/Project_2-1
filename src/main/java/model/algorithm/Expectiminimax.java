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



    public double star2(TreeNode node, double alpha, double beta, int depth, int color, int maxDepth){
        node.createChildren();
        if(!node.hasChildren()||depth==0){
            return node.getValue();
        }
        double L = -100;
        double U = 100;
        int N = node.getChildren().size();
        double A = N * (alpha-U);
        double B = N * (beta-L);
        double AX = Math.max(A,L);

        /* Probing phase */
        double[] w = new double[N];
        double vsum = 0;
        for(int i = 0; i<N; i++){
            B+=L;
            double BX = Math.min(B, U);
            w[i]= probe_Min(node.getChildren().get(i), AX, BX, depth-1, -color, maxDepth);
            vsum+=w[i];
            if(w[i]>=B){
                vsum += L*(N-i);
                if(depth<maxDepth){
                    for(int j = 0; j<node.getChildren().size(); j++){
                        node.getChildren().set(j,null);
                    }
                }
                return (vsum/N);
            }
            B -= w[i];
        }

        /* Search phase */
        vsum = 0;
        for(int i = 0; i<N; i++){
            A+=U;
            B+=w[i];
            AX = Math.max(A, L);
            double BX = Math.min(B,U);
            double v = alphaBeta(node.getChildren().get(i), AX, BX, depth-1, -color, maxDepth);
            vsum += v;
            if(v<=A){
                vsum+= U*(N-i);
                if(depth<maxDepth){
                    for(int j = 0; j<node.getChildren().size(); j++){
                        node.getChildren().set(j,null);
                    }
                }
                return (vsum/N);
            }
            if(v>=B){
                vsum += L*(N-1);
                if(depth<maxDepth){
                    for(int j = 0; j<node.getChildren().size(); j++){
                        node.getChildren().set(j,null);
                    }
                }
                return (vsum/N);
            }
            A -= v;
            B -= v;
        }
        if(depth<maxDepth){
            for(int j = 0; j<node.getChildren().size(); j++){
                node.getChildren().set(j,null);
            }
        }
        return (vsum/N);
    }

    public double probe_Min(TreeNode node, double alpha, double beta, int depth, int color, int maxDepth){
        node.createChildren();
        if(!node.hasChildren()||depth==0){
            return node.getValue();
        }
        int choice = pickSuccessor(node);
        return alphaBeta(node.getChildren().get(choice), alpha, beta, depth-1, -color, maxDepth);
    }

    public int pickSuccessor(TreeNode node){
        int choice = 0;
        int N = node.getChildren().size();
        if(N<2) {
            return choice;
        }
        else{
            for(int i = 0; i<N; i++){
                if(node.getChildren().get(i).hasBestQuality()){
                    return choice;
                }
                else if(node.getChildren().get(i).hasGoodQuality()){
                    choice = i;
                }
            }
        }
        return choice;
    }


    public double alphaBeta(TreeNode node, double alpha, double beta, int depth, int color, int maxDepth){
        node.createChildren();
        if(!node.hasChildren()||depth==0){
            return color*node.getValue();
        }

        double value = Double.MIN_VALUE;
        for(TreeNode child : node.getChildren()){
            value = Math.max(value, -alphaBeta(child, -beta, -alpha, depth-1, -color, maxDepth));
            alpha = Math.max(alpha, value);
            if(alpha >= beta){
                break;
            }
        }
        if(depth<maxDepth){
            for(int j = 0; j<node.getChildren().size(); j++){
                node.getChildren().set(j,null);
            }
        }
        return value;
    }
}