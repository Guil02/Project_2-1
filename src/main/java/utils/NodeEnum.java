package utils;

public enum NodeEnum {
    SEARCH_NODE(0),
    NN_NODE(1),
    TD_NODE(2),
    CHEAT_NODE(3),
    GA_NODE(4),
    MAX_NODE(1),
    MIN_NODE(2),
    CHANCE_NODE(3);

    private final int id;

    NodeEnum(int id){
        this.id = id;
    }
    public int getId(){return id;}
}
