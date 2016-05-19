package turing.model;

public enum Direction {
    LEFT, NOMOVE, RIGHT;

    int compareDirections(Direction otherDirection) {
        switch (this) {
        case LEFT:
            if (otherDirection == LEFT) {
                return 0;
            }
            return -1;
        case NOMOVE:
            if (otherDirection == LEFT) {
                return 1;
            }
            if (otherDirection == RIGHT) {
                return -1;
            }
            return 0;
        case RIGHT:
            if (otherDirection == RIGHT) {
                return 0;
            }
            return 1;
        default:
            return 0;
        }
    }

    @Override
    public String toString() {
        if (this == LEFT) {
            return "-1";
        }
        if (this == RIGHT) {
            return "+1";
        }
        return "0";
    }
}