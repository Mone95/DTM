package turing.model;

public enum Direction {
    LEFT, NOMOVE, RIGHT;
    
    @Override
    public String toString () {
        if (this == LEFT) {
            return "-1";
        }
        if (this == RIGHT) {
            return "+1";
        }
        return "0";
    }
}
