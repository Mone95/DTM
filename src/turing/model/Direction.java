package turing.model;

/**
 * An enum to represent the directions the heads of the tapes might move into
 * and compare them to each other.
 */
public enum Direction {
    /**
     * Decrease the position of the head.
     */
    LEFT,
    
    /**
     * Do not change the position of the head.
     */
    NOMOVE,
    
    /**
     * Increase the position of the head.
     */
    RIGHT;
    
    /**
     * Compares two directions by using the following order: {@code LEFT} < 
     * {@code NOMOVE} < {@code RIGHT}. Returns {@code -1} if {@code this} < 
     * {@code otherDirection}, {@code 0} if they are equal and {@code 1} if
     * {@code this} > {@code otherDirection}.
     * 
     * @param otherDirection The direction this one is compared to.
     * @return whether this is less, equal to or greater than the other.
     */
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

    /**
     * Returns a String representation of {@code this}, giving {@code -1} for
     * {@code LEFT}, {@code 0} for {@code NOMOVE} and {@code 1} for 
     * {@code RIGHT}.
     * 
     * @return A textual representation of this direction.
     */
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