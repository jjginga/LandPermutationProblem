package com.jjginga.state;

import java.util.*;

/**
 * Implements the IState interface for representing a map of lands. Each state
 * is a configuration of lands with specific borders. This class is used in
 * search algorithms to navigate through the space of possible land configurations.
 */
public class LandMap implements IState {
    private final int[][] landMap;//matrix that represents the map configuration
    private int borders;//number of borders of current configuration
    private int depth;//depth of the configuration in the search space

    /**
     * Constructs a LandMap state from a given map configuration. Automatically
     * evaluates the number of borders and sets the depth to 0.
     *
     * @param landMap The initial map configuration.
     */
    public LandMap(int[][] landMap) {
        this.landMap = landMap;
        evaluate();
        depth = 0;
    }

    /**
     * Constructs a LandMap state with a specified depth, used when generating successors.
     *
     * @param landMap The map configuration.
     * @param depth The depth of the new state.
     */
    public LandMap(int[][] landMap, int depth) {
        this.landMap = landMap;
        evaluate();
        this.depth = depth;
    }

    /**
     * Evaluates the current state, calculating the number of borders based on the
     * land configuration. This method updates the 'borders' field.
     */
    public void evaluate() {
        this.borders = countBorders();
    }

    /**
     * Evaluates the current state, calculating the number of borders based on the
     * land configuration. This method updates the 'borders' field.
     */
    private int countBorders() {
        int borders = 0;
        for(int row = 0; row < landMap.length; row++)
            for (int col = 0; col < landMap[row].length; col++) {
                if (col<landMap[row].length - 1 && landMap[row][col] != landMap[row][col + 1])
                    borders++;
                if (row < landMap.length -1 && landMap[row][col] != landMap[row + 1][col])
                    borders++;
            }
        return borders;
    }

    public int getObjectiveValue() {
        return borders;
    }

    /**
     * Generates successors by attempting to swap adjacent territories to see if
     * it results in a configuration with fewer borders. Each successful swap results
     * in a new successor state being added to the list.
     *
     * @return A list of successor states.
     */
    public List<IState> generateSuccessors() {
        List<IState> successors = new LinkedList<>();

        //attempts to swap each cell with its right and bottom neighbors, if such a swap
        //is valid (i.e., it does not increase the number of borders), then the new state
        //is added to the list of successors.
        for(int row = 0; row < landMap.length; row++){
            for(int col = 0; col < landMap[row].length; col++){
                //we evaluate the swap with the neighbour to the right
                if(col < landMap[row].length - 1 && landMap[row][col] != landMap[row][col+1]){
                    //we try the swap to evaluate if it is valid
                    if(trySwap(row, col, row, col + 1)){
                        //if is valid we clone and add to successors
                        successors.add(new LandMap(cloneMap(), this.getDepth() + 1));
                        //we revert the swap to original state
                        revertSwap(row, col, row, col + 1);
                    }
                }
                if(row < landMap.length - 1 && landMap[row][col] != landMap[row + 1][col]){
                    //we try the swap to evaluate if it is valid
                    if(trySwap(row, col, row + 1, col)){
                        //if is valid we clone and add to successors
                        successors.add(new LandMap(cloneMap(), this.getDepth() + 1));
                        //we revert the swap to original state
                        revertSwap(row, col, row + 1, col);
                    }
                }
            }
        }

        return successors;
    }

    /**
    * Attempts a swap between two adjacent territories and evaluates whether this swap
    * results in a valid new state. It is considered valid if it does not increase the
    * number of borders.
    *
    * @param row1 Row index of the first cell.
    * @param col1 Column index of the first cell.
    * @param row2 Row index of the second cell.
    * @param col2 Column index of the second cell.
    * @return True if the swap results in a valid new state, otherwise false.
    * The swap is temporary and is meant to be evaluated for its impact on the objective value.
    */
    private boolean trySwap(int row1, int col1, int row2, int col2){
        //we do the swap
        int tmp = landMap[row1][col1];
        landMap[row1][col1] = landMap[row2][col2];
        landMap[row2][col2] = tmp;

        //we count the borders after the swap
        int newBorders = countBorders();

        //if the number of borders increases, revert the swap and return false.
        if(newBorders>this.borders){
            revertSwap(row1, col1, row2, col2);
            return false;
        }

        //if the swap doesn't increase borders it is considered valid
        return true;
    }

    /**
     * Reverts a swap between two cells to restore the original configuration.
     * This method is used after a swap is evaluated for its validity.
     *
     * @param row1 Row index of the first cell.
     * @param col1 Column index of the first cell.
     * @param row2 Row index of the second cell.
     * @param col2 Column index of the second cell.
     */
    private void revertSwap(int row1, int col1, int row2, int col2) {
        //we simply revert the swap
        int tmp = landMap[row1][col1];
        landMap[row1][col1] = landMap[row2][col2];
        landMap[row2][col2] = tmp;
    }

    /**
     * Creates a deep copy of the current land map configuration.
     * This method is used when a valid new state is found, to create a new LandMap instance for the successor.
     *
     * @return A new 2D array that is a deep copy of the current land map configuration.
     */
    public int[][] cloneMap() {
        return Arrays.stream(this.landMap)
                     .map(int[]::clone)
                     .toArray(int[][]::new);
    }

    /**
     * Retrieves the depth of the current state in the search space.
     * The depth indicates how many steps have been taken from the initial state.
     *
     * @return The depth of the state.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Checks if this LandMap is equal to another object.
     * Two LandMaps are considered equal if their configurations (landMap arrays) are deeply equal.
     *
     * @param o The object to compare with this LandMap.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandMap)) return false;
        LandMap landMap = (LandMap) o;
        return Arrays.deepEquals(this.landMap, landMap.landMap);
    }

    /**
     * Generates a hash code for this LandMap.
     * The hash code is based on the deep contents of the landMap array.
     *
     * @return A hash code for this LandMap.
     * */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(landMap);
    }

}